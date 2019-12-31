    <?php
 $con=mysqli_connect("localhost","id10269351_simplicity","MomISlov2",
"id10269351_simplicity")or die("connection not successful");


mysqli_select_db($con,"id10269351_simplicity")or die("database not found");
     
    $sql = "select * from blood_bank";

    $res = mysqli_query($con,$sql);
     
    $result = array();
     
    while($row = mysqli_fetch_array($res))
{
    array_push($result,
    array('username'=>$row[0],
    'phoneno'=>$row[1],
    'email'=>$row[2],
    'gender'=>$row[3],
    'blood_group'=>$row[4],
    'address'=>$row[5],
    'password'=>$row[6]
    ));
    }
    echo json_encode(array("result"=>$result));
     
    mysqli_close($con);
    ?> 
	
	
	