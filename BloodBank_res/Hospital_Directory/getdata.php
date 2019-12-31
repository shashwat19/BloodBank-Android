   <?php
 $con=mysqli_connect("localhost","3119778_simplicity","MomISlov2",
"3119778_simplicity")or die("connection not successfull");


mysqli_select_db($con,"id10265853_ducat")or die("database not found");
if(isset($_POST['District']))
{
$district= $_POST['District'];
  
    $sql = "SELECT Hospital_Name,Address_Original_First_Line,Telephone FROM hospitals WHERE District='$district'";

    $res = mysqli_query($con,$sql);
     
    $result = array();
     
    while($row = mysqli_fetch_array($res))
{
    array_push($result,
    array('Hospital_Name'=>$row[2],
    'Address_Original_First_Line'=>$row[3],
    'Telephone'=>$row[6]
    ));
    }
    echo json_encode(array("result"=>$result));
     
    mysqli_close($con);
}	
    ?> 
	
	
	