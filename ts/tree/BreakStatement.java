package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST break statement node
 * 
 */
public class BreakStatement extends Statement {

	private String ident;

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public BreakStatement(final Location loc, final String ident) {
		super(loc);
		this.ident = ident;
	}

	@Override
	public <T> T apply(TreeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
