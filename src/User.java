
public class User {
	public int id;
	public String username;
	public String password;
	public String address;
	public Tax tax;
	
	public User(int id, String username, String password, String address) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.address = address.toLowerCase();
		if(address.equals("indiana")) {
			this.tax = Tax.INDIANA;
		} else if(address.equals("illinois")) {
			this.tax = Tax.ILLINOIS;
		}
	}
}
