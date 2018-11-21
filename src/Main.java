import java.io.File;

public class Main {

  public static void main(String[] args) {
    ReadWords readWords = new ReadWords("train", "test");
    readWords.readData();
  }

}
