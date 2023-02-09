
public class Evaluate {
	private char[][] gameBoard;
	private int size;
	private int tilesToWin;
	private int maxLevels;
	
	public Evaluate (int size, int tilesToWin, int maxLevels) {
		gameBoard = new char[size][size];
		
		//Filling the array with the char 'e'
		for (int i = 0; i < gameBoard.length; i ++) {
			for (int j = 0; j < gameBoard[i].length; j ++) {
				gameBoard[i][j] = 'e';
			}
		}
		this.size = size;
		this.tilesToWin = tilesToWin;
		this.maxLevels = maxLevels;
	}
	
	public Dictionary createDictionary() {
		return new Dictionary(size);
	}
	
	public Record repeatedState(Dictionary dict) {
		String gameStateStr = "";
		
		//Concatenating all contents in the array into a string
		for (int i = 0; i < gameBoard.length; i ++) {
			for (int j = 0; j < gameBoard[i].length; j ++) {
				gameStateStr += gameBoard[i][j];
			}
		}
		
		if (dict.get(gameStateStr) != null) return dict.get(gameStateStr);
		else return null;
	}
	
	public void insertState(Dictionary dict, int score, int level) {
		String gameStateStr = "";
		
		//Concatenating all contents in the array into a string
		for (int i = 0; i < gameBoard.length; i ++) {
			for (int j = 0; j < gameBoard[i].length; j ++) {
				gameStateStr += gameBoard[i][j];
			}
		}
		dict.put(new Record(gameStateStr, score, level));
	}
	
	public void storePlay(int row, int col, char symbol) {
		gameBoard[row][col] = symbol;
	}
	
	public boolean squareIsEmpty (int row, int col) {
		if (gameBoard[row][col] == 'e') return true;
		else return false;
	}
	
	public boolean tileOfComputer (int row, int col) {
		if (gameBoard[row][col] == 'c') return true;
		else return false;
	}
	
	public boolean tileOfHuman (int row, int col) {
		if (gameBoard[row][col] == 'h') return true;
		else return false;
	}
	
	public boolean wins (char symbol) {
		
		//Horizontal wins
		for (int i = 0; i < gameBoard.length; i ++) {
			for (int j = 0; j < gameBoard.length; j ++) {
				if (j + (tilesToWin - 1) >= gameBoard.length) break;
				for (int k = 0; k < tilesToWin; k ++) {
					if (gameBoard[i][j + k] == symbol);
					else break;
					if (k + 1 == tilesToWin) return true;
				}
			}
		}
		
		//Vertical wins
		for (int i = 0; i < gameBoard.length; i ++) {
			for (int j = 0; j < gameBoard.length; j ++) {
				if (i + (tilesToWin - 1) >= gameBoard.length) break;
				for (int k = 0; k < tilesToWin; k ++) {
					if (gameBoard[i + k][j] == symbol);
					else break;
					if (k + 1 == tilesToWin) return true;
				}
			}
		}
		
		//First diagonal wins
		for (int i = 0; i < gameBoard.length; i ++) {
			for (int j = 0; j < gameBoard.length; j ++) {
				if (j + (tilesToWin - 1) >= gameBoard.length || i + (tilesToWin - 1) >= gameBoard.length) break;
				for (int k = 0; k < tilesToWin; k ++) {
					if (gameBoard[i + k][j + k] == symbol);
					else break;
					if (k + 1 == tilesToWin) return true;
				}
			}
		}
		
		//Second diagonal wins
		for (int i = 0; i < gameBoard.length; i ++) {
			for (int j = gameBoard.length - 1; 0 < j; j --) {
				if (i + (tilesToWin - 1) >= gameBoard.length || j - (tilesToWin - 1) <= 0) break;
				for (int k = 0; k < tilesToWin; k ++) {
					if (gameBoard[i + k][j - k] == symbol);
					else break;
					if (k + 1 == tilesToWin) return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isDraw() {
		for (int i = 0; i < gameBoard.length; i ++) {
			for (int j = 0; j < gameBoard[0].length; j ++) {
				if (gameBoard[i][j] == 'e') return false;
			}
		}
		return true;
	}
	
	public int evalBoard() {
		if (wins('c') == true) return 3;
		else if (wins('h') == true) return 0;
		else if (isDraw() == true) return 2;
		else return 1;
	}
}
