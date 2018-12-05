
public class Classify {

  private static int kNeighbors;

  /**
   * Main method is the first one to be executed from the command line. It takes the arguments
   * and sets variables with their value. It will then pass this parameters
   * to a different method in order to execute a separate method.
   * @param args
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Please input some arguments for classification...");
      System.exit(0);
    }
    String method;
    String dirPath;
    if (args.length > 2) {
      method = args[0];
      kNeighbors = Integer.parseInt(args[1]);
      dirPath = args[2];
    } else {
      method = args[0];
      dirPath = args[1];
    }
    methods(method, dirPath);
  }

  /**
   * Method is in charge of determine what classifying method to run
   * @param method, either NB or KNN
   * @param dirPath, the directory path for the test/train folders
   */
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
