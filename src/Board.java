public class Board {
    private int size;
    private char[][] colorGrid;
    private int[] queens;
    public long solverIterations = 0;

    public Board(char[][] grid) {
        this.colorGrid = grid;
        this.size = grid.length;
        this.queens = new int[size];
        for (int i = 0; i < size; i++) {
            queens[i] = -1;
        }
    }

    public int getSize() {
        return size;
    }
    public char getColor(int r, int c) {
        return colorGrid[r][c];
    }
    public void setQueen(int row, int col) {
        queens[row] = col;
    }
    public int getQueenCol(int row) {
        return queens[row];
    }

    public boolean isSafe(int row, int col) {
        char currentColor = getColor(row, col);

        for (int i = 0; i < row; i++) {
            int placedRow = i;
            int placedCol = queens[i];

            // 1. Column Constraint
            if (placedCol == col) {
                return false;
            }

            // 2. Color Constraint
            if (colorGrid[placedRow][placedCol] == currentColor) {
                return false;
            }

            // 3. Touch Constraint
            int rowDiff = Math.abs(placedRow - row);
            int colDiff = Math.abs(placedCol - col);

            if (rowDiff <= 1 && colDiff <= 1) {
                return false;
            }
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (queens[i] == j) {
                    System.out.print("# "); // Queen
                } else {
                    System.out.print(". "); 
                }
            }
            System.out.println();
        }
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (queens[i] == j) {
                    sb.append("#"); 
                } else {
                    sb.append(colorGrid[i][j]);
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}