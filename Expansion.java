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
		createFileImage(extended_edges, "extended_edges")
		return extended_edges;
	}
