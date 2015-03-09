package ts.support;

import java.util.ArrayList;
import java.util.List;

import ts.tree.Statement;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.Modifier;

/**
 * Represent Function Object (<a
 * href="http://www.ecma-international.org/ecma-262/5.1/#sec-15.3">ELS
 * 15.3</a>).
 */
public class TSFunctionObject extends TSObject {

	private String functionName;
	private List<String> formalParameterList = new ArrayList<String>();
	public TSLexicalEnvironment lexEnviron;
	private TSCode prototype;
	private static int counter;

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public List<String> getFormalParameterList() {
		return formalParameterList;
	}

	public void setFormalParameterList(List<String> formalParameterList) {
		this.formalParameterList = formalParameterList;
	}

	public TSCode getPrototype() {
		return prototype;
	}

	public static String createFunctionPrologue(List<String> args){
		String code = "\nlexEnviron = TSLexicalEnvironment.newDeclarativeEnvironment(lexEnviron);\n";
		
		code += "String[] params = new String[" + args.size() + "];\n";
		for(int i = 0; i<args.size(); i++){
			code += "params[" + i + "] = \"" + args.get(i) + "\";";
		}
		
		code += "for(int i = 0; i<params.length; i++) { \n";
		code += "  if(i < args.length) lexEnviron.declareParameter(params[i], args[i]);\n";
		code += "  else lexEnviron.declareParameter(params[i], TSUndefined.value);\n";
		code += "}\n";
		return code;
	}

	public static String createFunctionEpilogue(){
		return "return TSUndefined.value;";
	}
	public static String createBody(String code, List<String> args){
		// get the class pool
	    ClassPool pool = ClassPool.getDefault();
	    pool.importPackage("ts.Message");
	    pool.importPackage("ts.support");
	    pool.importPackage("java.util");

	    // create an empty class in the default class pool for the Func1 class
	    String bodyClassName = "Func" + (++counter);
	    CtClass theClass = pool.makeClass(bodyClassName);
	    try {
	      theClass.addInterface(pool.get(TSCode.class.getName()));
	    } catch (NotFoundException ex) {
	      fatal("cannot add TSCode interface to " + bodyClassName + " class");
	    }

	    // make a execute method
	    String theMethodBody =
	      "public TSValue execute(boolean isConstructorCall, TSValue ths, " +
	      "TSValue args[], TSLexicalEnvironment lexEnviron)\n";
	   	code  = createFunctionPrologue(args) + code + createFunctionEpilogue();
	    theMethodBody += "{ \n "  + code +  " } ";
	    
	    CtMethod theMethod = null;
	    try {
	      theMethod = CtNewMethod.make(theMethodBody, theClass);
	    } catch (CannotCompileException ex) {
	      fatal("cannot compile the generated " + bodyClassName + " method body: " +
	        ex.getReason());
	    }

	    //add method
	    try {
	      theClass.addMethod(theMethod);
	    } catch (CannotCompileException ex) {
	      fatal("cannot add the execute method into the " + bodyClassName + " class: " +
	        ex.getReason());
	    }

	    // add a default constructor to the class
	    try {
	      theClass.addConstructor(CtNewConstructor.defaultConstructor(theClass));
	    } catch (CannotCompileException ex) {
	      fatal("cannot add a default constructor to the main class: " +
	        ex.getReason());
	    }
	    
	    return bodyClassName;
	}
	public void setPrototype(TSCode prototype){
		this.prototype = prototype;
	}

	public TSLexicalEnvironment getLexEnviron() {
		return lexEnviron;
	}

	public void setLexEnviron(TSLexicalEnvironment scope) {
		this.lexEnviron = scope;
	}

	public TSString toStr() {
		return TSString.create("");
	}

	public static void fatal(String message)
	{
		System.err.println(message);
		System.exit(-1);
	}
}
