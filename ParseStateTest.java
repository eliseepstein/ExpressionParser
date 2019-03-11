package parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParseStateTest {

	Connector con = Connector.build(TerminalSymbol.PLUS);
	Connector con2 = Connector.build(TerminalSymbol.DIVIDE);
	List<Token> lst = Arrays.asList(con, con2);
	Variable var = Variable.build("a");
	LeafNode node = LeafNode.build(var);	
	ParseState parseState = ParseState.build(node, lst);
	
	@Test 
	void testGetters() {
		assertSame(parseState.getNode(), node);
		assertSame(parseState.getSuccess(), true);
		assertEquals(parseState.getRemainder(), lst);
		assertFalse(parseState.hasNoRemainder());
	}
	
	@Test
	void testBuild() {
		Assertions.assertThrows(NullPointerException.class, () -> ParseState.build(null, lst));
		Assertions.assertThrows(NullPointerException.class, () -> ParseState.build(node, null));
		
		//rest is checked as part of getters with parseState since parseState is made using the build method
	}

}
