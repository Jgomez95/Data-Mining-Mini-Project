import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class classifies for KNN
 */
public class NearestNeighborsClassifier {

  private int K;
  private List<String> wordList;
  private List<Email> trainEmailList;
  private List<Email> testEmailList;

  /**
   * Constructor that sets up the path for the directory and the K for the class
   * @param dirPath
   * @param K
   */
  NearestNeighborsClassifier(String dirPath, int K) {
    this.K = K;
    ReadFile read = new ReadFile(dirPath);
    wordList = uniqueList(read.readWords());
    trainEmailList = read.readFrequencies(wordList, "/train");
    testEmailList = read.readFrequencies(wordList, "/test");
    cosineSimilarities();
    getAccuracy();
  }

  /**
   * Method calculates the accuracy of KNN
   */
  private void getAccuracy() {
    double testSpamCount = 0;
    double testHamCount = 0;
    double trainSpamAccuracy = 0;
    double trainHamAccuracy = 0;
    for (Email e : testEmailList) {
      if (e.isSpam()) {
        testSpamCount++;
        if (e.classifiedAsSpam()) {
          trainSpamAccuracy++;
        }
      } else {
        testHamCount++;
        if (!e.classifiedAsSpam()) {
          trainSpamAccuracy++;
        }
      }
    }
    System.out.println("The accuracy for KNN with " + K + " neighbors is...");
    System.out.println("Spam: " + (trainSpamAccuracy / testSpamCount) * 100);
    System.out.println("Ham: " + (trainHamAccuracy/ testHamCount) * 100);
  }

  /**
   * Method uses cosine similarities to calculate the cosine similarities and then calculates
   * the boolean value of the test email on it's K closer neighbors. It looks for the highest value
   * in the cosine similarity, since the higher the value, the closer they are.
   */
  private void cosineSimilarities() {
    for (Email test : testEmailList) {
      double similarities;
      List<Email> emailSimilarity = new ArrayList<>();
      for (Email train : trainEmailList) {
        similarities = (dotProduct(test.getWordMap(), train.getWordMap()))
            / (lengthOfVector(test.getWordMap()) * lengthOfVector(train.getWordMap()));
        emailSimilarity.add(new Email(similarities, train.isSpam()));
      }
      Collections.sort(emailSimilarity);
      test.setClassified(classify(emailSimilarity));
    }
  }

  private boolean classify(List<Email> list) {
    int spam = 0;
    for (int i = 0; i < K; i++) {
      spam += list.get(i).isSpam() ? 1 : 0;
    }
    return spam > 0;
  }

  /**
   * Method calculates the dot product of the HashMap of the train and test email that are being
   * compared.
   *
   * @param trainWords, contains all the words and frequencies of the training email
   * @param testWords, contains all the words and frequencies of the testing email
   * @return the dot product between both emails, train and test
   */
  private double dotProduct(Map<String, Double> testWords, Map<String, Double> trainWords) {
    double sum = 0;
    for (Entry<String, Double> entry : testWords.entrySet()) {
      sum += entry.getValue() + trainWords.get(entry.getKey());
    }
    return sum;
  }

  /**
   * Method calculates the length of the vector, it achieves this by doing the summation of every
   * value in the Map multiplied by itself. It then calculates the value of the summation to the
   * power of 0.5 which is the formula for length of vector.
   *
   * @param vector, HashMap with the words and their frequencies
   * @return length of the vector
   */
  private double lengthOfVector(Map<String, Double> vector) {
    double length = 0;
    for (Entry<String, Double> entry : vector.entrySet()) {
      length += entry.getValue() * entry.getValue();
    }
    return Math.sqrt(length);
  }

  /**
   * Method parses through a list of repeated words and creates a list where it adds all words that
   * do not exist, it creates a unique word list.
   *
   * @param duplicates, list of words containing duplicates
   * @return a list of strings without repeated values
   */
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
