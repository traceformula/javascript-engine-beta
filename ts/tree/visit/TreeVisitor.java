
package ts.tree.visit;

import ts.tree.*;

/**
 * All visitor classes for ASTs will implement this interface, which
 *   is parameterized by return type.
 *
 */
public interface TreeVisitor<T>
{
  T visit(BinaryOperator binaryOperator);

  //@Update 20150128
  T visit(UnaryOperator unaryOperator);
  T visit(BooleanLiteral booleanLiteral);
  T visit(StringLiteral stringLiteral);
  T visit(NullLiteral nullLiteral);

  T visit(ExpressionStatement expressionStatement);

  T visit(Identifier identifier);

  T visit(NumericLiteral numericLiteral);

  T visit(PrintStatement printStatement);

  T visit(VarStatement varStatement);
  T visit(BlockStatement unaryOperator);

  T visit(EmptyStatement emptyStatement);

  T visit(WhileStatement binaryOperator);

  T visit(IfStatement ifStatement);

  T visit(BreakStatement breakStatement);

  T visit(ContinueStatement continueStatement);

  T visit(LabelStatement labelStatement);

  T visit(ThrowStatement throwStatement);

  T visit(TryStatement tryStatement);

  T visit(CatchStatement catchStatement);

  T visit(FinallyStatement finallyStatement);

  T visit(FunctionExpression functionExpression);

  T visit(FunctionBody functionBody);

  T visit(CallExpression callExpression);

  T visit(ReturnStatement returnStatement);
}

