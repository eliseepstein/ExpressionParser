package parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConnectorTest { 
	
	@Test
	void testBuildExceptions() {
		Assertions.assertThrows(NullPointerException.class, () -> Connector.build(null));
		Assertions.assertThrows(IllegalArgumentException.class, () -> Connector.build(TerminalSymbol.VARIABLE));
	}

	@Test
	void testBuildTypeAndString() {
		Connector testConnector = Connector.build(TerminalSymbol.PLUS);
		assertSame(TerminalSymbol.PLUS, testConnector.getType());
		assertEquals("+", testConnector.toString());
		
		testConnector = Connector.build(TerminalSymbol.MINUS);
		assertSame(TerminalSymbol.MINUS, testConnector.getType());
		assertEquals("-", testConnector.toString());
		
		testConnector = Connector.build(TerminalSymbol.TIMES);
		assertEquals(TerminalSymbol.TIMES, testConnector.getType());
		assertEquals("*", testConnector.toString());
		
		testConnector = Connector.build(TerminalSymbol.DIVIDE);
		assertEquals(TerminalSymbol.DIVIDE, testConnector.getType());
		assertEquals("/", testConnector.toString());
		
		testConnector = Connector.build(TerminalSymbol.OPEN);
		assertEquals(TerminalSymbol.OPEN, testConnector.getType());
		assertEquals("(", testConnector.toString());
		
		assertSame(testConnector, Connector.build(TerminalSymbol.OPEN));
		
		testConnector = Connector.build(TerminalSymbol.CLOSE);
		assertEquals(TerminalSymbol.CLOSE, testConnector.getType());
		assertEquals(")", testConnector.toString());
		
		assertSame(testConnector, Connector.build(TerminalSymbol.CLOSE));
		
	
	}
	
	
}