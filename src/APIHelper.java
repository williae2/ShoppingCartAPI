import java.util.HashMap;

public class APIHelper {
	public void applyTax(Cart cart, Tax tax) {
		if(tax.equals(Tax.ILLINOIS)) {
			cart.total = (cart.total + 20.0)*.01 + cart.total;
		} else if(tax.equals(Tax.INDIANA)) {
			cart.total = (cart.total*.07)+ cart.total;
		}
	}
}
