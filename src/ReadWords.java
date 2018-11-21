import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ReadWords {
    private File trainingFolder;
    private File testingFolder;

    private Scanner input = null;

    private HashMap<String, Integer> spamWords = new HashMap();
    private HashMap<String, Integer> ham = new HashMap<>();

    /**
     * Constructor method
     * @param TRAININGPATH
     * @param TESTINGPATH
     */
    public ReadWords(String TRAININGPATH, String TESTINGPATH){
        trainingFolder = new File(TRAININGPATH);
        testingFolder = new File(TESTINGPATH);
    }

    /**
     * Calls the method to initialiate the read from the folders
     */
    public void readData() {
        listFilesForFolder(trainingFolder);
        //listFilesForFolder(testingFolder);
    }

    /**
     * opens and read the files in the designated folder
     * @param folder
     */
    public void listFilesForFolder(final File folder){

        for (File fileEntry : folder.listFiles()){
            if(fileEntry.isDirectory()){
                listFilesForFolder(fileEntry);
            }
            else {
                System.out.println(fileEntry.getName());

                // Opening and reading the files will be done here
                try {
                    input = new Scanner(new File(folder + "/" + fileEntry.getName()));
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }

                String line;
                String word = null;
                try {
                    while ((line = input.nextLine()) != null) {
                        StringTokenizer st = new StringTokenizer(line);
                        while (st.hasMoreTokens()) {
                            word = st.nextToken().toLowerCase();
                                if(fileEntry.getName().contains("spm")){
                                    if(spamWords.containsKey(word)){
                                        spamWords.put(word, spamWords.get(word)+1);
                                        System.out.println("Inserted into spamwords");
                                    } else{
                                        //if word does not already existing int he hashmap
                                        spamWords.put(word, 1);
                                        System.out.println("Inserted into spamwords");
                                    }
                                } else {
                                    //Inserting into the ham map if the word already exists
                                    if (ham.containsKey(word)) {
                                        ham.put(word, ham.get(word) + 1);
                                        System.out.println("Inserted into ham");
                                    } else {
                                        //if word does not already existing int he hashmap
                                        ham.put(word, 1);
                                        System.out.println("Inserted into ham");
                                    }
                                }
                        }
                    }
                } catch (NoSuchElementException e){

                }
            }
        }
    }


}
