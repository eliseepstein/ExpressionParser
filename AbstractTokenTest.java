package parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AbstractTokenTest {

	@Test
	void testMatches() {
		Variable var = Variable.build("hello");
		assertTrue(var.matches(TerminalSymbol.VARIABLE));
		assertFalse(var.matches(TerminalSymbol.DIVIDE));
	}

}
