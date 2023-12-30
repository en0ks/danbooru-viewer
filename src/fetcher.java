import java.util.*;
import java.util.stream.Collectors;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

class Fetcher {
  public static ArrayList<Picture> fetchSite(String link) throws IOException {
    try {
      URLConnection urlc = (new URL(link)).openConnection();
      InputStream in = urlc.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      StringBuilder content = new StringBuilder();

      br.lines().forEach(line -> content.append(line));

      Document doc = Jsoup.parse(content.toString());

      ArrayList<Element> pictures = getPictures(getArticles(doc));
      ArrayList<Element> sources = getSources(getPictures(getArticles(doc)));
      ArrayList<Pair<String, String>> URLs = getURLsPairTitle(sources);
      // printElements(getSources(pictures));
      // printURLs(getURLs(sources));
      ArrayList<Picture> pictures2 = new ArrayList<>();
      for (Pair pair : URLs) {
        pictures2.add(new Picture((String) pair.a, (String) pair.b));
      }
      return pictures2;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static ArrayList<Element> getArticles(Document document) {
    return new ArrayList<Element>(
        document.body().getElementsByTag("article").stream().collect(Collectors.toList()));
  }

  private static ArrayList<Element> getPictures(ArrayList<Element> articleList) {
    ArrayList<Element> pictureList = new ArrayList<>();
    for (Element article : articleList)
      for (Element picture : article.getElementsByTag("picture"))
        pictureList.add(picture);
    return pictureList;
  }

  private static ArrayList<Element> getSources(ArrayList<Element> pictureList) {
    ArrayList<Element> sources = new ArrayList<>();
    for (Element picture : pictureList)
      sources.add(picture.getElementsByTag("img").first());
    return sources;
  }

  private static ArrayList<Pair<String, String>> getURLsPairTitle(ArrayList<Element> sources) {
    ArrayList<Pair<String, String>> urls = new ArrayList<>();
    for (Element source : sources) {
      String url = source.attr("src").replaceAll("180x180", "original");
      String title = source.attr("title");
      urls.add(new Pair(url, title));
    }
    return urls;
  }

  private static void printElements(ArrayList<Element> elements) {
    for (Element el : elements)
      System.out.println(el.toString());
  }

  private static void printURLs(ArrayList<String> urls) {
    for (String url : urls)
      System.out.println(url);
  }
}
