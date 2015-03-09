package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST Try statement node
 * 
 */
public class TryStatement extends Statement {

	/** statements representing try block, catch and finally statements */
	Statement blockStmt, catchStmt, finallyStmt;
	public static int count = 0;
	public TryStatement(Location loc, Statement blockStmt, Statement catchStmt,
			Statement finallyStmt) {
		super(loc);
		this.blockStmt = blockStmt;
		this.catchStmt = catchStmt;
		this.finallyStmt = finallyStmt;
	}

	public Statement getBlockStmt() {
		return blockStmt;
	}

	public void setBlockStmt(Statement blockStmt) {
		this.blockStmt = blockStmt;
	}

	public Statement getCatchStmt() {
		return catchStmt;
	}

	public void setCatchStmt(Statement catchStmt) {
		this.catchStmt = catchStmt;
	}

	public Statement getFinallyStmt() {
		return finallyStmt;
	}

	public void setFinallyStmt(Statement finallyStmt) {
		this.finallyStmt = finallyStmt;
	}

	@Override
	public <T> T apply(TreeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
