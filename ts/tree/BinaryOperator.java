
package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST binary operator node
 *
 */
public class BinaryOperator extends Expression
{
  private Binop op;
  private Expression left;
  private Expression right;

  public BinaryOperator(final Location loc, final Binop op,
     final Expression left, final Expression right)
  {
    super(loc);
    this.op = op;
    this.left = left;
    this.right = right;
  }

  public Binop getOp()
  {
    return op;
  }

  /** Convert operator kind to (Java) String for displaying. */
  public String getOpString()
  {
    return op.toString();
  }

  public Expression getLeft()
  {
    return left;
  }

  public Expression getRight()
  {
    return right;
  }

  public <T> T apply(TreeVisitor<T> visitor)
  {
    return visitor.visit(this);
  }
}

