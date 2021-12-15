import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class API {
	private HashMap<Integer, Cart> userCarts;
	private ArrayList<DiscountCode> discounts;
	private ArrayList<User> users;
	private ArrayList<Item> items;
	private APIHelper help = new APIHelper();
	
	public API() {
		this.userCarts = new HashMap<>();
		this.discounts = new ArrayList<>();
		this.users = new ArrayList<>();
		this.items = new ArrayList<>();
	}
	public void intantiateAPI() {
		//This would be the function that connects to the database
	}
	public Cart viewCart(Request r) {
		if(userCarts.get(r.getUserID()) == null) {
			Cart c = new Cart(userCarts.size());
			userCarts.put(r.getUserID(), c);
			this.writeCart();
			return c;
		}
		return userCarts.get(r.getUserID());
		
	}
	public void addToCart(AddRequest r) throws Exception {
		int requestItem = r.getItemID();
		int userID = r.getUserID();
		Item toAdd = null;
		for(Item i : items) {
			if(i.getID() == requestItem) {
				toAdd = i;
				if(i.getNumInStock() == 0) {
					throw new Exception("No more in stock");
				}
				break;
			}
		}
		if(userCarts.get(r.getUserID()) == null) {
			Cart c = new Cart(userCarts.size());
			if(toAdd != null) {
				c.items.put(toAdd, 1);
				c.total += toAdd.getPrice();
				c.numItems+=1;
			}
			userCarts.put(userID, c);
		} else {
			Cart c = userCarts.get(userID);
			
			if(toAdd != null) {
				if(c.items.containsKey(toAdd)) {
					c.items.put(toAdd, c.items.get(toAdd)+1);
					c.total += toAdd.getPrice();
					c.numItems+=1;
				} else {
					c.items.put(toAdd, 1);
					c.total += toAdd.getPrice();
					c.numItems+=1;
				}
			}
			userCarts.put(userID, c);
			this.writeCart();
		}
		
	}
	public void applyDiscountCode(DiscountRequest r) throws Exception {
		int userID = r.getUserID();
		Cart c = userCarts.get(userID);
		String code = r.getCode();
		DiscountCode discount = null;
		for(DiscountCode dc : this.discounts) {
			if(dc.code.equals(code)) {
				discount = dc;
				break;
			}
		}
		Calendar now = Calendar.getInstance();
		if(now.after(discount.expDate)) {
			throw new Exception("Code is expired");
		}
			double newTotal = 0;
			if(discount.type.equals(DiscountType.PERCENT)) {
				newTotal = c.total - (c.total*(discount.discount/100));
			} else if(discount.type.equals(DiscountType.FLAT)) {
				c.total = c.total - discount.discount;
			}
			else {
				throw new Exception("Invalid Code");
			}
		c.total = newTotal;
		userCarts.put(userID, c);
		this.writeCart();
	}
	public void changeQuantity(QuantityRequest r) throws Exception {
		int userID = r.getUserID();
		int itemID = r.getItemID();
		int newQuantity = r.getQuantity();
		Cart c = userCarts.get(userID);
		for(Item i : this.items) {
			if(i.getID() == itemID) {
				if(newQuantity > i.getNumInStock()) {
					throw new Exception("There is not enough in stock");
				}
			}
		}
		for(Item i : c.items.keySet()) {
			if(i.getID() == itemID) {
				if(newQuantity == 0) {
					c.items.remove(i);
				} else {
					c.items.put(i, newQuantity);
				}
				break;
			}
		}
		double newTotal = 0;
		int numItems = 0;
		for(Item i : c.items.keySet()) {
			newTotal += i.getPrice()*c.items.get(i);
			numItems += c.items.get(i);
		}
		c.total = newTotal;
		c.numItems = numItems;
		userCarts.put(userID, c);
		this.writeCart();
	}
	public void handleTax(Request r) {
		int userID = r.getUserID();
		Cart c = userCarts.get(userID);
		User user = null;
		for(User u : users) {
			if(u.id == userID) {
				user = u;
				break;
			}
		}
		help.applyTax(c, user.tax);
		userCarts.put(userID, c);
		this.writeCart();
	}
	// the following were not added to diagram because they are not important to the end functionality and are used
	//for testing purposes
	public void addItem(Item i) {
		this.items.add(i);
	}
	public void addUser(User u) {
		this.users.add(u);
	}
	public void addDiscountCode(DiscountCode dc) {
		this.discounts.add(dc);
	}
	public void writeCart() {
		File file = new File("carts.txt");
		file.delete();
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
		      FileWriter myWriter = new FileWriter("carts.txt");
		      for(int userID : this.userCarts.keySet()) {
		    	  System.out.println("userID is " + userID);
		    	  Cart c = this.userCarts.get(userID);
		    	  String itemString = "";
		    	  for(Item i : c.items.keySet()) {
		    		  itemString = itemString + "[ItemName : " + i.getName() + ", ItemDescription : " + i.getDescription() +
		    				  ", ItemPrice : " + i.getPrice() + ", image : " + i.getPicture() + ", NumberInStock : " + i.getNumInStock() + "], "; 
		    	  }
		    	  if(!itemString.equals("")) {
			    	  itemString = itemString.substring(0, itemString.length()-2);  
		    	  }
		    	  myWriter.write("{UserID : " + userID + ", NumItems : " + c.numItems + ", Items : " 
		    		      + itemString + ", Total : " + c.total + "}");
		    	  myWriter.write(System.getProperty( "line.separator" ));
		      }
		      
		      myWriter.close();
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
}
