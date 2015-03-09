package ts.tree;

import java.util.List;

import ts.Location;
import ts.tree.visit.TreeVisitor;

/**
 * AST function body node
 * 
 */
public class FunctionBody extends Statement {

	/** A List holding the sourceElements enclosed by this function body */
	private List<Statement> sourceElements;
	public static int count = 0;

	public FunctionBody(Location loc, List<Statement> sourceElements) {
		super(loc);
		this.sourceElements = sourceElements;
	}

	public List<Statement> getSourceElements() {
		return sourceElements;
	}

	public void setSourceElements(List<Statement> sourceElements) {
		this.sourceElements = sourceElements;
	}

	@Override
	public <T> T apply(TreeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
