import java.io.*;
import java.util.ArrayList;

//------------DESCRIPTION---------------- 
// This class contains the following:

// Tests_reader, which reads the file to test
// Function that starts the timer and returns it
// Copies an array list to another array list
// Prints arraylist in a specific format
// Get_sumXcoordinate, Get_sumYcoordinate, these function work out the sum of all X and Y coordinatees
// Flip, which takes an Arraylist from an index to another index in order to reverse it
// Swap, it waps two index in an array
// QuickSort - Dual Pivot, sorts any array in descending order 
//------------------------------------------ 
public class Utils {
	public static String file_name; // name of the file chosen
	private int counter_id = 0; // id of the city
	private static double start; // it starts the timer
	public static boolean the_end = false; // it states the end of the program
	public static int MAX_SWAP = 0;

	// new object of City 
	City city = new City();

	Utils() {
	}

	// starts the time of the timer
	public static void start_time() {
		start = System.nanoTime();
	}

	// stops the timer and returns it
	public static double stop_time() {
		double stop = System.nanoTime();
		return (stop - start) / 1000000;
	}

	// reader of the file
	public boolean Tests_reader(String file) {

		try {
			switch (file.trim()) {
			// if the input is equal to zero it means that the user wants to exit
			case "0":
				System.out.println("\nThank you and Goodbye!");
				the_end = true;
				return true;

			default:

				City.cities.clear();
				// string which keeps track of the current line
				String current_line;
				// Buffer reader for the txt file
				BufferedReader txt_reader = new BufferedReader(new FileReader("Tests/" + file + ".txt"));
				file_name = file;

				// starting the timer
				Utils.start_time();
				// reading the file
				while ((current_line = txt_reader.readLine()) != null) {
					// replacing when there is more than one space, with only one space
					current_line = current_line.replaceAll("\\s+", " ");
					// need to trim what it reads, if there is any space, and store as object into
					// the arraylist
					String[] split_line = current_line.trim().split(" ");
					city.city_number = Integer.parseInt(split_line[0]); // assigning the value to city number
					city.X = Double.parseDouble(split_line[1]); // assigning value to x coordinate
					city.Y = Double.parseDouble(split_line[2]); // assigning value to y coordinate
					City.cities.add(new City(city.city_number, city.X, city.Y, counter_id++));
				}
				// closing the buffer reader
				txt_reader.close();
			}
		} catch (IOException e) {

			System.out.println("No file found with that name");
			// returns false if the file was not found and asks for the input
			return false;
		}
		// returns true if the file is found
		return true;
	}

	/**
	 * This method returns a copy of the array given
	 *
	 * @param Main_ArrayList<City> the arraylist which wants to make the copy of
	 * @param Copy_ArrayList<City> the arraylist where the copy will go into
	 * @return Copied ArrayList
	 */
	public static ArrayList<City> copy_arrayList(ArrayList<City> main_arrayList, ArrayList<City> copy_arrayList) {
		copy_arrayList.removeAll(copy_arrayList); // it empties the copied arraylist
		for (int cities = 0; cities < main_arrayList.size(); cities++) {
			copy_arrayList.add(main_arrayList.get(cities));
		}
		return copy_arrayList;
	}

	/**
	 * Prints any arraylist of object City in a specific format
	 *
	 * @param Main_ArrayList<City> the arraylist which wants to make the copy of
	 * @return printed ArrayList Of Type City
	 */
	public static void print_array(ArrayList<City> array_list) {
		System.out.print("[");
		for (int i = 0; i < array_list.size(); i++) {
			System.out.print(" " + array_list.get(i).get_City());
			if (i < (array_list.size() - 1)) {
				System.out.print(",");
			}
		}
		System.out.print(" ]");
	}

	// return the sum of all X's
	public static double getSum_Xcoordinates(ArrayList<City> list, double sum_Xcoordinates) {
		for (int x_coordinate = 0; x_coordinate < list.size(); x_coordinate++) {
			sum_Xcoordinates += list.get(x_coordinate).get_Xcoordinate();
		}
		return sum_Xcoordinates;
	}

// return the sum of all Y's
	public static double getSum_Ycoordinates(ArrayList<City> list, double sum_Ycoordinates) {
		for (int y_coordinate = 0; y_coordinate < list.size(); y_coordinate++) {
			sum_Ycoordinates += list.get(y_coordinate).get_Ycoordinate();
		}
		return sum_Ycoordinates;
	}

