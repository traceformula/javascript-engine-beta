/**
 * Traverse an AST to generate Java code.
 *
 */

package ts.tree.visit;

import ts.Message;
import ts.Main;
import ts.tree.*;

import java.util.ArrayList;
import java.util.List;
import ts.support.TSRuntimeException;
import ts.support.TSUncaughtException;
import ts.support.TSReferenceException;
import ts.support.TSFunctionObject;

/**
 * Does a traversal of the AST to generate Java code to execute the program
 * represented by the AST.
 * <p>
 * Uses a static nested class, Encode.ReturnValue, for the type parameter.
 * This class contains two String fields: one for the temporary variable
 * containing the result of executing code for an AST node; one for the
 * code generated for the AST node.
 * <p>
 * The "visit" method is overloaded for each tree node type.
 */
public final class Encode extends TreeVisitorBase<Encode.ReturnValue>
{
  /**
   * Static nested class to represent the return value of the Encode methods.
   * <p>
   * Contains the following fields:
   * <p>
   * <ul>
   * <li> a String containing the result operand name<p>
   * <li> a String containing the code to be generated<p>
   * </ul>
   * Only expressions generate results, so the result operand name
   * will be null in other cases, such as statements.
   */
  static public class ReturnValue
  {
    public String result;

    public String code;

    // initialize both fields
    private ReturnValue()
    {
      result = null;
      code = null;
    }

    // for non-expressions
    public ReturnValue(final String code)
    {
      this();
      this.code = code;
    }

    // for most expressions
    public ReturnValue(final String result, final String code)
    {
      this();
      this.result = result;
      this.code = code;
    }
  }

  // simple counter for expression temps
  private int nextTemp = 0;

  // by default start output indented 2 spaces and increment
  // indentation by 2 spaces
  public Encode()
  {
    this(2, 2);
  }

  // initial indentation value
  private final int initialIndentation;

  // current indentation amount
  private int indentation;

  // how much to increment the indentation by at each level
  // using an increment of zero would mean no indentation
  private final int increment;

  // increase indentation by one level
  private void increaseIndentation()
  {
    indentation += increment;
  }

  // decrease indentation by one level
  private void decreaseIndentation()
  {
    indentation -= increment;
  }

  public Encode(final int initialIndentation, final int increment)
  {
    // setup indentation
    this.initialIndentation = initialIndentation;
    this.indentation = initialIndentation;
    this.increment = increment;
  }

  // generate a string of spaces for current indentation level
  private String indent()
  {
    String ret = "";
    for (int i = 0; i < indentation; i++)
    {
      ret += " ";
    }
    return ret;
  }

  // generate main method signature
  public String mainMethodSignature()
  {
    return "public static void main(String args[])";
  }

  // generate and return prologue code for the main method body
  public String mainPrologue(String filename)
  {
    String ret = "";
    ret += indent() + "{\n";
    increaseIndentation();
    ret += indent() + "TSLexicalEnvironment " + "lexEnviron" + " = " +
      "TSLexicalEnvironment.newDeclarativeEnvironment(null);\n" +
      "lexEnviron.declareVariable(TSString.create(\"undefined\"), false);\n";
    ret += indent() + "try {\n";
    increaseIndentation();
    return ret;
  }

  // generate and return epilogue code for main method body
  public String mainEpilogue()
  {
    decreaseIndentation();
    String ret = "";
    ret += "}catch (TSRuntimeException ex) { Message.executionError(ex.toString()); }\n";
    decreaseIndentation();
    ret += indent() + "}";
    return ret;
  }

  // return string for name of next expression temp
  private String getTemp()
  {
    String ret = "temp" + nextTemp;
    nextTemp += 1;
    return ret;
  }

  // visit a list of ASTs and generate code for each of them in order
  // use wildcard for generality: list of Statements, list of Expressions, etc
  public List<Encode.ReturnValue> visitEach(final Iterable<?> nodes)
  {
    List<Encode.ReturnValue> ret = new ArrayList<Encode.ReturnValue>();

    for (final Object node : nodes)
    {
      ret.add(visitNode((Tree) node));
    }
    return ret;
  }

