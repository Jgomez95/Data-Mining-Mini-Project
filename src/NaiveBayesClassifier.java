import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NaiveBayesClassifier {

  private Map<String, Double> spamWords;
  private Map<String, Double> hamWords;
  private List<Email> emailList;

  NaiveBayesClassifier(Map<String, Double> spamWords, Map<String, Double> hamWords) {
    this.spamWords = spamWords;
    this.hamWords = hamWords;
    classify();
  }

  private void classify() {
    double spamPercentage = 1;
    double hamPercentage = 1;
    double spamAccuracy = 0;
    double hamAccuracy = 0;
    int spamCount = 0;
    int hamCount = 0;
    int hamSize = hamWords.size();
    int spamSize = spamWords.size();

    //loop through the files in test and check for every word.
    ReadWords testdata = new ReadWords();
    emailList = testdata.getEmailList();

    for (Email e : emailList) {
      if (e.isSpam) {
        spamCount++;
      } else {
        hamCount++;
      }

      for (String word : hamWords.keySet()) {
        if (e.wordList.contains(word)) {
          hamPercentage += Math.log10(hamWords.get(word) / hamSize);
        } else {
          hamPercentage += Math.log10((hamSize - hamWords.get(word)) / hamSize);
        }
      }

      for (String word : spamWords.keySet()) {
        if (e.wordList.contains(word)) {
          spamPercentage += Math.log10(spamWords.get(word) / spamSize);
        } else {
          spamPercentage += Math.log10((spamSize - spamWords.get(word)) / spamSize);
        }
      }
      if (hamPercentage > spamPercentage) {
        if (e.isSpam) {
          hamAccuracy++;
        }
      } else {
        if (!e.isSpam) {
          spamAccuracy++;
        }
      }
      System.out.println("ham accuracy: " + hamAccuracy);
      System.out.println("spam accuracy: " + spamAccuracy);
      hamPercentage = 1;
      spamPercentage = 1;
    }
    System.out.println();
    System.out.println("ham accuracy: " + hamAccuracy);
    System.out.println("spam accuracy: " + spamAccuracy);
    System.out.println(hamCount);
    System.out.println(spamCount);

    System.out.println("SPAM Accuracy: " + ((int) spamAccuracy / spamCount));
  }
}
