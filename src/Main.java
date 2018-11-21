import java.io.File;

public class Main {

  public static void main(String[] args) {
    ReadWords readWords = new ReadWords("train");
    new NaiveBayesClassifier(readWords.getSpamWords(), readWords.getHamWords());
  }
}
