package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST continue statement node
 * 
 */
public class ContinueStatement extends Statement {

	private String ident;

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public ContinueStatement(final Location loc, final String ident) {
		super(loc);
		this.ident = ident;
	}

	@Override
	public <T> T apply(TreeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
