import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.Image;
import java.util.Vector;
import java.awt.image.BufferedImage;

class CharacterDetector
{
   BufferedImage imageToRead;
   Vector<Vector<Coordinate>> die = new Vector<Vector<Coordinate>>();
   Vector<Character> letters = new Vector<Character>();

   int xmax = 0;
   int xmin = 2000;
   int ymax = 0;
   int ymin = 2000;

   double blacks;
   double whites;
   double blackThree;
   double blackFour;
   double blackFive;
   double blackSix;
   double blackSeven;
   double blackEight;
   double whiteThree;
   double whiteFour;
   double whiteFive;
   double whiteSix;
   double whiteSeven;
   double whiteEight;

   /*******************************************************************************************************************
    * constructor
    *******************************************************************************************************************/
   public CharacterDetector(BufferedImage image, Vector<Vector<Coordinate>> vector)
   {
      imageToRead = image;
      die = vector;
      for (int i = 0; i < die.size(); i++)
      {
         findCoordinates(i);
         countFeatures(i);
         DetectCharacter(i);
      }
   }

   /*******************************************************************************************************************
    * constructor
    *******************************************************************************************************************/
   private void DetectCharacter(int dieIndex)
   {
      blacks = die.elementAt(dieIndex).size();

      double centerRatio = blackEight / blacks;
      if (centerRatio < .5 || centerRatio > .75)
         return;

      if (xmax < 220)
         blacks -= 4;
      //if (xmax < 220)
      //   blacks += 4;
      if (xmin > 295)
         blacks += 10;
      if (ymax < 190)
         blacks -= 4;
      if (ymin > 222)
         blacks += 10;
      //if (ymax > 230)
      //   blacks += 10;

      double[] letterScores = new double[26];
      letterScores[0] = score(629, 489, 6, 37, 65, 45, 40, 421, 0.65, 0.90,
              28.17304385, -61.38941399, 4.709220349, 76.53634852, 1.12508928,
              28.17304385, -61.38941399, 4.709220349, 76.53634852);    //A 0

      letterScores[1] = score(768, 318, 1, 16, 155, 22, 40, 535, 0.38, 0.90,
              28.87933208, -62.47479073, 28.50631712, 26.08918668, 2.708132364,
              9.707338513, -33.84051164, 12.95741687, 33.91594268);   //B 2 776

      letterScores[2] = score(599, 576, 3, 33, 86, 33, 54, 380, 0.8, 0.93,
              45.19392213, -72.08779234, 20.54468364, 60.25061684, 0.984097607,
              45.19392213, -72.08779234, 20.54468364, 60.25061684);  //C 2

      letterScores[3] = score(712, 415, 2, 16, 141, 20, 31, 468, 0.54, 0.92,
              61.14294796, -129.4970929, 56.29713901, 69.65211933, 1.589825357,
              26.97482998, -76.33512843, 22.5188556, 79.60242209);     //D 3

      letterScores[4] = score(680, 321, 5, 16, 164, 28, 10, 469, 0.4, 0.8,
              58.92632739, -130.8539221, 57.17491283, 60.32187022, 1.687019649,
              23.57053096, -77.2661254, 23.82288034, 82.54571714);   //E 4

      letterScores[5] = score(513, 409, 7, 6, 135, 10, 10, 357, 0.57, 0.9,
              73.45072995, -189.1185186, 80.21218485, 129.6975582, 1.149088611,
              96.6456973, -215.9103087, 100.731581, 97.90894103);  //F 5

      letterScores[6] = score(745, 491, 7, 34, 118, 38, 57, 495, 0.66, 0.92,
              44.84125869, -96.28211747, 44.62356409, 48.38744056, 1.390633865,
              26.71394135, -50.4334901, 14.02454871, 61.29075805);   //G 6

      letterScores[7] = score(696, 438, 8, 5, 157, 12, 11, 457, 0.37, 0.75,
              68.60666956, -149.0748933, 66.04106314, 83.2984365, 1.475292496,
              68.60666956, -149.0748933, 66.04106314, 83.2984365);   //H 7

      letterScores[8] = score(292, 0, 0, 0, 0, 0, 0, 0, 0.03, 0.94,
              0.417302972, -1.450861971, 0.221736105, 2.431270696, 21.11074115,
              1.251908915, -2.686781428, 1.256504593, 1.228377857);  //I 8

      letterScores[9] = score(431, 389, 3, 11, 94, 11, 17, 286, 0.8, 0.92,
              95.25293261, -356.9390101, 117.7897677, 335.6941689, 0.991965027,
              72.26084543, -178.469505, 82.45283741, 89.51844503);   //J 9

      letterScores[10] = score(698, 474, 5, 41, 111, 41, 62, 444, 0.425, 0.9,
              55.62018303, -115.8399215, 56.75223973, 12.64304333, 1.223419103,
              37.92285206, -82.05327775, 40.46681442, 9.030745236);  //K 10

      letterScores[11] = score(425, 525, 4, 9, 99, 9, 19, 270, 0.75, 0.9,
              261.5750112, -610.4276985, 272.3419533, 356.8261088, 0.775193939,
              122.0683386, -263.9687345, 108.0722037, 171.2765322);   //L 11

      letterScores[12] = score(909, 358, 9, 23, 201, 27, 32, 597, 0.35, 0.85,
              13.44255631, -49.38436361, 11.4179796, 75.79135142, 2.241074612,
              29.12553868, -68.5893939, 26.91380906, 37.89567571);   //M 12

      letterScores[13] = score(761, 397, 6, 36, 122, 30, 41, 466, 0.35, 0.92,
              34.29919551, -73.11248031, 26.33812154, 47.92352211, 1.903197392,
              29.0009458, -61.36226026, 28.02646267, 24.07751836);  //N 13

      letterScores[14] = score(715, 551, 0, 39, 92, 39, 60, 466, 0.88, 0.92,
              65.1662363, -153.498493, 65.1662363, 62.40872405, 1.218182742,
              65.1662363, -153.498493, 65.1662363, 62.40872405);  //O 14

      letterScores[15] = score(609, 394, 2, 16, 113, 20, 21, 421, 0.614, 0.93,
              50.03205206, -127.2799897, 55.82933711, 75.59401485, 1.4798966,
              36.86572257, -98.75171612, 26.05369065, 106.1915923);    //P 15

      letterScores[16] = score(766, 548, 2, 44, 95, 46, 69, 500, 0.779, 0.94,
              -26.73354433, 12.16874107, 16.20565765, 15.7808695, 1.304487791,
              3.819077762, -24.33748213, 20.52716635, 22.0932173);  //Q 16

      letterScores[17] = score(723, 373, 4, 19, 141, 22, 30, 489, 0.380, 0.9,
              39.29070044, -87.06291199, 36.37122166, 45.77088434, 1.891044335,
              25.71754938, -62.57646799, 26.32583663, 52.03015057);  //R 17 766. 22, 131, 33, 35, 489

      letterScores[18] = score(657, 413, 4, 40, 95, 40, 59, 412, 0.68, 0.93,
              -0.633504053, -14.12262024, 4.204473257, 34.92783565, 1.517683025,
              18.37161754, -31.50430669, 10.51118314, 30.30503388);  //S 18

      letterScores[19] = score(434, 628, 3, 6, 101, 9, 8, 296, 0.72, 0.90,
              -465.8009032, 973.1037832, -414.3205227, -482.6744096, 0.707925881,
              87.98461505, -243.2759458, 72.80203471, 247.6881839);  //T 19 428

      letterScores[20] = score(635, 479, 5, 17, 145, 17, 26, 431, 0.525, 0.94,
              78.39900952, -169.7349627, 72.34363383, 81.22408001, 1.294864833,
              34.33533264, -106.0843517, 36.17181691, 92.42740139);  //U 20 635

      letterScores[21] = score(515, 619, 4, 42, 52, 44, 42, 320, 0.85, 0.93,
              -45.23315585, 29.73150861, -48.11628678, 104.1976684, 0.829230004,
              -45.23315585, 29.73150861, -48.11628678, 104.1976684);   //V 21

      letterScores[22] = score(749, 620, 7, 42, 137, 46, 48, 442, 0.59, 0.9,
              45.82845483, -114.3263017, 37.46102662, 92.59377611, 1.176691941,
              33.32978533, -111.4681442, 22.19912689, 146.6068122);  //W 22

      letterScores[23] = score(623, 509, 3, 44, 52, 44, 69, 381, 0.42, 0.94,
              -17.68004305, 37.96120241, -16.86368629, -32.26944379, 1.060706889,
              -16.20670613, 42.39000935, -17.14474773, -32.26944379);   //X 23

      letterScores[24] = score(438, 617, 5, 38, 37, 38, 44, 278, 0, 0.95,
              16.124922, 6.120177944, -1.947901469, 57.2673374, 0.652816414,
              16.124922, 6.120177944, -1.947901469, 57.2673374);  //Y 24 452

      letterScores[25] = score(587, 439, 6, 12, 122, 16, 40, 380, 0, 0.81,
              53.2004795, -107.432108, 56.75641449, 35.37343653, 1.327662242,
              53.2004795, -107.432108, 56.75641449, 35.37343653);  //Z 25

      double biggest = 0;
      for (int i = 0; i < 26; i++)
         if (letterScores[i] > biggest)
            biggest = letterScores[i];

      System.out.println(whites);
      System.out.println(whiteThree + blackThree);
      System.out.println(whiteFour + blackFour);
      System.out.println(whiteFive + blackFive);
      System.out.println(whiteSix + blackSix);
      System.out.println(whiteSeven + blackSeven);
      System.out.println(whiteEight + blackEight);

      /*double[] finalLetterScores = new double[26];
      for (int i = 0; i < 26; i++)
      {
         if (letterScores[i] > .9)
         {
            finalLetterScores[i] = bitmap();
            //load image 2
            //find the letter in image 2
            //iterate through both images xmin to xmax and ymin to ymax
            for (int y1 = xmin, int y2 = xmax; y1 < xmax, y2 < xmax2; y1++, y2++;)
            {
               for (int x1 = xmin, int x2 = xmax; x1 < xmax, x2 < xmax2; x1++, x2++;)
               {
                  if (imageToRead.getRGB(center.x, center.y + 1)) == -16777216)
               }
            }
         }
      }*/
      int j = 1;
      for (int i = 0; i < 26; i++)
         if (letterScores[i] == biggest)
            letters.add(letterIndex(i));
   }