  //@Update 20150128
  //get and return code for unary operator
  public Encode.ReturnValue visit(final UnaryOperator unaryOperator){
    String result = getTemp();
    String methodName = getMethodNameForUnaryOperator(unaryOperator);
    
    Encode.ReturnValue v = visitNode(unaryOperator.getExpression());
    String code = v.code;
    code += indent() + "TSValue " + result + " = TSValue." + methodName + "("
      + v.result + ");\n";
    
    return new Encode.ReturnValue(result, code);
  }
  //support to get unary operators
  //TODO: if necessary, add ++, --, -
  private static String getMethodNameForUnaryOperator(
    final UnaryOperator opNode){
    final Unop op = opNode.getOp();
    switch(op){
      case NOT: return "not";
      case NEG: return "neg";
      default:
        assert false: "unexpected operator: " + opNode.getOpString();
    }
    return null;
  }
  public Encode.ReturnValue visit(final BooleanLiteral booleanLiteral)
  {
    String result = getTemp();
    String code = indent() + "TSValue " + result + " = TSBoolean.create(" + booleanLiteral.getValue() + ");\n";

    return new Encode.ReturnValue(result, code);
  }
  public Encode.ReturnValue visit(final StringLiteral stringLiteral)
  {
    String result = getTemp();
    String code = indent() + "TSValue " + result + " = TSString.create" +
      "(\"" + stringLiteral.getValue() + "\");\n";
    return new Encode.ReturnValue(result, code);
  }
  public Encode.ReturnValue visit(final NullLiteral nullLiteral)
  {
    String result = getTemp();
    String code = indent() + "TSValue " + result + " = TSNull.value;\n";
    return new Encode.ReturnValue(result, code);
  }


  // gen and return code for a binary operator
  public Encode.ReturnValue visit(final BinaryOperator binaryOperator)
  {
    String result = getTemp();

    Encode.ReturnValue leftReturnValue = visitNode(binaryOperator.getLeft());
    String code = leftReturnValue.code;

    Encode.ReturnValue rightReturnValue = visitNode(binaryOperator.getRight());
    code += rightReturnValue.code;

    String methodName = getMethodNameForBinaryOperator(binaryOperator);
    code += indent() + "TSValue " + result + " = " + leftReturnValue.result +
      "." + methodName + "(" + rightReturnValue.result + ");\n";
    return new Encode.ReturnValue(result, code);
  }

  // support routine for handling binary operators
  private static String getMethodNameForBinaryOperator(
    final BinaryOperator opNode)
  {
    final Binop op = opNode.getOp();

    switch (op) {
      case ADD:
        return "add";
      case SUB:
        return "sub";
      case ASSIGN:
        return "simpleAssignment";
      case MULTIPLY:
        return "multiply";
      case DIVIDE:
        return "divide";
      case EQ:
        return "eq";
      case LT:
        return "lt";
      case GT:
        return "gt";
      default:
        assert false: "unexpected operator: " + opNode.getOpString();
    }
    // cannot reach
    return null;
  }

  // process an expression statement
  public Encode.ReturnValue visit(final ExpressionStatement
    expressionStatement)
  {
    Encode.ReturnValue exp = visitNode(expressionStatement.getExp());
    String code = indent() + "Message.setLineNumber(" +
      expressionStatement.getLineNumber() + ");\n";
    code += exp.code;
    return new Encode.ReturnValue(code);
  }

  public Encode.ReturnValue visit(final Identifier identifier)
  {
    String result = getTemp();
    String code = indent() + "TSValue " + result +
      " = " + "lexEnviron" +
      ".getIdentifierReference(TSString.create(\"" +
      identifier.getName() + "\"));\n";

    return new Encode.ReturnValue(result, code);
  }

  public Encode.ReturnValue visit(final NumericLiteral numericLiteral)
  {
    String result = getTemp();
    String code = indent() + "TSValue " + result + " = " + "TSNumber.create" +
      "(" + numericLiteral.getValue() + ");\n";

    return new Encode.ReturnValue(result, code);
  }

  public Encode.ReturnValue visit(final PrintStatement printStatement)
  {
    Encode.ReturnValue exp = visitNode(printStatement.getExp());
    String code = indent() + "Message.setLineNumber(" +
      printStatement.getLineNumber() + ");\n";
    code += exp.code;
    code += indent() + "System.out.println(" + exp.result +
      ".toStr().getInternal());\n";
    return new Encode.ReturnValue(code);
  }

