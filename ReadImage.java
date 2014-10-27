package semester; //just ignore, this is where I'm keeping all of the files for this project

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ReadImage 
{
	public static void main(String[] args) throws IOException 
	{
	//Replace block with actual name of image file
	
		BufferedImage image = ImageIO.read(new File("block")); //Option 1 if we can use BufferedImage
	}
}
