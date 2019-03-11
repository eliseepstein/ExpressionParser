package parser;

public abstract class AbstractToken implements Token {

	/** tests whether the given parameter type matches getType() 
	 * @param type a type of TerminalSymbol to check if it matches the type of the given object 
	 * @return a boolean representing whether or not the given parameter type matches the type of the object
	 */
	public final boolean matches(TerminalSymbol type) {
		return type == getType();
	}
	
}
