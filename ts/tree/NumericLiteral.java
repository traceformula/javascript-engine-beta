
package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST numeric literal expression leaf node
 *
 */
public final class NumericLiteral extends Expression
{
  private double value;

  public NumericLiteral(final Location loc, final double value)
  {
    super(loc);
    this.value = value;
  }

  public double getValue()
  {
    return value;
  }

  public <T> T apply(TreeVisitor<T> visitor)
  {
    return visitor.visit(this);
  }
}

