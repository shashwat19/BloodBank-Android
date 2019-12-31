<?php

require_once "db_connect.php";
if(isset($_POST['username']) && isset($_POST['password']))
{
$name= $_POST['username'];
$pass= $_POST['password'];


//$query= "INSERT INTO user_data VALUES('$name','$pass')";

$query="SELECT username,password FROM blood_bank WHERE username='$name' AND password='$pass'";
$con= mysqli_query($res,$query);
if(mysqli_num_rows($con)>0)
{
	//$response[$result]=array();
	/*
	$row=$con->fetch_assoc();
	$pro=array();
		//$response["pro"]=array();
		
	$pro["NAME"]=$row["username"];
	$pro["CONTACT"]=$row["contact"];
	$pro["ADDRESS"]=$row["address"];
	*/
	echo "success";
	//echo $pro ["NAME"];

	}
	else
	{
	    echo "invalid";
	}
}
?>