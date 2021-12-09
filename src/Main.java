import java.util.Scanner;
//------------DESCRIPTION---------------- 
// This class contains the following:

// Main Class
// Insert the file by just typing the name: test4_2020.txt -> test4_2020
// It starts the Program by reading the file
// It will run until the user will not input "0"
//------------------------------------------ 
public class Main {

	public static void main(String[] args) {
		// name of the file
		String name_file;
		// name of the scanner input
		Scanner input_file = new Scanner(System.in);
		// while the_end is false the program will continue
		// the_end will be true when typing "0" for exiting
		while (!Utils.the_end) {
			// Creating a new Travel object
			Utils utils = new Utils();
			// it checks if it can find the file, if not it will ask again
			do {
				System.out.println("----------- Welcome to the Travelling Salesman Problem -----------");
				System.out.println("\nif you want to exit insert -----> 0");
				System.out.print("\nOtherwise please insert the file to test: ");

				name_file = input_file.nextLine();

			} while (!(utils.Tests_reader(name_file)));
			// the_end will be true when typing "0" for exiting
			if (!Utils.the_end) {
				// Creating a new Travel object
				Travel Travel = new Travel();
				// it prints the result
				Travel.result();
			}
		}
		// closing the scanner
		input_file.close();
	}
}