   /*******************************************************************************************************************
    *
    *******************************************************************************************************************/
   private double score(double dieSize1, double whites1, double three1, double four1, double five1, double six1,
                        double seven1, double eight1, double lowerBound, double upperBound,
                        double fourConst, double fiveConst, double sixConst, double sevenConst, double sizeRatio,
                        double fourConst2, double fiveConst2, double sixConst2, double sevenConst2)
   {
      double actualSizeRatio = blacks / whites;

      double tempBlackFour = blackFour;
      double tempBlackFive = blackFive;
      double tempBlackSix = blackSix;
      double tempBlackSeven = blackSeven;

      double adjustment = Math.abs(actualSizeRatio - sizeRatio); // times constant;
      double adjustmentConstant = actualSizeRatio / sizeRatio;

      double tempFour = 0;
      double tempFive = 0;
      double tempSix = 0;
      double tempSeven = 0;

      //0.65 0.90
      if (adjustmentConstant > lowerBound && adjustmentConstant < upperBound) {
         tempFour = fourConst * adjustment;
         tempFive = fiveConst * adjustment;
         tempSix = sixConst * adjustment;
         tempSeven = sevenConst * adjustment;
      }
      else if (adjustmentConstant < lowerBound)
      {
         tempFour = fourConst2 * adjustment;
         tempFive = fiveConst2 * adjustment;
         tempSix = sixConst2 * adjustment;
         tempSeven = sevenConst2 * adjustment;
      }

      tempBlackFour = Math.abs(tempBlackFour - tempFour);
      tempBlackFive = Math.abs(tempBlackFive - tempFive);
      tempBlackSix = Math.abs(tempBlackSix - tempSix);
      tempBlackSeven = Math.abs(tempBlackSeven - tempSeven);


      double ratio57 = (five1 + seven1) / (tempBlackFive + tempBlackSeven);
      double ratio64 = (four1 + six1) / (tempBlackFour + tempBlackSix);


      double ratioSize = dieSize1 / blacks;
      //double ratio57 = fiveseven1 / (blackFive + blackSeven);
      //double ratio64 = foursix1 / (blackSix + blackFour);
      double ratioCenter = eight1 / blackEight;
      double ratioCorner;
      if (blackThree == 0)
         ratioCorner = 1;
      else
         ratioCorner = three1 / blackThree;
      double whiteRatio = whites1 / whites;

      ratioSize = Math.abs(ratioSize);
      ratio57 = Math.abs(ratio57);
      ratio64 = Math.abs(ratio64);
      //ratio4 = Math.abs(ratio4);
      ratioCenter = Math.abs(ratioCenter);
      ratioCorner = Math.abs(ratioCorner);
      //whiteRatio = Math.abs(whiteRatio);

      ratioSize = Math.abs(1 - ratioSize);
      ratioSize = 1 - ratioSize;

      ratio57 = Math.abs(1 - ratio57);
      ratio57 = 1 - ratio57;

      ratio64 = Math.abs(1 - ratio64);
      ratio64 = 1 - ratio64;

      //ratio4 = Math.abs(1 - ratio4);
      //ratio4 = 1 - ratio4;

      ratioCenter = Math.abs(1 - ratioCenter);
      ratioCenter = 1 - ratioCenter;

      ratioCorner = Math.abs(1 - ratioCorner);
      ratioCorner = 1 - ratioCorner;

      //whiteRatio = Math.abs(1 - whiteRatio);
      //whiteRatio = 1 - whiteRatio;

      /*if ((Math.abs(blacks - dieSize1) > 10))
      {
         ratioSize -= .07;
      }
      if ((Math.abs(blacks - dieSize1) > 50))
      {
         ratioSize -= .3;
      }*/

      if (ratio64 < 0 || fourConst == 58.92632739 || fourConst == -465.8009032 ||
              fourConst == 95.25293261 || fourConst == 68.60666956 || fourConst == -465.8009032 || fourConst == 78.39900952)//fourConst == 124.2920768 || fourConst == 261.5750112 || fourConst == 39.29070044)
         return (ratioSize * .7) + (ratio64 * 0) + (ratio57 * .3) + (ratioCorner * 0); //.55 .1 .35
      else
         return (ratioSize * .7) + (ratio64 * .1) + (ratio57 * .2) + (ratioCorner * 0); //.55 .1 .35
   }