  public Encode.ReturnValue visit(final VarStatement varStatement)
  {
    //@Update 20150204
    List<Encode.ReturnValue> exp = visitEach(varStatement.getExps());
    String code = indent() + "Message.setLineNumber(" +
      varStatement.getLineNumber() + ");\n";
    for(String name: varStatement.getNames())
    code += indent() + "lexEnviron.declareVariable(TSString.create" +
      "(\"" + name + "\"), false);\n";
    for(Encode.ReturnValue r: exp)
      code += r.code;
    return new Encode.ReturnValue(code);
  }

  public Encode.ReturnValue visit(final BlockStatement blockStatement) {
    List<Statement> stmtList = blockStatement.getStatementList();
    String code = indent() + "Message.setLineNumber(" +
      blockStatement.getLineNumber() + ");\n";

    if (stmtList != null && stmtList.size() > 0
        && blockStatement.getLabels() != null) {
      for (String label : blockStatement.getLabels()) {
        for (Statement stmt : stmtList) {
          stmt.addLabel(label);
        }
      }
    }

    if (blockStatement.getStatementList() == null
        || blockStatement.getStatementList().size() == 0) {
    } else if (blockStatement.getStatementList() == null
        || blockStatement.getStatementList().size() > 0) {
      for(Statement stmt : stmtList){
        Encode.ReturnValue val = visitNode(stmt);
        code += val.code;
      }
    }
    return new Encode.ReturnValue(code);
  }

  public Encode.ReturnValue visit(final EmptyStatement emptyStatement) {
    return new Encode.ReturnValue("");
  }

  public Encode.ReturnValue visit(final WhileStatement whileStatement) {

    WhileStatement.count++; //increment to mark it is in while loop
    String code = indent() + "Message.setLineNumber(" +
      whileStatement.getLineNumber() + ");\n";

    if (whileStatement.getStmt() != null
        && whileStatement.getLabels() != null) {
      for (String label : whileStatement.getLabels()) {
        whileStatement.getStmt().addLabel(label);
      }
    }
    
    
    code += indent() + "while (true){\n";
    Encode.ReturnValue val = visitNode(whileStatement.getExp());
    increaseIndentation();
    code += indent() + val.code + "\n";
    if(val.result!=null) code += indent() + "if (!" + val.result + ".toNumber().toBoolean().getBool()) break;\n";
    decreaseIndentation();
    if(whileStatement.getStmt() != null) {
      increaseIndentation();
      val = visitNode(whileStatement.getStmt());
      code += indent() + val.code ;
      decreaseIndentation();
    }
    code += indent() + "}\n";

    WhileStatement.count--; //decrement to mark it existing while loop
    return new Encode.ReturnValue(code);
  }

  public Encode.ReturnValue visit(final IfStatement ifStatement) {
    String code = indent() + "Message.setLineNumber(" +
      ifStatement.getLineNumber() + ");\n";
    Encode.ReturnValue val = visitNode(ifStatement.getExp());

    code += val.code + "\n";
    if(val.result!=null) {
      code += indent() + "if (" + val.result + ".toNumber().toBoolean().getBool()==true) {\n";
      if(ifStatement.getIfStmt()!=null){
        val = visitNode(ifStatement.getIfStmt());
        increaseIndentation();
        code += indent() + val.code;
        decreaseIndentation();
      }
      code += "}\n";
      if(ifStatement.getElseStatement()!=null){
        code += indent() + "else {\n";
        val = visitNode(ifStatement.getElseStatement());
        increaseIndentation();
        code += indent() + val.code;
        decreaseIndentation();
        code += indent() + "}\n";
      }
    }
    return new Encode.ReturnValue(code);
  }

  public Encode.ReturnValue visit(final BreakStatement breakStatement) {
    String code = indent() + "Message.setLineNumber(" +
      breakStatement.getLineNumber() + ");\n" ;
    
    if(WhileStatement.count == 0){
      code += indent() + "if(true) throw new TSRuntimeException(" +
        "TSString.create(\"Break must be in while loop.\"));\n";
    }else {
      code += indent() + "if(true) break;\n";
    }
    
    return new Encode.ReturnValue(code);
  }

