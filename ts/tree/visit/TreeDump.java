
package ts.tree.visit;

import ts.tree.*;

import java.io.PrintWriter;
import java.util.List;

/**
 * Dump an AST to a stream. Uses prefix order with indentation.
 * <p>
 * Using Object for the type parameter but there is really no return type.
 * <p>
 * The "visit" method is overloaded for each tree node type.
 */
public final class TreeDump extends TreeVisitorBase<Object>
{
  // where to write the dump to
  private PrintWriter writer;

  // current indentation amount
  private int indentation;

  // how much to increment the indentation by at each level
  // using an increment of zero would mean no indentation
  private int increment;

  // by default start even to the left margin and increment indentation
  // by 2 spaces
  public TreeDump(final PrintWriter writer)
  {
    this(writer, 0, 2);
  }

  public TreeDump(final PrintWriter writer, final int indentation,
    final int increment)
  {
    this.writer = writer;
    this.indentation = indentation;
    this.increment = increment;
  }

  // generate a string of spaces for current indentation level
  private void indent()
  {
    for (int i = 0; i < indentation; i++)
    {
      writer.print(" ");
    }
  }

  // visit a list of ASTs and dump them in order
  // use wildcard for generality: list of Statements, list of Expressions, etc
  public List<Object> visitEach(final Iterable<?> nodes)
  {
    for (final Object node : nodes)
    {
      visitNode((Tree) node);
    }
    return null;
  }
  
  public Object visit(final BinaryOperator binaryOperator)
  {
    indent();
    writer.println(binaryOperator.getOpString());
    indentation += increment;
    visitNode(binaryOperator.getLeft());
    visitNode(binaryOperator.getRight());
    indentation -= increment;
    return null;
  }

  public Object visit(final ExpressionStatement expressionStatement)
  {
    indent();
    writer.println("ExpressionStatement");
    indentation += increment;
    visitNode(expressionStatement.getExp());
    indentation -= increment;
    return null;
  }

  public Object visit(final Identifier identifier)
  {
    indent();
    writer.println("Identifier " + identifier.getName());
    return null;
  }

  public Object visit(final NumericLiteral numericLiteral)
  {
    indent();
    writer.println("NumericLiteral " + numericLiteral.getValue());
    return null;
  }

  public Object visit(final PrintStatement printStatement)
  {
    indent();
    writer.println("PrintStatement");
    indentation += increment;
    visitNode(printStatement.getExp());
    indentation -= increment;
    return null;
  }

  public Object visit(final VarStatement varStatement)
  {
    indent();
    writer.println("Var " + varStatement.getName());
    return null;
  }

}

