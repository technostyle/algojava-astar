package algojava;

import java.util.LinkedList;

public class BoardState {
    private BoardMatrix current;
    private BoardMatrix target;
    private int N;
    private int Nsq;
    private int steps;
    private boolean logging = false;
    private int[] manhattanAtoms;
    private int manhattan = -1;

    public BoardState(BoardMatrix current, int steps) {
        this.current = current.copy();
        this.N = current.getN();
        init();
        manhattan();
    }

    public BoardState(int N) {
        this.N = N;
        this.current = new BoardMatrix(N);
        init();
        manhattan();
    }

    private void init() {
        this.Nsq = N * N;
        this.target = new BoardMatrix(N);
        this.steps = steps;
        this.manhattanAtoms = new int[Nsq];
    }

    public int getN() {
        return N;
    }

    public boolean isVictory() {
        return current.isEqual(target);
    }

    public boolean isEqual(BoardState other) {
        return 
            (other != null) &&
            current.isEqual(other.getMatrix());
    }

    private int targetX(int value) {
        return (value - 1) % N;
    }

    private int targetY(int value) {
        return (value - 1) / N;
    }

    private int countManhattanAtom(int val, int x, int y) {
        int tx = (val == 0) ? N - 1 : targetX(val);
        int ty = (val == 0) ? N - 1 : targetY(val);
        int atom = Math.abs(x - tx) + Math.abs(y - ty);
        manhattanAtoms[val] = atom;

        // System.out.printf(
        //     "value: %d \t x: %d, y: %d \t targetX: %d, targetY: %d\n",
        //     val, x, y, tx, ty
        // );

        return atom;
    }

    public int countManhattan() {
        int total = 0;
        int val;
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                val = current.getXY(x, y); 
                total += countManhattanAtom(val, x, y);
            }
        }

        if (logging) {
            System.out.printf("Counting Manhattan.\n%s", this);
        }

        return total;
    }

    public int manhattan() {
        if (this.manhattan == -1) {
            this.manhattan = countManhattan();
        }

        return this.manhattan;
    }

    public int steps() {
        return this.steps;
    }

    public int incrementSteps() {
        return ++steps;
    }

    public String getMatrixString() {
        return current.toString();
    }

    public BoardMatrix getMatrix() {
        return current.copy();
    }

    public LinkedList<BoardState> getNextStates() {
        LinkedList<BoardState> res = new LinkedList<BoardState>();

        if (current.canMoveSpotRight()) {
            BoardMatrix movedRight = current.copy();
            movedRight.moveSpotRight();
            BoardState state = new BoardState(movedRight, steps + 1);
            res.addLast(state);
        }

        if (current.canMoveSpotUp()) {
            BoardMatrix movedUp = current.copy();
            movedUp.moveSpotUp();
            BoardState state = new BoardState(movedUp, steps + 1);
            res.addLast(state);
        }

        if (current.canMoveSpotLeft()) {
            BoardMatrix movedLeft = current.copy();
            movedLeft.moveSpotLeft();
            BoardState state = new BoardState(movedLeft, steps + 1);
            res.addLast(state);
        }

        if (current.canMoveSpotDown()) {
            BoardMatrix movedDown = current.copy();
            movedDown.moveSpotDown();
            BoardState state = new BoardState(movedDown, steps + 1);
            res.addLast(state);
        }

        return res;
    }

    public void setLogging(boolean flag) {
        logging = flag;
    }

    private String getMatrixVertSeparator() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (8 * N - 1); i++) {
            sb.append("-");
        }
        return sb.toString();
    }

    private String currentAndTargetMatrixInRow() {
        StringBuilder sb = new StringBuilder();
        String matrixHoriSeparator = "\t | \t";
        String matrixVertSeparator = getMatrixVertSeparator(); 
        String matrixInnerSeparator = "\t";

        sb.append(matrixVertSeparator);
        sb.append(matrixHoriSeparator);
        sb.append(matrixVertSeparator);
        sb.append("\n");

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // refactor later
                sb.append(current.get(i, j));
                if (j != N - 1) {
                    sb.append(matrixInnerSeparator);
                }
            }

            sb.append(matrixHoriSeparator);

            for (int j = 0; j < N; j++) {
                // refactor later
                sb.append(target.get(i, j));
                if (j != N - 1) {
                    sb.append(matrixInnerSeparator);
                }
            }

            sb.append("\n");
        }

        sb.append(matrixVertSeparator);
        sb.append(matrixHoriSeparator);
        sb.append(matrixVertSeparator);
        sb.append("\n");

        return sb.toString();
    }

    private String manhattanAtomsInRow() {
        StringBuilder sb = new StringBuilder();
        String arrayIdentificator = ": ";
        String arraySeparator = "\t";

        for (int val = 0; val < Nsq; val++) {
            sb.append(val);
            sb.append(arrayIdentificator);
            sb.append(manhattanAtoms[val]);
            sb.append(arraySeparator);

            if ((val + 1) % N == 0)
                sb.append("\n");
        }
        sb.append("\n");

        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current board state: \n");
        sb.append(currentAndTargetMatrixInRow());
        sb.append(manhattanAtomsInRow());
        sb.append("Next states:\n");
        
        LinkedList<BoardState> nextStates = getNextStates();
        BoardState next;
        while((next = nextStates.pollLast()) != null) {
            sb.append(next.getMatrixString());
        }

        return sb.toString();
    }
}