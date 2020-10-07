<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>item.jsp</title>
<script src="../js/jquery-3.5.1.min.js"></script>
<style>
td {
	padding: 5px;
}
</style>
</head>
<body>
	<table border="1" style="border-collapse: collapse; width:70%">
		<tr>
			<td>Item No</td>
			<td><input type="text" name="itemNo"></td>
		</tr>
		<tr>
			<td>Item</td>
			<td><input type="text" name="item"></td>
		</tr>
		<tr>
			<td>Category</td>
			<td><input type="text" name="category"></td>
		</tr>
		<tr>
			<td>Price</td>
			<td><input type="text" name="price"></td>
		</tr>
		<tr>
			<td>Content</td>
			<td><input type="text" name="content"></td>
		</tr>
		<tr>
			<td>Like It</td>
			<td id="star"></td>
		</tr>
		<tr>
			<td>Image</td>
			<td align="center"><img id="image"></td>
		</tr>
	</table>
	<%
		String itemNo = request.getParameter("itemNo");
	%>
	<script>
		$.ajax({
			url : 'GetProductServlet',	// 이 서블릿은 같은 경로에 있으니까 ../으로 상위에 안올라가도 된다.
			// data로 받아오는 값이 뭔지 알려줘야지.. < % %>는 자바 코드를 의미
			data: {itemNo: '<%=itemNo%>'},
			dataType : 'json', // json 파일이니까 알아서 파싱해줘
			success : function(result) {
				let data = result;
			    let star = showStar(data[0].likeIt);
				console.log(data);
				
				$('input[name=itemNo]').val(data[0].itemNo);
				$('input[name=item]').val(data[0].item);
				$('input[name=category]').val(data[0].category);
				$('input[name=price]').val(data[0].price);
				$('input[name=content]').val(data[0].content).css({'width': '80%'});
				$('#star').html(star);
				$('input[name=image]').val(data[0].image);
				$('#image').attr('src', '../images/' + data[0].image).css({'width': '80%'});
			},
			error : function(result) {
				console.log(result);
			}
		});
		
		function showStar(like) {
			let star = '';
		    let cnt = Math.floor(like);
		    let num = 0;
		    for(let i=0; i<cnt; i++) {
		        star += '&#127773;';
		        num++;
		    }
		    
		    let half = like - cnt;
		    if(half == 0.5) {
		        star += '&#127767;';
		        num++;
		    }
		    
	        for(let i=0; i < 5-num; i++) {
		        star += '&#127770;';
		    }
	        return star;
		}
	</script>
</body>
</html>