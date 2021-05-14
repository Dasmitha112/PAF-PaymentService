<%@page import="com.payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment</title>
<link rel="stylesheet" href="Views/bootstrap.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/payments.js"></script>
</head>
<body>
	<div class="container"><div class="row"><div class="col-6"> 
		<h1>Payment Management</h1>
			<form id="formPayment" name="formPayment">
 					<br> Account Number:
 				<input id="accountNo" name="accountNo" type="text" class="form-control form-control-sm">
 					<br> Amount: 
 				<input id="amount" name="amount" type="text" class="form-control form-control-sm">
 				<br> Type: 
 				<input id="type" name="type" type="text" class="form-control form-control-sm">
 				<br> Date: 
 				<input id="date" name="date" type="date" class="form-control form-control-sm">
 				<br> Description: 
 				<input id="description" name="description" type="text" class="form-control form-control-sm">
 				<br> Buyer Name:
 				<input id="buyerName" name="buyerName" type="text" class="form-control form-control-sm">
 					<br>
 				<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
 				<input type="hidden" id="hidPaymentIDSave" name="hidPaymentIDSave" value="">
			</form>
	<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>
<div id="divPaymentsGrid">
 	<%
 		payment paymentObj = new payment(); 
 		out.print(paymentObj.readPayments()); 
 	%>
</div>
</div> </div> </div>
	

</body>
</html>