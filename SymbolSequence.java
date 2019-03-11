package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/** a builder of multiple tree nodes */
final class SymbolSequence {
	
	/** the list used to produce a SymbolSequence */
	private final List<Symbol> production;
	
	/** a SymbolSequence with an empty production */
	static final SymbolSequence EPSILON = new SymbolSequence(new ArrayList<Symbol>());
		
	private SymbolSequence(List<Symbol> production) {
		this.production = production;
	}
	
	/**
	 * builds a SymbolSequence
	 * @param production the list used to produce a SymbolSequence
	 * @return a SymbolSequence w/the inputed production
	 */
	public static final SymbolSequence build(List<Symbol> production) {
		Objects.requireNonNull(production, "Production list must not be null");
		return new SymbolSequence(production);
	}
	
	/**
	 * builds a SymbolSequence
	 * @param symbols a variable number of Symbols to produce a SymbolSequence
	 * @return a SymbolSequence w/the inputed symbols
	 */
	public static final SymbolSequence build(Symbol...symbols) {
		return build(Arrays.asList(symbols));
	}
	
	
	/**
	 * @return the string representation of a SymbolSequence
	 */
	public String toString() {
		return production.toString();
	}

	/**
	 * matches the inputed list of tokens to the symbols in the production
	 * @param input a list of Tokens for the symbols in the production to be matched to
	 * @return a successful ParseState if all of the symbols in the production match the input, FAILURE otherwise
	 */
	ParseState match(List<Token> input) {
		Objects.requireNonNull(input, "input must not be null");
		List<Token> remainder = new ArrayList<Token>(input);
		InternalNode.Builder builder = new InternalNode.Builder();
		
		for (Symbol symbol : production) {
			ParseState state = symbol.parse(remainder);
			if (state.getSuccess()) {
				builder.addChild(state.getNode());
				remainder = state.getRemainder();
			}
			else {
				return ParseState.FAILURE;
			}
		}
		builder = builder.simplify();
		return ParseState.build(builder.build(), remainder);
	}
}
