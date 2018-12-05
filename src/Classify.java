import java.util.List;

public class Classify {

  private static int kNeighbors;

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Please input some arguments for classification...");
      System.exit(0);
    }
    if (args.length > 2) {
      kNeighbors = Integer.parseInt(args[2]);
    }
    String method = args[0];
    String dirPath = args[1];
    methods(method, dirPath);
  }

  private static void methods(String method, String dirPath) {
    switch (method.toUpperCase()) {
      case "KNN":
        System.out.println("Using KNN");
        new NearestNeighborsClassifier(dirPath, kNeighbors);
        break;
      case "NB":
        new NaiveBayesClassifier(dirPath);
        break;
      default:
        System.out.printf("%s is not a valid clasifier method...", method);
        break;
    }
  }
}
