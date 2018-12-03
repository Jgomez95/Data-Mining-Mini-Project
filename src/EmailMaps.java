import java.util.Map;

public class EmailMaps {

  private Map<String, Double> spamWords;
  private Map<String, Double> hamWords;

  EmailMaps(Map<String, Double> spamWords, Map<String, Double> hamWords) {
    this.spamWords = spamWords;
    this.hamWords = hamWords;
  }

  public Map<String, Double> getSpamWords() {
    return spamWords;
  }

  public void setSpamWords(Map<String, Double> spamWords) {
    this.spamWords = spamWords;
  }

  public Map<String, Double> getHamWords() {
    return hamWords;
  }

  public void setHamWords(Map<String, Double> hamWords) {
    this.hamWords = hamWords;
  }
}
