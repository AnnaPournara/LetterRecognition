import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Attributes {
	public int[][] pixels = getImageToPixels(loadImage());
	public int[] indexes = boundingBox(pixels);
	public int[][] boundingBoxTable = storeBoundingBox(pixels);
	
	public int[][] storeBoundingBox(int[][] table){
		int[][] bbTable = new int[indexes[1]-indexes[0]+1][indexes[3]-indexes[2]+1];
		for(int i = indexes[0]; i<=indexes[1];i++) {
			for(int j=indexes[2];j<=indexes[3];j++) {
				bbTable[i-indexes[0]][j-indexes[2]] = table[i][j];
			}
		}
		return bbTable;
	}
	
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
		int [] x = new int[4];
		x=boundingBox(pixels);
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
	
	private double getAttr1() {
		double attr1 = indexes[2]+(indexes[3]-indexes[2])/2; //x-box
		return attr1;
	}
	
	private double getAttr2() {
		double attr2 = indexes[0]+(indexes[1]-indexes[0])/2; //y-box
		return attr2;
	}
	
	private double getAttr3() {
		double attr3 = indexes[3]-indexes[2]+1; //width of bb
		return attr3;
	}
	
	private double getAttr4() {
		double attr4 = indexes[1]-indexes[0]+1; //height of bb
        return attr4;
    }

    private double getAttr5() {
    	double attr5 = 0; //num of 1s
        for(int i=indexes[0]; i<=indexes[1]; i++) {
        	for(int j=indexes[2]; j<=indexes[3]; j++) {
        		if(pixels[i][j]==1)
        			attr5++;
        	}
        }
        return attr5;
    }

    private double getAttr6() {
        double attr6 = 0; //mean horizontal position of on pixels
        int counter = 0;
        int sum = 0;
        for (int i =0;i<boundingBoxTable.length;i++) {
        	for (int j=0; j<boundingBoxTable[i].length;j++) {
        		if(boundingBoxTable[i][j] == 1) {
        			counter++;
        			sum += getCartesianCoordinates(new int[] {i,j},boundingBoxTable)[0];
        		}
        	}
        }
        attr6 = (((double)sum)/((double)counter)) / ((double)boundingBoxTable[0].length);
        return attr6;
    }

    private double getAttr7() {
    	double attr7 = 0; //mean vertical position of on pixels
        int counter = 0;
        int sum = 0;
        for (int i =0;i<boundingBoxTable.length;i++) {
        	for (int j=0; j<boundingBoxTable[i].length;j++) {
        		if(boundingBoxTable[i][j] == 1) {
        			counter++;
        			sum += getCartesianCoordinates(new int[] {i,j},boundingBoxTable)[1];
        		}
        	}
        }
        attr7 = (((double)sum)/((double)counter)) / ((double)boundingBoxTable.length);
        return attr7;
    }

    private double getAttr8() {
    	double attr8 = 0; //mean squared horizontal position of on pixels
        int counter = 0;
        int sum = 0;
        for (int i =0;i<boundingBoxTable.length;i++) {
        	for (int j=0; j<boundingBoxTable[i].length;j++) {
        		if(boundingBoxTable[i][j] == 1) {
        			counter++;
        			int x = getCartesianCoordinates(new int[] {i,j},boundingBoxTable)[0];
        			sum += x*x;
        		}
        	}
        }
        attr8 = ((double)sum)/((double)counter);
        return attr8;
    }

    private double getAttr9() {
    	double attr9 = 0; //mean squared vertical position of on pixels
        int counter = 0;
        int sum = 0;
        for (int i =0;i<boundingBoxTable.length;i++) {
        	for (int j=0; j<boundingBoxTable[i].length;j++) {
        		if(boundingBoxTable[i][j] == 1) {
        			counter++;
        			int y = getCartesianCoordinates(new int[] {i,j},boundingBoxTable)[1];
        			sum += y*y;
        		}
        	}
        }
        attr9 = ((double)sum)/((double)counter);
        return attr9;
    }

    private double getAttr10() {
    	double attr10 = 0; //mean product of horizontal and vertical position of on pixels
        int counter = 0;
        int sum = 0;
        for (int i =0;i<boundingBoxTable.length;i++) {
        	for (int j=0; j<boundingBoxTable[i].length;j++) {
        		if(boundingBoxTable[i][j] == 1) {
        			counter++;
        			int x = getCartesianCoordinates(new int[] {i,j},boundingBoxTable)[0];
        			int y = getCartesianCoordinates(new int[] {i,j},boundingBoxTable)[1];
        			sum += x*y;
        		}
        	}
        }
        attr10 = ((double)sum)/((double)counter);
        return attr10;
    }
    
    private double getAttr11() {
    	double attr11 = 0; //mean product of  squared horizontal and vertical position of on pixels
        int counter = 0;
        int sum = 0;
        for (int i =0;i<boundingBoxTable.length;i++) {
        	for (int j=0; j<boundingBoxTable[i].length;j++) {
        		if(boundingBoxTable[i][j] == 1) {
        			counter++;
        			int x = getCartesianCoordinates(new int[] {i,j},boundingBoxTable)[0];
        			int y = getCartesianCoordinates(new int[] {i,j},boundingBoxTable)[1];
        			sum += x*x*y;
        		}
        	}
        }
        attr11 = ((double)sum)/((double)counter);
        return attr11;
    }

    private double getAttr12() {
    	double attr12 = 0; //mean product of  horizontal and squared vertical position of on pixels
        int counter = 0;
        int sum = 0;
        for (int i =0;i<boundingBoxTable.length;i++) {
        	for (int j=0; j<boundingBoxTable[i].length;j++) {
        		if(boundingBoxTable[i][j] == 1) {
        			counter++;
        			int x = getCartesianCoordinates(new int[] {i,j},boundingBoxTable)[0];
        			int y = getCartesianCoordinates(new int[] {i,j},boundingBoxTable)[1];
        			sum += x*y*y;
        		}
        	}
        }
        attr12 = ((double)sum)/((double)counter);
        return attr12;
    }

    private double getAttr13() {
    	double attr13 = 0; //width
        return attr13;
    }

    private double getAttr14() {
    	double attr14 = 0; //width
        return attr14;
    }

    private double getAttr15() {
    	double attr15 = 0; //width
        return attr15;
    }

    private double getAttr16() {
    	double attr16 = 0; //width
        return attr16;
    }
	
    public String[] getAttributes () {
    	//scaling to 0-15 and return as string
    	String[] attributes = new String[16];
    	double[] results = new double[16];
    	results[0] = getAttr1();
    	results[1] = getAttr2();
    	results[2] = getAttr3();
    	results[3] = getAttr4();
    	results[4] = getAttr5();
    	results[5] = getAttr6();
    	results[6] = getAttr7();
    	results[7] = getAttr8();
    	results[8] = getAttr9();
    	results[9] = getAttr10();
    	results[10] = getAttr11();
    	results[11] = getAttr12();
    	results[12] = getAttr13();
    	results[13] = getAttr14();
    	results[14] = getAttr15();
    	results[15] = getAttr16();
    	double min = results[0];
    	double max = results[0];
    	for(int i = 0; i<results.length;i++) {
    		if(results[i]<min) {
    			min = results[i];
    		}
    		if(results[i]>max) {
    			max = results[i];
    		}
    	}
    	for(int i = 0; i<results.length;i++) {
    		results[i] = ((results[i]-min)/(max-min))*15;
    	}
    	for(int i = 0; i<attributes.length;i++) {
    		attributes[i] = Integer.toString((int) Math.round(results[i]));
    	}
    	return attributes;
    }
}