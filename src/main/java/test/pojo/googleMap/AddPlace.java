package test.pojo.googleMap;

import java.util.List;

public class AddPlace {
	Location LocationObject;
	private String accuracy;
	private String name;
	private String phone_number;
	private String address;
	private List<String> types;
	private String website;
	private String language;

	// Getter Methods

	public Location getLocation() {
		return LocationObject;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public String getName() {
		return name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public String getAddress() {
		return address;
	}

	public List<String> getTypes() {
		return types;
	}

	public String getWebsite() {
		return website;
	}

	public String getLanguage() {
		return language;
	}

	// Setter Methods

	public void setLocation(Location locationObject) {
		this.LocationObject = locationObject;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
