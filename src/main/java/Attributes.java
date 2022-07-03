import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Attributes {
	public BufferedImage loadImage() {
		try {
			BufferedImage bufferedImage = ImageIO.read(new File("alpha.png"));
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
		for (int i =0; i<pixels.length;i++) {
            for(int j =0; j<pixels[i].length;j++) {
                System.out.print(pixels[i][j]);
            }
            System.out.println();
        }
		int [] x = new int[4];
		x=boundingBox(pixels);
		for(int k=0; k<x.length;k++) {
			System.out.println(x[k]);
		}
		return pixels;
	}
	
	public int[] boundingBox(int [][] pixls) {
		int top=-1;
		int bottom=-1;
		int left=-1;
		int right=-1;
		int [] bb = new int[4];
		for(int i=0; i<pixls.length; i++) {
			for(int j=0; j<pixls[i].length; j++) {
				//top boundary
				if(pixls[i][j]==1 && top==-1) {
					top = i;
				}
				//bottom boundary
				if(pixls[i][j]==1) {
					bottom = i;
				}
				//left boundary
				if(pixls[i][j]==1) {
					if(left==-1)
						left = j;
					else if(j<left)
						left=j;
				}
				//right boundary
				if(pixls[i][j]==1) {
					if(right==-1)
						right =j;
					else if(j>right)
						right=j;
				}	
			}
		}
		bb[0]=top;
		bb[1]=bottom;
		bb[2]=left;
		bb[3]=right;
		return bb;
	}
	
	public String getAttr1(int [] BB) {
		int attr1 = BB[2]+(BB[3]-BB[2])/2; //x-box
		return Integer.toString(attr1);
	}
	
	public String getAttr2(int [] BB) {
		int attr2 = BB[0]+(BB[1]-BB[0])/2; //y-box
		return Integer.toString(attr2);
	}
	
	public String getAttr3(int [] BB) {
		int attr3 = BB[3]-BB[2]+1; //width of bb
		return Integer.toString(attr3);
	}
	
	public String getAttr4(int [] BB) {
        int attr4 = BB[1]-BB[0]+1; //height of bb
        return Integer.toString(attr4);
    }

    public String getAttr5(int [] BB, int [][] pixls) {
        int attr5 = 0; //num of 1s
        for(int i=BB[0]; i<=BB[1]; i++) {
        	for(int j=BB[2]; j<=BB[3]; j++) {
        		if(pixls[i][j]==1)
        			attr5++;
        	}
        }
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
	
}