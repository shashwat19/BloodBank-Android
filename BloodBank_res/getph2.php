    <?php
 $con=mysqli_connect("localhost","id10269351_simplicity","MomISlov2",
"id10269351_simplicity")or die("connection not successfull");


mysqli_select_db($con,"id10269351_simplicity")or die("database not found");

    if(isset($_POST['bgroup']) && isset($_POST['city']))
	{
    $bgroup=$_POST['bgroup'];
    $city=$_POST['city'];
	
     
    $sql = "select phoneno from blood_bank2 where blood_group=$bgroup and city=$city";

    $res = mysqli_query($con,$sql);
     
    $result = array();
     
    while($row = mysqli_fetch_array($res))
{
    array_push($result,
    array('phoneno'=>$row[1]
    ));
    }
    echo json_encode(array("records"=>$result));
	}
     
    mysqli_close($con);
    ?> 
	
	
	