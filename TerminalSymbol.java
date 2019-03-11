package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** an enum to express the elements that appear in a list representation of an expression */
public enum TerminalSymbol implements Symbol {
	VARIABLE(""), PLUS("+"), MINUS("-"), TIMES("*"), DIVIDE("/"), OPEN("("), CLOSE(")");
	
	private String symbol;
	
	TerminalSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * @return the symbol of any TerminalSymbol
	 */
	public String getSymbol() {
		return symbol;
	}

	@Override
	public ParseState parse(List<Token> input) {
		Objects.requireNonNull(input, "Input must not be null");
		
		if (!input.isEmpty() && tokenTypeMatchesThisSymbolsType(input.get(0))) {
			Node node = LeafNode.build(input.get(0));
			List<Token> remainder = new ArrayList<>(input);
			remainder.remove(0);
			return ParseState.build(node, remainder);
		}
		else {
			return ParseState.FAILURE;
		}
		
	}
	
	private boolean tokenTypeMatchesThisSymbolsType(Token token) {
		Objects.requireNonNull(token, "Token must not be null");
		return token.matches(TerminalSymbol.valueOf(this.name()));
	}
}