import java.util.HashMap;

public class Cart {
	public int id;
	public int numItems;
	public HashMap<Item, Integer> items;
	public double total;
	
	public Cart(int id) {
		this.id = id;
		this.numItems = 0;
		this.items = new HashMap<>();
		this.total = 0.0;
	}
}
