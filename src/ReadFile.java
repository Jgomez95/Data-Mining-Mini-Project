import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ReadFile {

  private String dirPath;
  private File folder;
  private Scanner in;
  private HashMap<String, Double> spamWords = new HashMap<>();
  private HashMap<String, Double> hamWords = new HashMap<>();
  private List<Email> emailList = new ArrayList<>();

  public ReadFile(String dirPath) {
    this.dirPath = dirPath;
    readTestData();
  }

  private void readTestData() {
    File folder = new File(dirPath + "/test");
    if (!folder.exists()) {
      System.out.println("Not a valid directory path");
      return;
    }
    ArrayList<String> textWordList;
    for (File file: Objects.requireNonNull(folder.listFiles())) {
      try {
        in = new Scanner(new File("test/" + file.getName()));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      textWordList = new ArrayList<>();
      boolean isSpam = file.getName().contains("spm");
      String line;
      String word;
      try {
        while ((line = in.next()) != null) {
          StringTokenizer st = new StringTokenizer(line, "\t\n\r\f,.:;?![]'");
          while (st.hasMoreTokens()) {
            word = st.nextToken().toLowerCase();
            if (isNumeric(word)) continue;
            textWordList.add(word);
          }
        }
      } catch (NoSuchElementException e) {
        // do nothing
      }
      emailList.add(new Email(textWordList, isSpam));
    }
  }

  private boolean isNumeric(String s) {
    try {
      Double.parseDouble(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  public List<Email> getEmailList() {
    return emailList;
  }
}
