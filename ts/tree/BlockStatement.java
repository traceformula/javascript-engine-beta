package ts.tree;

import java.util.List;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST block statement node
 * 
 */
public final class BlockStatement extends Statement {

	/** A List holding the statements enclosed by this block */
	private List<Statement> stmtList;

	public BlockStatement(final Location loc, final List<Statement> stmtList) {
		super(loc);
		this.stmtList = stmtList;
	}

	public List<Statement> getStatementList() {
		return stmtList;
	}

	public <T> T apply(TreeVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
