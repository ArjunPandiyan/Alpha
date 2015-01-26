<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.intuit.Beans.URLCache"%>
<%@ page import="com.intuit.Utils.Utils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Dashboard</title>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js">
</script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<script type="text/javascript">
var globalRowCount = 0;

var valid_responseCodes = {
	'200':true
}

function checkIfValidURL(responseCode){
	if(valid_responseCodes[responseCode]){
		return "success";
	}
	else{
		return "danger";
	}
}

// function to clean all the data from the table
// 
function removeAllDataFromTable(tableId){
	$("#"+tableId).find("tr:gt(0)").remove();
}

function getAllData(){
	$.ajax({
	    url : "http://localhost:8080/easyurl-ws/v1/getAllURLs",
	    type: "GET",
	    dataType: 'json',
	    success: function(data)
	    {
	    	data = JSON.stringify(data);
	    	var result = $.parseJSON(data);
	    	console.log(result);
	    	for (var i = 0; i < result.length; i++) {
		    	$('#search-table')
	    		.append(
	    				'<tr class='+checkIfValidURL(result[i].urlStatus)+'>'+
	    					'<td nowrap id='+result[i].urlKey+'ZzDdSstd-left'+'>'+'<input id='+result[i].urlKey+'ZzDdSsleft-editZzDdSseditBtn'+' type="button" class="btn btn-primary btn-large" value="Edit" onclick="editAction();"></input></td>'+
                            '<td nowrap>'+result[i].urlKey+'</td>'+
                            '<td nowrap>'+'<input type="text" id='+result[i].urlKey+'ZzDdSsvalueZzDdSseditable'+' value="'+result[i].urlValue+'" readonly></input></td>'+
                            '<td nowrap><input id='+result[i].urlKey+'ZzDdSsorgIdZzDdSs'+' value="'+result[i].orgId+'" readonly></input></td>'+
                            '<td nowrap>'+result[i].ownerEmail+'</td>'+
        					'<td nowrap>'+result[i].lastModifiedUserEmail+'</td>'+
        					'<td nowrap>'+result[i].lastModifiedDate+'</td>'+
        					'<td nowrap><input id='+result[i].urlKey+'ZzDdSsurlInfoZzDdSseditable'+' value="'+result[i].urlInfo+'" readonly></input></td>'+
        					'<td nowrap id='+result[i].urlKey+'ZzDdSstd-right'+'>'+' <input type="button" id='+result[i].urlKey+'ZzDdSsright-editZzDdSseditBtn'+' class="btn btn-primary btn-large" value="Edit" onclick="editAction();"></input></td>'+
	    				'</tr>');
	    	}
	    },
	    error: function (error, textStatus, errorThrown)
	    {
		    alert("There was some problem retrieving the data");
	    }
	});
}

function isEmpty(object){
	 for(var property in object) {
	        if(object.hasOwnProperty(property))
	            return false;
	 }
	 return true;
};

/* This is to check the validation whether the value is null or undefined or just
* consists of spaces
* 
*/
function checkValidation(value){
	if(value !== undefined && value !== null && value.length>0 && value.replace(/\s+/g, '').length>0){
		return true;
	}
	else{
		return false;
	}
};

function addURL(btn){
		var btnId = btn.id.split("ZzDdSs")[0];
		// Creating a JSON string
		var urlObject = {};
		urlObject["urlKey"] = $("#"+btnId+"ZzDdSskey").val();
		urlObject["urlValue"] =$("#"+btnId+"ZzDdSsurl").val();
		urlObject["ownerEmail"] = $("#"+"emailIdUser").val();
		console.log($("#"+btnId+"ZzDdSsorg").val());
		urlObject["orgId"] =$("#"+btnId+"ZzDdSsorg").val();
		urlObject["urlInfo"] = $("#"+btnId+"ZzDdSsinfo").val();
		var jsonURL = JSON.stringify(urlObject);
		if(checkValidation($("#"+btnId+"ZzDdSskey").val()) && checkValidation($("#"+btnId+"ZzDdSsurl").val()) && checkValidation($("#"+"emailIdUser").val()) && checkValidation($("#"+btnId+"ZzDdSsorg").val()) && checkValidation($("#"+btnId+"ZzDdSsinfo").val())){
			$.ajax({
			    url : "http://localhost:8080/easyurl-ws/v1/insert",
			    type: "POST",
			    dataType: 'text',
			    data : jsonURL,
			    success: function(data)
			    {
				    if(data.indexOf("Success")>-1){
			    		removeAllDataFromTable('search-table');
			    		getAllData();
				    }
				    else{
			    		alert(data);
				    }
			    },
			    error: function (error, textStatus, errorThrown)
			    {
				    alert(error);
			    }
			});
		}
		else{
			alert("Kindly check all the mandatory fields");
		}
}


