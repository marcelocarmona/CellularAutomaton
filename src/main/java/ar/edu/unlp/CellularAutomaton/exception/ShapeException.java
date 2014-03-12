package ar.edu.unlp.CellularAutomaton.exception;

/**
 * Exeption for Shape too big
 * 
 * @author mclo
 */
public class ShapeException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a ShapeException
	 * @param message of the exception
	 */
	public ShapeException(String message) {
		super(message);
	}
}
