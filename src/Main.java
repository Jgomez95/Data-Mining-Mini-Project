import java.util.List;

public class Main {

  private static String kNeighbors;

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Please input some arguments for classification...");
      System.exit(0);
    }
    if (args.length > 2) {
      kNeighbors = args[2];
    }
    String method = args[0];
    String dirPath = args[1];
    methods(method, dirPath);
  }

  private static void methods(String method, String dirPath) {
    switch (method) {
      case "knn":
        // TODO add kNeighbors as a paramater
        System.out.println("Using KNN");
        //new NearestNeighborsClassifier();
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