  public Encode.ReturnValue visit(final ContinueStatement continueStatement) {
    String code = indent() + "Message.setLineNumber(" +
      continueStatement.getLineNumber() + ");\n;";

    if(WhileStatement.count == 0){
      code += indent() + "if(true) throw new TSRuntimeException(" +
        "TSString.create(\"continue must be in while loop.\"));\n";
    }else {
      code += indent() + " continue;\n";
    }

    return new Encode.ReturnValue(code);
  }

  public Encode.ReturnValue visit(final ThrowStatement throwStatement) {
    String code = indent() + "Message.setLineNumber(" +
      throwStatement.getLineNumber() + ");\n";
    Encode.ReturnValue val = visitNode(throwStatement.getExp());
    code += indent() + val.code +";\n";
    if(TryStatement.count == 0){
      code += indent() + "if(true) throw new TSUncaughtException("
        + val.result + ".getValue());\n";
      return new Encode.ReturnValue(code);
    }
    code += indent() + "if(true) throw new TSRuntimeException(" 
      + val.result + ".getValue());\n";
    return new Encode.ReturnValue(code);
  }

  public Encode.ReturnValue visit(final TryStatement tryStatement) {
    TryStatement.count++; //mark it is in a try catch
    String code = indent() + "Message.setLineNumber(" +
      tryStatement.getLineNumber() + ");\n";
    Encode.ReturnValue val = visitNode(tryStatement.getBlockStmt());
    code += indent() + "try {\n";
    increaseIndentation();
    code += indent() + val.code;
    decreaseIndentation();
    code += "}\n";
    if(tryStatement.getCatchStmt()!= null){
      CatchStatement cStmt = (CatchStatement) tryStatement.getCatchStmt();
      val = visitNode(cStmt);
      code += indent() + "catch (TSRuntimeException ex_" + cStmt.getIdent() + ") {\n";
      increaseIndentation();
      code += indent() + val.code;
      decreaseIndentation();
      code += indent() + "}\n";
    }
    if (tryStatement.getFinallyStmt() != null) {
      FinallyStatement fStmt = (FinallyStatement) tryStatement.getFinallyStmt();
      val = visitNode(fStmt);
      code += indent() + "finally {\n";
      increaseIndentation();
      code += indent() + val.code;
      decreaseIndentation();
      code += "}\n";      
    }
    TryStatement.count--; //mark just existed a try/catch
    
    return new Encode.ReturnValue(code);
  }

  public Encode.ReturnValue visit(final CatchStatement catchStatement) {
      String code = indent() + "Message.setLineNumber(" +
        catchStatement.getLineNumber() + ");\n";
      Encode.ReturnValue val = visitNode(catchStatement.getStmt());
      
      code += indent() + "TSLexicalEnvironment " + "oldEnv" + " = lexEnviron;\n" ;
      code += indent() + "lexEnviron = " + "TSLexicalEnvironment.newDeclarativeEnvironment(oldEnv);\n";
     
      code += indent() +   "lexEnviron.declareParameter(TSString.create(\"" + catchStatement.getIdent() 
        + "\").getInternal(), ex_" +  catchStatement.getIdent() + ".getInternal());\n";
      code += indent() + val.code;
      code += indent() + "lexEnviron = oldEnv;\n";
      return new Encode.ReturnValue(code);
    }

  public Encode.ReturnValue visit(final FinallyStatement fStatement) {
    String code = indent() + "Message.setLineNumber(" +
        fStatement.getLineNumber() + ");\n";
    Encode.ReturnValue val = visitNode(fStatement.getStmt());
    code += indent() + val.code;
    return new Encode.ReturnValue(code);
  }

