package xlo.database.annotation;

/**
 * @author XiaoLOrange
 * @time 2020.12.02
 * @title
 */

public enum OperaType {
	/**
	 * =
	 */
	EQUAL("="),
	/**
	 * >
	 */
	GT(">"),
	/**
	 * >=
	 */
	GET(">="),
	/**
	 * <
	 */
	LT("<"),
	/**
	 * <=
	 */
	LET("<="),
	/**
	 * LIKE - '%-%'
	 */
	LIKE("%-%"),
	/**
	 * LEFT LIKE '%-'
	 */
	LL("%-"),
	/**
	 * RIGHT LIKE '-%'
	 */
	RL("-%"),

	;

	private String opera;

	OperaType(String o) {
		this.opera = o;
	}

	/**
	 * Returns the name of this enum constant, as contained in the
	 * declaration.  This method may be overridden, though it typically
	 * isn't necessary or desirable.  An enum type should override this
	 * method when a more "programmer-friendly" string form exists.
	 *
	 * @return the name of this enum constant
	 */
	@Override
	public String toString() {
		return opera;
	}
}
