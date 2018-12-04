import java.util.ArrayList;
import java.util.List;

public class NearestNeighborsClassifier {

  private int K;
  private List<String> wordList = new ArrayList<>();
  private List<Email> emailList = new ArrayList<>();

  NearestNeighborsClassifier(String dirPath, int K) {
    this.K = K;
    ReadFile read = new ReadFile(dirPath);
    wordList = read.readWords();
    for (String s : wordList) {
      System.out.println(s);
    }

  }
}
