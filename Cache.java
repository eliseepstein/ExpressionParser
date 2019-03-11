package parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/** a class that helps to avoid creating multiple immutable objects with the same non-null parameter */ 
class Cache<T, V> {
	private Map<T, V>  cache = new HashMap<T, V>();
	
    /**
     * adds/gets items to/from the cache
     * @param key each object has a key that is an identifier of 
     * @param constructor the function that creates a new object
     * @return an object with the inputed key that is made with the inputed constructor
     */
	V get(T key, Function<? super T, ? extends V> constructor) {
		Objects.requireNonNull(key, "Key cannot be null");
		Objects.requireNonNull(constructor, "No constructor was passed in");
		
		if (!cache.containsKey(key)) {
			cache.put(key, constructor.apply(key));
		}
		return cache.get(key);	
	}
	
}
	
