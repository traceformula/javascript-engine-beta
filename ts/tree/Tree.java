
package ts.tree;

import ts.tree.visit.TreeVisitor;

/**
 * interface for all AST classes
 *
 */
public interface Tree
{
  // apply a visitor
  public <T> T apply(TreeVisitor<T> visitor);
}

