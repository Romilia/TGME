package GameLogic;

import java.util.Objects;

public class Tuple {

    public int row;
    public int col;

    public Tuple(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple)) return false;
        Tuple tuple = (Tuple) o;
        return row == tuple.row && col == tuple.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "("+this.row+","+this.col+")";
    }
}
