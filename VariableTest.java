package parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VariableTest {

	Variable var = Variable.build("a");
	Variable var2 = Variable.build("b");
	
	@Test
	void testToString() {
		
		assertEquals("a", var.toString());
		assertSame(var.getRepresentation(), var.toString());
	}
	
	@Test
	void testGetRepresentation() {;
		assertEquals("a", var.getRepresentation());
		assertEquals("b", var2.getRepresentation());
	}
	
	@Test
	void testBuildDuplicate() {
		assertEquals(var, Variable.build("a"));
	}
	
	@Test
	void testExpectedErrors() {
		assertThrows(NullPointerException.class, () -> Variable.build(null));
	}
	
	@Test
	void testGetType() {
		assertSame(TerminalSymbol.VARIABLE, var.getType() );
		assertSame(TerminalSymbol.VARIABLE, var2.getType() );
	}

}