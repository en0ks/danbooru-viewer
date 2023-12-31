import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

class Gallery extends Fetcher {
  ArrayList<Picture> pictures;

  public Gallery() {
    this.pictures = new ArrayList<>();
  }

  public void addPicture(Picture pic) {
    this.pictures.add(pic);
  }

  public void fetchPictures() throws IOException {
    for (Picture pic : Fetcher.fetchPictureListFromSite(Constants.INDEX_PAGE))
      if (!this.contains(pic))
        pictures.add(pic);
  }

  public void fetchPictures(int index) throws IOException {
    String indexStr = Integer.toString(index);
    String url = Constants.INDEX_PAGE.concat(indexStr);
    try {
      for (Picture pic : Fetcher.fetchPictureListFromSite(url))
        if (!this.contains(pic))
          pictures.add(pic);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean contains(Picture picture) {
    for (Picture pic : this.pictures)
      if (picture.equals(pic))
        return true;
    return false;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Picture pic : pictures)
      sb.append(pic.toString());
    return sb.toString();
  }

  public ArrayList<Picture> getPictureList() {
    return this.pictures;
  }

  public JSONArray getJSON() throws JSONException {
    JSONArray pictures = new JSONArray();
    for (Picture pic : this.pictures) {
      pictures.put(pic.getJSON());
    }
    return pictures;
  }

  public static ArrayList<Picture> readJSON(String name) throws JSONException {
    try (FileReader reader = new FileReader(Constants.NAME_JSON)) {
      JSONTokener jsonTokener = new JSONTokener(reader);
      Object obj = jsonTokener.nextValue();
      if (obj == null || obj.getClass() != JSONArray.class) {
        System.out.println("[ERROR] expected JSONArray");
        System.exit(1);
      }
      JSONArray pictureList = (JSONArray) obj;
      for (int i = 0; i < pictureList.length(); i++) {
        Object obj2 = pictureList.get(i);
        if (obj2 == null || obj2.getClass() != JSONObject.class) {
          System.out.println("[ERROR] expected JSONObject");
          System.exit(1);
        }
        JSONObject picturep = (JSONObject) obj2;
        System.out.println(picturep.get("dataID")); // TODO: continue writing JSONReader. Now we have gone into
                                                    // JSONArray and can access individual JSONObjects within the JSON
                                                    // structure (I think?)
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
