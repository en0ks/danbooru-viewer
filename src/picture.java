import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;

class Picture {
  private Path path;
  private String title;
  private ArrayList<Tag> tags;

  class Tag {
    String name;

    String getName() {
      return this.name;
    }
  }

  public Picture(String pathStr, String title) {
    this.path = Paths.get(pathStr);
    this.title = title;
    this.tags = new ArrayList<>();
  }

  public Path getPath() {
    return this.path;
  }

  public boolean equals(Picture pic) {
    return pic != null && this.path.toString().equals(pic.getPath().toString());
  }

  public String pairToString() {
    StringBuilder sb = new StringBuilder();
    String linkFormat = " [ " + Constants.ANSI_GREEN + "LINK" + Constants.ANSI_RESET;
    String tagFormat = "    [ " + Constants.ANSI_GREEN + "TAGS" + Constants.ANSI_RESET;
    sb.append(linkFormat).append(System.lineSeparator()).append("   ").append(path.toString())
        .append(System.lineSeparator());
    String[] title_parts = title.split("\\s+");
    sb.append(tagFormat).append(System.lineSeparator());
    for (String part : title_parts) {
      sb.append("      ").append(part).append(System.lineSeparator());
    }
    sb.append("   ]").append(System.lineSeparator());
    sb.append("  ]").append(System.lineSeparator()).append(System.lineSeparator());
    return sb.toString();

  }

  public String urlToString() {
    StringBuilder sb = new StringBuilder();
    sb.append(path.toString()).append(System.lineSeparator());
    return sb.toString();
  }
}
