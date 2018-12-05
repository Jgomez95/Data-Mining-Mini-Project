import java.util.List;
import java.util.Map;

public class Email implements Comparable<Email>{
  private List<String> wordList;
  private boolean isSpam;
  private boolean classifiedAsSpam;
  private Map<String, Double> wordMap;
  private double similarity;

  public Email(List<String> wordList, boolean isSpam) {
    this.wordList = wordList;
    this.isSpam = isSpam;
  }

  public Email(boolean isSpam, Map<String, Double> wordMap) {
    this.isSpam = isSpam;
    this.wordMap = wordMap;
  }

  public Email(double similarity, boolean isSpam) {
    this.similarity = similarity;
    this.isSpam = isSpam;
  }

  public void setClassified(boolean classifiedAsSpam) {
    this.classifiedAsSpam = classifiedAsSpam;
  }

  public double getSimilarity() {
    return similarity;
  }

  public boolean classifiedAsSpam() {
    return classifiedAsSpam;
  }

  public List<String> getWordList() {
    return wordList;
  }

  public boolean isSpam() {
    return isSpam;
  }

  public void setSpam(boolean spam) {
    isSpam = spam;
  }

  public Map<String, Double> getWordMap() {
    return wordMap;
  }

  @Override public int compareTo(Email o) {
    return Double.compare(o.similarity, similarity);
  }
}
