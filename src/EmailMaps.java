import java.util.Map;

public class EmailMaps {

  private Map<String, Double> spamWords;
  private Map<String, Double> hamWords;
  private float spamCount;
  private float hamCount;

  EmailMaps(Map<String, Double> spamWords, Map<String, Double> hamWords, float spamCount,
      float hamCount) {
    this.spamWords = spamWords;
    this.hamWords = hamWords;
    this.spamCount = spamCount;
    this.hamCount = hamCount;
  }

  public Map<String, Double> getSpamWords() {
    return spamWords;
  }

  public Map<String, Double> getHamWords() {
    return hamWords;
  }

  public float getSpamCount() {
    return spamCount;
  }

  public float getHamCount() {
    return hamCount;
  }
}
