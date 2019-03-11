package parser;

import java.util.List;
import java.util.Optional;

/** represents any node in the tree */
public interface Node {

	/** 
	 *  @return the list representation of the subtree rooted at the given node
	 */
	List<Token> toList();

	/** 
	 *  @return a copy of this nodes children, or null if no children
	 */
	List<Node> getChildren();

	/** 
	 *  @returns whether the node has at least one child or is a leaf node
	 */
	boolean isFruitful();
	
	/**
	 * @return whether or not the node is a leaf corresponding to an operator
	 */
	boolean isOperator();
	
	/**
	 * @return whether the node's first child is an operator
	 */
	boolean isStartedByOperator();
	
	/**
	 * @return the first child of the node or empty if there isn't a first child
	 */
	Optional<Node> firstChild();
	
	/**
	 * @return whether the node's only child is a leaf node
	 */
	boolean isSingleLeafParent();
}
