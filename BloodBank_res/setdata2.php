<?php

$con=mysqli_connect("localhost","id10269351_simplicity","MomISlov2",
"id10269351_simplicity")or die("connection not successful");


mysqli_select_db($con,"id10269351_simplicity")or die("database not found");

if(isset($_POST['name'])&& isset($_POST['phoneno'])&& isset($_POST['email'])&& isset($_POST['gender'])&& isset($_POST['blood_group'])&& isset($_POST['address'])&& isset($_POST['city']))
{
	


$name=$_POST['name'];
$phoneno=$_POST['phoneno'];
$email=$_POST['email'];
$gender=$_POST['gender'];
$blood_group=$_POST['blood_group'];
$address=$_POST['address'];
$city=$_POST['city'];



$qry="insert into blood_bank2 (name,phoneno,email,gender,blood_group,address,city) values('$name','$phoneno','$email','$gender','$blood_group','$address','$city')";

mysqli_query($con,$qry)or die("Query Problem");
}
else
{
echo "waiting for data...";
}
?>