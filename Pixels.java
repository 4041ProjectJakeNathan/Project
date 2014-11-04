/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pixels;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
/**
 *
 * @author Nathan Studanski
 * @since October 29 2014
 */
public class Pixels {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fileName = "skyline.jpg";
        BufferedImage img = readImage(fileName);
        System.out.println("Width = " + img.getWidth());
        System.out.println("Height = " + img.getHeight());
        ColorPixel[][] pixels = filterAndArray(img);
        //printLumiGrayArray(pixels);
        //buildImage(pixels);
        //test();
    }
    
    protected static BufferedImage readImage(String fileName) {
        try{File file = new File(fileName); 
        return ImageIO.read(file);}
        catch(IOException e){ System.out.println("Invalid file"); return null;}
    }
    
    protected static ColorPixel[][] filterAndArray (BufferedImage img){
        BufferedImage avggray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage lumigray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage lightgray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage sepia = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        ColorPixel[][] ans = new ColorPixel[img.getWidth()][img.getHeight()];
        Color c;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                ans[i][j] = new ColorPixel(img.getRGB(i, j));
                lumigray.setRGB(i, j, ans[i][j].getLumiGray());
                avggray.setRGB(i, j, ans[i][j].getAverageGray());
                lightgray.setRGB(i, j, ans[i][j].getLightGray());
                c = new Color(ans[i][j].getSepiaRed(), ans[i][j].getSepiaGreen(), ans[i][j].getSepiaBlue());
                sepia.setRGB(i, j, c.getRGB());
            }
        }
        
        try{
        ImageIO.write(avggray, "jpg", new File("averageGray.jpg"));
        ImageIO.write(lumigray, "jpg", new File("lumiGray.jpg"));
        ImageIO.write(lightgray, "jpg", new File("lightGray.jpg"));
        ImageIO.write(sepia, "jpg", new File("sepia.jpg"));
        }
        catch(IOException e) {System.out.println("IO Exception");}
        return ans;
    }
    protected static void test(){
        int[][] i = new int[5][10];
        // First = width
        // Second = height
        for (int j = 0; j < i[0].length; j++) {
            for (int k = 0; k < i.length; k++) {
                System.out.print("X");
            }
            System.out.println("");
        }
    }
    protected static void printLumiGrayArray(ColorPixel[][] pixels){
        String s = "";
        for (int j = 0; j < pixels[0].length; j++) {
            for (int k = 0; k < pixels.length; k++) {
                s += pixels[k][j].getLumiGray() + " ";
            }
            s += System.lineSeparator();
        }
        System.out.println(s);
    }
    
    protected static void buildImage(ColorPixel[][] a){
        ColorPixel p;
        BufferedImage img = new BufferedImage(a.length, a[0].length, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                p = a[i][j];
                img.setRGB(i, j, p.getLumiGray());
            }
        }
        try{
        ImageIO.write(img, "jpg", new File("testImage.jpg"));
        }
        catch(IOException e) {System.out.println("IO Exception");}
    }
}