	/**
	 * Flip it flips the given array from a city to another, given their indexes
	 * 
	 * @param Array it can take any sort of array
	 * @param City  from which city
	 * @param City  to another city
	 * @return ArrayList passed as a parameter
	 */
	public static ArrayList<City> flip(ArrayList<City> list, City A, City B) {
		// retrieving the index of the city passed in the array
		int city_1 = list.indexOf(A);
		int city_2 = list.indexOf(B);
		// if the two cities are eqaul no point two flip
		if (city_1 == city_2) {
			return list;
		}

		if (city_1 > city_2) {
			for (int swaps = (list.size() + 1 - city_1 + city_2) / 2; swaps > 0; swaps--) {
				// it reverses the cities by swapping them
				City.swap_city(list, city_1++, city_2--);
				// if city1 is equal the size of the list city is equal to zero or remins the
				// same
				city_1 = city_1 == list.size() ? 0 : city_1;
				// if city2 is less than zero means that it is looking for the last index or
				// remais the same
				city_2 = city_2 < 0 ? list.size() - 1 : city_2;
			}
			return list;
		}
		// if city1 is less than city2 it will keep on swapping cities until it is not
		// true anymore
		while (city_1 < city_2)
			City.swap_city(list, city_1++, city_2--);
		return list;
	}

	// this will swap two value in the array
	public static void swap(double[][] array, int index_1, int index_2) {
		double[] holder = array[index_1];
		array[index_1] = array[index_2];
		array[index_2] = holder;
	}

	/**
	 * Quick-Sort dual pivot need to create three partitions first group the left =
	 * elements need to be less than first pivot second group middle = elements need
	 * to be greater than first pivot and less than second_pivot third group right =
	 * elements need to be greater than second pivot
	 * 
	 * @param Array   it can take any sort of array
	 * @param Integer It will be the very first index of the array
	 * @param Integer It will be where until where it needs to be sorted
	 * @return Array depending from the array passed as paramenter
	 */
	public static double[][] quickSort_dualP(double[][] array, int left, int right) {

		// if index to the right is bigger than the one on the left theen return the
		// array
		if (right < left) {
			return array;
		}
		// swap the sign the other way around if from least to greater
		// if value on the very left is smaller than the last index swap
		if (array[left][1] < array[right][1]) {
			swap(array, left, right);
		}
		// assigning the two pivots
		double first_pivot = array[left][1];
		double second_pivot = array[right][1];
		// splitting the three parts
		int left_partEnd = left + 1, right_partEnd = right - 1, middle_part = left_partEnd;
		while (middle_part <= right_partEnd) {
			// swap the sign the other way around if from least to greater
			if (array[middle_part][1] > first_pivot) {
				swap(array, middle_part, left_partEnd);
				left_partEnd++;
				// swap the sign the other way around if from least to greater
			} else if (array[middle_part][1] <= second_pivot) {
				// swap the sign the other way around if from least to greater
				while (array[right_partEnd][1] < second_pivot && middle_part < right_partEnd) {
					right_partEnd--;
				}
				swap(array, middle_part, right_partEnd);
				right_partEnd--;
				// swap the sign the other way around if from least to greater
				if (array[middle_part][1] > first_pivot) {
					swap(array, middle_part, left_partEnd);
					left_partEnd++;
				}
			}
			middle_part++;
		}
		left_partEnd--; // decrementing the left pivot
		right_partEnd++; // decrementing the right pivot

		// these swap the two pivots to their place
		swap(array, left, left_partEnd);
		swap(array, right, right_partEnd);

		quickSort_dualP(array, left, left_partEnd - 1);
		// ending the sorting for the first five bigger ones
		if (left_partEnd > MAX_SWAP)
			return array;
		quickSort_dualP(array, left_partEnd + 1, right_partEnd - 1);
		quickSort_dualP(array, right_partEnd + 1, right);

		return array;
	}
}
