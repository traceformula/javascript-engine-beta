
package ts.tree;

import ts.Location;
import ts.tree.visit.TreeVisitor;

import java.util.List;
import java.util.ArrayList;

/**
 * AST var statement node
 *
 */
public final class VarStatement extends Statement
{
  private String name;
  private List<Expression> exps = new ArrayList<Expression>();
  private List<String> names = new ArrayList<String>();
  public VarStatement(final Location loc, final String name)
  {
    super(loc);
    this.name = name;
    names.add(name);
  }

  public void addExp(Expression exp){
    exps.add(exp);
  }
  public void addExps(VarStatement r)
  {
    names.addAll(r.getNames());
    exps.addAll(r.getExps());
  }
  public List<Expression> getExps(){
    return exps;
  }

  public String getName()
  {
    return name;
  }

  //@update 20150204
  public List<String> getNames(){
    return names;
  }

  public <T> T apply(TreeVisitor<T> visitor)
  {
    return visitor.visit(this);
  }
}

