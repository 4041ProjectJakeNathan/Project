	/*
	 * It took a while, but I think I finally get why this is used.  After thresholding only the most
	 * prevalent edges will still be present (if we're lucky, most of the noise has been extracted).
	 * This process then purposefully expands or thickens the remaining edges to hopefully cause
	 * intersection if thresholding removed some of the finer edges.  The biggest question is:
	 * How many iterations of this should we use?
	 * 
	 * Also: Terribly inefficient to do this separately from sobelOperator.  
	 * Just didn't want to screw up sobelOperator while trying to come up with this.
	 */

public BufferedImage expansion(BufferedImage edges)
	{
		int i = 3; // i = # of iterations
		BufferedImage extended_edges = edges;
		for (int q = 0; q < i; q++) //times we want to expand
		{
			edges = extended_edges;
			for (int x = 1; x < edges.getWidth()-1; x++)
			{
				for (int y = 1; y < edges.getHeight()-1; y++)
				{
					if (edges.getRGB(x,y) == 255)
						{
							extended_edges.setRGB(x-1, y-1, 255);
							extended_edges.setRGB(x-1, y  , 255);
							extended_edges.setRGB(x-1, y+1, 255);
							extended_edges.setRGB(x-1, y  , 255);
							extended_edges.setRGB(x+1, y-1, 255);
							extended_edges.setRGB(x-1, y+1, 255);
							extended_edges.setRGB(x,   y+1, 255);
							extended_edges.setRGB(x+1, y+1, 255);
						}
				}
			}
		}
		createFileImage(extended_edges, "extended_edges");
		return extended_edges;
	}
	
		/*
	 * I think we should re-add the buffer method because accounting for the edges
	 * in all of these operations is annoying to say the least.
	 */
	//current implementation will shift lines to the right, can correct later
	public BufferedImage thin(BufferedImage img)
	{
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y< img.getHeight(); y++)
			{
				int neighbors = checkNeighbors(img, x, y);
				
				if (neighbors == 0)//check if lone pixel
					img.setRGB(x, y, 0);
				if (neighbors > 2)//check excessive amount
					img.setRGB(x, y, 0);
			}
		}
		return img;
	}
	
	public int checkNeighbors(BufferedImage img, int x, int y)
	{
		int neighbors = 0;
		
		if (img.getRGB(x-1, y-1) == 255)
				neighbors++;
		if (img.getRGB(x-1, y  ) == 255)
			neighbors++;
		if (img.getRGB(x-1, y+1) == 255) 
			neighbors++;						
		if (img.getRGB(x-1, y  ) == 255) 						
			neighbors++;						
		if (img.getRGB(x+1, y-1) == 255) 		
			neighbors++;			
		if (img.getRGB(x-1, y+1) == 255) 		
			neighbors++;			
		if (img.getRGB(x,   y+1) == 255) 		
			neighbors++;			
		if (img.getRGB(x+1, y+1) == 255) 		
			neighbors++;
		
		return neighbors;
	}
