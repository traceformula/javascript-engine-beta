
package ts.tree;

import ts.Location;

/**
 * superclass for all AST expression nodes
 *
 */
public abstract class Expression extends Node
{
  public Expression(final Location loc)
  {
    super(loc);
  }
}

