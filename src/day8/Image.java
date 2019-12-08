package day8;

public class Image {

	int width, height, layers;
	int[][][] image;
	
	public Image(String input, int width, int height) {
		this.width = width;
		this.height = height;
		this.layers = input.length()/(width * height);
		image = new int[layers][height][width];
		int inputIndex = 0;
		for (int i = 0; i < layers; i++) {
			for (int j = 0; j < height; j++) {
				for (int k = 0; k < width; k++) {
					image[i][j][k] = input.charAt(inputIndex++) - '0';
				}
			}
		}
	}

	public int calculateChecksum() {
		int[] count = new int[] {width * height, 0, 0};
		for (int i = 0; i < layers; i++) {
			int[] layerCount = new int[] {0, 0, 0};
			for (int j = 0; j < height; j++) {
				for (int k = 0; k < width; k++) {
					layerCount[image[i][j][k]]++;
				}
			}
			if (layerCount[0] < count[0]) {
				count = layerCount;
			}
		}
		return count[1] * count[2];
	}

}
