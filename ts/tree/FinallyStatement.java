package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST finally statement node
 * 
 */
public class FinallyStatement extends Statement {

	private Statement stmt;

	public FinallyStatement(Location loc, Statement stmt) {
		super(loc);
		this.stmt = stmt;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	@Override
	public <T> T apply(TreeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
