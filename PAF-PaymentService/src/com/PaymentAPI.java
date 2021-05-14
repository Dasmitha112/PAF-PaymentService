package com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap; 
import java.util.Map; 
import java.util.Scanner;


@WebServlet("/PaymentAPI")
public class PaymentAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	payment paymentObj = new payment();

	
    public PaymentAPI() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String output = paymentObj.insertPayment( 
				Integer.parseInt(request.getParameter("accountNo")),
				Integer.parseInt(request.getParameter("amount")),
				request.getParameter("type"), 
				request.getParameter("date"),
				request.getParameter("description"),
				request.getParameter("buyerName"));
				
				response.getWriter().write(output); 
	}


	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		Map paras = getParasMap(request); 
		 String output = paymentObj.updatePayment(paras.get("hidPaymentIDSave").toString(), 
				paras.get("accountNo").toString(), 
				paras.get("amount").toString(), 
				paras.get("type").toString(), 
				paras.get("date").toString(),
				paras.get("description").toString(),
				paras.get("buyerName").toString());
 
		response.getWriter().write(output); 
	}


	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Map paras = getParasMap(request); 
		 String output = paymentObj.deletePayment(paras.get("id").toString()); 
		response.getWriter().write(output); 
	}
	
	private static Map getParasMap(HttpServletRequest request) 
	{ 
	 Map<String, String> map = new HashMap<String, String>(); 
	try
	 { 
		Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
		String queryString = scanner.hasNext() ? 
				scanner.useDelimiter("\\A").next() : ""; 
		scanner.close(); 
		String[] params = queryString.split("&"); 
		for (String param : params)
		{
			String[] p = param.split("=");
			map.put(p[0], p[1]); 
			} 
		} 
		catch (Exception e) 
		{ 
		} 
		return map; 
	}


}
