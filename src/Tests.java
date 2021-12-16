import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class Tests {

	//Test to make a new cart for the user viewing, if no cart is present
	@Test
	void ViewNewCartTest() {
		API api = new API();
		Item bandaid = new Item(0, "BandAid", "To heal", 1.23, "bandage.jpg", 10);
		User user = new User(0, "williae2", "password123", "indiana");
		api.addItem(bandaid);
		api.addUser(user);
		Cart newCart = api.viewCart(new Request(user.id));
		
		assertTrue(newCart != null);
	}
	
	//Tests view and add by viewing cart after adding an item to see if the cart was updated
	@Test
	void addToCartAndViewTest() {
		API api = new API();
		Item bandaid = new Item(0, "BandAid", "To heal", 1.23, "bandage.jpg", 10);
		User user = new User(0, "williae2", "password123", "indiana");
		api.addItem(bandaid);
		api.addUser(user);
		double before = api.viewCart(new Request(user.id)).total;
		try {
			api.addToCart(new AddRequest(user.id, bandaid.getID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double after = api.viewCart(new Request(user.id)).total;
		
		assertTrue(before != after);
	}
	//Tests adding multiple items
		@Test
		void add2ToCartTest() {
			API api = new API();
			Item bandaid = new Item(0, "BandAid", "To heal", 1.23, "bandage.jpg", 10);
			Item monster = new Item(2, "Monster Energy Drink", "To energize", 2.99, "monster.jpg", 20);
			User user = new User(0, "williae2", "password123", "indiana");
			api.addItem(bandaid);
			api.addItem(monster);
			api.addUser(user);
			double before = 0;
			double after = 0;
			try {
				api.addToCart(new AddRequest(user.id, bandaid.getID()));
				before = api.viewCart(new Request(user.id)).total;
				api.addToCart(new AddRequest(user.id, monster.getID()));
				after = api.viewCart(new Request(user.id)).total;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			assertTrue(before != after);
		}
		
		//Tests adding multiple items
		@Test
		void addSameToCartTest() {
			API api = new API();
			Item bandaid = new Item(0, "BandAid", "To heal", 1.23, "bandage.jpg", 10);
			User user = new User(0, "williae2", "password123", "indiana");
			api.addItem(bandaid);
			api.addUser(user);
			double before = 0;
			double after = 0;
			try {
				api.addToCart(new AddRequest(user.id, bandaid.getID()));
				before = api.viewCart(new Request(user.id)).total;
				api.addToCart(new AddRequest(user.id, bandaid.getID()));
				after = api.viewCart(new Request(user.id)).total;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			assertTrue(before != after);
		}
	
	//Adding an item that is not in stock
	@Test
	void addToCartErrorTest() {
		API api = new API();
		Item bandaid = new Item(0, "BandAid", "To heal", 1.23, "bandage.jpg", 0);
		User user = new User(0, "williae2", "password123", "indiana");
		api.addItem(bandaid);
		api.addUser(user);
		api.viewCart(new Request(user.id));
		try {
			api.addToCart(new AddRequest(user.id, bandaid.getID()));
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("No more in stock"));
		}
		
	}
	//Changes quantity
	@Test
	void changeQuantityTest() {
		API api = new API();
		Item bandaid = new Item(0, "BandAid", "To heal", 1.23, "bandage.jpg", 10);
		User user = new User(0, "williae2", "password123", "indiana");
		api.addItem(bandaid);
		api.addUser(user);
		api.viewCart(new Request(user.id));
		try {
			api.addToCart(new AddRequest(user.id, bandaid.getID()));
		} catch (Exception e) {
			//nothing
		}
		double before = api.viewCart(new Request(user.id)).total; 
		try {
			api.changeQuantity(new QuantityRequest(user.id, bandaid.getID(), 5));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double after = api.viewCart(new Request(user.id)).total;
		
		assertTrue(before != after);
		
		
		
	}
	//Changes quantity
		@Test
		void changeQuantitySameTest() {
			API api = new API();
			Item bandaid = new Item(0, "BandAid", "To heal", 1.23, "bandage.jpg", 10);
			User user = new User(0, "williae2", "password123", "indiana");
			api.addItem(bandaid);
			api.addUser(user);
			api.viewCart(new Request(user.id));
			try {
				api.addToCart(new AddRequest(user.id, bandaid.getID()));
			} catch (Exception e) {
				//nothing
			}
			double before = api.viewCart(new Request(user.id)).total; 
			try {
				api.changeQuantity(new QuantityRequest(user.id, bandaid.getID(), 1));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			double after = api.viewCart(new Request(user.id)).total;
			
			assertTrue(before == after);
			
			
			
		}
	//Change quantity to be more than what is in stock, throw an error
	@Test
	void changeQuantityTooMuchTest() {
		API api = new API();
		Item bandaid = new Item(0, "BandAid", "To heal", 1.23, "bandage.jpg", 3);
		User user = new User(0, "williae2", "password123", "indiana");
		api.addItem(bandaid);
		api.addUser(user);
		api.viewCart(new Request(user.id));
		try {
			api.addToCart(new AddRequest(user.id, bandaid.getID()));
		} catch (Exception e) {
			//nothing
		}
		try {

			api.changeQuantity(new QuantityRequest(user.id, bandaid.getID(), 5));
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("There is not enough in stock"));
		}
		
		
		
	}
	//Remove the item from the cart, leaves the cart empty
	@Test
	void changeQuantityToZeroTest() {
		API api = new API();
		Item bandaid = new Item(0, "BandAid", "To heal", 1.23, "bandage.jpg", 10);
		User user = new User(0, "williae2", "password123", "indiana");
		api.addItem(bandaid);
		api.addUser(user);
		api.viewCart(new Request(user.id));
		try {
			api.addToCart(new AddRequest(user.id, bandaid.getID()));
		} catch (Exception e) {
			//nothing
		}
		double after = 0;
		int cartSize = 0;
		try {
			api.changeQuantity(new QuantityRequest(user.id, bandaid.getID(), 0));
			after = api.viewCart(new Request(user.id)).total;
			cartSize = api.viewCart(new Request(user.id)).numItems;
		} catch (Exception e) {
			
		}
		assertTrue(after == 0 && cartSize == 0);		
	}
	//Tests the ApplyDiscount function
	@Test
	void applyDiscountTest() {
		API api = new API();
		Item bandaid = new Item(0, "BandAid", "To heal", 1.23, "bandage.jpg", 10);
		User user = new User(0, "williae2", "password123", "indiana");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 1);
		DiscountCode code = new DiscountCode(0, "pog", c,  10, "percent");
		api.addItem(bandaid);
		api.addUser(user);
		api.addDiscountCode(code);
		api.viewCart(new Request(user.id));
		try {
			api.addToCart(new AddRequest(user.id, bandaid.getID()));
		} catch (Exception e) {
			//nothing
		}
		try {
			api.applyDiscountCode(new DiscountRequest(user.id, "pog"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double cost = api.viewCart(new Request(user.id)).total;
		assertTrue(cost < 1.23);
		
	}
	//tests an expired discount code
	@Test
	void applyExpiredDiscountTest() {
		API api = new API();
		Item bandaid = new Item(0, "BandAid", "To heal", 1.23, "bandage.jpg", 10);
		User user = new User(0, "williae2", "password123", "indiana");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		DiscountCode code = new DiscountCode(0, "pog", c,  10, "percent");
		api.addItem(bandaid);
		api.addUser(user);
		api.addDiscountCode(code);
		api.viewCart(new Request(user.id));
		try {
			api.addToCart(new AddRequest(user.id, bandaid.getID()));
		} catch (Exception e) {
			//nothing
		}
		try {
			api.applyDiscountCode(new DiscountRequest(user.id, "pog"));
			fail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().equals("Code is expired"));
		}
		
	}
	//test to see if multiple discounts can be applied
	@Test
	void apply2DiscountsTest() {
		API api = new API();
		Item bandaid = new Item(0, "BandAid", "To heal", 1.23, "bandage.jpg", 10);
		User user = new User(0, "williae2", "password123", "indiana");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 1);
		DiscountCode code1 = new DiscountCode(0, "pog", c,  10, "percent");
		DiscountCode code2 = new DiscountCode(1, "lol", c,  5, "percent");
		api.addItem(bandaid);
		api.addUser(user);
		api.addDiscountCode(code1);
		api.addDiscountCode(code2);
		api.viewCart(new Request(user.id));
		try {
			api.addToCart(new AddRequest(user.id, bandaid.getID()));
		} catch (Exception e) {
			//nothing
		}
		double before = 0;
		double after = 0;
		try {
			api.applyDiscountCode(new DiscountRequest(user.id, "pog"));
			before = api.viewCart(new Request(user.id)).total;
			api.applyDiscountCode(new DiscountRequest(user.id, "lol"));
			after = api.viewCart(new Request(user.id)).total;
		} catch (Exception e) {
		}
		assertTrue(after < before);
	}
	
	//Testing indiana tax
	@Test
	void applyTaxTest() {
		API api = new API();
		Item bandaid = new Item(0, "BandAid", "To heal", 1.23, "bandage.jpg", 10);
		User user = new User(0, "williae2", "password123", "indiana");
		api.addItem(bandaid);
		api.addUser(user);
		api.viewCart(new Request(user.id));
		try {
			api.addToCart(new AddRequest(user.id, bandaid.getID()));
		} catch (Exception e) {
			//nothing
		}
		try {
			api.handleTax(new Request(user.id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double cost = api.viewCart(new Request(user.id)).total;
		assertTrue(cost > 1.23);
		
	}
	
	//Comparing indiana tax and illinois tax
		@Test
		void applyTax2Test() {
			API api = new API();
			Item bandaid = new Item(0, "BandAid", "To heal", 1.23, "bandage.jpg", 10);
			User user1 = new User(0, "williae2", "password123", "indiana");
			User user2 = new User(1, "alsowilliae2", "password123", "illinois");
			api.addItem(bandaid);
			api.addUser(user1);
			api.addUser(user2);
			api.viewCart(new Request(user1.id));
			api.viewCart(new Request(user2.id));
			try {
				api.addToCart(new AddRequest(user1.id, bandaid.getID()));
				api.addToCart(new AddRequest(user2.id, bandaid.getID()));
			} catch (Exception e) {
				//nothing
			}
			try {
				api.handleTax(new Request(user1.id));
				api.handleTax(new Request(user2.id));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			double cost1 = api.viewCart(new Request(user1.id)).total;
			double cost2 = api.viewCart(new Request(user2.id)).total;
			assertTrue(cost1 != cost2);
			
		}
	

}
