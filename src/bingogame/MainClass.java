/**
 *
 * @author Furkan AK @Kowachka
 */

package bingogame;

public class MainClass {

	public static void main(String[] args) {

		// Predefined card for first player
		int[][] card1 = { { 5, -1, 22, -1, 45, -1, 60, 73, -1 }, { -1, 10, -1, 31, 47, 58, 68, -1, -1 },
				{ -1, 17, 26, 38, -1, -1, -1, 79, 86 } };

		// Predefined card for second player
		int[][] card2 = { { 1, -1, 24, -1, 43, -1, 60, 73, -1 }, { -1, -1, -1, 31, 48, 52, 68, -1, 90 },
				{ -1, 17, -1, 38, -1, 55, -1, 79, 86 } };

		int[][] randomcard = null; // You can enter null in the constructor method to create a Random Card.

		Integer[] randomPermutation = null; // You can enter null in the constructor method to generate random numbers.

		Integer[] Permutation = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
				25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
				51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76,
				77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90 };

		BingoGameClass game = new BingoGameClass(randomcard, randomcard, randomPermutation);

		game.playGame(); // Play Gamee.

	}

}
