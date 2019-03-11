package parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** builder of internal tree nodes */
public enum NonTerminalSymbol implements Symbol {
	EXPRESSION, EXPRESSION_TAIL, TERM, TERM_TAIL, UNARY, FACTOR;

	/** the map of Non-terminal symbols and their respective symbol sequences */
	private static final Map<NonTerminalSymbol,Map<TerminalSymbol, SymbolSequence>> productions;

	static {
		/** creates the non-terminal expression map */

		// EXPRESSION is of the form: TERM EXPRESSION_TAIL
		SymbolSequence termAndExpressionTail = SymbolSequence.build(NonTerminalSymbol.TERM, NonTerminalSymbol.EXPRESSION_TAIL);

		Map<TerminalSymbol, SymbolSequence> expressionInnerMap = 
				new HashMap<>(Map.of(TerminalSymbol.OPEN, termAndExpressionTail, 
									TerminalSymbol.MINUS, termAndExpressionTail, 
									TerminalSymbol.VARIABLE, termAndExpressionTail));

		// EXPRESSION_TAIL is of the form: + TERM EXPRESSION_TAIL, - TERM EXPRESSION_TAIL, or EPSILON
		SymbolSequence plusTermAndExpressionTail = SymbolSequence.build(TerminalSymbol.PLUS, NonTerminalSymbol.TERM, NonTerminalSymbol.EXPRESSION_TAIL);
		SymbolSequence minusTermAndExpressionTail = SymbolSequence.build(TerminalSymbol.MINUS, NonTerminalSymbol.TERM, NonTerminalSymbol.EXPRESSION_TAIL);

		Map<TerminalSymbol, SymbolSequence> expressionTailInnerMap = 
				new HashMap<>(Map.of(TerminalSymbol.PLUS, plusTermAndExpressionTail,
						             TerminalSymbol.MINUS, minusTermAndExpressionTail,
						             TerminalSymbol.CLOSE, SymbolSequence.EPSILON));
		   expressionTailInnerMap.put(null, SymbolSequence.EPSILON);

		// TERM is of the form: UNARY TERM_TAIL
		SymbolSequence unaryAndTermTail = SymbolSequence.build(NonTerminalSymbol.UNARY, NonTerminalSymbol.TERM_TAIL);

		Map<TerminalSymbol, SymbolSequence> termInnerMap = 
				new HashMap<>(Map.of(TerminalSymbol.MINUS, unaryAndTermTail,
									TerminalSymbol.VARIABLE, unaryAndTermTail,
									TerminalSymbol.OPEN, unaryAndTermTail));

		// TERM_TAIL is of the form: * UNARY TERM_TAIL, / UNARY TERM_TAIL, or EPSILON
		SymbolSequence timesUnaryAndTermTail = SymbolSequence.build(TerminalSymbol.TIMES, NonTerminalSymbol.UNARY, NonTerminalSymbol.TERM_TAIL);
		SymbolSequence divideUnaryAndTermTail = SymbolSequence.build(TerminalSymbol.DIVIDE, NonTerminalSymbol.UNARY, NonTerminalSymbol.TERM_TAIL);


		Map<TerminalSymbol, SymbolSequence> termTailInnerMap = 
				new HashMap<>(Map.of(TerminalSymbol.TIMES, timesUnaryAndTermTail,
									TerminalSymbol.DIVIDE, divideUnaryAndTermTail,
									TerminalSymbol.PLUS, SymbolSequence.EPSILON,
									TerminalSymbol.MINUS, SymbolSequence.EPSILON,
									TerminalSymbol.CLOSE,SymbolSequence.EPSILON));
				termTailInnerMap.put(null, SymbolSequence.EPSILON);

		// UNARY is of the form: - FACTOR, or FACTOR
		SymbolSequence minusFactor = SymbolSequence.build(TerminalSymbol.MINUS, NonTerminalSymbol.FACTOR);
		SymbolSequence factor = SymbolSequence.build(NonTerminalSymbol.FACTOR);

		Map<TerminalSymbol, SymbolSequence> unaryInnerMap = 
				new HashMap<>(Map.of(TerminalSymbol.MINUS, minusFactor, 
									TerminalSymbol.VARIABLE, factor, 
									TerminalSymbol.OPEN, factor));

		// FACTOR is of the form: ( EXPRESSION ), or VARIABLE
		SymbolSequence openExpressionClose = SymbolSequence.build(TerminalSymbol.OPEN, NonTerminalSymbol.EXPRESSION, TerminalSymbol.CLOSE);
		SymbolSequence variable = SymbolSequence.build(TerminalSymbol.VARIABLE);

		Map<TerminalSymbol, SymbolSequence> factorInnerMap = 
				new HashMap<>(Map.of(TerminalSymbol.OPEN, openExpressionClose, 
									TerminalSymbol.VARIABLE, variable));

		productions = Map.of(NonTerminalSymbol.EXPRESSION, expressionInnerMap,
							NonTerminalSymbol.EXPRESSION_TAIL, expressionTailInnerMap,
							NonTerminalSymbol.TERM, termInnerMap,
							NonTerminalSymbol.TERM_TAIL, termTailInnerMap,
							NonTerminalSymbol.UNARY, unaryInnerMap,
							NonTerminalSymbol.FACTOR, factorInnerMap);
	}	

	@Override
	public ParseState parse(List<Token> input) {
		Objects.requireNonNull(input, "The input must not be null");
		SymbolSequence sequenceToCompareAgainst;

		if (!input.isEmpty()) {
			sequenceToCompareAgainst = getSymbolsAssociatedSequence(input.get(0).getType());
		}
		else {
			sequenceToCompareAgainst = getSymbolsAssociatedSequence(null);
		}

		if (sequenceToCompareAgainst != null) {
			ParseState state = sequenceToCompareAgainst.match(input);
			if (state.getSuccess()) {
				return state;
			} 
		}
		return ParseState.FAILURE;
	}

	/**
	 * Returns the SymbolSequence from the productions table that matches this Non-terminal and the given token
	 * @param input - the TerminalSymbol to use as the key to get the symbolSequence from this symbols production table
	 * @return the SymbolSequence from the productions table that matches this Non-terminal and the given token
	 */
	private SymbolSequence getSymbolsAssociatedSequence(TerminalSymbol type) {
		Map<TerminalSymbol, SymbolSequence> innerMap = productions.get(this);
		return innerMap.get(type);
	}

	/**
	 * attempts to parse the input with an Expression
	 * @param input the list of tokens to be parsed
	 * @return the root node if the parsing process is successful and an empty optional otherwise
	 */
	static final Optional<Node> parseInput(List<Token> input) {
		ParseState state = NonTerminalSymbol.EXPRESSION.parse(input);
		Optional<Node> returnOptional;

		if (state.getSuccess() && state.hasNoRemainder()) {
			returnOptional = Optional.of(state.getNode());
		}
		else {
			returnOptional = Optional.empty();
		}

		return returnOptional;
	}
}
