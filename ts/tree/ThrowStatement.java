package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST Throw statement node
 * 
 */
public class ThrowStatement extends Statement {

	private Expression exp;

	public Expression getExp() {
		return exp;
	}

	public void setExp(Expression exp) {
		this.exp = exp;
	}

	public ThrowStatement(Location loc, Expression exp) {
		super(loc);
		this.exp = exp;
	}

	@Override
	public <T> T apply(TreeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
