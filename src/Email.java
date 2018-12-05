import java.util.List;
import java.util.Map;

public class Email {
  private List<String> wordList;
  private boolean isSpam;
  private boolean classified;
  private Map<String, Double> wordMap;

  public Email(List<String> wordList, boolean isSpam) {
    this.wordList = wordList;
    this.isSpam = isSpam;
  }

  public Email(boolean isSpam, Map<String, Double> wordMap) {
    this.isSpam = isSpam;
    this.wordMap = wordMap;
  }

  public boolean isClassified() {
    return classified;
  }

  public void setClassified(boolean classified) {
    this.classified = classified;
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

}
