package ts.tree;

import java.util.List;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST call expression node
 * 
 */
public class CallExpression extends Expression {

	private Expression memberExp, callExp;

	public Expression getCallExp() {
		return callExp;
	}

	public void setCallExp(Expression callExp) {
		this.callExp = callExp;
	}

	private List<Expression> arguments;

	public Expression getMemberExp() {
		return memberExp;
	}

	public void setMemberExp(Expression memberExp) {
		this.memberExp = memberExp;
	}

	public List<Expression> getArguments() {
		return arguments;
	}

	public void setArguments(List<Expression> arguments) {
		this.arguments = arguments;
	}

	public CallExpression(Location loc, Expression memberExp,
			List<Expression> arguments, Expression callExpression) {
		super(loc);
		this.memberExp = memberExp;
		this.arguments = arguments;

	}

	@Override
	public <T> T apply(TreeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
