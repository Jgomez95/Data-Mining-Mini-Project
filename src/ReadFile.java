import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Regex used to get rid of symbols grabbed from: https://stackoverflow.com/questions/8359566/regex-to-match-symbols
 * Class reads the files, it has multiple methods in charge of doing separate things, as reading
 * all the words in all the training data, creating a hashMap with the training and test data
 * frequencies
 */
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
          + "Make sure your directory has a test folder. Should look like: " + dirPath + "/test");
      return null;
    }
    ArrayList<String> textWordList;
    for (File file : Objects.requireNonNull(folder.listFiles())) {
      try {
        in = new Scanner(file);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      textWordList = new ArrayList<>();
      boolean isSpam = file.getName().contains("spm");
      String line;
      String word;
      try {
        while ((line = in.next()) != null) {
          StringTokenizer st =
              new StringTokenizer(line, " /[-!$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/]/");

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
          + "Make sure your directory has a train folder. Should look like: " + dirPath + "/train");
      return null;
    }
    for (File file : Objects.requireNonNull(folder.listFiles())) {
      try {
        in = new Scanner(file);
      } catch (FileNotFoundException e) {
        // do nothing
        e.printStackTrace();
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
          StringTokenizer st =
              new StringTokenizer(line, " /[-!$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/]/");
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

  List<String> readWords() {
    List<String> wordList = new ArrayList<>();
    File folder = new File(dirPath + "/train");
    if (!folder.exists()) {
      return null;
    }
    for (File file : Objects.requireNonNull(folder.listFiles())) {
      try {
        in = new Scanner(file);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      String line;
      String word;
      try {
        while ((line = in.next()) != null) {
          StringTokenizer st =
              new StringTokenizer(line, " /[-!$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/]/");
          while (st.hasMoreTokens()) {
            word = st.nextToken().toLowerCase();
            if (isNumeric(word)) continue;
            wordList.add(word);
          }
        }
      } catch (NoSuchElementException e) {
        // do nothing
      }
    }
    return wordList;
  }

  List<Email> readFrequencies(List<String> wordList, String path) {
    List<Email> emailList = new ArrayList<>();
    File folder = new File(dirPath + path);
    for (File file : folder.listFiles()) {
      try {
        in = new Scanner(file);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      Map<String, Double> wordMap = new HashMap<>();
      // insert all the words into the word map and initialize with 0
      for (String s : wordList) {
        wordMap.put(s, 0d);
      }
      boolean isSpam = file.getName().contains("spm");
      String line;
      String word;
      try {
        while ((line = in.next()) != null) {
          StringTokenizer st =
              new StringTokenizer(line, " /[-!$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/]/");

          while (st.hasMoreTokens()) {
            word = st.nextToken().toLowerCase();
            if (wordMap.containsKey(word)) {
              wordMap.put(word, wordMap.get(word) + 1);
            }
          }
        }
      } catch (NoSuchElementException e) {
        // do nothing
      }
      emailList.add(new Email(isSpam, wordMap));
    }
    return emailList;
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
