//@Update 20140128


package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

public class UnaryOperator extends Expression{
    private Unop op;
    private Expression expression;

    public UnaryOperator(final Location loc, final Unop op,
     final Expression expression)
    {
        super(loc);
        this.op = op;
        this.expression = expression;
    }

    public Unop getOp()
    {
    return op;
    }

    /** Convert operator kind to (Java) String for displaying. */
    public String getOpString()
    {
    return op.toString();
    }

    public Expression getExpression()
    {
    return expression;
    }

    public <T> T apply(TreeVisitor<T> visitor)
    {
    return visitor.visit(this);
    }
}