   /*******************************************************************************************************************
    *
    *******************************************************************************************************************/
   private double bitmap()
   {
      double j = 5;
      return j;
   }

   /*******************************************************************************************************************
    *
    *******************************************************************************************************************/
   private char letterIndex(int i)
   {
      if (i == 0)
         return 'a';
      if (i == 1)
         return 'b';
      if (i == 2)
         return 'c';
      if (i == 3)
         return 'd';
      if (i == 4)
         return 'e';
      if (i == 5)
         return 'f';
      if (i == 6)
         return 'g';
      if (i == 7)
         return 'h';
      if (i == 8)
         return 'i';
      if (i == 9)
         return 'j';
      if (i == 10)
         return 'k';
      if (i == 11)
         return 'l';
      if (i == 12)
         return 'm';
      if (i == 13)
         return 'n';
      if (i == 14)
         return 'o';
      if (i == 15)
         return 'p';
      if (i == 16)
         return 'q';
      if (i == 17)
         return 'r';
      if (i == 18)
         return 's';
      if (i == 19)
         return 't';
      if (i == 20)
         return 'u';
      if (i == 21)
         return 'v';
      if (i == 22)
         return 'w';
      if (i == 23)
         return 'x';
      if (i == 24)
         return 'y';
      if (i == 25)
         return 'z';

      return '0';
   }

