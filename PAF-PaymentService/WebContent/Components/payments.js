$(document).ready(function()
	{
	if ($("#alertSuccess").text().trim() == "")
	{
	$("#alertSuccess").hide();
	}
	$("#alertError").hide();
	});
	
	
// SAVE ============================================
	$(document).on("click", "#btnSave", function(event)
	{
		// Clear alerts---------------------
		$("#alertSuccess").text("");
		$("#alertSuccess").hide();
		$("#alertError").text("");
		$("#alertError").hide();
		
		// Form validation-------------------
	    var status = validatePaymentForm();
		if (status != true)
		{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
		}
		
		 // If valid------------------------
		 var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT"; 
		 $.ajax( 
		 { 
		 url : "PaymentAPI", 
		 type : type, 
		 data : $("#formPayment").serialize(), 
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onPaymentSaveComplete(response.responseText, status); 
		 } 
 	}); 
});




//validate function=================================

function validatePaymentForm()
{
if ($("#accountNo").val().trim() == "")
	 {
	 	return "Insert accountNo.";
	 }

	if ($("#amount").val().trim() == "")
	 {
	 	return "Insert amount.";
	 }

	if ($("#type").val().trim() == "")
	 {
	 	return "Insert type.";
	 }	

	if ($("#date").val().trim() == "")
	 {
	 	return "Insert date.";
	 }

	if ($("#description").val().trim() == "")
	 {
	 	return "Insert description.";
	 }

	if ($("#buyerName").val().trim() == "")
	 {
	 	return "Insert buyerName.";
	 }


return true;
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
 $("#hidPaymentIDSave").val($(this).closest("tr").find('#hidPaymentIDUpdate').val());
 $("#accountNo").val($(this).closest("tr").find('td:eq(0)').text());
 $("#amount").val($(this).closest("tr").find('td:eq(1)').text());
 $("#type").val($(this).closest("tr").find('td:eq(2)').text());
 $("#date").val($(this).closest("tr").find('td:eq(3)').text());
 $("#description").val($(this).closest("tr").find('td:eq(4)').text());
 $("#buyerName").val($(this).closest("tr").find('td:eq(5)').text());
}); 


// DELETE===========================================
	$(document).on("click", ".btnRemove", function(event)
	{ 
	 $.ajax( 
	 { 
	 url : "PaymentAPI", 
	 type : "DELETE", 
	 data : "id=" + $(this).data("payid"),
	 dataType : "text", 
	 complete : function(response, status) 
	 { 
	 onPaymentDeleteComplete(response.responseText, status); 
	 } 
	 }); 
});



function onPaymentSaveComplete(response, status)
	{ 
	if (status == "success") 
	 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
	 { 
	 $("#alertSuccess").text("Successfully saved."); 
	 $("#alertSuccess").show();
	 $("#divPaymentsGrid").html(resultSet.data); 
	 } else if (resultSet.status.trim() == "error") 
	 { 
	 $("#alertError").text(resultSet.data); 
	 $("#alertError").show(); 
	 } 
	 } else if (status == "error") 
	 { 
	 $("#alertError").text("Error while saving."); 
	 $("#alertError").show(); 
	 } else
	 { 
	 $("#alertError").text("Unknown error while saving.."); 
	 $("#alertError").show(); 
	 } 
	 $("#hidPaymentIDSave").val(""); 
	 $("#formPayment")[0].reset(); 
}




function onPaymentDeleteComplete(response, status)
	{ 
	if (status == "success") 
	 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
	 { 
	 $("#alertSuccess").text("Successfully deleted."); 
	 $("#alertSuccess").show();
	 $("#divPaymentsGrid").html(resultSet.data); 
	 } else if (resultSet.status.trim() == "error") 
	 { 
	 $("#alertError").text(resultSet.data); 
	 $("#alertError").show(); 
	 } 
	 } else if (status == "error") 
	 { 
	 $("#alertError").text("Error while deleting."); 
	 $("#alertError").show(); 
	 } else
	 { 
	 $("#alertError").text("Unknown error while deleting.."); 
	 $("#alertError").show(); 
 } 
}