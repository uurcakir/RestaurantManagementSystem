package Models;

public class Reservation {

	private int id;
	private String customerName;
	private String phoneNumber;
	private String date;
	private int tableNumber;
	private int people;

	public Reservation(int id, String customerName, String phoneNumber, int tableNumber, int people, String date) {

		this.id = id;
		this.customerName = customerName;
		this.phoneNumber = phoneNumber;
		this.date = date;
		this.tableNumber = tableNumber;
		this.people = people;
	}

	public int getId() {
		return id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public int getpeople() {
		return people;
	}

	public void setpeople(int people) {
		this.people = people;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
