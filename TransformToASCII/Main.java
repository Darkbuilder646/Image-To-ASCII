package TransformToASCII;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.setProperty("console.encoding", "UTF-8");

        BufferedImage image = loadImage("TransformToASCII\\IMG\\Flandre.jpg");
        int newWidth = 250;
        int newHeight = calculateNewHeight(image, newWidth);

        BufferedImage resizedImage = resizeImage(image, newWidth, newHeight);
        
        // System.out.println("⣿");

        convertToBraille(resizedImage, 2, 4);
    }
    //#region //* Image Init
    private static BufferedImage loadImage(String filePath) throws IOException {
        return ImageIO.read(new File(filePath));
    }

    private static int calculateNewHeight(BufferedImage image, int newWidth) {
        return (int) (image.getHeight() * ((double) newWidth / image.getWidth()));
    }

    private static BufferedImage resizeImage(BufferedImage image, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        resizedImage.getGraphics().drawImage(image, 0, 0, newWidth, newHeight, null);
        return resizedImage;
    }
    //#endregion

    private static void convertToBraille(BufferedImage image, int cellWidth, int cellHeight) {
        for (int y = 0; y < image.getHeight(); y += cellHeight) {
            for (int x = 0; x < image.getWidth(); x += cellWidth) {
                int gray = getAverageGrayValue(image, x, y, cellWidth, cellHeight); //For braille but also work for ASCII => (width: 2 height: 4)

                char brailleChar = getAsciiChar(gray);
                System.out.print(brailleChar);
            }
            System.out.println();
        }
    }

    private static int getAverageGrayValue(BufferedImage image, int startX, int startY, int width, int height) {
        int sum = 0;
        for (int y = startY; y < startY + height; y++) {
            for (int x = startX; x < startX + width; x++) {
                int rgb = image.getRGB(x, y);
                int gray = convertPixelToGray(rgb);
                sum += gray;
            }
        }
        return sum / (width * height);
    }

    private static int convertPixelToGray(int rgb) {
        //? Gray Scale Formula :
        return (int) (0.21 * ((rgb >> 16) & 0xFF) + 0.72 * ((rgb >> 8) & 0xFF) + 0.07 * (rgb & 0xFF));
    }

    private static char getAsciiChar(int gray) {
        // Gray Scale table for Braille symbol
        // String listChars = "⠁⠂⠃⠄⠅⠆⠇⠈⠉⠊⠋⠌⠍⠎⠏⠐⠑⠒⠓⠔⠕⠖⠗⠘⠙⠚⠛⠜⠝⠞⠟⠠⠡⠢⠣⠤⠥⠦⠧⠨⠩⠪⠫⠬⠭⠮⠯⠰⠱⠲⠳⠴⠵⠶⠷⠸⠹⠺⠻⠼⠽⠾⠿";
        String listChars = "@%#*+=-:. ";

        // Normalise Gray scale
        int index = gray * (listChars.length() - 1) / 255;

        return listChars.charAt(index);
    }
    
}