package parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LeafNodeTest {

	Variable var = Variable.build("a");
	LeafNode node = LeafNode.build(var);
	
	@Test
	void testBuild() {
		assertEquals(var, node.getToken());
	}
	
	@Test
	void testToList() {
		assertEquals(1, node.toList().size());
		assertTrue(node.toList().contains(var));
	}
	
	@Test
	void testExpectedException() {
		assertThrows(NullPointerException.class, () -> LeafNode.build(null));
	}
	
	
	@Test
	void testToString() {
		Connector con = Connector.build(TerminalSymbol.PLUS);
		Variable var = Variable.build("v");
		
		LeafNode leafNode = LeafNode.build(con);
		assertEquals("+", leafNode.toString());
		
		LeafNode leafNode2 = LeafNode.build(var);
		assertEquals("v", leafNode2.toString());
	}

}