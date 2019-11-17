{

function nameChange2(){
	  /*入居者名変更*/
	var radio = document.getElementsByName("occupantNameChange");
	if(radio[0].checked){
		document.getElementById('nameForm').style.display = 'none';
	}else if(radio[1].checked){
		document.getElementById('nameForm').style.display = 'block';
	}
   }

   function floorChange(){ //関数名は手短に ファイル名は被らないように
	 /*入居フロア変更*/
	var floor = document.getElementsByName("occupantFloorChange");
	if(floor[0].checked){
		document.getElementById('floorForm').style.display = 'none';
	}else if(floor[1].checked){
		document.getElementById('floorForm').style.display = 'block';
	}
  }

  function roomChange(){
	 /*居室番号変更*/
	var roomNumber = document.getElementsByName("roomNumberChange");
	if(roomNumber[0].checked){
		document.getElementById('roomNumberForm').style.display = 'none';
	}else if(roomNumber[1].checked){
		document.getElementById('roomNumberForm').style.display = 'block';
	}
  }




}