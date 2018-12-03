import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NaiveBayesClassifier {

  private Map<String, Double> spamWords;
  private Map<String, Double> hamWords;
  private float spamCount;
  private float hamCount;
  private List<Email> emailList;

  NaiveBayesClassifier(String dirPath) {
    ReadFile read = new ReadFile(dirPath);
    EmailMaps maps = read.readTrainData();
    List<Email> emailList = read.readTestData();
    if (maps != null && emailList != null) {
      this.spamWords = maps.getSpamWords();
      this.hamWords = maps.getHamWords();
      this.spamCount = maps.getSpamCount();
      this.hamCount = maps.getHamCount();
      this.emailList = emailList;
    }
    test();
  }

  private void test() {
    float spamProbability = 0;
    float hamProbability = 0;
    float spamAccuracy = 0;
    float hamAccuracy = 0;
    float hamTestCount = 0;
    float spamTestCount = 0;
    System.out.println(spamCount + "\t" + hamCount);
    for (Email e : emailList) {
      if (e.isSpam) {
        System.out.println(spamTestCount);
        spamTestCount++;
      } else {
        System.out.println(hamTestCount);
        hamTestCount++;
      }
      for (String word : e.wordList) {
        if (spamWords.containsKey(word)) {
          spamProbability += Math.log10(spamWords.get(word) / spamCount);
        } else{
          spamProbability += Math.log10(1 / (spamCount + 1));
        }
        if (hamWords.containsKey(word)) {
          hamProbability += Math.log10(hamWords.get(word) / hamCount);
        } else {
          hamProbability += Math.log10(1 / (hamCount + 1));
        }
      }
      spamProbability += Math.log10(spamCount / (spamCount + hamCount));
      hamProbability += Math.log10(spamCount / (spamCount + hamCount));
      if (spamProbability > hamProbability) {
        spamAccuracy = e.getIsSpam() ? spamAccuracy++ : spamAccuracy;
      } else {
        hamAccuracy = e.getIsSpam() ? hamAccuracy : hamAccuracy++;
      }
      spamProbability = 0;
      hamProbability = 0;
    }

    //Used to calculate the accuracies
    int mPercent = (int) ((hamAccuracy / hamTestCount) * 100);
    int sPercent = (int) ((spamAccuracy / spamTestCount) * 100);
    int tPercent = (int) (((spamAccuracy + hamAccuracy) / (spamTestCount + hamTestCount)) * 100);

    String s = "------Naive Bayes-----\nMail: " + mPercent + "% Accuracy\nSpam:  " + sPercent + "% Accuracy" + "\nOverall: " + tPercent
        + "% Accuracy";
    System.out.println(s);
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
