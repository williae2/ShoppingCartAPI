import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DiscountCode {
	public int id;
	public String code;
	public Calendar expDate;
	public HashMap<Item, Integer> items;
	public double discount;
	public DiscountType type;
	
	public DiscountCode(int id, String code, Calendar expDate, double discount, String type) {
		this.id = id; 
		this.code = code;
		this.expDate = expDate;
		this.discount = discount;
		if(type.equals("percent")) {
			this.type = DiscountType.PERCENT;
		}
		else if(type.equals("flat")) {
			this.type = DiscountType.FLAT;
		}
	}

}
