
package ts.tree;

import java.util.ArrayDeque;
import java.util.Deque;

import ts.Location;

/**
 * superclass for all AST statement nodes
 *
 */
public abstract class Statement extends Node
{
  private final ArrayDeque<String> labelSet;

  public Statement(final Location loc)
  {
    super(loc);
    labelSet = new ArrayDeque<String>();
  }

  public boolean addLabel(String label) {
        if (labelSet.contains(label)) {
            return false;
        }
        labelSet.push(label);
        return true;

    }

    public String removeLabel(String label) {
        return labelSet.poll();
    }

    public Deque<String> getLabels() {
        return labelSet;
    }
}

