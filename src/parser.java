import org.json.*;
import java.io.*;
import java.util.*;

class Parser {
  public static ArrayList<Picture> readJSON(String filename) throws JSONException {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      StringBuilder content = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        content.append(line);
      }
      String jsonString = content.toString(); // assign your JSON String here
      JSONArray obj = new JSONArray(jsonString);
      ArrayList<Picture> pictures = new ArrayList<>();
      for (int i = 0; i < obj.length(); i++) {
        JSONObject pictureHead = obj.getJSONObject(i);
        JSONObject pictureTail = pictureHead.getJSONObject("picture");
        String dataID = (String) pictureTail.get("dataID");
        String uploaderID = (String) pictureTail.get("uploaderID");
        String url = (String) pictureTail.get("URL");
        JSONArray tagsArray = pictureTail.getJSONArray("tags");
        ArrayList<String> tags = new ArrayList<>();
        for (int j = 0; j < tagsArray.length(); j++)
          tags.add((String) tagsArray.get(j));
        String rating = (String) pictureTail.get("rating");
        String score = (String) pictureTail.get("score");
        pictures.add(new Picture(dataID, uploaderID, url,
            dataID + "." + uploaderID,
            tags, rating, score));
      }
      return pictures;
    } catch (IOException e) {
      System.err.println("Error reading the file: " + e.getMessage());
    }
    return null;
  }
}
