package ts.tree;

import java.util.ArrayList;
import java.util.List;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST function expression node
 * 
 */
public class FunctionExpression extends Expression {

	/** name of the function */
	private String functionName;
	/** A List holding the parameters list for the function */
	private List<String> formalParameterList = new ArrayList<String>();
	/** statement representing function body */
	private Statement functionBody;

	public FunctionExpression(Location loc, String functionName,
			List<String> formalParameterList, Statement functionBody) {

		super(loc);
		if (formalParameterList != null) {
			this.formalParameterList = formalParameterList;
		}
		this.functionBody = functionBody;

	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public List<String> getFormalParameterList() {
		return formalParameterList;
	}

	public void setFormalParameterList(List<String> formalParameterList) {
		this.formalParameterList = formalParameterList;
	}

	public Statement getFunctionBody() {
		return functionBody;
	}

	public void setFunctionBody(Statement functionBody) {
		this.functionBody = functionBody;
	}

	@Override
	public <T> T apply(TreeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
