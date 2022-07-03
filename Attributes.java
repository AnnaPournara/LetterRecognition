import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.lang.Math;

public class Attributes {
	public BufferedImage loadImage() {
		try {
			BufferedImage bufferedImage = ImageIO.read(new File("/Users/sissy/Desktop/Untitled.png"));
			return bufferedImage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int[][] getImageToPixels(BufferedImage bufferedImage){
		if (bufferedImage == null) {
			throw new IllegalArgumentException();
		}
		int h = bufferedImage.getHeight();
		int w = bufferedImage.getWidth();
		int[][] pixels = new int[h][w];
		for (int i = 0; i < h; i++) {
			/**
			 * get pixels from image
			 */
			bufferedImage.getRGB(0, i, w, 1, pixels[i], 0, w);
		}
		for (int i=0; i<pixels.length; i++) {
			for(int j=0; j<pixels[i].length; j++) {
				if(pixels[i][j]<-1) {
					pixels[i][j] = 1;
				}
				else {
					pixels[i][j] = 0;
				}
			}
		}
		return pixels;
	}
	
	
	public String getAttr1(int [][] pixls) {
		int attr1 = 0; //x-box
		return Integer.toString(attr1);
	}
	
	public String getAttr2(int [][] pixls) {
		int attr2 = 0; //y-box
		return Integer.toString(attr2);
	}

	public String getAttr3(int [][] pixls) {
		int attr3 = 0; //width
		return Integer.toString(attr3);
	}

	public String getAttr4(int [][] pixls) {
		int attr4 = 0; //width
		return Integer.toString(attr4);
	}

	public String getAttr5(int [][] pixls) {
		int attr5 = 0; //width
		return Integer.toString(attr5);
	}

	public String getAttr6(int [][] pixls) {
		int attr6 = 0; //width
		return Integer.toString(attr6);
	}

	public String getAttr7(int [][] pixls) {
		int attr7 = 0; //width
		return Integer.toString(attr7);
	}

	public String getAttr8(int [][] pixls) {
		int attr8 = 0; //width
		return Integer.toString(attr8);
	}

	public String getAttr9(int [][] pixls) {
		int attr9 = 0; //width
		return Integer.toString(attr9);
	}

	public String getAttr11(int [][] pixls) {
		int attr11 = 0; //width
		return Integer.toString(attr11);
	}

	public String getAttr10(int [][] pixls) {
		int attr10 = 0; //width
		return Integer.toString(attr10);
	}

	public String getAttr12(int [][] pixls) {
		int attr12 = 0; //width
		return Integer.toString(attr12);
	}

	public String getAttr13(int [][] pixls) {
		int attr13 = 0; //width
		return Integer.toString(attr13);
	}

	public String getAttr14(int [][] pixls) {
		int attr14 = 0; //width
		return Integer.toString(attr14);
	}

	public String getAttr15(int [][] pixls) {
		int attr15 = 0; //width
		return Integer.toString(attr15);
	}

	public String getAttr16(int [][] pixls) {
		int attr16 = 0; //width
		return Integer.toString(attr16);
	}
	
	private int[] getCartesianCoordinates(int[] pos, int[][] table){
		int[] coords = new int[2];
		double[] center = new double[2];
		center[0] = ((table.length+1) / 2.0);
		center[1] = ((table[0].length+1) /2.0);
		if((pos[0]+1-center[0])<0) {
			coords[0] = (int)Math.floor(pos[0]+1-center[0]);
		}
		else {
			coords[0] = (int)Math.ceil(pos[0]+1-center[0]);
		}
		if((pos[1]+1-center[1])<0) {
			coords[1] = (int)Math.floor(pos[1]+1-center[1]);
		}
		else {
			coords[1] = (int)Math.ceil(pos[1]+1-center[1]);
		}
		
		return coords;
	}
	
}