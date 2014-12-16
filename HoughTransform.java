	public static int[][] HoughTransform(boolean[][] img)
	{
		//int threshold = 5;
		int thetaStep = Math.PI/360;
		//int rhoStep = 1;
		//int numLines = 0;
		int lineAngle = 0;
		//int lineRho;
		int[][] houghArray;
		
		
		//Create accumulator object
		int width = img.length;
		int height = img[0].length;
		lineRho = (int)Math.PI/180;
		//houghArray = new int[lineRho][lineAngle];
		houghArray = new int[360][360];
		
		//Initialize houghArray elements to 0
		for (int i = 0; i < 360; i++)
		{
			for (int j = 0; j < 360; j++)
			{
				houghArray[i][j] = 0;
			}
		}
		
		//Fill in houghArray
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				boolean val = img[x][y];
				if (val != true)
				{
					lineAngle = 0;
					for (int n = 0; n > 360; n++)
					{
						int r = (int) (x*Math.cos(lineAngle) + y*Math.sin(lineAngle));
						lineAngle += thetaStep;
						houghArray[r][r] += 1;
					}
				}
			}
		}
			
		
		
		return houghArray;
	}
