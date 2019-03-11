package parser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

public class Parse {
	private static final Hashtable<String, TerminalSymbol> connectorTable = new Hashtable<String, TerminalSymbol>();
	
	public static void main(String[] args) {	
		if(isValidInput(args)) {
			buildConnectorTable();
			List<Token> input = buildInputList(args[0]);
			printResult(NonTerminalSymbol.parseInput(input));
		}
		else {
			System.out.println("Input must be exactly 1 argument. Ex: \"a + b\"");
		}
	}
	
	/**
	 * @param args the arguments from the command line
	 * @return whether the input is valid or not
	 */
	private static boolean isValidInput(String[] args) {
		return args.length == 1;
	}
	
	/**
	 * builds a table that maps String representations of symbols to TerminalSymbol objects
	 */
	private static void buildConnectorTable() {
		connectorTable.put("+", TerminalSymbol.PLUS);
		connectorTable.put("-", TerminalSymbol.MINUS);
		connectorTable.put("*", TerminalSymbol.TIMES);
		connectorTable.put("/", TerminalSymbol.DIVIDE);
		connectorTable.put("(", TerminalSymbol.OPEN);
		connectorTable.put(")", TerminalSymbol.CLOSE);
	}
	
	/**
	 * turns a string into a list of Tokens to be parsed
	 * @param inputString the String to be turned into a list of Tokens to be parsed
	 * @return a list of Tokens to be parsed
	 */
	private static List<Token> buildInputList(String inputString) {
		List<Token> input = new ArrayList<Token>();
		
		for (String str : inputString.split(" ")) {
			Optional<TerminalSymbol> connectorSymbol = getCorrespondingSymbol(str);
			if (connectorSymbol.isPresent()) {
				input.add(Connector.build(connectorSymbol.get()));
			}
			else {
				input.add(Variable.build(str));
			}
		}
		return input;
	}	
	
	/**
	 * gets the TerminalSymbol corresponding to the inputed string
	 * @param symbolString the string to be matched to a corresponding TerminalSymbol
	 * @return an optional that holds the matched TerminalSymbol or an empty optional if there is no symbol that matches
	 */
	private static Optional<TerminalSymbol> getCorrespondingSymbol(String symbolString) {
		TerminalSymbol symbol = connectorTable.get(symbolString);
		Optional<TerminalSymbol> optionalSymbol;
		if (symbol != null) {
			optionalSymbol = Optional.of(symbol);
		}
		else {
			optionalSymbol = Optional.empty();
		}
		return optionalSymbol;
	}
	
	/**
	 * prints the result of the parsing 
	 * @param node the node that been parsed into
	 */
	private static void printResult(Optional<Node> node) {
		if (node.isPresent()) {
			System.out.println(node.get().toString());
		}
		else {
			System.out.println("Unable to parse the input, please make sure the input is formatted correctly. \nArguments should be in quotations and separated by a space.\nExample: \"a + ( b / c )\"");
		}
	}
}
