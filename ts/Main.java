/**
 * main for the ts system
 *
 */

package ts;

import ts.parse.*;
import ts.tree.*;
import ts.tree.visit.*;
import ts.support.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.Token;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.Loader;
import javassist.NotFoundException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/** Main class for the Tscript translator. Processes command-line
 *  arguments, initiates the parser and lexer, and then generates
 *  and executes Java bytecode by traversing the AST built by the parser.
 *  <p>
 *
 *  The supported command line arguments are:
 *  <p>
 *
 *  <ul>
 *  <li>-AST: dump the AST as HTML
 *  <p>
 *
 *  <li>-java: dump the generated Java code as Java source code
 *  <p>
 *
 *  <li>-noexec: do not execute the program
 *  <p>
 *
 *  <li>-log: display internal translator log messages
 *  <p>
 *  </ul>
 *
 *  The filename for the program to be translated should be given last
 *  on the command line, and should have a ".ts" extension.
 */
public class Main
{
  /** Tracks compiler version. */
  public static final String version = "2.0";

  private static String determineBaseFileName(String sourceFile)
  {
    // the following require Java 1.7
    Path path = Paths.get(sourceFile);
    String fileName = path.getFileName().toString();
    return fileName.substring(0, fileName.length() - 3);
  }

  // open an output file, when the output file name is based
  // upon the source file name
  private static PrintWriter openOutputFile(String outputClassName,
    String fileExtension)
  {
    String outFile = outputClassName + fileExtension;
    Message.log("output file created: " + outFile);
    try {
      return new PrintWriter(outFile);
    }
    catch (Exception e)
    {
      Message.fatal("problem creating " + outFile + "(" + e.toString() +
        ")");
      // fatal terminates program so this cannot be reached
      return null;
    }
  }

