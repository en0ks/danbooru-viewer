import java.util.*;
import org.json.JSONException;
import java.io.IOException;
import java.io.FileWriter;

class Application {

  public static void main(String[] args) {
    System.out.println("!Danbooru View!");
    Terminal.printHelp();

    Gallery gallery = new Gallery();

    Scanner scanner = new Scanner(System.in);
    boolean quitp = false;
    while (!quitp) {
      System.out.printf(">");
      String input = scanner.nextLine();

      switch (input) {
        case "l" -> {
          try {
            for (int i = 0; i <= 0; i++) {
              System.out.println("page :: " + i);
              gallery.fetchPictures(i);
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        case "p" -> {
          // System.out.printf(gallery.toString());
          try {
            System.out.println(gallery.getJSON().toString(Constants.INDENT_JSON));
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
        case "o" -> {
          try (FileWriter file = new FileWriter(Constants.NAME_JSON, true)) {
            // We can write any JSONArray or JSONObject instance to the file
            file.write(gallery.getJSON().toString(Constants.INDENT_JSON));
            file.flush();
          } catch (JSONException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }

        }
        case "q" -> {
          System.out.println("[INFO] exiting");
          System.exit(0);
        }
        case "h" -> {
          Terminal.printHelp();
        }
        case "" -> {
        }
        default -> {
          System.out.println("[ERROR] wrong input?");
        }
      }
    }
    scanner.close();
  }

  class Terminal {
    private static void printHelp() {
      String l = System.lineSeparator();
      System.out.printf("   :: l -> Load new pictures" + l);
      System.out.printf("   :: p -> List loaded pictures" + l);
      System.out.printf("   :: q -> Quit" + l);
      System.out.printf("   :: o -> Write currently loaded pictures to index.JSON" + l);
      System.out.printf("   :: h -> Help" + l);
    }
  }
}
