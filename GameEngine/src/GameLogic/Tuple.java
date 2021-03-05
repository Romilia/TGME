package GameLogic;

public class Tuple {

    public int row;
    public int col;

    public Tuple(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Tuple)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Tuple c = (Tuple) o;

        // Compare the data members and return accordingly
        return c.row == this.row
                && c.col == this.col;
    }
}
