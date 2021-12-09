import java.util.*;
//------------DESCRIPTION---------------- 
// This class contains the following:

// Best First Search algorithm
// Chained Lin-Kernighan (Optimiser)
// Euclidian Distance
// Centroid Formula which gets the most centered coordinate
// Matrix filler, which works out every distance from city to city and stores it inside MATRIX DISTANCE
// Get_Cost which retrieves the cost from one city to another from the matrix filler
//------------------------------------------ 
public class Travel {
	// ---- Best First Search Attributes ----
	private ArrayList<City> visited = new ArrayList<City>(); // will store all the visited cities
	private ArrayList<City> unvisited = new ArrayList<City>(); // will store all the unvisited cities
	private ArrayList<City> best_route = new ArrayList<City>(); // hold the best route found
	private final int NUMBER_ofCITIES = City.cities.size(); // number of the cities just red from the file
	protected final double[][] MATRIX_DISTANCE = new double[NUMBER_ofCITIES][NUMBER_ofCITIES];// matrix for all the city distances
	private double distance; // will bee needed for comparing distance
	private double tot_distance; // will keep count of the cost of the travel
	private int index_city;// index of the best city which will be the next city visited

	// ---- Lin-Kernighan Attributes ----
	public ArrayList<City> potential_path = new ArrayList<City>(); // hold the best route found
	protected final int MAX_SWAP = (NUMBER_ofCITIES - 3) >= 5 ? 5 : (NUMBER_ofCITIES - 3); //The number of city swaps that the Lin-kernighan will carry out
	private City city_visiting;// this will get the city the travelig salesman will be at
	private double distance_hold;// it will hold the distance just worked out from different cities
	private double best_distance = Double.MAX_VALUE; // best distance calculated
	private double time_up; // it will store the time taken for the algorithm
	private int counter_base = 0; // counter which will help to count the city
	private final double THRESHOLD = 0.00001; // the allowance of acceptance for the Lin-kernighan
	private City starting_base = City.cities.get(counter_base); // it will hold the value of the city value
	private final int K_OPT = 2; // number of opts accepted for the Lin-kernighan

	public Travel() {
		// initialising the value of MAX_SWAP for the Optimisation
		Utils.MAX_SWAP = MAX_SWAP;
		
		// calling the matrix filler;
		matrix_filler();
		
		// it starts the algorithm
		Best_First_Search();
		
		// it operates the Lin-Kernighan
		optimisation();
		
		//stopping the timer
		time_up = Utils.stop_time();
	}

	// First Route
	protected void Best_First_Search() {

		// Copying the cities in unvisited
		Utils.copy_arrayList(City.cities, unvisited);
		// if visited is equal to zero set the starting point
		if (visited.size() == 0) {
			int starting = centroid();
			// adding the starting point to the visited list
			visited.add(unvisited.get(starting));
			// removing the starting point from the unvisited
			unvisited.remove(starting);
		}
		for (int k = 0; k < visited.size(); k++) {
			// retrieving the starting point
			city_visiting = visited.get(visited.size() - 1);
			// setting the distance which will need to be compared to the distance just calculated
			distance_hold = Integer.MAX_VALUE;
			// looping through cities in order to get the x and y
			for (int i = 0; i < unvisited.size(); i++) {
				// working out the distance for city visiting to the closest city
				distance = get_Cost(city_visiting, unvisited.get(i));
				// if the distance just found is less than the distance calculated before means the new City is closer
				if (distance < distance_hold) {
					distance_hold = distance;
					index_city = i;
				}
			}
			// keeping count the distance made
			if (unvisited.size() < 1) {
				// adding the distance from the last one visited to the starting point
				tot_distance += get_Cost(visited.get(visited.size() - 1), visited.get(0));
				break;
			} else {
				// summing the total distance so far
				tot_distance += distance_hold;
				// adding the next city to visit
				visited.add(unvisited.get(index_city));
				// removing the next city from unvisited
				unvisited.remove(index_city);
			}
		}

			// saving the total distance value to the best distance
			best_distance = tot_distance;
			// backing up the visited array into best_route
			Utils.copy_arrayList(visited, best_route);
		
		return;
	}

	private double optimisation() {
		if (NUMBER_ofCITIES > 3) {
			// copy the best_route found yet into arraylist
			while (counter_base < NUMBER_ofCITIES - 1) {
				// Lin-kernighan takes as parameters
				// 1st parameter number of starting opt 
				// 2nd parameter takes DELTA which will be added to the gain 
				if (!Lin_Kernighan(1, 0.0)) {
					// incrementing the starting base if Lin-kernighan returned false
					starting_base = City.cities.get(++counter_base);
				}
			}
		}
		// adding the returning to the first city to the list
		best_route.add(best_route.get(0));
		// setting back the distance back to zero
		distance = 0;
		// looping though to the list for working out the new cost
		for (int i = 0; i < best_route.size() - 1; i++) {
			distance += get_Cost(best_route.get(i), best_route.get(i + 1));
		}
		// returning the best distance just calculated
		return best_distance = distance;
	}

