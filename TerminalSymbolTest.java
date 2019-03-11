package parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class TerminalSymbolTest {

	@Test
	void testGetSymbol() {
		assertEquals("", TerminalSymbol.VARIABLE.getSymbol());
		assertEquals("+", TerminalSymbol.PLUS.getSymbol());
		assertEquals("-", TerminalSymbol.MINUS.getSymbol());
		assertEquals("*", TerminalSymbol.TIMES.getSymbol());
		assertEquals("/", TerminalSymbol.DIVIDE.getSymbol());
		assertEquals("(", TerminalSymbol.OPEN.getSymbol());
		assertEquals(")", TerminalSymbol.CLOSE.getSymbol());
	}
	
	@Test
	void testParse() {
		List<Token> lst = new ArrayList<>();
		Connector con = Connector.build(TerminalSymbol.PLUS);
		Connector con2 = Connector.build(TerminalSymbol.DIVIDE);
		
		assertSame(TerminalSymbol.DIVIDE.parse(lst), ParseState.FAILURE);
		
		lst.add(con);
		assertSame(TerminalSymbol.DIVIDE.parse(lst), ParseState.FAILURE);
		ParseState parse = TerminalSymbol.PLUS.parse(lst);
		assertEquals(parse.getSuccess(), true);
		assertEquals(parse.getNode().getClass(), LeafNode.class);
		
		LeafNode node = (LeafNode) parse.getNode();
		assertEquals(node.getToken(), LeafNode.build(con).getToken());
		assertEquals(parse.getRemainder(), new ArrayList<Token>());
		
		lst.add(con2);
		assertSame(TerminalSymbol.DIVIDE.parse(lst), ParseState.FAILURE);
		
		ParseState parse2 = TerminalSymbol.PLUS.parse(lst);
		assertEquals(parse2.getSuccess(), true);
		assertEquals(parse2.getNode().getClass(), LeafNode.class);
		
		LeafNode node2 = (LeafNode) parse2.getNode();
		assertEquals(node2.getToken(), LeafNode.build(con).getToken());
		assertEquals(parse2.getRemainder(), Arrays.asList(con2));
	}

}
