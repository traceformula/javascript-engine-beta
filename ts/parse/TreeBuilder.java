package ts.parse;

import ts.Location;
import ts.Message;
import ts.tree.*;

import java.util.*;

/**
 * Provides static methods for building AST nodes
 */
public class TreeBuilder
{

  /** Build a "var" statement.
   *
   *  @param  loc  location in source code (file, line, column)
   *  @param  name name of variable being declared.
   */
  public static Statement buildVarStatement(final Location loc,
    final String name)
  {
    Message.log("TreeBuilder: VarStatement (" + name + ")");
    return new VarStatement(loc, name);
  }

  public static VarStatement addVarDeclaration(VarStatement l, VarStatement r)
  {
    if (l == null) l = new VarStatement(r.getLoc(), r.getName());
    l.addExps(r);
    return l;
  }

  /** Build a expression statement.
   *
   *  @param  loc  location in source code (file, line, column)
   *  @param  exp  expression subtree
   */
  public static Statement buildExpressionStatement(final Location loc,
    final Expression exp)
  {
    Message.log("TreeBuilder: ExpressionStatement");
    return new ExpressionStatement(loc, exp);
  }

  /** Build a binary operator.
   *
   *  @param  loc   location in source code (file, line, column)
   *  @param  op    the binary operator
   *  @param  left  the left subtree
   *  @param  right the right subtree
      @see Binop
   */
  public static Expression buildBinaryOperator(final Location loc,
    final Binop op,
    final Expression left, final Expression right)
  {
    Message.log("TreeBuilder: Binop " + op.toString());

    return new BinaryOperator(loc, op, left, right);
  }

  //@Update 20150128
  /** Build a unary operator.
   *
   *  @param  loc   location in source code (file, line, column)
   *  @param  op    the unary operator
   *  @param  expression  the epxression
      @see Unop
   */
  public static Expression buildUnaryOperator(final Location loc,
    final Unop op, final Expression expression)
  {
    Message.log("TreeBuilder: Unop " + op.toString());
    
    return new UnaryOperator(loc, op, expression);
  }

  /** Build a identifier expression.
   *
   *  @param  loc  location in source code (file, line, column)
   *  @param  name name of the identifier.
   */
  public static Expression buildIdentifier(final Location loc,
    final String name)
  {
    Message.log("TreeBuilder: Identifier (" + name + ")");
    return new Identifier(loc, name);
  }

  //@Update 20150128
  /** Build a null literal expression. 
  *
  *  @param  loc   location in source code (file, line, column)
  */
  public static Expression buildNullLiteral(final Location loc){
    NullLiteral result = new NullLiteral(loc);
    return result;
  }
  /** Build a string literal expression. 
  *
  *  @param  loc   location in source code (file, line, column)
  *  @param  value value of the literal as a String
  */
  public static Expression buildStringLiteral(final Location loc, 
    final String value){
    Message.log(value);

    //the following code is to remove single quotes or double quotes
    String s = value.trim();
    if(s.length() > 0) s = s.substring(1, s.length());
    if(s.length() > 0) s = s.substring(0, s.length()-1);
    s = s.replace("\"", "\\\"");
    StringLiteral result = new StringLiteral(loc, s);
    return result;
  }
  
  /** Build a boolean literal expression. Conperts the String for
   *  the value to a boolean.
   *
   *  @param  loc   location in source code (file, line, column)
   *  @param  value value of the literal as a String
   */
  public static Expression buildBooleanLiteral(final Location loc, 
    final String value){
    BooleanLiteral result = new BooleanLiteral(loc, Boolean.parseBoolean(value));
    return result;
  }

  /** Build a numeric literal expression. Converts the String for
   *  the value to a double.
   *
   *  @param  loc   location in source code (file, line, column)
   *  @param  value value of the literal as a String
   */
  public static Expression buildNumericLiteral(final Location loc,
    final String value)
  {
    double d = 0.0;
    try
    {
      
      //20150130
      if(value.indexOf("0x") >=0 || value.indexOf("0X") >=0){
        String v = value.substring(2, value.length());
        d = (new java.math.BigInteger(v, 16)).doubleValue();
      }
      else
        d = Double.parseDouble(value);
    }
    catch(NumberFormatException nfe)
    {
      nfe.printStackTrace();
      Message.bug(loc, "numeric literal not parsable" + value);
    }
    Message.log("TreeBuilder: NumericLiteral " + d);
    return new NumericLiteral(loc, d);
  }

  /** Build a print statement.
   *
   *  @param  loc  location in source code (file, line, column)
   *  @param  exp  expression subtree.
   */
  public static Statement buildPrintStatement(final Location loc,
    final Expression exp)
  {
    Message.log("TreeBuilder: PrintStatement");
    return new PrintStatement(loc, exp);
  }

  //
  // methods to detect "early" (i.e. semantic) errors
  //

  // helper function to detect "reference expected" errors
  private static boolean producesReference(Node node)
  {
    if (node instanceof Identifier)
    {
      return true;
    }
    return false;
  }
  
