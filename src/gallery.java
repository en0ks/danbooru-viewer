import java.util.*;
import java.io.IOException;

class Gallery extends Fetcher {
  ArrayList<Picture> pictures;

  public Gallery() {
    this.pictures = new ArrayList<>();
  }

  public void addPicture(Picture pic) {
    this.pictures.add(pic);
  }

  public void fetchPictures() throws IOException {
    for (Picture pic : Fetcher.fetchSite(Constants.INDEX_PAGE))
      if (!this.contains(pic))
        pictures.add(pic);
  }

  public void fetchPictures(int index) throws IOException {
    String indexStr = Integer.toString(index);
    String url = Constants.INDEX_PAGE.concat(indexStr);
    try {
      for (Picture pic : Fetcher.fetchSite(url))
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

  public String pairsToString() {
    StringBuilder sb = new StringBuilder();
    for (Picture pic : pictures)
      sb.append(pic.pairToString());
    return sb.toString();
  }

  public String urlsToString() {
    StringBuilder sb = new StringBuilder();
    for (Picture pic : pictures)
      sb.append(pic.urlToString());
    return sb.toString();
  }
}