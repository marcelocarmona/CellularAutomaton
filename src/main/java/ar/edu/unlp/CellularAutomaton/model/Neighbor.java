package ar.edu.unlp.CellularAutomaton.model;


public class Neighbor {
	private int col;
	private int row;

	public Neighbor(int col, int row) {
		super();
		this.col = col;
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

    public boolean equals(Object obj) {
        if (obj instanceof Neighbor) {
        	Neighbor nr = (Neighbor)obj;
            return (col == nr.col) && (row == nr.row);
        }
        return super.equals(obj);
    }
    
    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(getCol());
        bits ^= java.lang.Double.doubleToLongBits(getRow()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }
    
    public String toString(){
    	return getClass().getSimpleName()+"("+col+", "+row+")";
    }

}
