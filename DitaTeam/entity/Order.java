package entity;

import java.sql.Date;

public class Order {
	private int Order_No;
	private String User_ID;
	private String Guest_ID;
	private Date Order_Date;
	
	
	public String getUser_ID() {
		return User_ID;
	}
	public void setUser_ID(String user_ID) {
		User_ID = user_ID;
	}
	public int getOrder_No() {
		return Order_No;
	}
	public void setOrder_No(int order_No) {
		Order_No = order_No;
	}
	public String getGuest_ID() {
		return Guest_ID;
	}
	public void setGuest_ID(String guest_ID) {
		Guest_ID = guest_ID;
	}
	public Date getOrder_Date() {
		return Order_Date;
	}
	public void setOrder_Date(Date order_Date) {
		Order_Date = order_Date;
	}
	
	
}
