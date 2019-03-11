package parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

class CacheTest {
    
	Function<String, String> func = x -> x;
	
	
	@Test
	void testGetExceptions() {
		Cache<String, String> cache = new Cache<String, String>();
		assertThrows(NullPointerException.class, () -> cache.get(null, null));
		assertThrows(NullPointerException.class, () -> cache.get(null, func));
		assertThrows(NullPointerException.class, () -> cache.get("a", null));
	}
	
	@Test
	void testGet() {
		Cache<String, String> cache = new Cache<String, String>();
		String str = cache.get("a", func);
		assertSame(str, cache.get("a", func));
	}
	
	@Test
	void multipleCaches() {
		Variable var1 = Variable.build("a");
		Connector conn1 = Connector.build(TerminalSymbol.PLUS);
		
		Variable.build("a");
		Connector.build(TerminalSymbol.PLUS);
		
		assertSame(var1, Variable.build("a"));
		assertSame(conn1, Connector.build(TerminalSymbol.PLUS));
	}

}