//The purpose of this function is to add a new row
function addNewRow(){
	$('#insert-table')
	.append(
			'<tr>'+
				'<td><span style="font-weight:bolder;color:red;font-size:25px;">|</span><input style="width:96%;" type="text" id='+globalRowCount+'ZzDdSskey'+' value=""></input></td>'+
				'<td><span style="font-weight:bolder;color:red;font-size:25px;">|</span><input style="width:96%;" type="text" id='+globalRowCount+'ZzDdSsurl'+' value=""></input></td>'+
				'<td><span style="font-weight:bolder;color:red;font-size:25px;">|</span><select style="width:200px;height:100px;" id='+globalRowCount+'ZzDdSsorg' +'>'+'<option value="CTO-DEV">CTO-DEV</option><option value="SBG">SBG</option><option value="IFS">IFS</option><option value="CG">CG</option></select></td>'+
				'<td><span style="font-weight:bolder;color:red;font-size:25px;">|</span><input style="width:96%;" type="text" id='+globalRowCount+'ZzDdSsinfo'+' value=""></input></td>'+
				'<td><input style="width:96%;" type="submit" class="btn btn-primary btn-large" id='+globalRowCount+'ZzDdSssubmit'+' value="Add" onclick="addURL(this);"></input></td>'+
			'</tr>');
	globalRowCount++;
}




//The purpose of this function is to add an existing row
function removeRow(){
	if(globalRowCount>=1){
		$('#insert-table tr:last')
		.remove();
		globalRowCount--;
	}
}

//By default there will be 1 row
	$(document)
			.ready(
					function() {
						getAllData();
						addNewRow();
					});



//Code for Quick Search
function filter(tableId,textBoxtoSearchId,columnNum) {
    var search = $("#"+textBoxtoSearchId).val();
    var $tr = $("#"+tableId+" tr").not($("#"+tableId +" tr.header-row")).hide();
    $tr.filter(function() {
        return $(this).find("td").eq(columnNum).text().indexOf(search) >= 0;
    }).show();
};

// This function is called when the cancel button is clicked
function cancelAction(){
	var cancelBtnId = event.target.id;
	var urlId = cancelBtnId.split("ZzDdSs");
	// If the button is an cancel button
	if(cancelBtnId.indexOf("cancelBtn")>-1){
		// make the fields uneditable
		$("[id^="+urlId[0]+"][id$="+"editable]").prop("readonly",true);
		$("[id^="+urlId[0]+"][id$="+"editable]").prop("disabled",true);
		// get the left and the right tds
		$('#'+urlId[0]+'ZzDdSstd-left').empty();
		$('#'+urlId[0]+'ZzDdSstd-right').empty();
		var htmlUpdateCancelContentLeft = '<input id='+urlId[0]+'ZzDdSsleft-editZzDdSseditBtn'+' type="button" class="btn btn-primary btn-large" value="Edit" onclick="editAction();"></input>';
		var htmlUpdateCancelContentRight = '<input type="button" id='+urlId[0]+'ZzDdSsright-editZzDdSseditBtn'+' class="btn btn-primary btn-large" value="Edit" onclick="editAction();"></input>';
		$('#' + urlId[0] + 'ZzDdSstd-left').html(htmlUpdateCancelContentLeft);
		$('#' + urlId[0] + 'ZzDdSstd-right').html(htmlUpdateCancelContentRight);
	}
};

