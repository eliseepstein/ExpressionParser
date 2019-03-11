package parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class InternalNodeTest {

	List<Node> children = new ArrayList<Node>(); 
	Variable var1 = Variable.build("a");
	Variable var2 = Variable.build("b");
	Variable var3 = Variable.build("c");
	Connector conn1 = Connector.build(TerminalSymbol.PLUS);
	Connector conn2 = Connector.build(TerminalSymbol.DIVIDE);
	
	@Test
	void testBuild() {
		children.add(LeafNode.build(var1));
		children.add(LeafNode.build(conn1));
		children.add(LeafNode.build(var2));
		
		assertEquals(children, InternalNode.build(children).getChildren());
		
		children.clear();
	}
	
	@Test
	void testGetChildren() {
		assertEquals(children, InternalNode.build(children).getChildren());
	}
	
	@Test
	void testToList() {
		children.add(LeafNode.build(var1));
		children.add(LeafNode.build(conn1));
		children.add(LeafNode.build(var2));
		
		InternalNode interNode = InternalNode.build(children);
		assertEquals(interNode.toList(), Arrays.asList(var1, conn1, var2));
		
		children.add(LeafNode.build(var3));
		InternalNode interNode2 = InternalNode.build(children);
		assertEquals(interNode2.toList(), Arrays.asList(var1, conn1, var2, var3));
		children.clear();
	
	}
	
	@Test
	void testExpectedException() {
		assertThrows(NullPointerException.class, () -> InternalNode.build(null));
	}
	
	@Test
	void testToString() {
		children.add(LeafNode.build(var1));
		children.add(LeafNode.build(conn1));
		children.add(LeafNode.build(var2));
		
		assertEquals("[a,+,b]", InternalNode.build(children).toString());
		
		children.clear();
		children.add(LeafNode.build(var1));
		
		assertEquals("[a]", InternalNode.build(children).toString());
		
		children.clear();
		assertEquals("[]", InternalNode.build(children).toString());
		
		//testing diagram on assignment
		children.add(LeafNode.build(conn2)); //divide
		children.add(LeafNode.build(var3)); //c
		InternalNode interNode = InternalNode.build(children);
		children.clear();
				
		children.add(LeafNode.build(var2)); //b
		children.add(interNode);
		InternalNode interNode2 = InternalNode.build(children);
		children.clear();
				
		children.add(LeafNode.build(conn1));
		children.add(interNode2);
		InternalNode interNode3 = InternalNode.build(children);
		children.clear();
				
		children.add(LeafNode.build(var1));
		children.add(interNode3);
		InternalNode interNode4 = InternalNode.build(children);
						
		assertEquals(interNode4.toString(), "[a,[+,[b,[/,c]]]]");		
		children.clear();
	}
	
	@Test
	void testFlattenTreeIfStartedByOperator() {
		Variable a = Variable.build("a");
		Variable b = Variable.build("b");
		Variable c = Variable.build("c");
		
		LeafNode nodeA = LeafNode.build(a);
		LeafNode nodeB = LeafNode.build(b);
		LeafNode nodeC = LeafNode.build(c);
		
		Connector con1 = Connector.build(TerminalSymbol.TIMES);
		Connector con2 = Connector.build(TerminalSymbol.DIVIDE);
		
		LeafNode nodeT = LeafNode.build(con1);
		LeafNode nodeD = LeafNode.build(con2);
		
		InternalNode inter1 = InternalNode.build(Arrays.asList(nodeD, nodeC));	

		InternalNode.Builder builder = new InternalNode.Builder();
		
		builder.addChild(nodeT);
		builder.addChild(nodeB);
		builder.addChild(inter1);
		builder = builder.simplify();
		
		InternalNode inter2 = builder.build();

		assertEquals("[*,b,/,c]", inter2.toString());
		
		
		builder = new InternalNode.Builder();
		builder.addChild(nodeA);
		builder.addChild(inter2);
		builder = builder.simplify();
		
		assertEquals("[a,*,b,/,c]", builder.build().toString());
		
	}
	
	@Test 
	void testFirstChild() {
		ArrayList<Node> children = new ArrayList<Node>();
		assertEquals(InternalNode.build(children).firstChild(), Optional.empty());
		
		Variable a = Variable.build("a");
		LeafNode var1 = LeafNode.build(a);
		children.add(var1);
		assertEquals(InternalNode.build(children).firstChild(), Optional.of(var1));
	}

}
