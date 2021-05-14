package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class payment {
	// A common method to connect to the DB
			private Connection connect() {
				Connection con = null;
				try {
					Class.forName("com.mysql.jdbc.Driver");

					// Provide the correct details: DBServer/DBName, username, password
					con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paymentdb", "root", "");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return con;
			}
			
	public String insertPayment(int accountNo, int amount, String type, String date, String description, String buyerName) {
				String output = "";
				try {
					Connection con = connect();
					if (con == null) {
						return "Error while connecting to the database for inserting.";
					}
					// create a prepared statement
					String query = " insert into payment(`id`,`accountNo`,`amount`,`type`,`date`,`description`,`buyerName`)"+ "values (?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement preparedStmt = con.prepareStatement(query);
					
					// binding values
					preparedStmt.setInt(1, 0); 
					preparedStmt.setInt(2, accountNo); 
					preparedStmt.setInt(3, amount); 
					preparedStmt.setString(4, type);
					preparedStmt.setString(5, date); 
					preparedStmt.setString(6, description); 
					preparedStmt.setString(7, buyerName); 
					
					// execute the statement
					preparedStmt.execute();
					con.close();

					String newPayment = readPayments(); 
					 output = "{\"status\":\"success\", \"data\": \"" + 
							 newPayment + "\"}";
				} catch (Exception e) {
					output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}"; 
					System.err.println(e.getMessage()); 
				}
				return output;
			}

	public String readPayments() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Account Number</th><th>Amount</th><th>Type</th><th>Date</th><th>Description</th><th>Buyer Name</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String id = Integer.toString(rs.getInt("id"));
				String accountNo = Integer.toString(rs.getInt("accountNo"));
				String amount = Integer.toString(rs.getInt("amount"));
				String type = rs.getString("type");
				String date = rs.getString("date");
				String description = rs.getString("description");
				String buyerName = rs.getString("buyerName");

				// Add into the html table
				output += "<tr><td><input id='hidPaymentIDUpdate' name='hidPaymentIDUpdate' type='hidden' value='" + id
						 + "'>" + accountNo + "</td>"; 
				output += "<td>" + amount + "</td>";
				output += "<td>" + type + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + description + "</td>";
				output += "<td>" + buyerName + "</td>";
		

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
				+ "class='btnUpdate btn btn-secondary' data-payid='" + id + "'></td>"
				+ "<td><input name='btnRemove' type='button' value='Remove' "
				+ "class='btnRemove btn btn-danger' data-payid='" + id + "'></td></tr>";
			}
			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePayment(String id, String accountNo, String amount, String type, String date, String description, String buyerName) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE payment SET accountNo=?,amount=?,type=?,date=?,description=?,buyerName=? WHERE id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values		
			preparedStmt.setInt(1, Integer.parseInt(accountNo)); 
			preparedStmt.setInt(2, Integer.parseInt(amount)); 
			preparedStmt.setString(3, type);
			preparedStmt.setString(4, date); 
			preparedStmt.setString(5, description); 
			preparedStmt.setString(6, buyerName); 
			preparedStmt.setInt(7, Integer.parseInt(id)); 
			
			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPayment = readPayments(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
					 newPayment + "\"}";
			 
		} catch (Exception e) {
			 output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}"; 
			 System.err.println(e.getMessage()); 
			}
		return output;
		}

	public String deletePayment(String id) {
		System.out.println(id);
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from payment where id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(id));
			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPayment = readPayments(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newPayment + "\"}"; 
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}"; 
			 System.err.println(e.getMessage());
		}
		return output;
	}
}