//function is called when the update function is called
function updateAction(updateBtn){
	var key = updateBtn.id.split("ZzDdSs")[0];	
	var urlObject = {};
	urlObject["urlKey"] =key;
	urlObject["urlValue"] =$("#"+key+"ZzDdSsvalueZzDdSseditable").val();
	urlObject["updateEmail"] = $("#"+"emailIdUser").val();
	urlObject["orgId"] =$("#"+key+"ZzDdSsorgIdZzDdSs").val();
	urlObject["urlInfo"] = $("#"+key+"ZzDdSsurlInfoZzDdSseditable").val();
	var jsonURL = JSON.stringify(urlObject);

	if(checkValidation($("#"+key+"ZzDdSsvalueZzDdSseditable").val()) && checkValidation($("#"+"emailIdUser").val()) && checkValidation($("#"+key+"ZzDdSsurlInfoZzDdSseditable").val())){
		$.ajax({
		    url : "http://localhost:8080/easyurl-ws/v1/update",
		    type: "POST",
		    dataType: 'text',
		    data : jsonURL,
		    success: function(data)
		    {
		    	if(data.indexOf("Success")>-1){
		    		alert("The data for "+key+" has been updated successfully");
			    }
			    else{
		    		alert(data);
			    }
		    },
		    error: function (error, textStatus, errorThrown)
		    {
			    alert(error);
		    }
		});
	}
	else{
		alert("Mandatory fields cannot be empty");
	}	
}

//function that is classed when the edit button is clicked or the cancel button is clicked
function editAction(){
	var editBtnId = event.target.id;
	console.debug(editBtnId);
	var urlId = editBtnId.split("ZzDdSs");
	// If the button is an edit button
	if(editBtnId.indexOf("editBtn")>-1){
		// Disable all the editable fields and change the Edit Button to Update Button
		$("[id^="+urlId[0]+"][id$="+"editable]").prop("readonly",false);
		$("[id^="+urlId[0]+"][id$="+"editable]").prop("disabled",false);
		// get the left and the right tds
		$('#'+urlId[0]+'ZzDdSstd-left').empty();
		$('#'+urlId[0]+'ZzDdSstd-right').empty();
		var htmlUpdateCancelContentLeft = '<input id='
					+ urlId[0]
					+ 'ZzDdSsleft-updateZzDdSsupdateBtn'
					+ ' type="button" class="btn btn-info btn-large"  value="Update" onclick="updateAction(this);">'
					+ '<input id='
					+ urlId[0]
					+ 'ZzDdSsleft-cancelZzDdSscancelBtn'
					+ ' type="button" class="btn btn-info btn-large" style="margin-left:2%;" value="Cancel" onclick="cancelAction();">';
		var htmlUpdateCancelContentRight = '<input id='
					+ urlId[0]
					+ 'ZzDdSsright-updateZzDdSsupdateBtn'
					+ ' type="button" class="btn btn-info btn-large"  value="Update" onclick="updateAction(this);">'
					+ '<input id='
					+ urlId[0]
					+ 'ZzDdSsright-cancelZzDdSscancelBtn'
					+ ' type="button" class="btn btn-info btn-large"  style="margin-left:2%;" value="Cancel" onclick="cancelAction();">'
			$('#' + urlId[0] + 'ZzDdSstd-left').html(htmlUpdateCancelContentLeft);
			$('#' + urlId[0] + 'ZzDdSstd-right').html(htmlUpdateCancelContentRight);
	}else if(editBtnId.indexOf("editBtn")>-1){
		// make the fields uneditable
		$("[id^="+urlId[0]+"][id$="+"editable]").prop("readonly",true);
		// get the left and the right tds
		$('#'+urlId[0]+'ZzDdSstd-left').empty();
		$('#'+urlId[0]+'ZzDdSstd-right').empty();
		var htmlUpdateCancelContentLeft = '<input id='+urlId[0]+'ZzDdSsleft-editZzDdSseditBtn'+' type="button" class="btn btn-primary btn-large" value="Edit" onclick="editAction();"></input>';
		var htmlUpdateCancelContentRight = '<input type="button" id='+urlId[0]+'ZzDdSsright-editZzDdSseditBtn'+' class="btn btn-primary btn-large" value="Edit" onclick="editAction();"></input>';
		$('#' + urlId[0] + 'ZzDdSstd-left').html(htmlUpdateCancelContentLeft);
		$('#' + urlId[0] + 'ZzDdSstd-right').html(htmlUpdateCancelContentRight);
	}
};

