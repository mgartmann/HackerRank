import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {
    private static final int FIELD_OCCUPIED_BY_QUEEN = 1;

    /*
     * Complete the 'queensAttack' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n: the number of rows and columns in the board
     *  2. INTEGER k: the number of obstacles on the board
     *  3. INTEGER r_q: the row number of the queen's position
     *  4. INTEGER c_q: the column number of the queen's position
     *  5. 2D_INTEGER_ARRAY obstacles
     */

    public static int queensAttack(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {
        Position queen = Position.of(r_q, c_q);

        int fieldsToTop = n - queen.row;
        int fieldsToRight = n - queen.column;
        int fieldsToLeft = queen.column - 1;
        int fieldsToBottom = queen.row - 1;

        Position positiveDiagonalTop;
        Position positiveDiagonalBottom;
        if (fieldsToTop < fieldsToRight) {
            positiveDiagonalTop = Position.of(n, queen.column + fieldsToTop);
            positiveDiagonalBottom = Position.of(queen.row - fieldsToLeft, 1);
        } else {
            positiveDiagonalTop = Position.of(queen.row + fieldsToRight, n);
            positiveDiagonalBottom = Position.of(1, queen.column - fieldsToBottom);
        }

        Position negativeDiagonalTop;
        Position negativeDiagonalBottom;
        if (fieldsToTop < fieldsToLeft) {
            negativeDiagonalTop = Position.of(n, queen.column - fieldsToTop);
            negativeDiagonalBottom = Position.of(queen.row - fieldsToRight, n);
        } else {
            negativeDiagonalTop = Position.of(queen.row + fieldsToLeft, 1);
            negativeDiagonalBottom = Position.of(1, queen.column + fieldsToBottom);
        }

        Position horizontalLeft = Position.of(r_q, 1);
        Position horizontalRight = Position.of(r_q, n);
        Position verticalTop = Position.of(n, c_q);
        Position verticalBottom = Position.of(1, c_q);

        // positive diagonal: y =  x + t => t = y - x = r - c
        // negative diagonal: y = -x + t => t = y + x = r + c
        int positiveDiagonalT = queen.row - queen.column;
        int negativeDiagonalT = queen.row + queen.column;

        // TODO this for-loop's conditional statements could probably be improved...
        for (List<Integer> obs : obstacles) {
            Position obstacle = Position.ofObstacle(obs.get(0), obs.get(1));

            if (isOnPositiveDiagonal(positiveDiagonalT, obstacle)) {
                if (obstacle.row > queen.row) {
                    if (obstacle.distanceTo(queen) < positiveDiagonalTop.distanceTo(queen)) {
                        positiveDiagonalTop = obstacle;
                    }
                } else {
                    if (obstacle.distanceTo(queen) < positiveDiagonalBottom.distanceTo(queen)) {
                        positiveDiagonalBottom = obstacle;
                    }
                }
            }
            else if (isOnNegativeDiagonal(negativeDiagonalT, obstacle)) {
                if (obstacle.row > queen.row) {
                    if (obstacle.distanceTo(queen) < negativeDiagonalTop.distanceTo(queen)) {
                        negativeDiagonalTop = obstacle;
                    }
                } else {
                    if (obstacle.distanceTo(queen) < negativeDiagonalBottom.distanceTo(queen)) {
                        negativeDiagonalBottom = obstacle;
                    }
                }
            }
            else if (obstacle.row == queen.row) {
                if (obstacle.column > queen.column) {
                    if (obstacle.distanceTo(queen) < horizontalRight.distanceTo(queen)) {
                        horizontalRight = obstacle;
                    }
                } else {
                    if (obstacle.distanceTo(queen) < horizontalLeft.distanceTo(queen)) {
                        horizontalLeft = obstacle;
                    }
                }
            }
            else if (obstacle.column == queen.column) {
                if (obstacle.row > queen.row) {
                    if (obstacle.distanceTo(queen) < verticalTop.distanceTo(queen)) {
                        verticalTop = obstacle;
                    }
                } else {
                    if (obstacle.distanceTo(queen) < verticalBottom.distanceTo(queen)) {
                        verticalBottom = obstacle;
                    }
                }
            }
        }

        int negativeDiagonalFields = Math.max(negativeDiagonalBottom.attackableFieldsUntil(negativeDiagonalTop) - FIELD_OCCUPIED_BY_QUEEN, 0);
        int positiveDiagonalFields = Math.max(positiveDiagonalBottom.attackableFieldsUntil(positiveDiagonalTop) - FIELD_OCCUPIED_BY_QUEEN, 0);
        int horizontalFields = Math.max(horizontalLeft.attackableFieldsUntil(horizontalRight) - FIELD_OCCUPIED_BY_QUEEN, 0);
        int verticalFields = Math.max(verticalBottom.attackableFieldsUntil(verticalTop) - FIELD_OCCUPIED_BY_QUEEN, 0);

        return negativeDiagonalFields + positiveDiagonalFields + horizontalFields + verticalFields;
    }

    private static boolean isOnPositiveDiagonal(int positiveDiagonalT, Position obstacle) {
        return obstacle.row == obstacle.column + positiveDiagonalT;
    }

    private static boolean isOnNegativeDiagonal(int negativeDiagonalT, Position obstacle) {
        return obstacle.row == -1 * obstacle.column + negativeDiagonalT;
    }
}

class Position {
    public final int row;
    public final int column;
    public final boolean isObstacle;

    private Position(int row, int column, boolean isObstacle){
        this.row = row;
        this.column = column;
        this.isObstacle = isObstacle;
    };

    public static Position of(int row, int column) {
        return new Position(row, column, false);
    }

    public static Position ofObstacle(int row, int column) {
        return new Position(row, column, true);
    }

    public int distanceTo(Position other) {
        int heightDifference = Math.abs(this.row - other.row);
        int widthDifference = Math.abs(this.column - other.column);
        if (heightDifference == 0 && widthDifference == 0) return 0;
        if (heightDifference == 0) return widthDifference + 1; // + 1 to account for the field this is standing on
        if (widthDifference == 0) return heightDifference + 1; // + 1 to account for the field this is standing on

        // Width difference can be returned for two points forming a diagonal
        // since an n x n chess board's diagonal is n fields long.
        // sqrt(n^2 + n^2) = length of diagonal by Pythagoras
        // sqrt(2) = ratio between a fields width and its diagonal's length.
        // The diagonal's length in fields is calculated by dividing its length by sqrt(2).
        // => sqrt(n^2 + n^2) / sqrt(2) = sqrt(2n^2) / sqrt(2) = n
        return widthDifference + 1; // + 1 to account for the field this is standing on
    }

    public int attackableFieldsUntil(Position other) {
        int attackableFields = distanceTo(other);
        if (this.isObstacle) attackableFields--;
        if (other.isObstacle) attackableFields--;
        return attackableFields;
    }
}

public class QueensAttackTwo {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        String[] secondMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int r_q = Integer.parseInt(secondMultipleInput[0]);

        int c_q = Integer.parseInt(secondMultipleInput[1]);

        List<List<Integer>> obstacles = new ArrayList<>();

        IntStream.range(0, k).forEach(i -> {
            try {
                obstacles.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Result.queensAttack(n, k, r_q, c_q, obstacles);

        System.out.println(result);

        bufferedReader.close();
    }
}
