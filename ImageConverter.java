import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.*;
import javax.swing.*;

/*******************************************************************************************************************
 * ImageConverter will turn a normal image to a greyscale image, and a greyscale image to a binary image.
 *******************************************************************************************************************/
class ImageConverter {
    /*******************************************************************************************************************
     * colorToRGB will be used in GreyscaleImage to operate on each pixel.
     * @param alpha
     * @param red
     * @param green
     * @param blue
     *******************************************************************************************************************/
    private static int colorToRGB(int alpha, int red, int green, int blue) {

        int nPixel = 0;
        nPixel += alpha;
        nPixel = nPixel << 8;
        nPixel += red;
        nPixel = nPixel << 8;
        nPixel += green;
        nPixel = nPixel << 8;
        nPixel += blue;

        return nPixel;

    }

    /*******************************************************************************************************************
     * GreyscaleImage will take in an image and turn it to greyscale.
     * @param imageToRead - Image to greyscale.
     *******************************************************************************************************************/
    public static BufferedImage GreyscaleImage(BufferedImage imageToRead) {

        int alpha, red, green, blue;
        int nPixel;

        BufferedImage lum = new BufferedImage(imageToRead.getWidth(), imageToRead.getHeight(), imageToRead.getType());

        for (int i = 0; i < imageToRead.getWidth(); i++) {
            for (int j = 0; j < imageToRead.getHeight(); j++) {

                // Get pixels by R, G, B
                alpha = new Color(imageToRead.getRGB(i, j)).getAlpha();
                red = new Color(imageToRead.getRGB(i, j)).getRed();
                green = new Color(imageToRead.getRGB(i, j)).getGreen();
                blue = new Color(imageToRead.getRGB(i, j)).getBlue();

                red = (int) (0.21 * red + 0.71 * green + 0.07 * blue);
                // Return back to original format
                nPixel = colorToRGB(alpha, red, red, red);

                // Write pixels into image
                lum.setRGB(i, j, nPixel);

            }
        }

        imageToRead = lum;
        lum = binarize(lum);

        BufferedImage newImage = new BufferedImage(
                imageToRead.getWidth() + lum.getWidth(),
                imageToRead.getHeight(),
                imageToRead.getType());

        Graphics2D g = (Graphics2D) newImage.getGraphics();
        g.drawImage(imageToRead, 0, 0, null);
        g.drawImage(lum, imageToRead.getWidth(), 0, null);


        JFrame editorFrame = new JFrame();
        ImageIcon imageIcon = new ImageIcon(newImage);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

        editorFrame.pack();
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setVisible(true);

        return lum;
    }

    /*******************************************************************************************************************
     * Binarize will take in a greyscale image and turn it into a binary image.
     * @param imageToRead - Must be a greyscale image.  It will turn it into a binary image.
     *******************************************************************************************************************/
    private static BufferedImage binarize(BufferedImage imageToRead) {

        int red;
        int newPixel;
        int threshold = 100;
        BufferedImage binarized = new BufferedImage(imageToRead.getWidth(), imageToRead.getHeight(), imageToRead.getType());

        for (int i = 0; i < imageToRead.getWidth(); i++)
        {
            for (int j = 0; j < imageToRead.getHeight(); j++)
            {

                // Get pixels
                red = new Color(imageToRead.getRGB(i, j)).getRed();
                int alpha = new Color(imageToRead.getRGB(i, j)).getAlpha();
                if (red > threshold)
                {
                    newPixel = 255;
                }
                else
                {
                    newPixel = 0;
                }
                newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
                binarized.setRGB(i, j, newPixel);
            }
        }
        imageToRead = binarized;
        return imageToRead;
    }
}

