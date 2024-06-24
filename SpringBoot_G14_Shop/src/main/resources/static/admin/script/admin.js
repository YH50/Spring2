function loginCheck(){
	if(document.loginForm.userid.value==""){
		alert("아이디를 입력하세요	");
		document.loginForm.userid.focus();
		return false;
	}else if(document.loginForm.pwd.value ==""){
		alert("패스워드를 입력하세요");
		document.loginForm.pwd.focus();
		return false;
	}else{
		return true;
	}
}

function go_search(command){
	var url = "shop.do?command=" + command + "&page=1";
	document.frm.action = url;
	document.frm.submit();
}


function go_detail(pseq){
	location.href="adminProductDetail?pseq=" + pseq;
}

function go_wrt(){
	location.href="productWriteForm";
}

function cals(){
	var value1=document.productWriteForm.price1.value;
	var value2=document.productWriteForm.price2.value;
	
	if(value1==""||value2==""){
		return;
	}else{
		document.productWriteForm.price3.value = value2 - value1;
	}
}

function go_save(){
	var theForm = document.productWriteFrm;
	if(theForm.kind.value==""){
		alert("상품분류를 선택하세요");
		theForm.kind.focus();
	}else if(theForm.name.value==""){
		alert("상품명을 입력하세요");
		theForm.name.focus();
	}else if(theForm.price1.value==""){
		alert("원가를 입력하세요");
		theForm.price1.focus();
	}else if(theForm.price2.value==""){
		alert("판매가를 입력하세요");
		theForm.price2.focus();
	}else if(theForm.content.value==""){
		alert("상세 정보를 입력하세요");
		theForm.content.focus();
	}else if(theForm.image.value==""){
		alert("상품이미지를 삽입하세요");
		theForm.image.focus();
	}else{
		theForm.action = "adminProductWrite";
		theForm.submit();
	}
}


function go_mod(pseq){
   var url = "adminProductUpdateForm&pseq="+pseq;
   location.href = url;
   
}

function go_update(){
	var theForm = document.productWriteFrm;
	if(theForm.kind.value==""){
		alert("상품분류를 선택하세요");
		theForm.kind.focus();
	}else if(theForm.name.value==""){
		alert("상품명을 입력하세요");
		theForm.name.focus();
	}else if(theForm.price1.value==""){
		alert("원가를 입력하세요");
		theForm.price1.focus();
	}else if(theForm.price2.value==""){
		alert("판매가를 입력하세요");
		theForm.price2.focus();
	}else if(theForm.content.value==""){
		alert("상세 정보를 입력하세요");
		theForm.content.focus();
	}else{
		theForm.action = "adminProductUpdate";
		theForm.submit();
	}
}


function go_order_save(){
	var count=0;
	if(document.frm.result.length==undefined){
		if(document.frm.result.checked==true)count++;
	}else{
		for(var i=0; i<document.frm.result.length; i++)
			if(document.frm.result[i].checked==true)
			count++
	}
	
	if(count==0){
		alert("주문처리할 항목을 선택해주세요")
	}else{
		document.frm.action = "adminOrderSave";
		document.frm.submit();
	}
}



























