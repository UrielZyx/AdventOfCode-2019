package Tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import day8.Image;

public class ImageTest {

	@Test
	public void test() {
		Image image = new Image("0222112222120000", 2, 2);
		Assert.assertArrayEquals(new int[][] {{0, 1}, {1, 0}}, image.getFinalImage());
	}

}
