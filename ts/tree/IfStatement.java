package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST If statement node
 * 
 */
public class IfStatement extends Statement {

	private Expression exp;
	private Statement ifStmt;
	private Statement elseStatement;

	public Expression getExp() {
		return exp;
	}

	public void setExp(Expression exp) {
		this.exp = exp;
	}

	public Statement getIfStmt() {
		return ifStmt;
	}

	public void setIfStmt(Statement ifStmt) {
		this.ifStmt = ifStmt;
	}

	public Statement getElseStatement() {
		return elseStatement;
	}

	public void setElseStatement(Statement elseStatement) {
		this.elseStatement = elseStatement;
	}

	public IfStatement(final Location loc, final Expression exp,
			final Statement ifStmt, final Statement elseStatement) {
		super(loc);
		this.exp = exp;
		this.ifStmt = ifStmt;
		this.elseStatement = elseStatement;
	}

	@Override
	public <T> T apply(TreeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
