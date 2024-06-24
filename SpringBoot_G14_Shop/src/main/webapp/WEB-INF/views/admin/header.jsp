<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shop</title>
<link rel="stylesheet" href="admin/css/admin.css">
<script src="admin/script/admin.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">
   $(function(){
      $('#myButton').click(function(){
         var formselect = $("#fileupForm")[0];  // 지목된 폼을 변수에다 저장함
         var formdata = new FormData(formselect);  // 전송용 폼 객체에 폼과 내부 데이터(이미지)를 저장
         $.ajax({    //
            url : "<%=request.getContextPath()%>/fileup",
            type:"POST", enctype: "multipart/form-data",
            data : formdata,
            async : false, timeout : 10000,
            contentType: false, processData : false,

            success: function (data) {    // controller에서 리턴딘 해시맵이 data로 전달됨
               if(data.STATUS == 1){
                  $("#filename").append("<div>"+data.SAVEFILENAME+"</div>");
                  $("#image").val(data.IMAGE);
                  $("#savefilename").val(data.SAVEFILENAME);
                  $("#filename").append("<img src='product_images/" + data.SAVEFILENAME + "'height='150'/>")
               }
            },
            error : function(){
               alert("실패");
            }
         });
      });
   });
</script>


</head>
<body>

<div id="wrap">
   <header>
      <div id="logo">
         <img style="width: 800px" src="/admin/images/bar_01.gif">
         <img src="/admin/images/text.gif">
         <input class="btn" type="button" value="logout"  
         onclick="location.href='adminLogout'">
      </div>
   </header>