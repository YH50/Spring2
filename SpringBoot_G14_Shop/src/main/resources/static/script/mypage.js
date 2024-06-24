function go_cart(){
	if( document.formm.quantity.value == ""){
		alert("수량을 입력하세요");
		 document.formm.quantity.focus();
	}else{
		document.formm.action = 'cartInsert';
		document.formm.submit();
	}	
	
}



function go_cart_delete(){
	// 자바스크립트에서  form 태그 내의 입력란들에 접근할때 name 이 같은 것들은   배열로 인식이 됩니다
	// document.cartFrm.cseq  들이  갯수만큼 배열로 인식됩니다 document.cartFrm.cseq[0] ~ 
	// 만약 해당이름의 태그가 한개라면 배열이 아니라 그냥 변수 처럼 인식
	// 배열이라면  .length 라는 속성을 쓸수가 있고,  배열이 아니라면 length 속성은   undefined 가 됩니다
	
	var count = 0;
	// 혹시라도 체크박스에 체크를 하나도 안넣고 삭제버튼을 눌렀는지 검사

	if( document.cartFrm.cseq.length == undefined ){
		// 체크박스가 한 개 >> 체크박스가 단일 변수로 인식
		if( document.cartFrm.cseq.checked==true){
			count++;
		}		
	}else{
		// 체크박스가 두 개 이상 >> 체크박스들을 배열로 가져옴
		for(var i=0; i<document.cartFrm.cseq.length; i++){
			if( document.cartFrm.cseq[i].checked== true){
				count++;
			}
		}
	}
	
	if( count == 0){
		alert("삭제할 항목을 선택하세요");
	}else{
		var ans = confirm("선택한 항목을 삭제할까예?");
		if( ans ){
			document.cartFrm.action = "cartDelete";
			document.cartFrm.submit();
		}
	}
}



function go_order_insert(){
	var count = 0;
	if( document.cartFrm.cseq.length == undefined ){
		if( document.cartFrm.cseq.checked==true) {
			count++;
		}		
	}else{
		for(var i=0; i<document.cartFrm.cseq.length; i++){
			if( document.cartFrm.cseq[i].checked== true){
				count++;
			}
		}
	}	
	if( count == 0){
		alert("주문할 항목을 선택하세요");
	}else{
		var ans = confirm("선택한 항목을 주문할까예?");
		if( ans ){
			document.cartFrm.action = "orderInsert";
			document.cartFrm.submit();
		}
	}
	
}


function go_order(){
	var ans = confirm("현재 상품을 주문할까요?");
	if( ans ){
		document.formm.action = "orderInsertOne";
		document.formm.submit();
	}
}

function qnaViewWithPass(pass, qseq) {
	var inputPass = prompt("비밀글입니다. 비밀번호 쳐주이소");
	if (pass == inputPass) {
		location.href = "qnaView?qseq=" + qseq ;
	}else{
		alert("비밀번호 똑디 치소!!");
	}
}

function qnaView(qseq) {
	location.href = "qnaView?qseq=" + qseq;
}

function enable() {
	if (document.formm.secret.checked == true) {
		document.formm.pass.disabled = false;
	}else{
		document.formm.pass.disabled = true;
		document.formm.pass.value="";
	}
}



function go_updateMember(){
	if( document.joinForm.pwd.value == "") {
	    alert("비밀번호를 입력해 주세요.");	    
	    document.joinForm.pwd.focus();
	} else if( document.joinForm.pwd.value != document.joinForm.pwdCheck.value) {
	    alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");	    
	    document.joinForm.pwd.focus();
	} else if( document.joinForm.name.value == "") {
	    alert("이름을 입력해 주세요.");	    
	    document.joinForm.name.focus();
	} else if( document.joinForm.phone.value == "") {
	    alert("전화번호를 입력해 주세요.");	   
	    document.joinForm.phone.focus();
	}else if( document.joinForm.email.value == "") {
	    alert("이메일을 입력해 주세요.");	   
	    document.joinForm.email.focus();
	} else{
	    document.joinForm.submit();
	}
}



function withdrawal(){
	var ans = confirm("정말로 탈퇴하시겠습니까?")
	if( ans ){
		location.href="deleteMember";
	}
}






