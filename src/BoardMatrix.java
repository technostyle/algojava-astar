package algojava;

import java.util.Arrays;
import java.util.HashMap;

public class BoardMatrix {
    private int[][] matrix;
    private int N, Nsq;
    private HashMap<String, Integer> spot;
    private boolean logging = false;

    private void validateMatrixSize(int N) {
        if (!
            (0 < N && N < 10)
        ) {
            throw new java.lang.Error("Incorrect board size. Should be from (0, 10)");
        }
    }

    private void validateSquareMatrix(int[][] matrix) {
        for (int i = 0; i < N; i++) {
            if (matrix[i].length != N) {
                throw new java.lang.Error("Incorrect board matrix");        
            }
        }    
    }

    private void validateMatrixValues(int[][] matrix) {
        boolean[] containsVal = new boolean[Nsq];
        Arrays.fill(containsVal, false);

        int val;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                val = matrix[i][j];
                if ( 
                    !(0 <= val && val < Nsq)
                ) {
                    throw new java.lang.Error("Incorrect board matrix value");        
                }

                if (containsVal[val]) {
                    throw new java.lang.Error("Board contains same value at least twice");
                }

                containsVal[val] = true;
            }
        }
    }

    private void initMatrixSize(int N) {
        this.N = N;
        this.Nsq = N * N;
    }    

    private void initMatrix() {
        matrix = new int[N][N];
    }

    public BoardMatrix(int[][] matrix) {
        validateMatrixSize(matrix.length);
        initMatrixSize(matrix.length);
        validateSquareMatrix(matrix);
        validateMatrixValues(matrix);
        initMatrix();
        copy(matrix, this.matrix);
        setSpot();
    }

    private void initBoard() {
        int value = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = value++;                
            }
        }
        matrix[N - 1][N - 1] = 0;
    }

    public BoardMatrix(int N) {
        validateMatrixSize(N);
        initMatrixSize(N);
        initMatrix();
        initBoard();
        setSpot();
    }

    private void setSpot() {
        spot = new HashMap<String, Integer>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] == 0) {
                    spot.put("x", j);
                    spot.put("y", i);
                    return;
                }
            }
        }

        throw new java.lang.Error("Could not set spot position. Incorrect board matrix values.");
    }

    public HashMap<String, Integer> getSpot() {
        return new HashMap<String, Integer>(spot);
    }

    public boolean isEqual(BoardMatrix other) {
        if (N != other.getN())
            return false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] != other.get(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 8 * N - 1; i++) {
            sb.append("-");
        }
        sb.append("\n");

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(matrix[i][j]);
                sb.append("\t");
            }
            sb.append("\n");
        }

        for (int i = 0; i < 8 * N - 1; i++) {
            sb.append("-");
        }
        sb.append("\n");

        return sb.toString();
    }

    private void copy(int[][] src, int[][] dest) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                dest[i][j] = src[i][j];
            }
        }
    }

    public BoardMatrix copy() {
        return new BoardMatrix(matrix);
    }

    // public BoardMatrix(BoardMatrix other) {
    //     return new BoardMatrix(other.matrix);
    //     // other.matrix = new int[N][N];
    //     // copy(matrix, other.matrix);
    //     // other.N = N;
    //     // other.Nsq = Nsq;
    //     // other.spot = spot;
    //     // other.logging = logging;
    // }

    private void validateIndex(int x, int y) {
        if (
            !(0 <= x && x < N) ||
            !(0 <= y && y < N)
        ) {
            throw new java.lang.Error("Index out of range");
        }

    }

    public int get(int i, int j) {
        validateIndex(i, j);
        return matrix[i][j];
    }

    public int getXY(int x, int y) {
        validateIndex(x, y);
        return matrix[y][x];
    }

    public int getN() {
        return N;
    }

    public boolean canMoveSpotRight() {
        return (spot.get("x") != N - 1);
    }
    public boolean canMoveSpotUp() {
        return (spot.get("y") != 0);
    }
    public boolean canMoveSpotLeft() {
        return (spot.get("x") != 0);
    }
    public boolean canMoveSpotDown() {
        return (spot.get("y") != N - 1);
    }

    private void swapCells(int x1, int y1, int x2, int y2) {
        int t = matrix[y1][x1];
        matrix[y1][x1] = matrix[y2][x2];
        matrix[y2][x2] = t;
    }

    public void moveSpotRight() {
        int x = spot.get("x");
        int y = spot.get("y");
        
        if (logging) {
            System.out.println("moving spot right");
        }

        if (canMoveSpotRight()) {
            swapCells(x, y, x + 1, y);
            spot.put("x", x + 1);
        } else if (logging) {
            System.out.println("cannot move spot right");
        }
    }

    public void moveSpotUp() {
        int x = spot.get("x");
        int y = spot.get("y");

        if (logging) {
            System.out.println("moving spot up");
        }

        if (canMoveSpotUp()) {
            swapCells(x, y, x, y - 1);
            spot.put("y", y - 1);
        } else if (logging) {
            System.out.println("cannot move spot up");
        }
    }

    public void moveSpotLeft() {
        int x = spot.get("x");
        int y = spot.get("y");

        if (logging) {
            System.out.println("moving spot left");
        }

        if (canMoveSpotLeft()) {
            swapCells(x, y, x - 1, y);
            spot.put("x", x - 1);
        } else if (logging) {
            System.out.println("cannot move spot left");
        }
    }

    public void moveSpotDown() {
        int x = spot.get("x");
        int y = spot.get("y");

        if (logging) {
            System.out.println("moving spot down");
        }

        if (canMoveSpotDown()) {
            swapCells(x, y, x, y + 1);
            spot.put("y", y + 1);
        } else if (logging) {
            System.out.println("cannot move spot down");
        }
    }

    public void setLogging(boolean flag) {
        logging = flag;
    }
}