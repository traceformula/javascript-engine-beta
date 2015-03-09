
package ts.tree;

import ts.Location;

/**
 * superclass for all AST nodes
 *
 */
public abstract class Node implements Tree
{
  /** Location in source code (file, line, column). */
  protected final Location loc;

  protected Node(final Location loc)
  {
    this.loc = loc;
  }

  public Location getLoc()
  {
    return loc;
  }

  public int getLineNumber()
  {
    return loc.getLine();
  }
}

