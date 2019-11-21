{
 /*画面の選択初期設定*/

 document.getElementById("caseIn").style.display ="none";

 document.getElementById("caseOut").style.display ="none";

 document.getElementById("space").style.display = "none";

 document.getElementById("where").style.display = "none";

 document.getElementById("select1").style.display = "none";

 document.getElementById("select2").style.display = "none";

 document.getElementById("select3").style.display = "none";


/*
 *
 * */

 //入庫時の個数を選ぶプルダウンの設定
 function inStock(){
		 document.getElementById("caseIn").style.display ="block";
		 document.getElementById("caseOut").style.display = "none";
		 document.getElementById("space").style.display = "none";
		 document.getElementById("outInfo").style.display = "none";

 }
 //出庫時の個数を選ぶプルダウンの設定
 function outStock(){
	 document.getElementById("caseIn").style.display ="none";
	 document.getElementById("caseOut").style.display = "block";
	 document.getElementById("space").style.display = "block";
	 document.getElementById("outInfo").style.display = "block";
 }


 //space  select1 select2 select3
 //共用部ボタンを押したときのプルダウン設定
 function share(){
	 document.getElementById("where").style.display = "none";
	 document.getElementById("client").style.display = "none";
 }

 //利用者居室を押したときのプルダウン設定
 function poss(){
	 document.getElementById("where").style.display = "block";
	 document.getElementById("client").style.display = "block";
 }

 //フロア選択したときの入居者プルダウン
 function floor1(){
	 document.getElementById("select1").style.display = "block";
	 document.getElementById("select2").style.display = "none";
	 document.getElementById("select3").style.display = "none";
 }
 function floor2(){
	 document.getElementById("select1").style.display = "none";
	 document.getElementById("select2").style.display = "block";
	 document.getElementById("select3").style.display = "none";
 }
 function floor3(){
	 document.getElementById("select1").style.display = "none";
	 document.getElementById("select2").style.display = "none";
	 document.getElementById("select3").style.display = "block";
 }




}