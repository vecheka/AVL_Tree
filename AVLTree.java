/*
 *  
 */

/**
 * @author Vecheka Chhourn
 * @version 1.0
 * @param <T> generic type-
 * A class that implements AVL Tree or a self-balancing Binary Search Tree
 */
public class AVLTree<T extends Comparable<T>> {
	
	/** Overall root of the tree. */
	private TreeNode<T> root;
	
	
	/**
	 * Constructor to initialize an empty tree.
	 */
	public AVLTree() {
		root = null;
	}
	
	/**
	 * Insert new data to the tree.
	 * @param theData new data to insert
	 */
	public void insert(final T theData) {
		root = insert(root, theData);
	}
	
	/**
	 * Insert new data to the tree
	 * @param theRoot overall root of a tree
	 * @param theData data to insert
	 * @return new tree
	 */
	private TreeNode<T> insert(TreeNode<T> theRoot, final T theData) {
		
		if (theRoot == null) {
			theRoot = new TreeNode<T>(theData);
		} else if (theData.compareTo(theRoot.data) == -1) {
			theRoot.left = insert(theRoot.left, theData);
//			theRoot = rotateLeft(theRoot);
		} else if (theData.compareTo(theRoot.data) == 1) {
			theRoot.right = insert(theRoot.right, theData);
		}
		

		return balanceInsertTree(theRoot, theData);
	}

	

	/** 
	 * Remove a specified data from the tree.
	 * @param theData data to remove from the tree
	 */
	public void remove(final T theData) {
		root = remove(root, theData);
	}
	
	/**
	 * Delete a data from the tree.
	 * @param theRoot overall root of a tree
	 * @param theData data to insert
	 * @return
	 */
	private TreeNode<T> remove(TreeNode<T> theRoot, final T theData) {
		
		if (theRoot == null) return theRoot;
		
		if (theRoot.data.compareTo(theData) == -1) {
			theRoot.right = remove(theRoot.right, theData);
		} else if (theRoot.data.compareTo(theData) == 1) {
			theRoot.left = remove(theRoot.left, theData);
		} else {
			if (theRoot.left == null && theRoot.right == null) {
				theRoot.data = null;
			} else if (theRoot.left != null && theRoot.data.compareTo(theData) == 0) {
				final T temp = theRoot.data;
				theRoot.data = theRoot.left.data;
				theRoot.left.data = temp;
				theRoot.left = remove(theRoot.left, theData);
			} else if (theRoot.right != null && theRoot.data.compareTo(theData) == 0) {
				final T temp = theRoot.data;
				theRoot.data = theRoot.right.data;
				theRoot.right.data = temp;
				theRoot.right = remove(theRoot.right, theData);
			}
		}
		
		
		
		return balanceRemoveTree(theRoot);
	}
	
	/**
	 * Balancing the tree by rotations during removal.
	 * @param theRoot root of the tree
	 * @return balanced tree
	 */
	private TreeNode<T> balanceRemoveTree(TreeNode<T> theRoot) {
		final int balance = getBalance(theRoot);
		if (balance > 1 && getBalance(theRoot.left) >= 0) { // right-rotation
			theRoot = rotateRight(theRoot);
		} else if (balance > 1 && getBalance(theRoot.left) < 0) { // left-right rotation
			theRoot = rotateLeftRight(theRoot);
		} else if (balance < -1 && getBalance(theRoot.right) <= 0) { // left rotation
			theRoot = rotateLeft(theRoot);
		} else if (balance < -1 && getBalance(theRoot.right) > 0) { // right-left rotation
			theRoot = rotateRightLeft(theRoot);
		}
		return theRoot;
	}

	/**
	 * Display tree nodes in-order traversal.
	 */
	public void printPreOrder() {
		printPreOrder(root);
	}
	
	/**
	 * Display tree nodes in-order traversal.
	 * @param theRoot overall root of a tree
	 */
	private void printPreOrder(final TreeNode<T> theRoot) {
		if (theRoot != null) {
			if (theRoot.data != null) System.out.print(theRoot.data + " ");
			printPreOrder(theRoot.left);
			printPreOrder(theRoot.right);
		}
		
	}