  /** Used to detect non-references on left-hand-side of assignment.
   *
   *  @param  loc  location in source code (file, line, column)
   *  @param  node tree to be checked
   */
  public static void checkAssignmentDestination(Location loc, Node node)
  {
    if (!producesReference(node))
    {
      Message.error(loc, "assignment destination must be a Reference");
    }
  }

  /**
   * Build a block.
   * 
   * @param loc
   *            location in source code (file, line, column)
   * @param stmtList
   *            stmtList list of statements enclosed in a block.
   */
  public static Statement buildBlock(final Location loc,
      List<Statement> stmtList) {
    Message.log("TreeBuilder: block");
    return new BlockStatement(loc, stmtList);
  }

  /**
   * Build a while statement.
   * 
   * @param loc
   *            location in source code (file, line, column)
   * 
   * @param exp
   *            conditional expression to be evaluated in while
   * @param stmt
   *            stmt in while block.
   */
  public static Statement buildWhileStatement(final Location loc,
      final Expression exp, final Statement stmt) {
    Message.log("TreeBuilder: while");
    return new WhileStatement(loc, exp, stmt);
  }

  /**
   * Build an If statement.
   * 
   * @param loc
   *            location in source code (file, line, column)
   * @param exp
   *            conditional expression to be evaluated in while
   * @param ifStmt
   *            ifStmt in if block.
   * @param elStatement
   *            else Stmt in if else block.
   */
  public static Statement buildIfStatement(final Location loc,
      final Expression exp, final Statement ifStmt,
      final Statement elStatement) {
    Message.log("TreeBuilder: if else");
    return new IfStatement(loc, exp, ifStmt, elStatement);
  }

  /**
   * Build an empty statement.
   * 
   * @param loc
   *            location in source code (file, line, column)
   */
  public static Statement buildEmptyStatement(final Location loc) {
    Message.log("TreeBuilder: empty");
    return new EmptyStatement(loc);
  }

  /**
   * Build an break statement.
   * 
   * @param loc
   *            location in source code (file, line, column)
   */
  public static Statement buildBreakStatement(final Location loc,
      final String ident) {
    Message.log("TreeBuilder: break");
    return new BreakStatement(loc, ident);
  }

  /**
   * Build a label statement.
   * 
   * @param loc
   *            location in source code (file, line, column)
   */
  public static Statement buildLabelStatement(final Location loc,
      final String ident, final Statement stmt) {
    Message.log("TreeBuilder: label");
    return new LabelStatement(loc, ident, stmt);
  }

  /**
   * Build a throw statement.
   * 
   * @param loc
   *            location in source code (file, line, column)
   */
  public static Statement buildThrowStatement(final Location loc,
      final Expression exp) {
    Message.log("TreeBuilder: Throw");
    return new ThrowStatement(loc, exp);
  }

  /**
   * Build a try statement.
   * 
   * @param loc
   *            location in source code (file, line, column)
   */
  public static Statement buildTryStatement(final Location loc,
      final Statement block, Statement catchStmt,
      Statement finallyStatement) {
    Message.log("TreeBuilder: Try");
    return new TryStatement(loc, block, catchStmt, finallyStatement);
  }

  /**
   * Build a catch statement.
   * 
   * @param loc
   *            location in source code (file, line, column)
   */
  public static Statement buildCatchStatement(final Location loc,
      final String ident, Statement block) {
    Message.log("TreeBuilder: Catch");
    return new CatchStatement(loc, ident, block);
  }

  /**
   * Build a finally statement.
   * 
   * @param loc
   *            location in source code (file, line, column)
   */
  public static Statement buildFinallyStatement(final Location loc,
      Statement block) {
    Message.log("TreeBuilder: Finally");
    return new FinallyStatement(loc, block);
  }

  /**
   * Build a continue statement.
   * 
   * @param loc
   *            location in source code (file, line, column)
   */
  public static Statement buildContinueStatement(final Location loc,
      String ident) {
    Message.log("TreeBuilder: continue");
    return new ContinueStatement(loc, ident);
  }

  /**
   * Build a function object.
   * 
   * @param loc
   *            location in source code (file, line, column)
   */
  public static Expression buildFunctionExpression(final Location loc,
      String functionName, List<String> paramList, Statement fucntionBody) {
    Message.log("TreeBuilder: function expression");
    FunctionExpression f= new FunctionExpression(loc, functionName, paramList,
        fucntionBody);
    return f;
  }

  public static Statement buildFunctionBody(final Location loc,
      List<Statement> sourceElements) {
    Message.log("TreeBuilder: function body");
    return new FunctionBody(loc, sourceElements);
  }

  public static Expression buildCallExpression(Location loc,
      Expression memberExp, List<Expression> arguments, Expression callExp) {
    return new CallExpression(loc, memberExp, arguments, callExp);
  }

  public static Statement buildReturnStatement(Location loc, Expression exp) {

    return new ReturnStatement(loc, exp);
  }

}
