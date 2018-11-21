import java.util.List;

public class Email {
  List<String> wordList;
  boolean isSpam;

  public Email(List<String> wordList, boolean isSpam) {
    this.wordList = wordList;
    this.isSpam = isSpam;
  }
}
