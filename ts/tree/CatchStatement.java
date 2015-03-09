package ts.tree;

import ts.Location;
import ts.support.TSString;
import ts.tree.visit.TreeVisitor;

/**
 * AST catch statement node
 * 
 */
public class CatchStatement extends Statement {

	private Statement stmt;
	private String identifier;
	private TSString parameter;

	public CatchStatement(Location loc, String ident, Statement stmt) {
		super(loc);
		this.identifier = ident;
		this.stmt = stmt;
	}

	public TSString getParameter() {
		return parameter;
	}

	public void setParameter(TSString parameter) {
		this.parameter = parameter;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public String getIdent() {
		return identifier;
	}

	public void setIdent(String ident) {
		this.identifier = ident;
	}

	@Override
	public <T> T apply(TreeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
