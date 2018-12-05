import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
        if (e.isClassified()) {
          trainSpamAccuracy++;
        }
      } else {
        testHamCount++;
        if (!e.isClassified()) {
          trainSpamAccuracy++;
        }
      }
    }
    System.out.println("The accuracy for KNN with " + K + " neighbors is...");
    System.out.println("Spam: " + (trainSpamAccuracy / testSpamCount));
    System.out.println("Ham: " + (trainHamAccuracy/ testHamCount));
  }

  /**
   * Method uses cosine similarities to calculate the cosine similarities and then calculates
   * the boolean value of the test email on it's K closer neighbors. It looks for the highest value
   * in the cosine similarity, since the higher the value, the closer they are.
   */
  private void cosineSimilarities() {
    for (Email test : testEmailList) {
      double similarities;
      List<Double> similaritiesList = new ArrayList<>();
      Map<Double, Boolean> similaritiesMap = new HashMap<>();
      for (Email train : trainEmailList) {
        similarities = (dotProduct(test.getWordMap(), train.getWordMap()))
            / (lenghtOfVector(test.getWordMap()) * lenghtOfVector(train.getWordMap()));
        similaritiesList.add(similarities);
        similaritiesMap.put(similarities, train.isSpam());
      }
      test(similaritiesList);
      Collections.sort(similaritiesList);
      List<Double> topK = new ArrayList<>(
          similaritiesList.subList(similaritiesList.size() - K, similaritiesList.size()));
      System.out.println(topK);
      System.out.println(similaritiesList.get(similaritiesList.size() - 3));
      System.out.println(similaritiesList.get(similaritiesList.size() - 2));
      System.out.println(similaritiesList.get(similaritiesList.size() - 1));

      test.setClassified(findIsSpam(topK, similaritiesMap, test.isSpam()));
      break;
    }
  }

  void test(List<Double> list) {
    System.out.println("List size: " + list.size());
    List<Double> temp = new ArrayList<>();
    for (Double d : list) {
      if (!temp.contains(d)) {
        temp.add(d);
      }
    }
    System.out.println("Temp size: " + temp.size());
  }

  /**
   * Method assigns the value of spam or not spam to the test email
   * @param list of K similarities found
   * @param map of similarities and their boolean values
   * @return boolean value determined by K close neighbors.
   */
  private boolean findIsSpam(List<Double> list, Map<Double, Boolean> map, boolean original) {
    int valueTrue = 0;
    int valueFalse = 0;
    for (Double d : list) {
      if (map.get(d)) {
        valueTrue++;
      } else {
        valueFalse++;
      }
    }
    System.out.println("false: " + valueFalse);
    System.out.println("true: " + valueTrue);
    System.out.println(original + "\t" + (valueTrue > valueFalse));
    return valueTrue > valueFalse;
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
  private double lenghtOfVector(Map<String, Double> vector) {
    double length = 0;
    for (Entry<String, Double> entry : vector.entrySet()) {
      length += entry.getValue() * entry.getValue();
    }
    return Math.pow(length, 0.5);
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
