package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class holds the main method and two other methods which are helper methods.
 * The two other methods will take a file name and pull the information required to make the tree
 * and the information required to decode a message out of the file.
 * They then call the necessary functions to perform these actions in MsgTree.java.
 * @author Logan Roe
 */
public class MsgDecoder {
	
	/**
	 * Takes an input from the user for the file name and calls the necessary functions 
	 * to print out the codes and the decoded message.
	 * @param args
	 * @throws FileNotFoundException - In case the file name entered is invalid.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Please enter a file name: ");
		Scanner scnr = new Scanner(System.in);
		String fileName = scnr.nextLine();
		
		MsgTree msgTree = constructTree(fileName);
		
		System.out.println("character   code\n--------------------");
		MsgTree.printCodes(msgTree, null);
		
		System.out.println("\nMESSAGE:");
		msgTree.decode(msgTree, getMsg(fileName));
		
		scnr.close();
	}
	
	/**
	 * Takes a file name input and constructs a tree from the information within the file.
	 * This function assumes that the formatting within the file is proper.
	 * @param fileName - The name of the file to construct the tree from.
	 * @return MsgTree - Returns the newly constructed tree from the file given.
	 * @throws FileNotFoundException - In case the file name entered is invalid.
	 */
	private static MsgTree constructTree(String fileName) throws FileNotFoundException {
		File treeFile = new File(fileName);
		Scanner treeScnr = new Scanner(treeFile);
		
		String encodingString = treeScnr.nextLine();
		String temp = treeScnr.nextLine();
		
		if (temp.contains("^")) {
			encodingString = encodingString + "\n" + temp;
		}
		
		MsgTree msgTree = new MsgTree(encodingString);
		
		treeScnr.close();
		
		return msgTree;
	}
	
	/**
	 * Takes a file name input and extracts the message in it's binary form.
	 * This function assumes that the formatting within the file is proper.
	 * @param fileName - The name of the file to extract the binary message from.
	 * @return String - The message in binary form from the file.
	 * @throws FileNotFoundException - In case the file name enetered is invalid.
	 */
	private static String getMsg(String fileName) throws FileNotFoundException {
		File msgFile = new File(fileName);
		Scanner msgScnr = new Scanner(msgFile);
		String msg = "";
		String potentialMsg;
		
		while (msgScnr.hasNextLine()) {
			potentialMsg = msgScnr.nextLine();
			if (!potentialMsg.contains("^")) {
				msg = msg + potentialMsg;
			}
		}
		
		msgScnr.close();
		return msg;
	}
}
