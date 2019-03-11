package parser;

import java.util.List;

/** symbols  are input parsers that build nodes */
interface Symbol {
	
	/** 
	 * Attempts to parse the input list into a node, possibly leaving a remainder
	 * @param input a list of tokens to be parsed into a Node
	 * @return a ParseState with the newly created node and a list of tokens not used
	 */
	ParseState parse(List<Token> input);
}