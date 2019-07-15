package Unit7;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;

public class Image {
    // Matrix that stores the image in gray level format (0-255)
    // 0 = black, 255 = white
    private int [][] img;
    
    // Constructor: create grayscale image from JPG file (parameter)
    public Image(String fileName) {
        try {
            BufferedImage imageRGB = ImageIO.read(new File(fileName));
            BufferedImage image = getGrayScale(imageRGB);
            int height = image.getHeight();
            int width = image.getWidth();
            img = new int[height][width];
            for(int i = 0; i < height; i++) {
                    for(int j = 0; j < width; j++) {
                    int pix = image.getRGB(j,i);
                    Color c = new Color(pix);
                    int R = c.getRed(); 
                    int G = c.getGreen(); 
                    int B = c.getBlue();
                    img[i][j] = (int) ((R + G + B) / 3);
                }
            }
        }
        catch (Exception e) {
            img = null;
        }
    }
    
    // Show image
    public void show() {
        // Create image from integer matrix
        BufferedImage image = convert();
        // Show image
        JFrame myWindow = new JFrame();
        myWindow.setTitle("Image");
        JLabel label = new JLabel(new ImageIcon(image));
        myWindow.add(label);
        myWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myWindow.pack();
        myWindow.setVisible(true);
    }
    
    // Save in JPG format (filename given) the grayscale image
    // Return true when saving was successful, false otherwise
    public boolean save(String fileName) {
        // Create image from integer matrix
        BufferedImage image = convert();
        // Save image
        try {
            File f = new File(fileName);
            return ImageIO.write(image, "jpg", f);
        }
        catch (IOException e) {
            System.out.println("Error saving image " + fileName);
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    // Converts color image in grayscale image (private class method)
    private static BufferedImage getGrayScale(BufferedImage inputImage){
        BufferedImage img = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
        BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = img.getGraphics();
        g.drawImage(inputImage, 0, 0, null);
        g.dispose();
        return img;
    }
    
    // Converts matrix of int into image (private method)
    private BufferedImage convert() {
        // Create image from integer matrix
        int width = img[0].length;
        int height = img.length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                int value = img[i][j] << 16 | img[i][j] << 8 | img[i][j];
                image.setRGB(j, i, value);
            }
        }
        return image;
    }
    
    // Obtains the symmetric image
    public void symmetric(){
        int height = img.length;
        int width = img[0].length;
        int k, i, j, aux;
        
        for(i = 0; i < height; i++) {
            for(j = width - 1, k = 0; j >= 0 && k < width /2; j--, k++) {
                aux = img [i][j];
                img [i][j] = img [i][k];
                img [i][k] = aux;
            }
        }
    }
    public void negative(){
        int height = img.length;
        int width = img[0].length;
        int k, i, j, aux;
        
        for(i = 0; i < height; i++) {
            for(j = width - 1, k = 0; j >= 0 && k < width; j--, k++) {
                img [i][j] = 255 - img [i][j];
            }
        }
    }
}