package parser;

/** represents an element of an expression */
public interface Token {

	/**
	 * @return the type of the Token
	 */
	TerminalSymbol getType(); 

	/** tests whether the given parameter type matches the type of the object 
	 * @param type a type of TerminalSymbol to check if it matches the type of the given object 
	 * @return a boolean representing whether or not the given parameter type matches the type of the Token
	 */
	boolean matches(TerminalSymbol type);

	/** 
	 * @return whether the token is an operator ( +, -, *, /)
	 */
	boolean isOperator();
}
