
public class QuantityRequest extends Request{
	private int quantity;
	private int itemID;
	public QuantityRequest(int id, int itemID, int quantity) {
		super(id);
		this.quantity = quantity;
		this.itemID = itemID;
		// TODO Auto-generated constructor stub
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	public int getItemID() {
		return itemID;
	}

}
