import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ReadWords {
  private File folder;

  private Scanner input;

  private HashMap<String, Double> spamWords = new HashMap();
  private HashMap<String, Double> hamWords = new HashMap<>();
  private List<Email> emailList = new ArrayList<>();
  /**
   * Default constructor
   */
  ReadWords() {
    readTestData();
  }
  /**
   * Constructor method
   */
  ReadWords(String path) {
    folder = new File(path);
    readData();
  }

  void readTestData() {
    File testFolder = new File("test");
    ArrayList<String> fileWordList;
    for (File file : Objects.requireNonNull(testFolder.listFiles())) {
      try {
        input = new Scanner(new File("test/" + file.getName()));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      fileWordList = new ArrayList<>();
      boolean isSpam = file.getName().contains("spm");
      String line;
      String word;
      try {
        while ((line = input.next()) != null) {
          StringTokenizer st = new StringTokenizer(line, "\t\n\r\f,.:;?![]'");
          while (st.hasMoreTokens()) {
            word = st.nextToken().toLowerCase();
            if (isNumeric(word)) {
              continue;
            }
            fileWordList.add(word);
          }
        }
      } catch (NoSuchElementException e) {
        // do nothing
      }
      emailList.add(new Email(fileWordList, isSpam));
    }
  }

  /**
   * Calls the method to initialiate the read from the folders
   */
  private void readData() {
    listFilesForFolder(folder);
  }

  /**
   * opens and read the files in the designated folder
   */
  private void listFilesForFolder(final File folder) {

    for (File fileEntry : folder.listFiles()) {
      if (fileEntry.isDirectory()) {
        listFilesForFolder(fileEntry);
      } else {
        //System.out.println(fileEntry.getName());

        // Opening and reading the files will be done here
        try {
          input = new Scanner(new File(folder + "/" + fileEntry.getName()));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }

        String line;
        String word;
        try {
          while ((line = input.nextLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, " \t\n\r\f,.:;?![]'");
            while (st.hasMoreTokens()) {
              word = st.nextToken().toLowerCase();
              if (fileEntry.getName().contains("spm") && !isNumeric(word)) {
                if (spamWords.containsKey(word)) {
                  spamWords.put(word, spamWords.get(word) + 1);
                  //                  System.out.println("Inserted into spamwords");
                } else {
                  //if word does not already existing int he hashmap
                  spamWords.put(word, 1d);
                  //                  System.out.println("Inserted into spamwords");
                }
              } else {
                //Inserting into the hamWords map if the word already exists
                if (hamWords.containsKey(word)) {
                  hamWords.put(word, hamWords.get(word) + 1);
                  //                  System.out.println("Inserted into hamWords");
                } else {
                  //if word does not already existing int he hashmap
                  hamWords.put(word, 1d);
                  //                  System.out.println("Inserted into hamWords");
                }
              }
            }
          }
        } catch (NoSuchElementException e) {
          // do nothing
        }
      }
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

  private void printSpamWords() {
    for (String word : spamWords.keySet()) {
      System.out.println(word + " = " + spamWords.get(word));
    }
  }

  private void printHamWords() {
    for (String word : hamWords.keySet()) {
      System.out.println(word + " = " + hamWords.get(word));
    }
  }

  HashMap<String, Double> getSpamWords() {
    return spamWords;
  }

  HashMap<String, Double> getHamWords() {
    return hamWords;
  }

  public List<Email> getEmailList() {
    return emailList;
  }
}