   /*******************************************************************************************************************
    * constructor
    *******************************************************************************************************************/
   private void countFeatures(int dieIndex)
   {
      blackThree = 0;
      blackFour = 0;
      blackFive = 0;
      blackSix = 0;
      blackSeven = 0;
      blackEight = 0;
      whites = 0;

      float adjacentPixels;
      blacks = die.elementAt(dieIndex).size();

      for (int x = xmin; x < xmax; x++)
         for (int y = ymin; y < ymax; y++)
            if ((imageToRead.getRGB(x, y)) == -1)
            {
               whites++;
               Coordinate send = new Coordinate();
               send.x = x;
               send.y = y;
               adjacentPixels = countPixels(send, false);

               if (adjacentPixels == 3)
                  whiteThree++;
               else if (adjacentPixels == 4)
                  whiteFour++;
               else if (adjacentPixels == 5)
                  whiteFive++;
               else if (adjacentPixels == 6)
                  whiteSix++;
               else if (adjacentPixels == 7)
                  whiteSeven++;
               else if (adjacentPixels == 8)
                  whiteEight++;
            }

      for (int j = 0; j < die.elementAt(dieIndex).size(); j++)
      {
         adjacentPixels = countPixels(die.elementAt(dieIndex).elementAt(j), true);

         if (adjacentPixels == 3)
            blackThree++;
         else if (adjacentPixels == 4)
            blackFour++;
         else if (adjacentPixels == 5)
            blackFive++;
         else if (adjacentPixels == 6)
            blackSix++;
         else if (adjacentPixels == 7)
            blackSeven++;
         else if (adjacentPixels == 8)
            blackEight++;
      }
   }

