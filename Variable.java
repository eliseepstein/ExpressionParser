package parser;

import java.util.Objects;
import java.util.function.Function;

/** represents a token such as 'a', 'b', or 'c' */
public final class Variable extends AbstractToken {

	private final String representation;
	private static Cache<String, Variable> cache = new Cache<String, Variable>();

	private Variable(String representation) {
		this.representation = representation;
	}

	/** makes a Variable
	 * @param representation the String representation for a variable
	 * @return a Variable with the inputed String representation
	 */
	public static final Variable build(String representation) {
		Objects.requireNonNull(representation, "Representation cannot be null");
		Function<String, Variable> variableConstructor = x -> new Variable(x);
		return cache.get(representation, variableConstructor);
	}

	/**
	 * @return the type of the Variable
	 */
	@Override
	public TerminalSymbol getType() {
		return TerminalSymbol.VARIABLE;
	}

	/**
	 * @return the String representation of the Variable
	 */
	public final String getRepresentation() {
		return representation;
	}

	/**
	 * @return the String representation of the Variable
	 */
	@Override
	public String toString() {
		return getRepresentation();
	}

	@Override
	public boolean isOperator() {
		return false;
	}

}

