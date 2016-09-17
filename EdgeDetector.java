import java.awt.image.BufferedImage;
import java.util.Vector;

/*******************************************************************************************************************
 * Coordinate is simply the xy coordinate on the image
 *******************************************************************************************************************/
class Coordinate
{
   public int x;
   public int y;
}

/*******************************************************************************************************************
 * EdgeDetector will do several things. It will...
 *      1. Find all of the black pixels and try its best to organize them into separate vectors
 *      2. Clean the noise from the pictures by eliminating small and large feedback.
 *      3. Merge the letters that didn't make it in one piece from the read.
 *      4. Count the feature ponints of the letter
 *      5. Using the amount of feature points (and more if required) determine what the character is.
 *******************************************************************************************************************/
class EdgeDetector {
   private static BufferedImage imageToRead;
   Vector<Vector<Coordinate>> die = new Vector<Vector<Coordinate>>();
   Coordinate coordinate = new Coordinate();
   int xmin;
   int ymin;
   int xmax;
   int ymax;

    /*******************************************************************************************************************
     * constructor
     *******************************************************************************************************************/
    public EdgeDetector(BufferedImage image) {
        //setup
        imageToRead = image;
        xmin = imageToRead.getMinX();
        ymin = imageToRead.getMinY();
        ymax = ymin + imageToRead.getHeight();
        xmax = xmin + imageToRead.getWidth();

        findDice();
        removeLargeNoise();
        mergeDice();
        removeSmallNoise();
        removeEmptyDice();
   }

   /*******************************************************************************************************************
    * findDice will find the black pixels and group them into die objects to the best of its ability
    *******************************************************************************************************************/
   private void findDice()
   {
      boolean temp = false;
      for (coordinate.x = xmin; coordinate.x < xmax; coordinate.x++)
         for (coordinate.y = ymin; coordinate.y < ymax; coordinate.y++)
            if ((imageToRead.getRGB(coordinate.x, coordinate.y)) == -16777216) {
               //test = true;
               int x1 = coordinate.x;
               int y1 = coordinate.y;


               if (die.size() != 0)
                  outerloop:
                          for (int i = 0; i < die.size(); i++) {
                             Vector<Coordinate> dieNo = die.elementAt(i);
                             for (int j = 0; j < dieNo.size(); j++) {
                                temp = false;
                                if (isAdjacent(coordinate, dieNo.elementAt(j))) {
                                   addCoordinate(i, x1, y1);
                                   break outerloop;
                                }
                             }
                             temp = true;
                          }
               else
                  addCoordinate(-1, x1, y1);

               if (temp)
                  addCoordinate(-1, x1, y1);
            }
   }

   /*******************************************************************************************************************
    * mergeDice will merge all of the broken and adjacent peices into full letters
    *******************************************************************************************************************/
   private void mergeDice()
   {
      for (int i = 0; i < die.size(); i++)
         for (int j = 0; j < die.elementAt(i).size(); j++)
            for (int w = 0; w < die.size(); w++)
               if (w != i)
                  for (int z = 0; z < die.elementAt(w).size(); z++)
                     if (isAdjacent(die.elementAt(i).elementAt(j), die.elementAt(w).elementAt(z))) {
                        die.elementAt(i).add(die.elementAt(w).elementAt(z));
                        die.elementAt(w).remove(z);
                     }
   }
   /*******************************************************************************************************************
    * removeEmptyDice will remove the empty dice
    *******************************************************************************************************************/
   private void removeEmptyDice()
   {
      for (int i = 0; i < die.size(); i++)
         if (die.elementAt(i).isEmpty())
            die.remove(i);
   }

   /*******************************************************************************************************************
    * removeSmallNoise will remove the large bits of black pixels
    *******************************************************************************************************************/
   private void removeSmallNoise()
   {
      for (int i = 0; i < die.size(); i++)
         if (die.elementAt(i).size() < 200)
         {
            die.remove(i);
            i--;
         }

       //TEST OUTPUT
       for (int i = 0; i < die.size(); i++) {
       System.out.println("DIE #" + i + ": " + die.elementAt(i).size());
       //COORDINATE OUTPUT
       for (int j = 0; j < die.elementAt(i).size(); j++)
          System.out.println("x:" + die.elementAt(i).elementAt(j).x + " y:" + die.elementAt(i).elementAt(j).y);
       }
   }

   /*******************************************************************************************************************
    * RemoveLargeNoise will remove the large blocks of black pixels
    *******************************************************************************************************************/
   private void removeLargeNoise()
   {
      for (int i = 0; i < die.size(); i++)
         if(die.elementAt(i).size() > 950)
         {
            die.remove(i);
            i--;
         }
   }

   /*******************************************************************************************************************
    * addCoordinate will add the coordinate to the specified die.
    * @param dieNo - The die to add to
    * @param x - the x value
    * @param y - the y value
    *******************************************************************************************************************/
   private void addCoordinate(int dieNo, int x, int y)
   {
      Coordinate temp = new Coordinate();
      temp.x = x;
      temp.y = y;
      if (dieNo == -1)
      {
         Vector<Coordinate> tempDie = new Vector<Coordinate>();
         tempDie.add(temp);
         die.add(tempDie);
      }
      else
         die.elementAt(dieNo).add(temp);
   }

   /*******************************************************************************************************************
    * isAdjacent will take in two coordinates, and determine if they are adjacent
    * @param one - A coordinate
    * @param two - A different coordinate
    *******************************************************************************************************************/
   private boolean isAdjacent(Coordinate one, Coordinate two)
   {
      return ((one.x == (two.x + 1) && one.y == two.y)           ||
              (one.x == (two.x - 1) && one.y == two.y)       ||
              (one.x == two.x && one.y == (two.y + 1))       ||
              (one.x == two.x && one.y == (two.y - 1))       ||
              (one.x == (two.x + 1) && one.y == (two.y + 1)) ||
              (one.x == (two.x + 1) && one.y == (two.y - 1)) ||
              (one.x == (two.x - 1) && one.y == (two.y + 1)) ||
              (one.x == (two.x - 1) && one.y == (two.y - 1)));
   }
}