  /** Execution starts here. */
  public static void main(String[] args) throws IOException
  {
    String sourceFile = null;
    boolean doDumpAST = false;
    boolean doDumpJava = false;
    boolean doExec = true;
    ANTLRInputStream reader = null;

    // process the command-line arguments
    //   all options must be first
    //     -log for displaying internal implementation messages
    //     -AST for dumping the AST
    //     -java for dumping generated Java code
    //     -noexec to disable execution of the program
    //   after the options the source file name must be given
    //     source file name must end in ".ts"
    for (String arg: args)
    {
      // source file name must be given last
      if (sourceFile != null)
      {
        Message.usage();
      }

      // handle options, which can be given redundantly
      if (arg.equals("-AST"))
      {
        doDumpAST = true;
      }
      else if (arg.equals("-java"))
      {
        doDumpJava = true;
      }
      else if (arg.equals("-log"))
      {
        Message.enableLogging();
      }
      else if (arg.equals("-noexec"))
      {
        doExec = false;
      }
      // if not a valid option then it is assumed to be the source file name
      else
      {
        sourceFile = arg;
      }
    }
    // source file name must be given and it must end with ".ts"
    if (sourceFile == null || !sourceFile.endsWith(".ts"))
    {
      Message.usage();
    }
    Message.log(sourceFile + ": doDumpAST is " + doDumpAST);
    Message.log(sourceFile + ": doDumpJava is " + doDumpJava);

    String baseFileName = determineBaseFileName(sourceFile);

    // open the source file
    try {
      reader = new ANTLRFileStream(sourceFile);
    }
    catch (Exception e)
    {
      Message.fatal("problem opening " + sourceFile + "(" + e.toString() + ")");
    }

    // create the lexer
    TscriptLexer lexer = new TscriptLexer(reader);
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    // create the parser
    TscriptParser parser = new TscriptParser(tokens);

    // will build my own abstract syntax tree
    parser.setBuildParseTree(false);

    // quit with first syntax error
    parser.setErrorHandler(new BailWithNoMessageErrorStrategy());

    // start the parser
    //   note: the method name is the name of the start symbol in the grammar
    try {
      parser.program();
    }
    catch (ParseCancellationException pce)
    {
      // underlying recognition exception is wrapped in the
      // parse cancellation exception that is thrown by the
      // bail error strategy. Use getCause inherited from
      // Throwable to get the recognition exception.
      RecognitionException re = (RecognitionException) pce.getCause();
      Token errorToken = re.getOffendingToken();
      Location loc = new Location(
        errorToken.getTokenSource().getSourceName(),
        errorToken.getLine(),
        errorToken.getCharPositionInLine()
      );
      Message.syntaxError(loc);   // exits the program
    }

    // a program is a List<Statement>
    // (use unbounded wildcard to avoid unchecked cast)
    List<?> root = (List<?>) parser.getSemanticValue();

    // count the number of statements
    int count = 0;
    for (Object item : root)
    {
      assert (item instanceof Statement) :
        "non-statement in statement list at root of AST";
      count += 1;
    }
    Message.log(String.format("%d statements at parse tree root", count));

    // if requested, dump the AST
    if (doDumpAST)
    {
      // determine dump file name (file.ts --> file.html)
      //   note that the dump file will be written in the current directory
      PrintWriter dumper = openOutputFile(baseFileName, ".html");

      dumper.println("<head>");
      dumper.println("<title> AST for " + sourceFile + "</title>");
      dumper.println("</head>");
      dumper.println("<body>");
      dumper.println("<pre>");
      dumper.println("Program");
      TreeDump treeDump = new TreeDump(dumper, 2, 2);
      for (Object item : root)
      {
        Tree t = (Tree) item;
        t.apply(treeDump);
      }
      dumper.println("</pre>");
      dumper.close();
    }

    // if semantic errors detected, cannot continue to code generation
    if (Message.getErrorCount() != 0)
    {
      Message.fatal("code generation skipped due to semantic errors");
    }

    // if no need to generate code then quit
    if (!doDumpJava && !doExec)
    {
      return;
    }

    // else generate the Java code
    Encode genCode = new Encode();
    String mainCodeString = "";
    mainCodeString += genCode.mainPrologue(sourceFile);
    for (Object item : root)
    {
      Tree t = (Tree) item;
      Encode.ReturnValue returnValue = t.apply(genCode);
      mainCodeString += returnValue.code;
    }
    mainCodeString += genCode.mainEpilogue();
    
    // if requested, output the generated code
    if (doDumpJava)
    {
      // determine Java file name (file.ts --> file.java)
      //   note that the Java file will be written in the current directory
      PrintWriter outputJava = openOutputFile(baseFileName, ".java");
      outputJava.println("import ts.Message;");
      outputJava.println("import ts.support.*;");
      outputJava.println("class " + baseFileName + " {");
      outputJava.println("  " + genCode.mainMethodSignature());
      outputJava.println(mainCodeString);
      outputJava.println("}");
      outputJava.close();
    }

    // if requested, create a class file for the generated code and execute it
    if (doExec)
    {
      // create an empty class in the default class pool
      ClassPool pool = ClassPool.getDefault();
      pool.importPackage("ts.Message");
      pool.importPackage("ts.support");
      //pool.importPackage("java.util.List");
      //pool.importPackage("java.util.ArrayList");
      CtClass theClass = pool.makeClass(baseFileName);

      // now make a main method with an empty body
      String theSignature = genCode.mainMethodSignature() + "{}";
      CtMethod theMethod = null;
      try {
        theMethod = CtNewMethod.make(theSignature, theClass);
      } catch (CannotCompileException ex) {
        Message.fatal("cannot compile the generated main method signature: " +
          ex.getReason());
      }

      // now set the body using the generated code
      String theMethodBody = "";
      theMethodBody += mainCodeString;
      try {
        theMethod.setBody(theMethodBody);
      } catch (CannotCompileException ex) {
        Message.fatal("cannot compile the generated main method body: " +
          ex.getReason());
        /*StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        
        Message.log(sw.toString());*/
      }

      // now add the main method to the class
      try {
        theClass.addMethod(theMethod);
      } catch (CannotCompileException ex) {
        Message.fatal("cannot add the main method into the main class: " +
          ex.getReason());
      }

      // add a default constructor to the class
      try {
        theClass.addConstructor(CtNewConstructor.defaultConstructor(theClass));
      } catch (CannotCompileException ex) {
        Message.fatal("cannot add a default constructor to the main class: " +
          ex.getReason());
      }

      // load the class and execute it
      Loader classLoader = new Loader(pool);
      try {
        classLoader.run(baseFileName, new String[0]);
      } catch (Throwable ex) {
        Message.fatal("uncaught Java exception in execution of generated code");
       /* StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        
        Message.log(sw.toString());*/
        ex.printStackTrace();
      }
    }
  }
}
