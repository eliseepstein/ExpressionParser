
package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** this class represents the current state of the parsing process */
final class ParseState {

		private final boolean success; 
		
		/** the result of parsing an initial prefix of a list */
		private final Node node;
		
		/** the part of the initial token list that was left over after parsing the Node */
		private final List<Token> remainder;
		
		/** the ParseState for when parsing fails */
		final static ParseState FAILURE = new ParseState(false, null, null);
		
	    private ParseState(boolean success, Node node, List<Token> remainder) {
	    	 	this.success = success;
	    	 	this.node = node;
	    	 	this.remainder = remainder;
		}
	 
	   
	    /**
	     * @return true if parsing was successful
	     */
	    boolean getSuccess() {
	    		return success;
	    }
	    
	    /**
	     * @return the node made through parsing
	     */
	    Node getNode() {
	    		return node;
	    }
	    
	    /**
	     * @return a list containing the tokens not used during parsing
	     */
	    List<Token> getRemainder() {
	    		return new ArrayList<>(remainder);
	    	}
	    
	    /**
	     * @return whether there is not a remainder from parsing
	     */
	    final boolean hasNoRemainder() {
	    		return getRemainder().isEmpty();
	    }
	    
	    /**
	     * builds a ParseState
	     * @param node the node that is made through parsing
	     * @param remainder the list of tokens left over after parsing
	     * @return a new ParseState
	     */
	    public static final ParseState build(Node node, List<Token> remainder) {
	    		Objects.requireNonNull(node, "node must not be null");
	    		Objects.requireNonNull(remainder, "The list remainder must not be null");
	    		return new ParseState(true, node, new ArrayList<>(remainder)); 
	    }
	
}
