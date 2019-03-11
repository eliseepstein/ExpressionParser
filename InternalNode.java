package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/** this class represents a node in the middle of a tree*/
public final class InternalNode implements Node {

		private final List<Node> children;
		private List<Token> computedChildrenTokenList;
		private String representation;
		
		private InternalNode(List<Node> children) {
			this.children = new ArrayList<>(children);
		}	
		
		/**
		 * makes an InternalNode
		 * @param children a list of the children to add for the InternalNode
		 * @return an InternalNode with the children in the parameter
		 */
		public static final InternalNode build(List<Node> children) {
			Objects.requireNonNull(children, "Children must not be null");
			return new InternalNode(children);
		}
		
		/**
		 * @return a list of all of the tokens of the children of an InternalNode
		 */
		@Override
		public final List<Token> toList() {
			if (computedChildrenTokenList == null) {
				computedChildrenTokenList = new ArrayList<Token>();
				children.forEach(node -> computedChildrenTokenList.addAll(node.toList()));
			}
			return computedChildrenTokenList;
		}

		/**
		 * gets the children of an InternalNode
		 * @return the children of an InternalNode
		 */
		public List<Node> getChildren() { 
			return new ArrayList<>(children);
		}
		
		/**
		 * @return returns the string representation of an InternalNode
		 */
		public String toString() {
			if (representation == null) {
				representation = buildRepresentation();
			}
			return representation;
		}
		
		/**
		 * builds and returns the string representation for an InternalNode
		 * @return the String representation for an InternalNode
		 */	
		private String buildRepresentation() {
			String repString = "[";
			
			for (Node child : children) {
				repString = repString + child.toString() + ",";
			}
			
			if (isFruitful()) {
				repString = repString.substring(0, repString.length() - 1);
			}
			
			repString += "]";
			
			return repString;
		}

		@Override
		public boolean isFruitful() {
			return !children.isEmpty();
		}
		
		static <T> boolean hasOnlyOneElement(List<T> list) {
			return list.size() == 1;
		}
		
		/** helps build and simplify the parse tree*/
		public static class Builder {
			private List<Node> children = new ArrayList<Node>();
			
			/**
			 * Appends a node to children
			 * @param node the node to be added to children
			 * @return whether or not the addition was successful
			 */
			public boolean addChild(Node node) {
				return children.add(node);
			}
			
			/**
			 * removes all childless nodes from children list
			 * @return a Builder with the simplified children list
			 */
			public Builder simplify() {
				
				removeAllChildlessNodes();
				simplifyIfHasOnlyOneChild();
				flattenTreeIfStartedByOperator();
				bringUpIfOnlyChildIsLeaf();
				return this;
			}
			
			/**
			 * removes all childless nodes from Builder's list 'children'
			 */
			void removeAllChildlessNodes() {
				children = children.stream()
						  .filter(node -> node.isFruitful())
						  .collect(Collectors.toList());
			}
			
			/**
			 * for each node in the children list, if the node has only 1 child, which is a Leaf node, 
			 * then it replaces the node with the leaf node child
			 */
			void bringUpIfOnlyChildIsLeaf() {
				for (int childIndex = 0; childIndex < children.size(); childIndex++) {
					if(children.get(childIndex).isSingleLeafParent()){
						children.set(childIndex, children.get(childIndex).firstChild().get());
					}
				}
			}
			
			/**
			 * if the internalNode starts with an operator and the directly previous node is not an operator, 
			 * then it replaces the internalNode with its children
			 */
			void flattenTreeIfStartedByOperator() {	
				List<Node> updatedChildrenList = new ArrayList<Node>();
				
				if (!children.isEmpty()) {
					Node lastChecked = children.get(0);
					updatedChildrenList.add(children.get(0));
					
					for (int childIndex = 1; childIndex < children.size(); childIndex++) {
						updatedChildrenList.addAll(flattenIfOperator(lastChecked, children.get(childIndex)));
						lastChecked = children.get(childIndex);
					}
					
				}
				children = updatedChildrenList;
			}
			
			/**
			 * updates the list of internalNodes to be replaced by their children
			 * @param previous the node that is directly before the previous node
			 * @param current the current node being considered to be replaced by its children
			 * @return the updated list of internalNodes
			 */
			List<Node> flattenIfOperator(Node previous, Node current) {
				List<Node> children = new ArrayList<Node>();
				
				if (!previous.isOperator() && current.isStartedByOperator()) {
					children.addAll(current.getChildren());
				}
				else {
					children.add(current);
				}
				return children;
			}
			
			/**
			 * if this InternalNode has only child, which is an InternalNode, 
			 * the children of the InternalNode turn into the children of the original node
			 */
			void simplifyIfHasOnlyOneChild() {
				if (hasOnlyOneElement(children) && !isLeafNode(children.get(0))) {
					children = children.get(0).getChildren();
				}
			}
			
			/**
			 * @return a new InternalNode
			 */
			public InternalNode build() {
				return InternalNode.build(children);
			}
	
		}

		@Override
		public boolean isOperator() {
			return false;
		}
		
		private static boolean hasOneChild(Node node) {
			return !isLeafNode(node) && hasOnlyOneElement(node.getChildren());
		}
		
		private static boolean isLeafNode(Node node) {
			return node.getChildren() == null;
 		}
		

		@Override
		public boolean isStartedByOperator() {
			return this.firstChild().get().isOperator();
		}

		@Override
		public Optional<Node> firstChild() {
			Optional<Node> firstChild;
			if (this.isFruitful()) {
				firstChild = Optional.of(children.get(0));
			}
			else {
				firstChild = Optional.empty();
			}
			return firstChild;
		}

		@Override
		public boolean isSingleLeafParent() {
			return hasOneChild(this) && isLeafNode(this.firstChild().get());
		}
		
}