</script>

<style>
.container{
	margin-left: 5px;
	width:100% !important;}
</style>
</head>
<body>
	<div class="container">
		<ul class="nav nav-tabs" id="tabId">
		  	<li class="active"><a href="#searchpanel" data-toggle="tab">Search</a></li>
		  	<li><a href="#addpanel" data-toggle="tab">Add</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="searchpanel">
				<div class="jumbotron">
					<span style="font-size: 20px; font-weight: bold;">Search Panel</span>
					<br /> <br />
					<table width="100%">
						<tr>
							<td><span style="font-weight: bold;">Key</span></td>
							<td><span style="font-weight: bold;">Organization Name</span>
							</td>
							<td><span style="font-weight: bold;">Email-Id(Created By)</span></td>
						</tr>
						<tr>
							<td><input type="text" id="key-search" class="form-control"
								placeholder="Key of the URL" onkeypress="filter('search-table','key-search',1)" onkeyup="filter('search-table','key-search',1)" onkeydown="filter('search-table','key-search',1)" onchange="filter('search-table','key-search',1)"/></td>
							<td><select class="form-control" id="organization-search">
									<option>CTO-DEV</option>
									<option>SBG</option>
									<option>IFS</option>
									<option>CG</option>
							</select> <!-- <input type="text" id="organization-search"
								class="form-control" placeholder="Organization" onkeypress="filter('search-table','organization-search',3)" onkeyup="filter('search-table','organization-search',3)" onkeydown="filter('search-table','organization-search',3)" onchange="filter('search-table','organization-search',3)" /> --></td>
							<td><input type="email" id="email-search" class="form-control"
								placeholder="XYZ@INTUIT.COM" onkeypress="filter('search-table','email-search',4)" onkeyup="filter('search-table','email-search',4)" onkeydown="filter('search-table','email-search',4)" onchange="filter('search-table','email-search',4)"/></td>
						</tr>
					</table>
				</div>
			</div>
  			<div class="tab-pane" id="addpanel">
  				<div class="jumbotron">
	  				<div id="userInfoDiv">
							<input id="addBtnId" type="button" class="btn btn-primary btn-large" value="Add New Row" onclick="addNewRow();"></input>
							<input id="removeBtnId" type="button" class="btn btn-danger btn-large" value="Remove Row" onclick="removeRow();"></input>
					</div>
					<br/>
					<div>
						<table class="table table-bordered table-striped" id="insert-table" width="100%;">
							<tr class="header-row">
								<th width="15%;">Key</th>
								<th width="35%;">URL</th>
								<th width="15%;">Organization</th>
								<th width="35%;">URL Info</th>
							</tr>
						</table>
	  				</div>
  				</div>
			</div>
		</div>
		<!--  Tab Ends -->
		<br />
		<div id="userInfoDiv">
			<label>Email Id</label>
			<span style="font-weight:bolder;color:red;font-size:25px;">|</span><input type="text" style="width:30%;" id="emailIdUser" value="" placeholder="xyz@intuit.com"/>    
		</div>
		<br/>
		<div>
			<table class="table table-bordered" id="search-table">
				<tr class="header-row">
					<th></th>
					<th>Key</th>
					<th>URL</th>
					<th>Organization</th>
					<th>Created By</th>
					<th>Last Modified By</th>
					<th>Last Modification Date</th>
					<th>URL Info</th>
					<th></th>
				</tr>
			</table>
			<br/>
		</div>
	</div>
</body>
</html>