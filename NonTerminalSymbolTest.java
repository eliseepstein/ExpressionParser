package parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class NonTerminalSymbolTest {
	
	Variable a = Variable.build("a");
	Variable b = Variable.build("b");
	Variable c = Variable.build("c");
	Variable d = Variable.build("d");
	Connector plus = Connector.build(TerminalSymbol.PLUS);
	Connector minus = Connector.build(TerminalSymbol.MINUS);
	Connector times = Connector.build(TerminalSymbol.TIMES);
	Connector divide = Connector.build(TerminalSymbol.DIVIDE);
	Connector open = Connector.build(TerminalSymbol.OPEN);
	Connector close = Connector.build(TerminalSymbol.CLOSE);
	
	@Test 
	void testExpectedParseExceptions() {
		assertThrows(NullPointerException.class, () -> {NonTerminalSymbol.parseInput(null);});
		assertThrows(NullPointerException.class, () -> {NonTerminalSymbol.EXPRESSION.parse(null);});
		
	}

	@Test
	void testValidParse() {
		
		List<Token> input = Arrays.asList(a, plus, b, divide, c);
		
		Optional<Node> node = NonTerminalSymbol.parseInput(input);
		assertTrue(node.isPresent());
		assertEquals(node.get().toString(), "[a,+,[b,/,c]]");
		
		input = Arrays.asList(a, times, open, c, minus, d, close);
		node = NonTerminalSymbol.parseInput(input);
		assertTrue(node.isPresent());
		assertEquals(node.get().toString(), "[a,*,[(,[c,-,d],)]]");
		
		input = Arrays.asList(minus, a);
		node = NonTerminalSymbol.parseInput(input);
		assertTrue(node.isPresent());
		assertEquals(node.get().toString(), "[-,a]");
		
		input = Arrays.asList(a, divide, open, b, plus, open, c, divide, d, close, close);
		node = NonTerminalSymbol.parseInput(input);
		assertTrue(node.isPresent());
		assertEquals(node.get().toString(), "[a,/,[(,[b,+,[(,[c,/,d],)]],)]]");
		
		input = Arrays.asList(a, times, b, minus, a, plus, b);
		node = NonTerminalSymbol.parseInput(input);
		assertTrue(node.isPresent());
		assertEquals(node.get().toString(), "[[a,*,b],-,a,+,b]");
		
		input = Arrays.asList(a, times, minus, b);
		node = NonTerminalSymbol.parseInput(input);
		assertTrue(node.isPresent());
		assertEquals(node.get().toString(), "[a,*,[-,b]]");
	}
	
	@Test
	void testInvalidParse() {
		
		List<Token> input = Arrays.asList(a, plus);
		
		Optional<Node> node = NonTerminalSymbol.parseInput(input);
		assertFalse(node.isPresent());
		
		input = Arrays.asList(times);
		
		node = NonTerminalSymbol.parseInput(input);
		assertFalse(node.isPresent());
		
		input = Arrays.asList(a, divide, open, b, minus, c);
		
		node = NonTerminalSymbol.parseInput(input);
		assertFalse(node.isPresent());
		
		input = Arrays.asList(a, close);
		
		node = NonTerminalSymbol.parseInput(input);
		assertFalse(node.isPresent());
	}

}
