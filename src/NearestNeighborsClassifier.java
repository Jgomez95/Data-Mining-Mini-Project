import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearestNeighborsClassifier {

  private int K;
  private List<String> wordList = new ArrayList<>();
  private List<Email> emailList = new ArrayList<>();
  private Map<String, Double> wordMap = new HashMap<>();

  NearestNeighborsClassifier(String dirPath, int K) {
    this.K = K;
    ReadFile read = new ReadFile(dirPath);
    wordList = read.readWords();
    emailList = read.readTrainFrequencies(uniqueList(wordList));
    System.out.println(emailList.size());
    for (Email e : emailList) {
      System.out.println(e.wordMap.size());
    }
  }

  private List<String> uniqueList(List<String> duplicates) {
    List<String> unique = new ArrayList<>();
    for (String s : duplicates) {
      if (!unique.contains(s)) {
        unique.add(s);
      }
    }
    return unique;
  }
}
