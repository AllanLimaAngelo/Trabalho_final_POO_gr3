package util;

import java.util.Scanner;

public final class Util {
		

	private Util() {
		throw new AssertionError();
	}
	
	public static void printMessage(String message) {
		System.out.println(message);
		try {
		    Thread.sleep(500); // Sleep for milliseconds
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
	}
	
	public static String askInputLine(String message, Scanner input) {
	    input.nextLine(); // Consume the newline character from previous input
	    System.out.println(message);
	    System.out.println("Press Enter 2x to confirm input.");

	    StringBuilder rawBuilder = new StringBuilder();
	    String line = input.nextLine(); // Read the first line of input

	    // Check if the first line is empty (Enter key pressed)
	    if (line.isEmpty()) {
	        return ""; // Return empty string if Enter key was pressed immediately
	    }

	    // Append the first line of input to the StringBuilder
	    rawBuilder.append(line);
	    rawBuilder.append("\n"); // Add newline character to maintain line breaks

	    return rawBuilder.toString().trim();
	}

	public static String askIntegerInput(String message, Scanner input) {
		String inputString = "";	
		while(!inputString.matches("-?\\d+")) {
			System.out.println(message);
			inputString = input.next();
			if(!inputString.matches("-?\\d+")) {
				System.out.println("\nInvalid input!");
			}
		}
		return inputString;
	}
	
	public static String askDoubleInput(String message, Scanner input) {
		String inputString = "";	
		while(!inputString.matches("-?\\d+(\\.\\d+)?")) {
			System.out.println(message);
			inputString = input.next();
			if(!inputString.matches("-?\\d+(\\.\\d+)?")) {
				System.out.println("\nInvalid input!");
			}
		}
		return inputString;
	}
	
	public static boolean validateInput(Scanner input) {
		String message = "Can we proceed? Input 1 for YES or 2 for NO";
		int choice = 0;
		while(choice <= 0 || choice > 2) {
			choice = Integer.parseInt(askIntegerInput(message, input));
			if(choice <= 0 || choice > 2) {
				System.out.println(" 1 for Yes 2 for No, please.");
			}
		}
		return choice == 1;
	}
}