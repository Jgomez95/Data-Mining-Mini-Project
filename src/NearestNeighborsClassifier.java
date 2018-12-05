import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearestNeighborsClassifier {

  private int K;
  private List<String> wordList;
  private List<Email> trainEmailList;
  private List<Email> testEmailList;
  private Map<String, Double> wordMap = new HashMap<>();

  NearestNeighborsClassifier(String dirPath, int K) {
    this.K = K;
    ReadFile read = new ReadFile(dirPath);
    wordList = uniqueList(read.readWords());
    trainEmailList = read.readFrequencies(wordList, "/train");
    testEmailList = read.readFrequencies(wordList, "/test");
    cosineSimilarities();
  }

  private double cosineSimilarities() {
    for (Email train : trainEmailList) {
      double similarities = 0;
      for (Email test : testEmailList) {
        similarities = (dotProduct(train.getWordMap(), test.getWordMap()))
            / (lenghtOfVector(train.getWordMap()) * lenghtOfVector(test.getWordMap()) );
        System.out.println(similarities);
      }
      break;
    }
    return 0;
  }

  /**
   * Method calculates the dot product of the HashMap of the train and test email that are being
   * compared.
   * @param trainWords, contains all the words and frequencies of the training email
   * @param testWords, contains all the words and frequencies of the testing email
   * @return the dot product between both emails, train and test
   */
  private double dotProduct(Map<String, Double> trainWords, Map<String, Double> testWords) {
    double sum = 0;
    for (Map.Entry<String, Double> entry : trainWords.entrySet()) {
      sum += entry.getValue() + testWords.get(entry.getKey());
    }
    return sum;
  }

  private double lenghtOfVector(Map<String, Double> vector) {
    double length = 0;
    for (Map.Entry<String, Double> entry : vector.entrySet()) {
      length += entry.getValue() * entry.getValue();
    }
    return Math.pow(length, 0.5);
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
