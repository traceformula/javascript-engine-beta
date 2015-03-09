//@Update 20150129

package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST String literal expression leaf node
 *
 */
public final class NullLiteral extends Expression
{

  public NullLiteral(final Location loc)
  {
    super(loc);
  }

  public double getValue()
  {
    return Double.NaN;
  }

  public <T> T apply(TreeVisitor<T> visitor)
  {
    return visitor.visit(this);
  }
}