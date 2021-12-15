
public class Item {
	private int id;
	private String name;
	private String description;
	private double price;
	private String picture;
	private int numInStock;

	public Item(int id, String name, String description, double price, String picture, int numInStock) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.picture = picture;
		this.numInStock = numInStock;
	}
	public int getID() {
		return this.id;
	}
	public String getName() {return this.name;}
	public String getDescription() {return this.description;}
	public double getPrice() {return this.price;}
	public String getPicture() {return this.picture;}
	public int getNumInStock() {return this.numInStock;}
	
	
	
			
}
