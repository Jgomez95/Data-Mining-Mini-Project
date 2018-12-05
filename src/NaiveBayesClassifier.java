import java.util.List;
import java.util.Map;

/**
 * Article that helped in the making of naive bayes using log probabilities
 * http://www.cs.rhodes.edu/~kirlinp/courses/ai/s17/projects/proj3/naive-bayes-log-probs.pdf
 */
public class NaiveBayesClassifier {

  private Map<String, Double> spamWords;
  private Map<String, Double> hamWords;
  private float trainSpamCount;
  private float trainHamCount;
  private List<Email> emailList;

  NaiveBayesClassifier(String dirPath) {
    ReadFile read = new ReadFile(dirPath);
    EmailMaps maps = read.readTrainData();
    List<Email> emailList = read.readTestData();
    if (maps != null && emailList != null) {
      this.spamWords = maps.getSpamWords();
      this.hamWords = maps.getHamWords();
      this.trainSpamCount = maps.getSpamCount();
      this.trainHamCount = maps.getHamCount();
      this.emailList = emailList;
    }
    classify();
  }

  private void classify() {
    double spamCount = 0;
    double hamCount = 0;
    double spamProbability = 0;
    double hamProbability = 0;
    int spamAccuracy = 0;
    int hamAccuracy = 0;
    for (Email e : emailList) {
      if (e.isSpam) {
        spamCount++;
      } else {
        hamCount++;
      }
      for (String word : e.getWordList()) {
        spamProbability += spamWords.containsKey(word) ?
            mEstimate(spamWords.get(word), trainSpamCount) :
            mEstimate(0, trainSpamCount);
        hamProbability += hamWords.containsKey(word) ?
            mEstimate(hamWords.get(word), trainHamCount) :
            mEstimate(0, trainHamCount);

        spamProbability += emailProbability(trainSpamCount);
        hamProbability += emailProbability(trainHamCount);
      }
      if (spamProbability > hamProbability) {
        spamAccuracy += e.isSpam ? 1 : 0;
      } else {
        hamAccuracy += e.isSpam ? 0 : 1;
      }
      spamProbability = 0;
      hamProbability = 0;
    }
    double spam = (spamAccuracy / spamCount) * 100;
    double ham = (hamAccuracy / hamCount) * 100;
    double total = (spamAccuracy + hamAccuracy) / (spamCount + hamCount) * 100;
    System.out.println("For Naive Bayes...");
    System.out.printf("Spam Accuracy: %.2f%n", spam);
    System.out.printf("Ham Accuracy: %.2f%n", ham);
    System.out.printf("Total Accuracy: %.2f%n", total);
  }

  /**
   * ln (1 / total number of email + spamwords + hamwords
   */
  private double mEstimate(double wordFrequency, double numOfEmails) {
    double m = spamWords.size() + hamWords.size();
    double p = 1 / m;
    return Math.log((wordFrequency + (m * p)) / (numOfEmails + m));
  }

  private double emailProbability(double numOfEmails) {
    return Math.log((numOfEmails) / (trainHamCount + trainSpamCount));
  }
}
