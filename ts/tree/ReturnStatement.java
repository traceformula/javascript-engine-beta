package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST Return statement node
 * 
 */
public class ReturnStatement extends Statement {

	private Expression exp;

	public ReturnStatement(Location loc, Expression exp) {
		super(loc);
		this.exp = exp;
	}

	public Expression getExp() {
		return exp;
	}

	public void setExp(Expression exp) {
		this.exp = exp;
	}

	@Override
	public <T> T apply(TreeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
