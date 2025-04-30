package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 * This is a class for MsgTree. It will keep track of the character at the given node and the left and right hand trees.
 * Further, it can be used to create a new MsgTree, list all of the encoding for each character in a tree, and decode messages from binary.
 * @author Logan Roe
 */
public class MsgTree {
	// The character at the given node.
	public char payloadChar;
	
	// The left hand MsgTree of the current tree.
	public MsgTree left;
	
	// The right hand MsgTree of the current tree.
	public MsgTree right;
	
	// To keep track of where the constructor is at in the encodingString parameter.
	private static int staticCharIdx = 0;
	
	// Constructor building the tree from a string
	
	/**
	 * A constructor that builds a binary tree from an encoding string.
	 * '^' indicates that it is a tree with a left and right-hand side.
	 * Any other character indicates it is a leaf and does not have a left or right-hand side.
	 * @param encodingString - The string from which the binary tree will be built.
	 */
	public MsgTree(String encodingString) {
		// Exit the constructor if no string is given.
		if (encodingString == null) {
			return;
		}
		
		// Initialize variables and the stack.
		MsgTree root = this;
		root.payloadChar = encodingString.charAt(staticCharIdx++);
		Stack<MsgTree> stack = new Stack<MsgTree>();
		stack.push(root);
		int counter = 0;
		
		// Loop through the entirety of the encodingString.
		for (int i = 0; i < encodingString.length() - 1; i++) {
			MsgTree currTree = stack.peek();
			
			// If the current character is '^' then create a new tree to the left and push it on the stack.
			if (currTree.payloadChar == '^') {
				currTree.left = new MsgTree(encodingString.charAt(staticCharIdx));
				stack.push(currTree.left);
				staticCharIdx++;
			} else {
				MsgTree newCurr;
				
				if (counter != 1) {
					stack.pop();
					newCurr = stack.peek();
					
					// Move back up the tree by removing trees from the stack.
					while (newCurr.right != null) {
						stack.pop();
						newCurr = stack.peek();
					}
					
					// Create a new tree to the right.
					newCurr.right = new MsgTree(encodingString.charAt(staticCharIdx));
					
					// Put the new tree on the stack, reset counter, and increment staticCharIdx.
					stack.push(newCurr.right);
					staticCharIdx++;
					counter = 0;
				} else {
					// Pop the top value off the stack and add a new tree to the right of the new current tree.
					stack.pop();
					newCurr = stack.peek();
					newCurr.right = new MsgTree(encodingString.charAt(staticCharIdx));
					
					// Add the new current tree to the stack and increment counter and staticCharIdx.
					stack.push(newCurr.right);
					staticCharIdx++;
					counter++;
				}
			}
		}
	}
	
	// Constructor for a single node with null children
	/**
	 * A constructor for a singular node.
	 * Initiates the left and right trees to null.
	 * @param payloadChar - The singular node's value.
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
		left = null;
		right = null;
	}
	
	// Method to print characters and their binary codes
	/**
	 * Prints out all of the characters in the tree with their respective binary codes.
	 * Further, a zero would mean to the left of the current node and a one would mean to the right.
	 * @param root - The entire tree.
	 * @param code - The binary code for where each character is in the tree.
	 */
	public static void printCodes(MsgTree root, String code) {

		if (root == null) {
			code = code.substring(0, code.length() - 2);
			return;
		}
		
		if (code == null) {
			code = "";
		}
		
		if (root.left == null && root.right == null) {
			String print;
			
			if (root.payloadChar == '\n') {
				print = "\\n" + "          " + code;
			} else {
				print = root.payloadChar + "           " + code;
			}
			
			System.out.println(print);
		}
		
		printCodes(root.left, code + "0");
		printCodes(root.right, code + "1");
	}
	
	/**
	 * This method will take a tree and a binary message as a string to then turn into a readable message.
	 * It searches the binary tree based upon if it is a 0 (to the left) or a 1 (to the right).
	 * It backs up to the root every single time it prints a character.
	 * @param codes - The given tree to decode from.
	 * @param msg - The binary string to use to properly decode the message.
	 */
	public void decode(MsgTree codes, String msg) {
		if (codes == null || msg == "") {
			return;
		}
		
		Stack<MsgTree> stack = new Stack<MsgTree>();
		stack.push(codes);
		
		for (int i = 0; i <= msg.length(); i++) {
			MsgTree currTree = stack.peek();
			
			if (currTree.payloadChar == '^' && i < msg.length()) {
				if (msg.charAt(i) == '0') {
					stack.push(currTree.left);
				} else {
					stack.push(currTree.right);
				}
			} else {
				System.out.print(currTree.payloadChar);
				if (i < msg.length()) {
					i--;
				}
				while (stack.peek() != codes) {
					stack.pop();
				}
			}
		}
	}
}