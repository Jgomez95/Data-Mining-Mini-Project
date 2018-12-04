import java.util.List;
import java.util.Map;

public class Email {
  List<String> wordList;
  boolean isSpam;
  Map<String, Double> wordMap;

  public Email(List<String> wordList, boolean isSpam) {
    this.wordList = wordList;
    this.isSpam = isSpam;
  }

  public Email(List<String> wordList, boolean isSpam, Map<String, Double> wordMap) {
    this.wordList = wordList;
    this.isSpam = isSpam;
    this.wordMap = wordMap;
  }

  public List<String> getWordList() {
    return wordList;
  }

  public void setWordList(List<String> wordList) {
    this.wordList = wordList;
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

  public void setWordMap(Map<String, Double> wordMap) {
    this.wordMap = wordMap;
  }
}
