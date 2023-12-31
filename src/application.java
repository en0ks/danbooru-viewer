import java.util.*;
import java.io.IOException;

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
            for (int i = 0; i <= 1000; i++) {
              System.out.println("page :: " + i);
              gallery.fetchPictures(i);
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        case "p" -> {
          System.out.printf(gallery.toString());
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
      System.out.printf(" Do : l -> Load new pictures" + System.lineSeparator());
      System.out.printf(" Do : p -> List loaded pictures" + System.lineSeparator());
      System.out.printf("    : q -> Quit" + System.lineSeparator());
      System.out.printf("    : h -> Help" + System.lineSeparator());
    }
  }
}
