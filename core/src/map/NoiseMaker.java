package map;

import com.badlogic.gdx.math.MathUtils;

public class NoiseMaker {

	public NoiseMaker() {}

		SimplexNoise simplexNoise = new SimplexNoise(150,0.7,MathUtils.random(10000));

		double xStart = 0;
		double xEnd = 500;
		double yStart = 0;
		double yEnd = 500;
		
		int xResolution = 200;
		int yResolution = 200;
		
		public double[][] generate() {
		
			double[][] result = new double[xResolution][yResolution];
			
			for (int i = 0;  i < xResolution; i++) {
				for (int j= 0; j < yResolution; j++) {
					int x = (int) (xStart+i*((xEnd-xStart) / xResolution));
					int y = (int) (yStart+j*((yEnd-yStart) / yResolution));
					result[i][j] = 0.15* (1 + simplexNoise.getNoise(x, y)); // original 0.3
				}

			}
			System.out.println(result.toString());
			return result;
		}
 		

	}
