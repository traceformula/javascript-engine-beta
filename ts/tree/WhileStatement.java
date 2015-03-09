package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST While statement node
 * 
 */
public class WhileStatement extends Statement {

	private Expression exp;
	private Statement stmt;
	private static final String EMPTY = "empty";
	public static int count = 0;
	public WhileStatement(final Location loc, final Expression exp,
			final Statement stmt) {
		super(loc);
		this.exp = exp;
		this.stmt = stmt;
		addLabel(EMPTY);
	}

	public Expression getExp() {
		return exp;
	}

	public void setExp(Expression exp) {
		this.exp = exp;
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
