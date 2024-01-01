import java.util.*;
import org.json.JSONException;
import java.io.IOException;
import java.io.FileWriter;

class Application {

  public static void main(String[] args) {
    // try {
    // System.out.println(new
    // Gallery(Parser.readJSON(Constants.NAME_JSON)).getJSON().toString(Constants.INDENT_JSON));
    // } catch (JSONException e) {e.printStackTrace();}

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
            writeFile(Constants.PICTURES_JSON, gallery);
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
      System.out.printf("   :: h -> Help" + l);
    }
  }

  public static void writeFile(String outfilename, Object obj) {
    if (obj.getClass() == Gallery.class) {
      try (FileWriter file = new FileWriter(outfilename, true)) {
        file.write(((Gallery) obj).getJSON().toString(Constants.INDENT_JSON).concat(System.lineSeparator()));
        file.flush();
      } catch (JSONException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
