package algojava;

import java.util.Random;

public class Game {
    private BoardMatrix matrix;
    private boolean logging = false;
    private int steps;

    public Game(int N) {
        this.matrix = new BoardMatrix(N);
    }

    public void setRandom(int steps) {
        Random random = new Random();
        matrix.setLogging(logging);
        this.steps = steps;

        for (int i = 0; i < steps; i++) {
            if (logging) {
                System.out.printf("%s", matrix);
            }
            moveSpot(random.nextInt(4));
        }
    }

    public void moveSpot(int dir) {
        switch(dir % 4) {
            case 0:
                matrix.moveSpotRight();
                break;
            case 1:
                matrix.moveSpotUp();
                break;
            case 2:
                matrix.moveSpotLeft();
                break;
            case 3:
                matrix.moveSpotDown();
                break;
        }
    }

    public void setLogging(boolean flag) {
        logging = flag;
    }

    public BoardMatrix getMatrix() {
        return matrix.copy();
        // return new BoardMatrix(matrix);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Game started with "); 
        sb.append(steps);
        sb.append(" random steps.\n");
        sb.append(matrix);

        return sb.toString();
    }
}