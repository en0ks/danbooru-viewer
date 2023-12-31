import java.util.*;
import java.util.stream.Collectors;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

class Fetcher {
  public static String fetchSite(String link) throws IOException {
    URLConnection urlc = (new URL(link)).openConnection();
    InputStream in = urlc.getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(in));
    StringBuilder content = new StringBuilder();
    br.lines().forEach(line -> content.append(line));
    return content.toString();
  }

  public static ArrayList<Picture> fetchPictureListFromSite(String link) throws IOException {
    try {
      ArrayList<Picture> pictures = new ArrayList<>();
      Document doc = Jsoup.parse(fetchSite(link));
      pictures = getPictures(doc);
      return pictures;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static ArrayList<Picture> getPictures(Document document) {
    ArrayList<Picture> pictures = new ArrayList<>();
    ArrayList<Element> articles = getArticles(document); // all id s
    for (Element article : articles) {
      String dataID = article.attr("data-id");
      ArrayList<String> tagNames = new ArrayList<String>(
          Arrays.stream(article.attr("data-tags").split("\\s+")).collect(Collectors.toList()));
      String uploaderID = article.attr("data-uploader-id");
      String rating = article.attr("data-rating");
      String score = article.attr("data-score");

      String urlStr = null;
      urlStr = article.getElementsByTag("img").attr("src").replace("180x180", "original");
      try {
        HttpURLConnection urlc2 = (HttpURLConnection) (new URL(urlStr)).openConnection();
        if (urlc2.getResponseCode() == 404) {
          urlStr = getTrueURL(fetchSite(Constants.FILE_PAGE + dataID));
          pictures.add(new Picture(dataID, uploaderID, urlStr,
              dataID + "." + uploaderID,
              tagNames, rating, score));
        }
        //System.out.println(urlStr);
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return pictures;
  }

  private static String getTrueURL(String articleHTML) {
    String url = null;
    Document doc = Jsoup.parse(articleHTML);
    Element el = doc.body().getElementById("post-info-size");
    if (el != null) {
      Element el2 = el.getElementsByTag("a").first();
      if (el2 != null)
        url = el2.attr("href");
    }
    return url;
  }

  private static ArrayList<Element> getArticles(Document document) {
    return new ArrayList<Element>(
        document.body().getElementsByTag("article").stream().collect(Collectors.toList()));
  }
}