	// function which tries to optimize the best route found
	// uses global variable best_route
	private boolean Lin_Kernighan(int opt, double DELTA) {
		// keep the score of the cities
		double[][] cities_cost = new double[NUMBER_ofCITIES - 3][2];
		// retrieving the index of starting base in best_route
		final int index_base = best_route.indexOf(starting_base);
		// it initializes the neighbour ahead of the starting city
		final City next_neighbour = best_route.get(City.next_neighbor(best_route, index_base));
		// it initializes the neighbour before the starting city
		final City prev_neighbour = best_route.get(City.previous_neighbor(best_route, index_base));
		// it retrieves the current city
		City city_holder;
		// it retrieves the city before the city_holder
		City previous_city;
		// it loops through all the cities, in order to get the five best suitable cities
		for (int city = 0, city_counter = 0; city < best_route.size(); city++) {
			city_holder = best_route.get(city);
			previous_city = best_route.get(City.previous_neighbor(best_route, city));

			// if true it does not count the current city, the one ahead and before of
			// neighbours in the arraylist and skips it to next iteration
			if (!(city_holder == starting_base || city_holder == next_neighbour || city_holder == prev_neighbour)) {
				cities_cost[city_counter][0] = city;
				cities_cost[city_counter++][1] = get_Cost(previous_city, city_holder) - get_Cost(next_neighbour, city_holder);
			}
		}

		// QuickSort-2 which will be needed to sort the cost of the cities calculated earlier
		Utils.quickSort_dualP(cities_cost, 0, cities_cost.length - 1);

		// it loops through the five best cities in the array
		for (int i = 0; i < MAX_SWAP; i++) {
			City best = best_route.get((int) cities_cost[i][0]);
			// it retrieves the previous best city
			final City prev_best = best_route.get(City.previous_neighbor(best_route, best_route.indexOf(best)));
			// working out the cost from current starting base to the city after
			final double old_city1 = get_Cost(starting_base, next_neighbour);
			// working out the cost of the previous best city to the best one
			final double old_city2 = get_Cost(prev_best, best);
			// working out the cost from the starting base to the previous best city
			final double new_city1 = get_Cost(starting_base, prev_best);
			// working out the cost from next neighbour of the current starting point to the best one
			final double new_city2 = get_Cost(next_neighbour, best);
			// working out the gain of each cost and if gain is positive means it was a good move
			final double gain = old_city1 + old_city2 - new_city1 - new_city2;

			// making the change by flipping cities
			Utils.flip(best_route, next_neighbour, prev_best);

			if (DELTA + gain > THRESHOLD || opt < K_OPT && Lin_Kernighan((opt + 1), (DELTA + gain))) {
				return true;
			} else {
				Utils.flip(best_route, prev_best, next_neighbour);
			}
		}
		return false;
	}

	// initializing the matrix with distances
	protected double[][] matrix_filler() {

		for (int city_id = 0; city_id < NUMBER_ofCITIES; city_id++) {
			for (int city = 0; city < NUMBER_ofCITIES; city++) {
				// working out the distance of every city and inserting it in an apposite matrix
				MATRIX_DISTANCE[City.cities.get(city_id).id_matrix][city] = Distance(
						City.cities.get(city_id).get_Xcoordinate(), City.cities.get(city_id).get_Ycoordinate(),
						City.cities.get(city).get_Xcoordinate(), City.cities.get(city).get_Ycoordinate());
			}
		}

		return MATRIX_DISTANCE;
	}

	// it returns the costs in the matrix given two cities
	public double get_Cost(City city1, City city2) {
		return MATRIX_DISTANCE[city1.CityCost_id()][city2.CityCost_id()];
	}

	// working out the distance
	private double Distance(double x1, double y1, double x2, double y2) {
		double x_tot = 0;
		double y_tot = 0;
		// working out the difference between x2 and x1
		x_tot = Math.pow((x2 - x1), 2);
		// working out the difference between y2 and y1
		y_tot = Math.pow((y2 - y1), 2);
		// working out the distance
		distance = Math.sqrt((x_tot + y_tot));

		return distance;
	}

	// working out the most centered City among all cities
	private int centroid() {
		double x_mean = 0;
		double y_mean = 0;
		int index = 0;
		// working out the mean value for all X coordinates
		x_mean = Utils.getSum_Xcoordinates(City.cities, x_mean) / NUMBER_ofCITIES;
		// working out the mean value for all Y coordinates
		y_mean = Utils.getSum_Ycoordinates(City.cities, y_mean) / NUMBER_ofCITIES;

		double holder = Double.MAX_VALUE;

		for (int i = 0; i < NUMBER_ofCITIES; i++) {
			distance = Distance(x_mean, y_mean, City.cities.get(i).get_Xcoordinate(),
					City.cities.get(i).get_Ycoordinate());
			index = distance < holder ? i : 0;
		}
		return index;
	}

	// function which will print out the result
	protected void result() {
		System.out.println("\n----------- RESULT -----------");
		System.out.println("\nThe file name tested is: " + Utils.file_name + ".txt");
		System.out.println("\nThe distance percurred is: " + best_distance + " miles");
		System.out.print("The Shortest Path found is: ");

		// prints the final route
		Utils.print_array(best_route);

		// stopping the timer
		System.out.println("\nComputed in: " + String.format("%.3f", time_up) + " milliseconds");
	}
}
