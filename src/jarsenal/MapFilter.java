package jarsenal;

/**
 *
 * @author maxon
 */
public final class MapFilter {

	private static int size;
	private static int seed;

	public static void setSeed(int seed) {
		MapFilter.seed = seed;
	}

	public static void removeSingleHill(int[][] map) {
		size = map.length;
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if (map[x][y] > 3 && countCrossAround(map, x, y, map[x][y] - 1) == 4) {
					map[x][y] = map[x][y] - 1;
				}
				if (map[x][y] < 3 && countCrossAround(map, x, y, map[x][y] + 1) == 4) {
					map[x][y] = map[x][y] + 1;
				}
			}
		}
	}

	public static void removeHighElevation(int[][] map) {
		size = map.length;
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if (map[x][y] > 3 && countAround(map, x, y, map[x][y] - 2) > 0) {
					map[x][y] = map[x][y] - 1;
				}
				if (map[x][y] < 3 && countCrossAround(map, x, y, map[x][y] + 2) > 0) {
					map[x][y] = map[x][y] + 1;
				}
			}
		}
	}
	
	//public static void filtrate
	// посчитать количество пикселей рядом заданной величины
	private static int countCrossAround(int[][] layer, int x, int y, int value) {
		int count = 0;
		if (layer[x][prev(y)] == value) {
			count++;
		}
		if (layer[next(x)][y] == value) {
			count++;
		}
		if (layer[x][next(y)] == value) {
			count++;
		}
		if (layer[prev(x)][y] == value) {
			count++;
		}
		return count;
	}

	private static int countAround(int[][] layer, int x, int y, int value) {
		int count = 0;
		if (layer[prev(x)][prev(y)] == value) {
			count++;
		}
		if (layer[x][prev(y)] == value) {
			count++;
		}
		if (layer[next(x)][prev(y)] == value) {
			count++;
		}
		if (layer[next(x)][y] == value) {
			count++;
		}
		if (layer[next(x)][next(y)] == value) {
			count++;
		}
		if (layer[x][next(y)] == value) {
			count++;
		}
		if (layer[prev(x)][next(y)] == value) {
			count++;
		}
		if (layer[prev(x)][y] == value) {
			count++;
		}
		return count;
	}

	private static int next(int x) {
		if (x == size - 1) {
			return 0;
		} else {
			return x + 1;
		}
	}

	private static int prev(int x) {
		if (x == 0) {
			return size - 1;
		} else {
			return x - 1;
		}
	}

}
