
package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST identifier expression leaf node
 *
 */
public final class Identifier extends Expression
{
  private String name;

  public Identifier(final Location loc, final String name)
  {
    super(loc);
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public <T> T apply(TreeVisitor<T> visitor)
  {
    return visitor.visit(this);
  }
}