   /*******************************************************************************************************************
    * countPixels will count the amount of black pixels around the given pixel
    * @param center - The pixel being observed
    *******************************************************************************************************************/
   private int countPixels(Coordinate center, boolean isBlack)
   {
      int adjacentPixels = 0;

      int compare;
      if (isBlack)
         compare = -16777216;
      else
         compare = -1;

      if ((imageToRead.getRGB(center.x, center.y + 1)) == compare)
         adjacentPixels++;
      if ((imageToRead.getRGB(center.x, center.y - 1)) == compare)
         adjacentPixels++;
      if ((imageToRead.getRGB(center.x + 1, center.y)) == compare)
         adjacentPixels++;
      if ((imageToRead.getRGB(center.x - 1, center.y)) == compare)
         adjacentPixels++;
      if ((imageToRead.getRGB(center.x + 1, center.y + 1)) == compare)
         adjacentPixels++;
      if ((imageToRead.getRGB(center.x - 1, center.y + 1)) == compare)
         adjacentPixels++;
      if ((imageToRead.getRGB(center.x - 1, center.y - 1)) == compare)
         adjacentPixels++;
      if ((imageToRead.getRGB(center.x + 1, center.y - 1)) == compare)
         adjacentPixels++;

      return adjacentPixels;
   }
   /*******************************************************************************************************************
    * constructor
    *******************************************************************************************************************/
   private void findCoordinates(int dieIndex)
   {
      xmax = 0;
      xmin = 2000;
      ymax = 0;
      ymin = 2000;

      for (int j = 0; j < die.elementAt(dieIndex).size(); j++) {
         if (die.elementAt(dieIndex).elementAt(j).x > xmax)
            xmax = die.elementAt(dieIndex).elementAt(j).x;
      }

      for (int j = 0; j < die.elementAt(dieIndex).size(); j++) {
         if (die.elementAt(dieIndex).elementAt(j).x < xmin)
            xmin = die.elementAt(dieIndex).elementAt(j).x;
      }

      for (int j = 0; j < die.elementAt(dieIndex).size(); j++) {
         if (die.elementAt(dieIndex).elementAt(j).y > ymax)
            ymax = die.elementAt(dieIndex).elementAt(j).y;
      }

      for (int j = 0; j < die.elementAt(dieIndex).size(); j++) {
         if (die.elementAt(dieIndex).elementAt(j).y < ymin)
            ymin = die.elementAt(dieIndex).elementAt(j).y;
      }
   }
}
