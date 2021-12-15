
public class DiscountRequest extends Request{
	private String code;
	public DiscountRequest(int id, String code) {
		super(id);
		this.code = code;
		// TODO Auto-generated constructor stub
	}
	public String getCode() {
		return this.code;
	}

}