  public Encode.ReturnValue visit(final FunctionExpression functionExpression) {

    String code = indent() + "Message.setLineNumber(" +
        functionExpression.getLineNumber() + ");\n";
    Encode.ReturnValue val = visitNode(functionExpression.getFunctionBody());
    String result = getTemp();

    String newEnv = getTemp();

    //declare a new environment
    code += indent() + "TSLexicalEnvironment " + newEnv + 
      " = TSLexicalEnvironment.newDeclarativeEnvironment(lexEnviron);\n";
    code += indent() + "TSFunctionObject " + result + " = " +
      "new TSFunctionObject ();\n";
    
    String vcode = val.code;
    String bodyClassName = TSFunctionObject.createBody(vcode, 
      functionExpression.getFormalParameterList());

    /*vcode = vcode.replaceAll("\n", "");
    vcode = vcode.replace("\\", "\\\\");
    vcode = vcode.replace("\"", "\\\"");
    code += indent() + "System.out.println(\"" + vcode + "\");\n";*/

    String bodyTemp = getTemp();
    code += indent() + "TSCode " + bodyTemp + " = new " + bodyClassName + " ();\n";

    //set body for the tsfunctionobject
    code += indent() + result + ".setPrototype(" + bodyTemp + ");\n";

    //set environment for the function object
    if(functionExpression.getFunctionName()!= null && 
      functionExpression.getFunctionName().trim().equals(""))
      code += indent() + newEnv + ".declareFunctionName(\"" + functionExpression.getFunctionName().trim() +
        "\", " + result + ");\n";
    code += indent() + result + ".setLexEnviron(" + newEnv + ");\n";
    
    return new Encode.ReturnValue(result, code);    
  }

  public Encode.ReturnValue visit(final FunctionBody functionBody) {
   
    FunctionBody.count++; //increase to mark it is in functionbody;
    List<Statement> sourceElements = functionBody.getSourceElements();
    String code = indent() + "Message.setLineNumber(" +
        functionBody.getLineNumber() + ");\n";
    boolean returnStatementFound = false;
    if (sourceElements != null && sourceElements.size() > 0) {
      for(Statement s: sourceElements){
        Encode.ReturnValue val = visitNode(s);
        code += indent() + val.code;
        if(s instanceof ReturnStatement) returnStatementFound = true;
      }
    }
    if(!returnStatementFound) code += indent() + "return TSNull.value;\n";

    FunctionBody.count--; //decrease to mark it going to exist function
    return new Encode.ReturnValue(code);
  }

  public Encode.ReturnValue visit(final CallExpression callExpression) {
    String code = indent() + "Message.setLineNumber(" +
        callExpression.getLineNumber() + ");\n";

    String argsTemp = getTemp();
    String args = "TSValue[]  " + argsTemp + " = {";

    for(Expression exp: callExpression.getArguments()){
      Encode.ReturnValue val = visitNode(exp);
      args += val.result + ",";
      code += indent() + val.code + "\n";
    }

    if(callExpression.getArguments() !=null && callExpression.getArguments().size()> 0) {
      args = args.substring(0, args.length()-1);
      args += "};\n";
    }else args = "TSValue[] " + argsTemp + " = new TSValue[0];\n";

    code += indent() + args;
    String result = getTemp();
    
    Encode.ReturnValue val;
    if(callExpression.getCallExp()!= null){
      val = visitNode(callExpression.getCallExp());
    }else{
      val = visitNode(callExpression.getMemberExp());
    }
    code += indent() + val.code + "\n";
    String functionObjectTemp = getTemp();
    code += indent() + "TSFunctionObject " + functionObjectTemp + 
      " = (TSFunctionObject) " + val.result + ".getValue();\n";

    code += indent() + "TSValue " + result + " = " 
      + functionObjectTemp + ".getPrototype().execute(false, null," + argsTemp + 
      "," + functionObjectTemp + ".getLexEnviron());\n";

    return new Encode.ReturnValue(result, code);
  }

  public Encode.ReturnValue visit(final ReturnStatement returnStatement) {
    String code = indent() + "Message.setLineNumber(" +
        returnStatement.getLineNumber() + ");\n";
    if(FunctionBody.count == 0){
      code += indent() + "if(true) throw new TSRuntimeException(" +
        "TSString.create(\"Return must be in function body.\"));\n";
      return new Encode.ReturnValue(code);
    }

    if (returnStatement.getExp() != null) {
      Encode.ReturnValue val = visitNode(returnStatement.getExp());
      code += indent() + val.code;
      code += indent() + "if(true) return " + val.result + ";\n";
      return new Encode.ReturnValue(val.result, code);
    }
    code += indent() + "if(true) return TSUndefined.value;\n";
    return new Encode.ReturnValue(code);
  }
}

