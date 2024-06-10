/**
 *
 * @author Furkan AK @Kowachka
 */

package bingogame;

import java.util.Scanner;

public class BingoGameClass<T> {

	private BingoCardClass[] playersCards = new BingoCardClass[2]; // Cards for two players.
	private Integer[] randomPermutation;
	private int drawIndex = 0;
	private int drawNumberIndex = 0;
	private Scanner scanner = new Scanner(System.in);
	private boolean isPermutationComplete = true;

	public BingoGameClass(int[][] player1Cards, int[][] player2Cards, Integer[] drawNumbers) {
		if (drawNumbers == null) {

			randomPermutation = generatePermutation(90);
			drawNumberIndex = 90;
		} else {
			randomPermutation = new Integer[90];
			int index = 0;
			for (int i = 0; i < drawNumbers.length; i++) {
				boolean isDuplicate = false;
				for (int j = 0; j < index; j++) {
					if (drawNumbers[i].equals(randomPermutation[j])) {
						isDuplicate = true;
						break;
					}
				}
				if (!isDuplicate) {
					randomPermutation[index++] = drawNumbers[i];
					drawNumberIndex++;
				}
			}
			boolean[] numbers = new boolean[90];
			for (Integer number : randomPermutation) {
				if (number != null && number >= 1 && number <= 90) {
					numbers[number - 1] = true;
				}
			}
			// Check if all numbers are present.
			for (boolean nmbr : numbers) {
				if (!nmbr) {
					isPermutationComplete = false; // Missing number found.
					break;
				}
			}
		}

		// Set up the card for Player 1.
		if (player1Cards != null) {
			playersCards[0] = new BingoCardClass(player1Cards);
		} else {
			playersCards[0] = new BingoCardClass(null); // Random card.
		}

		// Oyuncu 2 için kartı ayarla
		if (player2Cards != null) {
			playersCards[1] = new BingoCardClass(player2Cards);
		} else {
			playersCards[1] = new BingoCardClass(null); // Set up the card for Player 2.
		}
	}

	// Method to generate a random permutation of numbers from 1 to n.
	private Integer[] generatePermutation(int n) {
		Integer[] result = new Integer[n];
		for (int i = 0; i < n; i++) {
			result[i] = i + 1;
		}

		// To shuffle the index of the array.
		for (int i = n - 1; i > 0; i--) {
			int index = (int) (Math.random() * (i + 1)); // Selects a random index.
			int a = result[index];
			result[index] = result[i]; // Swaps the value at the selected index with the value at index i.
			result[i] = a;
		}
		return result; // Returns the shuffled array.
	}

	// Method managing the gameplay of the game.
	public void playGame() {
		if (!playersCards[0].isValid() || !playersCards[1].isValid()) {

			System.out.println(
					"Check the entered card and enough numbers in the row. Numbers should not repeat, entered numbers should be in the correct range and.");

		} else {
			boolean gameWon = false; // Keeps track of whether the game has been won or not.
			int winningPlayer = -1; // Index of the winning player.
			boolean tie = false;

			if (drawNumberIndex != 90) {
				System.out.println(
						"You haven't entered enough numbers to draw or you've entered the same number again. Please check your numbers.");
			} else if (!isPermutationComplete) {
				System.out.println(
						"Not all numbers from 1 to 90 are included in the numbers to be drawn. Please ensure that you have entered the numbers correctly.");
			} else {
				// The game continues until it is won or until all numbers are drawn.
				while (!gameWon && drawIndex < randomPermutation.length) {
					int drawnNumber = randomPermutation[drawIndex++]; // Draw the next number.
					clearConsole(); // Clears the console.
					System.out.println("\nDrawn Number: " + (drawnNumber));

					// Updates and prints the card for each player.
					for (int i = 0; i < playersCards.length; i++) {
						playersCards[i].markNumber(drawnNumber); // Marks if the number is drawn.
					}

					// Prints each player's card and their Bingo status.
					for (int i = 0; i < playersCards.length; i++) {
						System.out.println("Player " + (i + 1) + " Card:");
						playersCards[i].printCard();

						int markedRows = playersCards[i].checkRows(); // Checks the number of marked rows.
						if (markedRows == 1) {
							System.out.println("Player " + (i + 1) + " First Bingo!");
						} else if (markedRows == 2) {
							System.out.println("Player " + (i + 1) + " Second Bingo!");
						} else if (markedRows == 3) {
							winningPlayer = i + 1;
							gameWon = true;
						}
					}
					if (playersCards[0].checkRows() == 3 && playersCards[1].checkRows() == 3) {
						tie = true;
						break; // No need to continue the game if it's a tie.
					}

					// Announce the winner if the game is won.
					if (gameWon) {
						System.out.println();
						System.out.println("Player " + winningPlayer + " Bingo and won the game!");
					} else {
						waitForEnter(); // If the game is still ongoing, waits for a new input from the user.
					}
				}
				if (tie) {
					System.out.println("Both players completed all rows simultaneously. It's a tie game!");
				}
			}
		}
	}

	private void clearConsole() {
		// Prints multiple new lines to clear the terminal display.
		for (int i = 0; i < 50; ++i) {
			System.out.println();
		}
	}

	// Method waiting for the user to press the Enter key.
	private void waitForEnter() {
		System.out.println("Press Enter to continue...");
		scanner.nextLine(); // Waits for a line input from the user.
	}

}
