package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** represents a node at the end of the tree */
public final class LeafNode implements Node{

	private final Token token;
	
	private LeafNode(Token token) {
		this.token = token;
	}
	
	/**
	 * makes a LeafNode
	 * @param token the token to make a LeafNode with
	 * @return a new LeafNode with the given token
	 */
	public static final LeafNode build(Token token) {
		Objects.requireNonNull(token, "Token must not be null");
		return new LeafNode(token);
	}

	/**
	 * @return a list containing the token of the LeafNode
	 */
	public final List<Token> toList() {
		List<Token> tokenAsList = new ArrayList<Token>();
		tokenAsList.add(getToken());
		return tokenAsList;
	}
	
	/**
	 * @return the token of the LeafNode
	 */
	public final Token getToken() {
		return token;
	}
	
	/**
	 * @return the string representation of the token of the LeafNode
	 */
	public String toString() {
		return getToken().toString();
	}

	@Override
	public List<Node> getChildren() {
		return null;
	}

	@Override
	public boolean isFruitful() {
		return true;
	}

	@Override
	public boolean isOperator() {
		return this.getToken().isOperator();
	}

	@Override
	public boolean isStartedByOperator() {
		return false;
	}

	@Override
	public Optional<Node> firstChild() {
		return Optional.empty();
	}

	@Override
	public boolean isSingleLeafParent() {
		return false;
	}

}


