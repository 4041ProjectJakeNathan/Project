/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edgedetection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.FileWriter;
import java.util.Scanner;
/**
 *
 * @author nathan
 */
public class EdgeDetection {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner getFile = new Scanner(System.in);
        System.out.println("Please input the file name to use");
        String fileName = getFile.nextLine();
        BufferedImage buff = readImage(fileName);
        if(buff == null){System.out.println("Invalid image. Breaking");}
        else{
        System.out.println("Buffered built");
        Pixels[][] pixelArray = buffToPixelsArray(buff);
        System.out.println("Pixels built");
        short[][] gauss = gaussian(pixelArray);
        System.out.println("Gauss built");
        byte[][] binarySobel = sobelWithOtsu(gauss);
        System.out.println("Sobel built");
        buildBinaryImage(binarySobel);
        System.out.println("Binary written");
        }
        }
    
    
    protected static BufferedImage readImage(String fileName){
        try{File file = new File(fileName);
            return ImageIO.read(file);}
        catch(IOException e){ System.out.println("Invalid file"); return null;}
    }
    
    protected static void writeImageToFile(BufferedImage img, String fileName){
        try{
            ImageIO.write(img, "jpg", new File(fileName + ".jpg"));
        }
        catch(IOException e){}
    }
    
    protected static Pixels[][] buffToPixelsArray(BufferedImage img){
        Pixels[][] ret = new Pixels[img.getWidth()][img.getHeight()];
        Color c;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                ret[i][j] = new Pixels(img.getRGB(i, j));
            }
        }
        return ret;
    }
    
    protected static short colValue(Pixels[][] arr, int i, int j){
        return (short)(arr[i-2][j].getGray() + 2 * arr[i-1][j].getGray() + arr[i][j].getGray());
    }
    
    protected static short[][] gaussian(Pixels[][] arr){
        short[][] ret = new short[arr.length][arr[0].length];
        short colA, colB, colC;
        for (int i = 2; i < arr.length; i++) {
            colA = colValue(arr, i, 0);
            colB = colValue(arr, i, 1);
            for (int j = 2; j < arr[0].length; j++) {
                colC = colValue(arr, i, j);
                ret[i - 1][j - 1] = (short)((colA + 2 * colB + colC) / 16);
                colA = colB;
                colB = colC;
            }
        }
        return ret;
    }
    
    protected static byte[][] sobelWithOtsu(short[][] a){
        short[][] xFilter = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        short[][] yFilter = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
        short[][] ret = new short[a.length][a[0].length];
        byte[][] ret2 = new byte[a.length][a[0].length];
        short gradient, newx, newy;
        int[] histo = new int[256];
        for (int x = 0; x < a.length; x++) {
            for (int y = 0; y < a[0].length; y++) {
                // Setting the outer edges to black (0) for the outside edges
                if((x == 0 )|| (y == 0) || (x + 1) == a.length || (y + 1) == a[0].length){ ret[x][y] = 0;}
                else{
                    newx = (short)(xFilter[0][0]*a[x-1][y-1] +
                           xFilter[1][0]*a[x-1][y  ] +
                           xFilter[2][0]*a[x-1][y+1] +
                           xFilter[0][1]*a[x  ][y-1] +
                           xFilter[1][1]*a[x  ][y  ] +
                           xFilter[2][1]*a[x  ][y+1] +
                           xFilter[0][2]*a[x+1][y-1] +
                           xFilter[1][2]*a[x+1][y  ] +
                           xFilter[2][2]*a[x+1][y+1]);
                    newy = (short)(yFilter[0][0]*a[x-1][y-1] +
                           yFilter[1][0]*a[x-1][y  ] +
                           yFilter[2][0]*a[x-1][y+1] +
                           yFilter[0][1]*a[x  ][y-1] +
                           yFilter[1][1]*a[x  ][y  ] +
                           yFilter[2][1]*a[x  ][y+1] +
                           yFilter[0][2]*a[x+1][y-1] +
                           yFilter[1][2]*a[x+1][y  ] +
                           yFilter[2][2]*a[x+1][y+1]);
                    gradient = (short)(Math.sqrt((newx * newx) + (newy * newy)));
                    if(gradient > 255){gradient = 255;}
                    histo[gradient] = histo[gradient] + 1;
                    ret[x][y] = gradient;
                    
                }
            }
        }
            System.out.println("Calling otsu");
            short thresh = otsu(histo, a.length * a[0].length);
            // Optimally do this in another method
            System.out.println("Applying otsu");
            for (int i = 0; i < ret.length; i++) {
                for (int j = 0; j < ret[0].length; j++) {
                    if(ret[i][j] > thresh){ret2[i][j] = 0;}
                    else{ret2[i][j] = 1;}
                }
                
            }
        
        
        return ret2;
    }
    
    protected static short otsu(int[] histo, int size){
        // Algorithm from http://www.labbookpages.co.uk/software/imgProc/otsuThreshold.html
        float varMax = 0, meanB = 0, meanF = 0, newVar = 0;
        int sum = 0, sumB = 0, fore = 0, back = 0;
        short thresh = 0;
        for (int i = 0; i < 256; i++) {
            sum += i * histo[i];
            System.out.println("Histo at " + i + " = " + histo[i]);
            
        }
        System.out.println("Sum = " + sum);
        for (short i = 0; i < 256; i++) {
            back += histo[i];
            if(back != 0){fore = size - back;}
            if (fore == 0){break;}
            sumB += i * histo[i];
            meanB = (float)(sumB / back);
            meanF = (float)((sum - sumB)/fore);
            newVar = back * fore * (meanB - meanF) * (meanB - meanF);
            if (newVar > varMax){varMax = newVar; thresh = i;}
            
        }
        System.out.println("Threshold = " + thresh);
    return thresh;
}
    protected static void buildImage(short[][] a, String fileName){
        BufferedImage img = new BufferedImage(a.length, a[0].length, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                img.setRGB(i, j, a[i][j]);
            }
        }
        writeImageToFile(img, fileName);
    }
    
    protected static void buildBinaryImage(byte[][] a){
        BufferedImage img = new BufferedImage(a.length, a[0].length, BufferedImage.TYPE_INT_BGR);
        Color black = new Color(0, 0, 0);
        Color white = new Color(255, 255, 255);
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if(a[i][j] == 0){img.setRGB(i, j, black.getRGB());}
                else{img.setRGB(i, j, white.getRGB());}
            }
        }
        writeImageToFile(img, "BinaryImage");
    }
    
    
    
}
