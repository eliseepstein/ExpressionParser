package parser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/** Connector represents the non-variable symbols in expressions +, -, *, /, (, ) */
public final class Connector extends AbstractToken {
	
	private TerminalSymbol type;
	private static Cache<TerminalSymbol, Connector> cache = new Cache<TerminalSymbol, Connector>();
	
	private Connector(TerminalSymbol type) {
		this.type = type;
	}
	
	/**
	 * builds a connector and returns it
	 * @param type the type of symbol that the connector is
	 * @return the new connector or the connector from the cache if it has already been made
	 */
	public static final Connector build(TerminalSymbol type) {
		Objects.requireNonNull(type, "Type cannot be null");

		List<TerminalSymbol> typesOfConnectors = Arrays.asList(TerminalSymbol.PLUS, TerminalSymbol.MINUS,
															  TerminalSymbol.TIMES, TerminalSymbol.DIVIDE,
															  TerminalSymbol.OPEN, TerminalSymbol.CLOSE);
		
		if (!typesOfConnectors.contains(type)) {
			throw new IllegalArgumentException("Type must be one of: PLUS, MINUS, TIMES, DIVIDE, OPEN, CLOSE");	
		}
		
		else {
			Function<TerminalSymbol, Connector> connectorConstructor = x -> new Connector(x);
			return cache.get(type, connectorConstructor);
		}
	}
	
	/**
	 * @return the type of the symbol of the Connector
	 */
	@Override
	public TerminalSymbol getType() {
		return type;
	}
	
	/** 
	 * @return the string representation of the type of the Connector 
	 */
	@Override
	public String toString() {
		return getType().getSymbol();
	}

	@Override
	public boolean isOperator() {
		List<TerminalSymbol> operators = Arrays.asList(TerminalSymbol.PLUS, TerminalSymbol.MINUS,
													  TerminalSymbol.TIMES, TerminalSymbol.DIVIDE);
		
		return operators.contains(this.type);
	}

}
