{

/*タグの情報を変更をしようとしたときのフォームの表示設定*/
   function nameChange(){
	/*備品名変更*/
	var goodsName = document.getElementsByName("goodsNameChange");
	if(goodsName[0].checked){
		document.getElementById('nameForm').style.display = 'none';
	}else if(goodsName[1].checked){
		document.getElementById('nameForm').style.display = 'block';
	}
  }

  function priceChange(){
	 /*備品単価変更*/
	var price = document.getElementsByName("goodsPriceChange");
	if(price[0].checked){
		document.getElementById('priceForm').style.display = 'none';
	}else if(price[1].checked){
		document.getElementById('priceForm').style.display = 'block';
	}
  }



}