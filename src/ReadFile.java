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
  private Scanner in;

  public ReadFile(String dirPath) {
    this.dirPath = dirPath;
  }

  List<Email> readTestData() {
    List<Email> emailList = new ArrayList<>();
    File folder = new File(dirPath + "/test");
    if (!folder.exists()) {
      System.out.println("Cannot recognize test folder.\n"
          + "Make sure your directory has a test folder. Should look like: " + dirPath +"/test");
      return null;
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
          StringTokenizer st = new StringTokenizer(line);
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
    return emailList;
  }

  EmailMaps readTrainData() {
    HashMap<String, Double> spamWords = new HashMap<>();
    HashMap<String, Double> hamWords = new HashMap<>();
    float spamCount = 0;
    float hamCount = 0;
    boolean isSpam = false;
    File folder = new File(dirPath + "/train");
    if (!folder.exists()) {
      System.out.println("Cannot recognize train folder.\n"
          + "Make sure your directory has a train folder. Should look like: " + dirPath +"/train");
      return null;
    }
    for (File file : Objects.requireNonNull(folder.listFiles())) {
      try {
        in = new Scanner(new File("train/" + file.getName()));
      } catch (FileNotFoundException e) {
        // do nothing
      }
      if (file.getName().contains("spm")) {
        spamCount++;
        isSpam = true;
      } else {
        hamCount++;
        isSpam = false;
      }

      String line;
      String word;
      try {
        while ((line = in.nextLine()) != null) {
          StringTokenizer st = new StringTokenizer(line);
          while (st.hasMoreTokens()) {
            word = st.nextToken().toLowerCase();
            if (isSpam && !isNumeric(word)) {
              if (spamWords.containsKey(word)) {
                spamWords.put(word, spamWords.get(word) + 1);
              } else {
                spamWords.put(word, 1d);
              }
            } else {
              if (hamWords.containsKey(word)) {
                hamWords.put(word, hamWords.get(word) + 1);
              } else {
                hamWords.put(word, 1d);
              }
            }
          }
        }
      } catch (NoSuchElementException e) {
        // do nothing
      }
    }
    return new EmailMaps(spamWords, hamWords, spamCount, hamCount);
  }

  private boolean isNumeric(String s) {
    try {
      Double.parseDouble(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}
