import java.io.*;
import java.util.ArrayList;

class WordGenerator
{
   private static String letters;
   private static ArrayList<String> wordList;
   
   public static void main(String[] args)
   {
      GetCharacters();
      ReadDictionary();
      BuildList();
   }
   
   public WordGenerator(String characters)
   {
      letters = characters;
      wordList = new ArrayList<String>();
   }
   
   private static void GetCharacters()
   {
      letters = System.console().readLine("Please input 1-13 characters: ");
      return;
   }
   
   private static void ReadDictionary()
   {
      System.out.println(letters);
   }
   
   private static void BuildList()
   {
   }
}