
package ts.tree.visit;

import ts.tree.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Base implementation for AST visitors. Performs complete traversal
 * but does nothing. Parameterized by return value.
 * <p>
 * The "visit" method is overloaded for every tree node type.
 */
public class TreeVisitorBase<T> implements TreeVisitor<T>
{
  // override to add pre- and/or post-processing
  protected T visitNode(final Tree node)
  {
    return node.apply(this);
  }

  // visit a list of ASTs and return list of results
  // use wildcard to allow general use, with list of Statements, list
  //   of Expressions, etc
  protected List<T> visitEach(final Iterable<?> nodes)
  {
    final List<T> visited = new ArrayList<T>();
    for (final Object node : nodes)
    {
      visited.add(visitNode((Tree) node));
    }
    return visited;
  }
  
  public T visit(final BinaryOperator binaryOperator)
  {
    visitNode(binaryOperator.getLeft());
    visitNode(binaryOperator.getRight());
    return null;
  }

  //@Update 20150128
  public T visit(final UnaryOperator unaryOperator)
  {
    visitNode(unaryOperator.getExpression());
    return null;
  }
  public T visit(final BooleanLiteral booleanLiteral)
  {
    return null;
  }
  public T visit(final StringLiteral stringLiteral)
  {
    return null;
  }
  public T visit(final NullLiteral nullLIteral)
  {
    return null;
  }

  public T visit(final ExpressionStatement expressionStatement)
  {
    visitNode(expressionStatement.getExp());
    return null;
  }

  public T visit(final Identifier identifier)
  {
    return null;
  }

  public T visit(final NumericLiteral numericLiteral)
  {
    return null;
  }

  public T visit(final PrintStatement printStatement)
  {
    visitNode(printStatement.getExp());
    return null;
  }

  public T visit(final VarStatement varStatement)
  {
    return null;
  }

  public T visit(final BlockStatement blockStatement) {
    return null;
  }

  public T visit(final EmptyStatement emptyStatement) {
    return null;
  }

  @Override
  public T visit(WhileStatement whileStatment) {
    visitNode(whileStatment);
    return null;
  }

  @Override
  public T visit(IfStatement ifStatement) {
    visitNode(ifStatement);
    return null;
  }

  @Override
  public T visit(BreakStatement breakStatement) {
    visitNode(breakStatement);
    return null;
  }

  @Override
  public T visit(ContinueStatement continueStatement) {
    visitNode(continueStatement);
    return null;
  }

  @Override
  public T visit(LabelStatement labelStatement) {
    visitNode(labelStatement);
    return null;
  }

  @Override
  public T visit(ThrowStatement throwStatement) {
    visitNode(throwStatement);
    return null;
  }

  @Override
  public T visit(TryStatement tryStatement) {
    visitNode(tryStatement);
    return null;
  }

  @Override
  public T visit(CatchStatement catchStatement) {
    visitNode(catchStatement);
    return null;
  }

  @Override
  public T visit(FinallyStatement finallyStatement) {
    visitNode(finallyStatement);
    return null;
  }

  @Override
  public T visit(FunctionExpression functionExpression) {
    visitNode(functionExpression);
    return null;
  }

  @Override
  public T visit(FunctionBody functionBody) {
    visitNode(functionBody);
    return null;
  }

  @Override
  public T visit(CallExpression callExpression) {
    visitNode(callExpression);
    return null;
  }

  @Override
  public T visit(ReturnStatement returnStatement) {
    visitNode(returnStatement);
    return null;
  }

}

