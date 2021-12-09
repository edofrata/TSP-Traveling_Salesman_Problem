import java.util.ArrayList;

//------------DESCRIPTION---------------- 
// This class contains the following:

// City constructor, which instatiate cities
// Getter:
//Get X coordinate
//Get Y coordinate
//Get the City ID in the matrix which corrisponds to the index
//Next neighbor, retrieves the neighbor of the cities
//Previous neighbor, retrieves the neighbor of the cities
//Swap City, swaps two city
//------------------------------------------ 
public class City {

	protected final static ArrayList<City> cities = new ArrayList<City>();// Array which will store all the cities
	protected int id_matrix; // it holds the id for the matrix calculator
	protected int city_number; // will store the number of the city
	protected double X; // will store the x coordinate of the city
	protected double Y; // will store the y coordinate of the city

	City() {
	}

	// it creates the city object which are composed by a number, x and y
	// coordination
	public City(int city, double x, double y, int id) {
		this.city_number = city;
		this.X = x;
		this.Y = y;
		this.id_matrix = id;
	}

	// it return the city number
	public int get_City() {
		return city_number;
	}

	// it returns the x coordinate of the object
	public double get_Xcoordinate() {
		return X;
	}

	// it returns they coordinate of the object
	public double get_Ycoordinate() {
		return Y;
	}

	// it retrieves the id of the city for the matrix
	public int CityCost_id() {
		return id_matrix;
	}

	// it returns the next neighbour from the city passed in the list passed
	public static int next_neighbor(ArrayList<City> list, int index) {
		int position = index + 1;
		position = position == list.size() ? 0 : position;
		return position;
	}

	// it returns the previous neighbour from the city passed in the list passed
	public static int previous_neighbor(ArrayList<City> list, int index) {
		int position = index - 1;
		position = position < 0 ? list.size() - 1 : position;
		return position;
	}

	// swaps two cities in the array given
	public static ArrayList<City> swap_city(ArrayList<City> array_city, int city_1, int city_2) {

		City holder_city = array_city.get(city_1);
		array_city.set(city_1, array_city.get(city_2));
		array_city.set(city_2, holder_city);

		return array_city;
	}

}
