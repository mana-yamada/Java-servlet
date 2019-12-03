{
function nameChange(){
	  /*入居者名変更*/
	var radio = document.getElementsByName("staffNameChange");
	if(radio[0].checked){
		document.getElementById('nameForm').style.display = 'none';
	}else if(radio[1].checked){
		document.getElementById('nameForm').style.display = 'block';
	}
   }

}