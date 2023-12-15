package test.pojo.ecom;

import java.util.List;

public class EcomGetAllProducts {

	private List<Data> data;
	private int count;
	private String message;

	// Getter Methods

	public List<Data> getData() {
		return data;
	}

	public int getCount() {
		return count;
	}

	public String getMessage() {
		return message;
	}

	// Setter Methods

	public void setData(List<Data> data) {
		this.data = data;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
