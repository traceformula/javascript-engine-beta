/**
 * marks a location in the source code: embedded in AST nodes
 *
 */

package ts;

/** Tracks a location in the source code: filename, line number and
 *  column. Location references are stored in the AST by the parser.
 */
public final class Location
{
  protected String path;
  protected int line;
  protected int column;

  public Location(final String path, final int line, final int column)
  {
    this.path = path;
    this.line = line;
    this.column = column;
  }

  public String getPath()
  {
    return path;
  }

  public void setPath(final String path)
  {
    this.path = path.replace('\\', '/');
  }

  public int getLine()
  {
    return line;
  }

  public void setLine(final int line)
  {
    this.line = line;
  }

  public int getColumn()
  {
    return column;
  }

  public void setColumn(final int column)
  {
    this.column = column;
  }
}

