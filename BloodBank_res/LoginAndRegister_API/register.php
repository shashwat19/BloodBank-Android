<?php

require_once "db_connect.php";

if(isset($_POST['name']) && isset($_POST['contact'])&& isset($_POST['address']))
{
	
$NAME= $_POST["name"];
$CONTACT= $_POST["contact"];
$Address=$_POST["address"];

$query="SELECT contact FROM user_record WHERE contact='$CONTACT'";
$con= mysqli_query($res,$query);
			if(mysqli_num_rows($con)>0)
			{
				echo json_encode("This contact is already registerd");
			}	
			else
				 {
					 $query2= "INSERT INTO user_record(name,contact,address)  VALUES ('$NAME','$CONTACT','$Address')";        
					 $con2= mysqli_query($res,$query2);  
								if($con2)
									{
									  echo json_encode("Registration Successful");
									}
									 else
										{
											 echo json_encode("Registration Failed")  ;    
										}
								   
				}
			}
			else{
					echo json_encode("Waiting for data..")  ;    
				}
?>