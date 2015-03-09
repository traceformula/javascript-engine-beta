package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST Label statement node
 * 
 */
public class LabelStatement extends Statement {

	/** name of the label */
	private String label;
	/** labeled statement */
	private Statement stmt;

	public LabelStatement(final Location loc, final String label,
			final Statement stmt) {
		super(loc);
		this.label = label;
		this.stmt = stmt;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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