	// helper methods
	/**
	 * Rotate tree to the left.
	 * @param theRoot overall root of the tree
	 * @return new tree rotated to the left
	 */
	private TreeNode<T> rotateLeft(TreeNode<T> theRoot) {
		TreeNode<T> newRoot = theRoot.right;
		theRoot.right = newRoot.left;
		newRoot.left = theRoot;
		theRoot = newRoot;
		return theRoot;
	}

	/** 
	 * Rotate tree to the right.
	 * @param theRoot overall root of the tree
	 * @return new tree rotated to the right
	 */
	private TreeNode<T> rotateRight(TreeNode<T> theRoot) {
		TreeNode<T> newRoot = theRoot.left;
		theRoot.left = newRoot.right;
		newRoot.right = theRoot;
		theRoot = newRoot;
		return theRoot;
	}
	
	/**
	 * Rotate tree to the left and right.
	 * @param theRoot overall root of the tree
	 * @return new tree rotated to the left and right
	 */
	private TreeNode<T> rotateLeftRight(TreeNode<T> theRoot) {
		theRoot.left = rotateLeft(theRoot.left);
		return rotateRight(theRoot);
	}
	
	/**
	 * Rotate tree to the right and left.
	 * @param theRoot overall root of the tree
	 * @return new tree rotated to the right and left
	 */
	private TreeNode<T> rotateRightLeft(TreeNode<T> theRoot) {
		theRoot.right = rotateRight(theRoot.right);
		return rotateLeft(theRoot);
	}
	
	/**
	 * Get the height of the tree.
	 * @param theRoot root of the tree
	 * @return height of the tree
	 */
	private int calculateHeight(final TreeNode<T> theRoot) {
		if  (theRoot == null || theRoot.data == null) return 0;
		else if (theRoot.left == null && theRoot.right == null) return 1;
		else return 1 + Math.max(calculateHeight(theRoot.left), calculateHeight(theRoot.right));
	}
	
	
	/**
	 * Balance the tree by rotations during insertion.
	 * @param theRoot root of the tree
	 * @param theData data to add to the tree
	 */
	private TreeNode<T> balanceInsertTree(TreeNode<T> theRoot, final T theData) {
		final int balance = getBalance(theRoot);

		if (balance > 1 && theData.compareTo(theRoot.left.data) == -1) { // right-rotation
			theRoot = rotateRight(theRoot);
		} else if (balance < -1 && theData.compareTo(theRoot.right.data) == 1) { // left-rotation
			theRoot = rotateLeft(theRoot);
		} else if (balance > 1 && theData.compareTo(theRoot.left.data) == 1) { // left-right rotation
			theRoot = rotateLeftRight(theRoot);
		} else if (balance < -1 && theData.compareTo(theRoot.right.data) == -1) { // right-left rotation
			theRoot = rotateRightLeft(theRoot);
		}
		return theRoot;
	}
	
	/**
	 * Get the balance of the tree.
	 * @param theRoot root of the tree
	 * @return balance of the tree in integer value.
	 */
	private int getBalance(final TreeNode<T> theRoot) {
		return calculateHeight(theRoot.left) - calculateHeight(theRoot.right);
	}
}





/**
 * A class to construct a node in the tree.
 * @param <T> Generic type
 */
class TreeNode<T> {
	
	/** Value of a node.*/
	protected T data;
	/** Height of the tree.*/
	protected Integer height;
	/** Left child.*/
	protected TreeNode<T> left;
	/** Right child.*/
	protected TreeNode<T> right;
	
	
	/** 
	 * Constructor that takes an object, left root, and right root.
	 * @param theData to add to the tree
	 * @param theLeft left root
	 * @param theRight right root
	 */
	public TreeNode(final T theData, final TreeNode<T> theLeft, 
			final TreeNode<T> theRight) {
		data = theData;
		left = theLeft;
		right = theRight;
		height =  0;
	}
	
	/**
	 * Copy constructor that takes an object and add to the tree, 
	 * and initialize both children to null.
	 * @param theData to add to the tree
	 */
	public TreeNode(final T theData) {
		this(theData, null, null);
	}
	
	/**
	 * Constructor that takes no arguments, and initialize left and
	 * right child to null.
	 */
	public TreeNode() {
		left = null;
		right = null;
		height = 0;
		data = null;
	}
}