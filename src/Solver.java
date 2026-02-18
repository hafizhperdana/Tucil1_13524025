public class Solver {
    private long iterations;
    private int delayMs;
    private SolverListener listener;
    private long lastUpdateTime = 0; 

    public interface SolverListener {
        void onUpdate(Board board, long iterations);
    }
    public Solver(int initialDelay) {
        this.iterations = 0;
        this.delayMs = initialDelay;
    }
    public void setDelay(int delayMs) {
        this.delayMs = delayMs;
    }
    public void setListener(SolverListener listener) {
        this.listener = listener;
    }
    public long getIterations() { 
        return iterations; 
    }

    public boolean solve(Board board, int row) {
        int N = board.getSize();

        if (Thread.currentThread().isInterrupted()) {
            return false;
        }

        // BASE
        if (row == N) {
            boolean isValid = checkEntireBoard(board);
            if (isValid) {
                updateGUI(board, true); 
                return true;
            }
            return false;
        }

        // RECURSIVE 
        for (int col = 0; col < N; col++) {
            iterations++;
            board.setQueen(row, col);
            updateGUI(board, false);
            if (solve(board, row + 1)) {
                return true; 
            }
            board.setQueen(row, -1);
        }
        return false;
    }

    private boolean checkEntireBoard(Board board) {
        int N = board.getSize();
        for (int r = 0; r < N; r++) {
            int c = board.getQueenCol(r);
            if (!board.isSafe(r, c)) {
                return false; 
            }
        }
        return true;
    }

    private void updateGUI(Board board, boolean forceUpdate) {
        if (listener == null) return;

        long currentTime = System.currentTimeMillis();

        if (forceUpdate || (currentTime - lastUpdateTime >= delayMs)) {
            
            listener.onUpdate(board, iterations);
            
            lastUpdateTime = currentTime; 
        }
    }
}