
public class AddRequest extends Request{
	public int itemID;
	public AddRequest(int id, int itemID) {
		super(id);
		this.itemID = itemID;
		// TODO Auto-generated constructor stub
	}

	public int getItemID() {
		return this.itemID;
	}
}
