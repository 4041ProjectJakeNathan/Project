import java.lang.Math;

public class Sobel 
{
	/*
	public static void main(String[] args) 
	{


	}
	*/
	/*
	 * Input: GreyScaleImage {type?}
	 * Output: EdgeDetected {double[][] with edges emphasized}
	 * 
	 * Questions: Do we want to create a buffer layer of pixels on edges so that the Sobel
	 * operator doesn't result in a reduced image?
	 */
	
	public int[][] addBuffer(int[][] greyImage)
	{
		/*
		 * This function's purpose is not necessary, but it adds a border to the original
		 * greyscale image by copying the image's current border.  This is meant to preserve
		 * the original size of the image as a matrix is applied.
		 */
		int[][] bufferedImage = new int[greyImage.length+2][greyImage[0].length+2];
		for (int x = 0; x < greyImage.length; x++)
			for (int y = 0; y < greyImage[0].length; y++)
				bufferedImage[x+1][y+1] = greyImage[x][y];
		bufferedImage[0] = bufferedImage[1]; //handles first row
		bufferedImage[bufferedImage.length-1] = bufferedImage[bufferedImage.length-2]; //handles last row
		bufferedImage[0][0] = bufferedImage[0][1]; //handles first column
		bufferedImage[bufferedImage.length-1][bufferedImage[0].length-1] = bufferedImage[bufferedImage.length-2][bufferedImage[0].length-2]; //handles last column
		
				
		bufferedImage[0][0] = greyImage[0][0]; //top left
		bufferedImage[0][bufferedImage[0].length-1] = greyImage[0][greyImage[0].length-1]; //top right
		bufferedImage[bufferedImage.length-1][0] = greyImage[greyImage.length-1][0]; //bottom left
		bufferedImage[bufferedImage.length-1][bufferedImage[0].length-1] = greyImage[greyImage.length-1][greyImage[0].length-1]; //bottom right
	
		return bufferedImage;
	}
	
	public int[][] sobelOperator(int[][] b_img)
	{
		int[][] xSobelFilter = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}}; //filters for x gradient
		int[][] ySobelFilter = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}}; //filters for y gradient
		
		int[][] result = new int[b_img.length-2][b_img[0].length-2]; //will store image gradient
		
		//applies filters to get final gradient image
		for (int x = 1; x < b_img.length-1; x++)
		{	//how to get height and width?
			for(int y = 1; y < b_img[0].length-1; y++)
			{
				int newX = xSobelFilter[0][0]*b_img[x-1][y-1] + xSobelFilter[0][1]*b_img[x-1][y] + xSobelFilter[0][2]*b_img[x-1][y+1] +
						xSobelFilter[1][0]*b_img[x-1][y] + xSobelFilter[1][1]*b_img[x][y] + xSobelFilter[1][2]*b_img[x+1][y-1] +
						xSobelFilter[2][0]*b_img[x-1][y+1] + xSobelFilter[2][1]*b_img[x][y+1] + xSobelFilter[2][2]*b_img[x+1][y+1];
				
				int newY = ySobelFilter[0][0]*b_img[x-1][y-1] + ySobelFilter[0][1]*b_img[x-1][y] + ySobelFilter[0][2]*b_img[x-1][y+1] +
						ySobelFilter[1][0]*b_img[x-1][y] + ySobelFilter[1][1]*b_img[x][y] + ySobelFilter[1][2]*b_img[x+1][y-1] +
						ySobelFilter[2][0]*b_img[x-1][y+1] + ySobelFilter[2][1]*b_img[x][y+1] + ySobelFilter[2][2]*b_img[x+1][y+1];
				
				int gradient = (int) Math.sqrt(newX*newX + newY*newY);
				result[x-1][y-1] = gradient; //good for left borders, but right?
			}
		}
		return result;
	}

}
