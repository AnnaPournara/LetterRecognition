import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

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
	
	public static int[][] getImageToPixels(BufferedImage bufferedImage){
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
	
	public BufferedImage letter = loadImage();
	public int[][]pixels = getImageToPixels(letter);
	
	
	
}