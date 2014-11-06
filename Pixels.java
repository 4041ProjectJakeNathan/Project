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
import java.io.FileWriter;
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
        String fileName = "ColoredSquares.jpg";
        BufferedImage img = readImage(fileName);
        System.out.println("Width = " + img.getWidth());
        System.out.println("Height = " + img.getHeight());
        ColorPixel[][] pixels = filterAndArray(img);
        BufferedImage sobel = sobelOperator(pixels);
        //outerEdges(sobel);
        grayToFakeColor(sobel, "sobelContrast");
        //BufferedImage roberts = robertsOperator(pixels);
        //grayToFakeColor(roberts, "robertsContrast");
        //ColorPixel[][] robertsArray = filterAndArray(roberts);
        //ColorPixel[][] sobelArray = filterAndArray(sobel);
        //BufferedImage sobelRob = sobelOperator(robertsArray);
        //grayToFakeColor(sobelRob, "sobelRobertsContrast");
        //arrayToTxt(filterAndArray(sobel), "sobel");
        
        //printLumiGrayArray(pixels);
        //buildImage(pixels);
        //test();
    }
    protected static void outerEdges(BufferedImage sob){
        
    }
    protected static void arrayToTxt(ColorPixel[][] arr, String fileName){
        int gray;
        String fGray;
        String result = "";
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                /*gray = arr[i][j].getLumiGray();
                if(gray < 10){ fGray = "00" + gray;}
                else if (gray < 100 & gray > 9){ fGray = "0" + gray;}
                else{fGray = "" + gray;}
                result += fGray + " ";
                */
                gray = arr[i][j].getLumiGray();
                if(gray > 30){fGray = "X";}
                else{fGray = " ";}
                result += fGray;
            }
            result += System.lineSeparator();
            
        }
        try {
            File newTextFile = new File(fileName + ".txt");

            FileWriter fw = new FileWriter(newTextFile);
            fw.write(result);
            fw.close();

        } catch (IOException e) {
            
        }
    }
    protected static void grayToFakeColor(BufferedImage img, String fileName){
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int c;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
               c = img.getRGB(i, j);
               result.setRGB(i, j, c);
            }
        }
        createFileImage(result, fileName);
    }
    
    protected static void createFileImage(BufferedImage img, String fileName){
        try{
        ImageIO.write(img, "jpg", new File(fileName + ".jpg"));
        }
        catch(IOException e) {System.out.println("IO Exception");}
    }
    protected static BufferedImage readImage(String fileName) {
        try{File file = new File(fileName); 
        return ImageIO.read(file);}
        catch(IOException e){ System.out.println("Invalid file"); return null;}
    }
    
    protected static ColorPixel[][] filterAndArray (BufferedImage img){
        /*
        BufferedImage avggray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage lumigray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage lightgray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage sepia = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        */
        ColorPixel[][] ans = new ColorPixel[img.getWidth()][img.getHeight()];
        Color c;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                ans[i][j] = new ColorPixel(img.getRGB(i, j));
                /*
                lumigray.setRGB(i, j, ans[i][j].getLumiGray());
                avggray.setRGB(i, j, ans[i][j].getAverageGray());
                lightgray.setRGB(i, j, ans[i][j].getLightGray());
                c = new Color(ans[i][j].getSepiaRed(), ans[i][j].getSepiaGreen(), ans[i][j].getSepiaBlue());
                sepia.setRGB(i, j, c.getRGB());
                */
            }
        }
        /*
        createFileImage(avggray, "averageGray");
        createFileImage(lumigray, "lumiGray");
        createFileImage(lightgray, "lightGray");
        createFileImage(sepia, "sepia");
        */
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
    /*public int[][] addBuffer(ColorPixel[][] greyImage)
	{
		*
		 * This function's purpose is not necessary, but it adds a border to the original
		 * greyscale image by copying the image's current border.  This is meant to preserve
		 * the original size of the image as a matrix is applied.
		 
		int[][] bufferedImage = new int[greyImage.length+2][greyImage[0].length+2];
		for (int x = 0; x < greyImage.length; x++)
			for (int y = 0; y < greyImage[0].length; y++)
				bufferedImage[x+1][y+1] = greyImage[x][y].getLumiGray();
		bufferedImage[0] = bufferedImage[1]; //handles first row
		bufferedImage[bufferedImage.length-1] = bufferedImage[bufferedImage.length-2]; //handles last row
		bufferedImage[0][0] = bufferedImage[0][1]; //handles first column
		bufferedImage[bufferedImage.length-1][bufferedImage[0].length-1] = bufferedImage[bufferedImage.length-2][bufferedImage[0].length-2]; //handles last column
		
				
		bufferedImage[0][0] = greyImage[0][0].getLumiGray(); //top left
		bufferedImage[0][bufferedImage[0].length-1] = greyImage[0][greyImage[0].length-1].getLumiGray(); //top right
		bufferedImage[bufferedImage.length-1][0] = greyImage[greyImage.length-1][0].getLumiGray(); //bottom left
		bufferedImage[bufferedImage.length-1][bufferedImage[0].length-1] = greyImage[greyImage.length-1][greyImage[0].length-1].getLumiGray(); //bottom right
	
		return bufferedImage;
	} 
    */
	public static BufferedImage robertsOperator(ColorPixel[][] img){
            BufferedImage result = new BufferedImage(img.length, img[0].length, BufferedImage.TYPE_BYTE_GRAY);
            int grad, a, b;
            for (int i = 0; i < img.length; i++) {
                for (int j = 0; j < img[0].length; j++) {
                    if(i == (img.length - 1) || j == (img[0].length - 1)){result.setRGB(i, j, img[i][j].getLumiGray());}
                    else{
                        a = img[i][j].getLumiGray() - img[i + 1][j+1].getLumiGray();
                        b = img[i][j + 1].getLumiGray() - img[i+1][j].getLumiGray();
                        grad = a * a + b * b;
                        result.setRGB(i, j, grad);
                    }
                }
            }
            createFileImage(result, "roberts");
            return result;
        }
	public static BufferedImage sobelOperator(ColorPixel[][] b_img)
	{
		int[][] xSobelFilter = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}}; //filters for x gradient
		int[][] ySobelFilter = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}}; //filters for y gradient
		
		BufferedImage result = new BufferedImage(b_img.length, b_img[0].length, BufferedImage.TYPE_BYTE_GRAY); //will store image gradient
		
		//applies filters to get final gradient image
		for (int x = 0; x < b_img.length; x++)
		{	//how to get height and width?
			for(int y = 0; y < b_img[0].length; y++)
			{
                                // Buffer
                                if(x == 0 || y == 0 || x == (b_img.length - 1) || y == (b_img[0].length - 1)){ result.setRGB(x, y, b_img[x][y].getLumiGray());}
                                
                                else{int newX = xSobelFilter[0][0]*b_img[x-1][y-1].getLumiGray() + xSobelFilter[0][1]*b_img[x-1][y].getLumiGray() + xSobelFilter[0][2]*b_img[x-1][y+1].getLumiGray() +
						xSobelFilter[1][0]*b_img[x-1][y].getLumiGray() + xSobelFilter[1][1]*b_img[x][y].getLumiGray() + xSobelFilter[1][2]*b_img[x+1][y-1].getLumiGray() +
						xSobelFilter[2][0]*b_img[x-1][y+1].getLumiGray() + xSobelFilter[2][1]*b_img[x][y+1].getLumiGray() + xSobelFilter[2][2]*b_img[x+1][y+1].getLumiGray();
				
				int newY = ySobelFilter[0][0]*b_img[x-1][y-1].getLumiGray() + ySobelFilter[0][1]*b_img[x-1][y].getLumiGray() + ySobelFilter[0][2]*b_img[x-1][y+1].getLumiGray() +
						ySobelFilter[1][0]*b_img[x-1][y].getLumiGray() + ySobelFilter[1][1]*b_img[x][y].getLumiGray() + ySobelFilter[1][2]*b_img[x+1][y-1].getLumiGray() +
						ySobelFilter[2][0]*b_img[x-1][y+1].getLumiGray() + ySobelFilter[2][1]*b_img[x][y+1].getLumiGray() + ySobelFilter[2][2]*b_img[x+1][y+1].getLumiGray();
				
				/*
				In the link http://www2.ph.ed.ac.uk/~wjh/teaching/dia/documents/edge-ohp.pdf that you provided, Nathan, 
				it suggested calculating gradient by taking the absolute value of newX and newY because it was more efficient.
				We should test this:
				
				int gradient = (int) Math.abs(newX) + Math.abs(newY);
				*/
				int gradient = (int) Math.sqrt(newX*newX + newY*newY);
                                // Threshold
                                if (gradient > 120){gradient = 255;}
                                else{gradient = 0;}
				result.setRGB(x, y, gradient);  //good for left borders, but right?
                                }
			}
		}
                createFileImage(result, "sobel");
                return result;
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
