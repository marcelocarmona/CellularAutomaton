package ar.edu.unlp.CellularAutomaton.model;

/**
 * @author mclo
 */

import java.awt.Dimension;
import java.util.Enumeration;

/**
 * Shape contains data of one (predefined) shape.
 *
 */
@Deprecated
public class Shape {
  private static final Shape CLEAR;
  private static final Shape GLIDER;
  private static final Shape SMALLEXPL;
  private static final Shape EXPLODER;
  private static final Shape CELL10;
  private static final Shape FISH;
  private static final Shape PUMP;
  private static final Shape SHOOTER;
  public static final Shape[] COLLECTION;

  static {
    CLEAR = new Shape("Clear", new int[][] {} );
    GLIDER = new Shape("Glider", new int[][] {{1,0}, {2,1}, {2,2}, {1,2}, {0,2}});
    SMALLEXPL = new Shape("Small Exploder", new int[][] {{0,1}, {0,2}, {1,0}, {1,1}, {1,3}, {2,1}, {2,2}});
    EXPLODER = new Shape("Exploder", new int[][] {{0,0}, {0,1}, {0,2}, {0,3}, {0,4}, {2,0}, {2,4}, {4,0}, {4,1}, {4,2}, {4,3}, {4,4}});
    CELL10 = new Shape("10 Cell Row", new int[][] {{0,0}, {1,0}, {2,0}, {3,0}, {4,0}, {5,0}, {6,0}, {7,0}, {8,0}, {9,0}});
    FISH = new Shape("Lightweight spaceship", new int[][] {{0,1}, {0,3}, {1,0}, {2,0}, {3,0}, {3,3}, {4,0}, {4,1}, {4,2}});
    PUMP = new Shape("Tumbler", new int[][] {{0,3}, {0,4}, {0,5}, {1,0}, {1,1}, {1,5}, {2,0}, {2,1}, {2,2}, {2,3}, {2,4}, {4,0}, {4,1}, {4,2}, {4,3}, {4,4}, {5,0}, {5,1}, {5,5}, {6,3}, {6,4}, {6,5}});
    SHOOTER = new Shape("Gosper Glider Gun", new int[][] {{0,2}, {0,3}, {1,2}, {1,3}, {8,3}, {8,4}, {9,2}, {9,4}, {10,2}, {10,3}, {16,4}, {16,5}, {16,6}, {17,4}, {18,5}, {22,1}, {22,2}, {23,0}, {23,2}, {24,0}, {24,1}, {24,12}, {24,13}, {25,12}, {25,14}, {26,12}, {34,0}, {34,1}, {35,0}, {35,1}, {35,7}, {35,8}, {35,9}, {36,7}, {37,8}});
    COLLECTION = new Shape[] {CLEAR, GLIDER, SMALLEXPL, EXPLODER, CELL10, FISH, PUMP, SHOOTER};
  }
	
	
  private final String name;
  private final int[][] shape;
  
  /**
   * Construct a Shape.
   * @param name name of shape
   * @param shape shape data
   */
  public Shape( String name, int[][] shape ) {
    this.name = name;
    this.shape = shape;
  }
  
  /**
   * Get dimension of shape.
   * @return dimension of the shape in cells
   */
  public Dimension getDimension() {
    int shapeWidth = 0;
    int shapeHeight = 0;
    for (int cell = 0; cell < shape.length; cell++) {
      if (shape[cell][0] > shapeWidth)
        shapeWidth = shape[cell][0];
      if (shape[cell][1] > shapeHeight)
        shapeHeight = shape[cell][1];
    }
    shapeWidth++;
    shapeHeight++;
    return new Dimension( shapeWidth, shapeHeight );
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

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return name+" ("+shape.length+" cell"+(shape.length==1?"":"s")+")";
  }
}