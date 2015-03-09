package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST empty statement node
 * 
 */
public final class EmptyStatement extends Statement {
	public EmptyStatement(final Location loc) {
		super(loc);
	}

	public <T> T apply(TreeVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
