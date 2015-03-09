
package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST expression statement node
 *
 */
public final class ExpressionStatement extends Statement
{
  private Expression exp;

  public ExpressionStatement(final Location loc, final Expression exp)
  {
    super(loc);
    this.exp = exp;
  }

  public Expression getExp()
  {
    return exp;
  }

  public <T> T apply(TreeVisitor<T> visitor)
  {
    return visitor.visit(this);
  }
}

