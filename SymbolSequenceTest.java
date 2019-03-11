package parser;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class SymbolSequenceTest {
	
	List<Symbol> list = Arrays.asList(NonTerminalSymbol.TERM, NonTerminalSymbol.EXPRESSION_TAIL);
	List<Symbol> list2 = Arrays.asList(null, null);
	SymbolSequence sequence = SymbolSequence.build(NonTerminalSymbol.TERM, NonTerminalSymbol.EXPRESSION_TAIL);
	SymbolSequence sequence2 = SymbolSequence.build(list);
	SymbolSequence sequence3 = SymbolSequence.build(list2);
	SymbolSequence sequence4 = SymbolSequence.build(null, null);
	Variable a = Variable.build("a");
	Variable b = Variable.build("b");
	Connector plus = Connector.build(TerminalSymbol.PLUS);
	Connector divide = Connector.build(TerminalSymbol.DIVIDE);
	
	@Test
	void testExpectedBuildExceptions() {
		List<Symbol> list3 = null;
		List<Token> list4 = null;
		assertThrows(NullPointerException.class, () -> {SymbolSequence.build(list3);});
		assertThrows(NullPointerException.class, () -> {SymbolSequence.build(TerminalSymbol.CLOSE).match(list4); });
	}
	
	@Test
	void testBuildAndToString() {
		assertEquals(sequence.toString(), sequence2.toString());
		assertEquals(sequence.toString(), "[TERM, EXPRESSION_TAIL]");
		assertEquals(sequence3.toString(), sequence4.toString());
		assertEquals(sequence3.toString(), "[null, null]");
	}
	
	@Test
	void testMatch() {
		List<Token> input = new ArrayList<Token>();
		input.add(a);
		input.add(plus);
		input.add(b);
				
		ParseState state = sequence.match(input);

		assertTrue(state.getSuccess());
		assertEquals(state.getNode().toString(), "[a,+,b]");
		
		input.add(divide);
		
		state = sequence.match(input);

		assertSame(state, ParseState.FAILURE);
		
		input.clear();
		input.add(divide);
		input.add(b);
		
		state = sequence.match(input);
		assertFalse(state.getSuccess());
		assertSame(state, ParseState.FAILURE);
	}

}
