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


//    ReadWords readWords = new ReadWords("train");
//    new NaiveBayesClassifier(readWords.getSpamWords(), readWords.getHamWords());
  }

  private static void methods(String method, String dirPath) {
    ReadFile read = new ReadFile(dirPath);
    switch (method) {
      case "knn":
        // TODO add kNeighbors as a paramater
        System.out.println("Using KNN");
        new ClosestNeighbors();
        break;
      case "NB":
        System.out.println("Using NB");
        List<Email> list = read.getEmailList();
        for (Email e :  list) {
          System.out.println(e.getWordList().size() + "\t" + e.getIsSpam());
        }
        System.out.println(list.size());
        //new NaiveBayesClassifier(read.getSpamWords(), read.getHamWords());
        break;
      default:
        System.out.printf("%s is not a valid clasifier method...", method);
        break;
    }
  }
}
