package OSM2Tramod;

import java.util.Objects;

public class GridLocator {
	private int x;
	private int y;
	 private int hashCode;

	
	public GridLocator(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.hashCode = Objects.hash(x, y);this.hashCode = Objects.hash(x, y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GridLocator that = (GridLocator) o;
        return x == that.x && y == that.y;
    }

	 @Override
	    public int hashCode() {
	        return this.hashCode;
	    }
	 
	 public String toString() {
		 return "x: " + this.getX() + ", y :" + this.getY();
	 }

}
