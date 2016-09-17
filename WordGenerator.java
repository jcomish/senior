
import java.io.*;
import java.util.ArrayList;
import java.awt.image.*;
import javax.imageio.*;

import java.util.Vector;

/*******************************************************************************************************************
 * WordGenerator is the main driver for the program.
 *******************************************************************************************************************/
class WordGenerator
{
    private static Vector<Character> dLetters = new Vector<Character>();
    private static ArrayList<String> wordList = new ArrayList<String>();

    /*******************************************************************************************************************
     * Main
     *******************************************************************************************************************/
    public static void main(String[] args)
    {
        try
        {
            ImageConverter imageC = new ImageConverter();
            BufferedImage image = new BufferedImage(200, 300, BufferedImage.TYPE_INT_RGB);
            image = ImageIO.read(new File("Test.jpg"));
            image = imageC.GreyscaleImage(image);
            EdgeDetector temp = new EdgeDetector(image);
            Vector<Vector<Coordinate>> vector = temp.die;
            CharacterDetector letterReader = new CharacterDetector(image, vector);
            dLetters = letterReader.letters;

            System.out.println("\n\nLETTER RESULT");
            for (int i = 0; i < dLetters.size(); i++)
                System.out.println("LETTER: " + dLetters.elementAt(i));
        }
        catch (Exception e)
        {
            System.out.println("ERROR: failed to get letters.");
        }

        try
        {
            ReadDictionary();
        }
        catch(IOException e)
        {
            System.out.println("ERROR: failed to read dictonary.");
        }
        BuildList();
        return;
   }

    /*******************************************************************************************************************
     * ReadDictionary will read in all of the words in a text file and put them in wordList.
     *******************************************************************************************************************/
    private static void ReadDictionary() throws IOException {
        String filePath = "wordlist.txt";
        FileReader fr = new FileReader(filePath);
        BufferedReader textReader = new BufferedReader(fr);
        String sCurrentLine;
        while ((sCurrentLine = textReader.readLine()) != null) {
            wordList.add(sCurrentLine);
        }
   }

    /*******************************************************************************************************************
     * BuildList will use the characters in wordList and build a list of possible words with the letters specified dLetters.
     *******************************************************************************************************************/
   private static void BuildList()
   {
       ArrayList<String> newList = new ArrayList<String>();
       ArrayList<Character> letterss = new ArrayList<Character>();

       for (String cWord : wordList) {
           Boolean isValid = true;
           letterss.clear();
           //Keep a separate reference of the letters.
           for (int i = 0; i < dLetters.size(); i++)
               letterss.add(dLetters.elementAt(i));

           //Convert the sequence to a list of chars
           ArrayList<Character> word = new ArrayList<Character>();
           for (int z = 0; z < cWord.length(); z++)
               word.add(cWord.charAt(z));

           outerloop:
           for (int j = 0; j < letterss.size(); ++j)
           {
               innerloop:
               for (int i = 0; i < word.size(); ++i)
               {
                   if (letterss.get(j) == word.get(i))
                   {
                       letterss.remove(j);
                       j--;
                       word.remove(i);
                       if (word.isEmpty())
                           break outerloop;
                       break innerloop;
                   }
               }
           }

           if (word.size() > 0)
               isValid = false;

           if (isValid)
               newList.add(cWord);
       }

       for (String cWord : newList)
           System.out.println(cWord);
   }
}