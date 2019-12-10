package Tests;

import static org.junit.Assert.*;

import org.javatuples.Pair;
import org.junit.Test;

import day10.AsteroidMapper;

public class AsteroidsTest {

	@Test
	public void stationTest() {
		AsteroidMapper mapper = new AsteroidMapper(".#..#,.....,#####,....#,...##");
		assertArrayEquals(new int[] {8}, new int[] {mapper.visibilityFromBestPositionForStation()});
	}

	@Test
	public void station2Test() {
		AsteroidMapper mapper = new AsteroidMapper(".#..##.###...#######,##.############..##.,.#.######.########.#,.###.#######.####.#.,#####.##.#.##.###.##,..#####..#.#########,####################,#.####....###.#.#.##,##.#################,#####.##.###..####..,..######..##.#######,####.##.####...##..#,.#####..#.######.###,##...#.##########...,#.##########.#######,.####.#.###.###.#.##,....##.##.###..#####,.#.#.###########.###,#.#.#.#####.####.###,###.##.####.##.#..##");
		assertArrayEquals(new int[] {210}, new int[] {mapper.visibilityFromBestPositionForStation()});
	}

	@Test
	public void blastedTest() {
		AsteroidMapper mapper = new AsteroidMapper(".#....#####...#..,##...##.#####..##,##...#...#.#####.,..#.....#...###..,..#.#.....#....##");
		assertArrayEquals(new int[] {30}, new int[] {mapper.visibilityFromBestPositionForStation()});
		assertEquals(Pair.with(1, 8), mapper.findNthBlastedAsteroid(1));
		assertEquals(Pair.with(0, 9), mapper.findNthBlastedAsteroid(2));
		assertEquals(Pair.with(2, 12), mapper.findNthBlastedAsteroid(10));
		assertEquals(Pair.with(3, 2), mapper.findNthBlastedAsteroid(20));
		assertEquals(Pair.with(2, 5), mapper.findNthBlastedAsteroid(25));
		assertEquals(Pair.with(1, 5), mapper.findNthBlastedAsteroid(27));
		assertEquals(Pair.with(1, 6), mapper.findNthBlastedAsteroid(28));
		assertEquals(Pair.with(0, 6), mapper.findNthBlastedAsteroid(29));
		assertEquals(Pair.with(0, 7), mapper.findNthBlastedAsteroid(30));
		assertEquals(Pair.with(0, 8), mapper.findNthBlastedAsteroid(31));
		assertEquals(Pair.with(0, 14), mapper.findNthBlastedAsteroid(33));
		assertEquals(Pair.with(3, 14), mapper.findNthBlastedAsteroid(36));
	}

	@Test
	public void blasted2Test() {
		AsteroidMapper mapper = new AsteroidMapper(".#..##.###...#######,##.############..##.,.#.######.########.#,.###.#######.####.#.,#####.##.#.##.###.##,..#####..#.#########,####################,#.####....###.#.#.##,##.#################,#####.##.###..####..,..######..##.#######,####.##.####...##..#,.#####..#.######.###,##...#.##########...,#.##########.#######,.####.#.###.###.#.##,....##.##.###..#####,.#.#.###########.###,#.#.#.#####.####.###,###.##.####.##.#..##");
		assertArrayEquals(new int[] {210}, new int[] {mapper.visibilityFromBestPositionForStation()});
		assertEquals(Pair.with(12,11), mapper.findNthBlastedAsteroid(1));
		assertEquals(Pair.with(1,12), mapper.findNthBlastedAsteroid(2));
		assertEquals(Pair.with(2,12), mapper.findNthBlastedAsteroid(3));
		assertEquals(Pair.with(8,12), mapper.findNthBlastedAsteroid(10));
		assertEquals(Pair.with(0,16), mapper.findNthBlastedAsteroid(20));
		assertEquals(Pair.with(9,16), mapper.findNthBlastedAsteroid(50));
		assertEquals(Pair.with(16,10), mapper.findNthBlastedAsteroid(100));
		assertEquals(Pair.with(6,9), mapper.findNthBlastedAsteroid(199));
		assertEquals(Pair.with(2,8), mapper.findNthBlastedAsteroid(200));
		assertEquals(Pair.with(9,10), mapper.findNthBlastedAsteroid(201));
		assertEquals(Pair.with(1,11), mapper.findNthBlastedAsteroid(299));
	}

}
