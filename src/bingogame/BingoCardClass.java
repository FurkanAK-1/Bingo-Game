/**
 *
 * @author Furkan AK @Kowachka
 */

package bingogame;

public class BingoCardClass<T> {

	private NodeClass<Integer> head; // The starting node of the card.
	private boolean isRandomized;
	private boolean isValid = true;

	public BingoCardClass(int[][] predefinedcardNumbers) {
		if (predefinedcardNumbers == null) {
			generateNumbersForRandomCard(); // Create a random card.
			isRandomized = true;
		} else {
			createPredefinedCard(predefinedcardNumbers); // Create a predeterfined card.
			isRandomized = false;
		}
	}

	private void generateNumbersForRandomCard() {

		NodeClass<Integer> lastRow = null;
		for (int row = 0; row < 3; row++) { // Loop for three lines.
			NodeClass<Integer> currentRow = null;
			NodeClass<Integer> lastCol = null;

			// Determine the block states for each column in this row.
			boolean[] isColumnBlocked = new boolean[9];
			int count = 0;
			while (count < 4) {
				int columnBlock = (int) (Math.random() * 9);
				if (!isColumnBlocked[columnBlock]) {
					isColumnBlocked[columnBlock] = true;
					count++;
				}
			}
			int rowCount = 0; // Number of numbers in the row.
			// Assign appropriate numbers for each column.
			for (int col = 0; col < 9 && rowCount < 5; col++) {
				if (!isColumnBlocked[col]) {
					int startValue;
					int endValue;
					if (col == 0) {
						startValue = 1;
						endValue = 9;
					} else if (col == 8) {
						startValue = 80;
						endValue = 90;
					} else {
						startValue = col * 10;
						endValue = (col * 10) + 9;
					}
					int number = generateUniqueNumber(startValue, endValue);
					NodeClass<Integer> newNode = new NodeClass<>(number); // Creating a new node.
					if (currentRow == null) {
						currentRow = newNode; // Linking to the next node.
					} else {
						lastCol.next = newNode; // Updating the last node.
					}
					lastCol = newNode;
					rowCount++;
				}
			}

			// Linking rows vertically.
			if (lastRow == null) {
				head = currentRow;
			} else {
				NodeClass<Integer> tempLastRow = lastRow;
				NodeClass<Integer> tempCurrentRow = currentRow;
				while (tempLastRow != null && tempCurrentRow != null) {
					tempLastRow.down = tempCurrentRow; // Vertical connection.
					tempLastRow = tempLastRow.next;
					tempCurrentRow = tempCurrentRow.next;
				}
			}
			lastRow = currentRow; // Updating this row as the last row.
		}
	}

	// Generating unique numbers within a specific range.
	private int generateUniqueNumber(int start, int end) {

		int number = (int) (Math.random() * ((end - start) + 1)) + start;
		// Checking if the number is already present on the card.
		while (numberExists(number)) {
			number = (int) (Math.random() * ((end - start) + 1)) + start; // Kontrol edilen aralık genişletilir.
		}
		return number;
	}
	// Checking if a specific number exists on the card.

	private boolean numberExists(Integer number) {
		NodeClass<Integer> row = head;

		while (row != null) {
			NodeClass<Integer> column = row;
			while (column != null) {
				if (column.data.equals(number)) {
					return true; // Number found.
				}
				column = column.next;
			}
			row = row.down;
		}
		return false; // Number not found.
	}

	private void createPredefinedCard(int[][] predefinedNumbers) {
		NodeClass<Integer> lastRow = null;

		for (int row = 0; row < predefinedNumbers.length; row++) {
			int count = 0; // Satırdaki -1 sayacı
			NodeClass<Integer> currentRowFirstNode = null;
			NodeClass<Integer> lastNodeCurrentRow = null;
			for (int col = 0; col < predefinedNumbers[row].length; col++) {
				int number = predefinedNumbers[row][col];
				// Check the correct number range for the column.

				if (number != -1 && !isNumberInRange(number, col)) {
					isValid = false;
				}
				if (number != -1 && numberExists(number)) {
					isValid = false;
				}
				if (number == -1) {
					count++;
				}

				NodeClass<Integer> newNode = new NodeClass<>(number);
				if (currentRowFirstNode == null) {
					currentRowFirstNode = newNode;
				} else {
					lastNodeCurrentRow.next = newNode;
				}
				lastNodeCurrentRow = newNode;

				if (lastRow != null) {
					NodeClass<Integer> lastNodePreviousRow = lastRow;
					for (int k = 0; k < col; k++) {
						if (lastNodePreviousRow.next != null) {
							lastNodePreviousRow = lastNodePreviousRow.next;
						}

					}

					lastNodePreviousRow.down = newNode;

				}
			}
			if (count != 4) {
				isValid = false;
			}
			if (head == null) {
				head = currentRowFirstNode;
			}
			lastRow = currentRowFirstNode;
		}

	}

	private boolean isNumberInRange(int number, int column) {
		int start;
		int end;
		if (column == 0) {
			start = 1; // Start value for the 1st column.
			end = 9; // End value.
		} else {
			start = column * 10; // Start value for other columns.
			end = (column == 8) ? 90 : start + 9; // End value.
		}

		return number >= start && number <= end;
	}

	public boolean isValid() {
		return isValid;
	}

	public boolean isRandomized() {
		return isRandomized;
	}

	public void printCard() {
		if (isRandomized) {
			NodeClass<Integer> row = head;
			while (row != null) {
				NodeClass<Integer> column = row;
				int columnCount = 0;
				while (columnCount < 9) {
					if (column != null && getColumnIndex(column.data) == columnCount) {

						System.out
								.print((column.marked ? "[" + column.data + "]" : String.format("%2d ", column.data)));

						column = column.next;
					} else {
						System.out.print(" X ");
					}
					columnCount++;
				}

				System.out.println();
				row = row.down;
			}
		} else {
			NodeClass<Integer> rowPointer = head;
			while (rowPointer != null) {
				NodeClass<Integer> colPointer = rowPointer;
				for (int column = 0; column < 9; column++) {
					if (colPointer != null && colPointer.data != -1) {
						System.out.print((colPointer.marked ? "[" + colPointer.data + "]"
								: String.format("%2d ", colPointer.data)));
						colPointer = colPointer.next;
					} else {
						System.out.print(" X ");
						if (colPointer != null) {
							colPointer = colPointer.next;
						}
					}
				}
				System.out.println();
				rowPointer = rowPointer.down;

			}
		}
	}

	// Determining in which column a number is located.
	private int getColumnIndex(int number) {
		if (number == 90) {
			return 8;
		} else {
			return (number) / 10;
		}

	}

	// Row check for Bingo.
	public int checkRows() {
		int fullRowCount = 0;
		NodeClass<Integer> row = head;
		while (row != null) {
			boolean allMarked = true;
			NodeClass<Integer> col = row;
			while (col != null) {
				if (!col.marked && col.data != -1) {
					allMarked = false;
					break;
				}
				col = col.next;
			}
			if (allMarked) {
				fullRowCount++;
			}
			row = row.down;
		}
		return fullRowCount;
	}

	// Mark if number is found on the card.
	public void markNumber(int number) {
		NodeClass<Integer> row = head;
		while (row != null) {
			NodeClass<Integer> col = row;
			while (col != null) {
				if (col.data != null && col.data.equals(number)) {
					col.marked = true;
					return;
				}
				col = col.next;
			}
			row = row.down;
		}
	}

}
