import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ReadWords {
  private File trainingFolder;
  private File testingFolder;

  private Scanner input = null;

  private HashMap<String, Integer> spamWords = new HashMap();
  private HashMap<String, Integer> hamWords = new HashMap<>();

  /**
   * Constructor method
   *
   * @param TRAININGPATH
   * @param TESTINGPATH
   */
  ReadWords(String TRAININGPATH, String TESTINGPATH) {
    trainingFolder = new File(TRAININGPATH);
    testingFolder = new File(TESTINGPATH);
    readData();
    printSpamWords();
  }

  /**
   * Calls the method to initialiate the read from the folders
   */
  private void readData() {
    listFilesForFolder(trainingFolder);
    //listFilesForFolder(testingFolder);
  }

  /**
   * opens and read the files in the designated folder
   *
   * @param folder
   */
  public void listFilesForFolder(final File folder) {

    for (File fileEntry : folder.listFiles()) {
      if (fileEntry.isDirectory()) {
        listFilesForFolder(fileEntry);
      } else {
        System.out.println(fileEntry.getName());

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
                  spamWords.put(word, 1);
//                  System.out.println("Inserted into spamwords");
                }
              } else {
                //Inserting into the hamWords map if the word already exists
                if (hamWords.containsKey(word)) {
                  hamWords.put(word, hamWords.get(word) + 1);
//                  System.out.println("Inserted into hamWords");
                } else {
                  //if word does not already existing int he hashmap
                  hamWords.put(word, 1);
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

  public HashMap<String, Integer> getSpamWords() {
    return spamWords;
  }

  public HashMap<String, Integer> getHamWords() {
    return hamWords;
  }
}
