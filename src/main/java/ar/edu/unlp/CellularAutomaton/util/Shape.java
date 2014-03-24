package ar.edu.unlp.CellularAutomaton.util;

import java.util.Enumeration;

/**
 * Shape contains data of one (predefined) shape.
 */
public enum Shape {
    CLEAR("Clear", new int[][] {} ),
    GLIDER("Glider", new int[][] {{1,0}, {2,1}, {2,2}, {1,2}, {0,2}}),
    SMALLEXPL("Small Exploder", new int[][] {{0,1}, {0,2}, {1,0}, {1,1}, {1,3}, {2,1}, {2,2}}),
    EXPLODER("Exploder", new int[][] {{0,0}, {0,1}, {0,2}, {0,3}, {0,4}, {2,0}, {2,4}, {4,0}, {4,1}, {4,2}, {4,3}, {4,4}}),
    CELL10("10 Cell Row", new int[][] {{0,0}, {1,0}, {2,0}, {3,0}, {4,0}, {5,0}, {6,0}, {7,0}, {8,0}, {9,0}}),
    FISH("Lightweight spaceship", new int[][] {{0,1}, {0,3}, {1,0}, {2,0}, {3,0}, {3,3}, {4,0}, {4,1}, {4,2}}),
    PUMP("Tumbler", new int[][] {{0,3}, {0,4}, {0,5}, {1,0}, {1,1}, {1,5}, {2,0}, {2,1}, {2,2}, {2,3}, {2,4}, {4,0}, {4,1}, {4,2}, {4,3}, {4,4}, {5,0}, {5,1}, {5,5}, {6,3}, {6,4}, {6,5}}),
    SHOOTER("Gosper Glider Gun", new int[][] {{0,2}, {0,3}, {1,2}, {1,3}, {8,3}, {8,4}, {9,2}, {9,4}, {10,2}, {10,3}, {16,4}, {16,5}, {16,6}, {17,4}, {18,5}, {22,1}, {22,2}, {23,0}, {23,2}, {24,0}, {24,1}, {24,12}, {24,13}, {25,12}, {25,14}, {26,12}, {34,0}, {34,1}, {35,0}, {35,1}, {35,7}, {35,8}, {35,9}, {36,7}, {37,8}});
    
    
    private final String name;
    private final int[][] shape;
    
    
    /**
     * Construct a Shape.
     * @param name name of shape
     * @param shape shape data
     */
    private Shape( String name, int[][] shape ) {
      this.name = name;
      this.shape = shape;
    }
    
    /**
     * Get height of shape
     * @return height of the shape in cells
     */
    public int getHeight(){
    	int shapeHeight = 0;
    	for (int cell = 0; cell < shape.length; cell++) {
    		 if (shape[cell][1] > shapeHeight)
    			 shapeHeight = shape[cell][1];
    	}
    	shapeHeight++;
    	return shapeHeight;
    }
    
    /**
     * Get width of shape
     * @return width of the shape in cells
     */
    public int getWidth(){
    	int shapeWidth = 0;
    	for (int cell = 0; cell < shape.length; cell++) {
    		 if (shape[cell][0] > shapeWidth)
    			 shapeWidth = shape[cell][0];
    	}
    	shapeWidth++;
    	return shapeWidth;
    }
    
    /**
     * Get name of shape.
     * @return name of shape
     */
    public String getName() {
      return name;
    }
    
    /**
     * Get shape data.
     * Hide the shape implementation. Returns a anonymous Enumerator object.
     * @return enumerated shape data
     */
    public Enumeration<int[]> getCells() {
      return new Enumeration<int[]>() {
        private int index=0;
        public boolean hasMoreElements() {
          return index < shape.length;
        }
        public int[] nextElement() {
          return shape[index++];
        }
      };
    }    
    
    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    public String toString() {
      return name+" ("+shape.length+" cell"+(shape.length==1?"":"s")+")";
    }
}
