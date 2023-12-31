import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;
import java.net.URL;;

class Picture {
  private String dataID;
  private String uploaderID;
  private URL url;
  private Path path;
  private ArrayList<Tag> tags;
  private String rating;
  private String score;

  class Tag {
    String name;

    public Tag(String name) {
      this.name = name;
    }

    String getName() {
      return this.name;
    }

    public String toString() {
      String s = "        ";
      return this.name + s + System.lineSeparator();
    }
  }

  public Picture(String dataID, String uploaderID, String urlStr, String pathStr,
      ArrayList<String> tagNames, String rating, String score) throws MalformedURLException {
    this.dataID = dataID;
    this.uploaderID = uploaderID;
    this.url = new URL(urlStr);
    this.path = Paths.get(Constants.DOWNLOAD_ROOT_DIR + pathStr);
    this.tags = new ArrayList<>();
    for (String name : tagNames)
      this.tags.add(new Tag(name));
    this.rating = rating;
    this.score = score;
  }

  public void setUploaderID(String id) {
    this.uploaderID = id;
  }

  public void setDataID(String id) {
    this.dataID = id;
  }

  public void setPath(Path path) {
    this.path = path;
  }

  public Path getPath() {
    return this.path;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    String l = System.lineSeparator();
    String s = "  ";
    String ls = l + s;
    sb.append(Constants.ANSI_GREEN).append("[PICTURE]").append(Constants.ANSI_RESET);
    sb.append(ls).append(Constants.ANSI_CYAN).append("* dataID *").append(Constants.ANSI_RESET).append(ls)
        .append(dataID)
        .append(ls).append(Constants.ANSI_CYAN).append("* uploaderID *").append(Constants.ANSI_RESET).append(ls)
        .append(uploaderID)
        .append(ls).append(Constants.ANSI_CYAN).append("* URL *").append(Constants.ANSI_RESET).append(ls)
        .append(url.toString())
        .append(ls).append(Constants.ANSI_CYAN).append("* Path *").append(Constants.ANSI_RESET).append(ls)
        .append(path.toString());
    // .append(ls).append(title)

    sb.append(ls).append(Constants.ANSI_CYAN).append("* Rating *").append(Constants.ANSI_RESET).append(ls)
        .append(rating)
        .append(ls).append(Constants.ANSI_CYAN).append("* Score *").append(Constants.ANSI_RESET).append(ls)
        .append(score).append(ls).append(Constants.ANSI_GREEN).append("[TAGS]").append(Constants.ANSI_RESET)
        .append(l);
    for (Tag tag : this.tags) {
      sb.append(s).append(s).append(tag.toString());
    }
    sb.append(l);
    return sb.toString();
  }

  public JSONObject getJSON() throws JSONException {
    JSONObject pic = new JSONObject();
    pic.put("dataID", this.dataID);
    pic.put("uploaderID", this.uploaderID);
    pic.put("URL", this.url);
    pic.put("path", this.path);

    JSONArray tagsArray = new JSONArray();
    for (Tag tag : this.tags)
      tagsArray.put(tag.getName());

    pic.put("tags", tagsArray);
    pic.put("rating", this.rating);
    pic.put("score", this.score);

    JSONObject head = new JSONObject();
    head.put("picture", pic);

    return head;
  }

  public static ArrayList<Picture> readJSON(String name) {
    // JSON parser object to parse read file
    JSONParser jsonParser = new JSONParser();

    try (FileReader reader = new FileReader(Constants.NAME_JSON)) {
      Object obj = jsonParser.parse(reader);
      JSONArray pictureList = (JSONArray) obj;
      for (int i = 0; i < pictureList.length(); i++) {
        
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
