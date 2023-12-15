package test.pojo.ecom;

public class EcomAddToCart {

	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public EcomAddToCartProduct getProduct() {
		return product;
	}
	public void setProduct(EcomAddToCartProduct product) {
		this.product = product;
	}
	private String _id;
	private EcomAddToCartProduct product;
	
}
