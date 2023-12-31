import java.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;

class Gallery extends Fetcher {
  ArrayList<Picture> pictures;

  public Gallery() {
    this.pictures = new ArrayList<>();
  }

  public Gallery(ArrayList<Picture> pictures) {
    this.pictures = pictures;
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
}
