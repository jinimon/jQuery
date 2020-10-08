<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>commetns.jsp</title>
<script src='../js/jquery-3.5.1.min.js'></script>
<style>
input {
	margin: 10px;
}
</style>
<script>
	window.onload = function() {
		loadCommentList();
	}

	// 목록 조회
	function loadCommentList() {
		$.ajax({
			url : '../CommentsServ',
			data : 'cmd=selectAll', // {cmd: 'selectAll'} : 둘다 같은 의미. {}는 object 형태
			dataType : 'xml',
			type : 'get',
			// 성공시 loadCommentResult로 data가 매개값으로 넘어오는데 type이 xml이다.
			success : loadCommentResult, // 익명함수 대신 우리는 함수를 생성해서 해보자
			error : function(a, b) {
				alert('댓글 로딩 실패 ' + b);
			}
		});
	}

	// callback 함수
	// 자바스크립트는 변수 타입이 없다. 단지 let, var 같이 선언하는 용도로만 존재.
	function loadCommentResult(xmlResult) {
		let xmlDoc = xmlResult;
		let code = xmlDoc.getElementsByTagName('code').item(0).firstChild.nodeValue;

		if (code == 'success') {
			// eval을 사용해서 안에 내용을 그대로 보여주겠다(?)
			let commentList = eval('('
					+ xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue
					+ ')');
			console.log(commentList); // commentList : 오브젝트
			let listDiv = $('#commentList');

			for (let i = 0; i < commentList.length; i++) {
				let commentDiv = makeCommentView(commentList[i]);
				listDiv.append(commentDiv);
			}
		}
	}

	function makeCommentView(comment) {
		let div = document.createElement('div'); // $('<div />') : jquery로 쓰면 이렇게
		div.setAttribute('id', 'c' + comment.id);
		div.className = 'comment';
		div.comment = comment; // {id:2, name:xxx, content:내용..}

		let str = '<strong>' + comment.name + '</strong> ' + comment.content
				+ '<input type="button" value="수정" onclick="viewUpdateForm('
				+ comment.id + ')">'
				+ '<input type="button" value="삭제" onclick="confirmDelete('
				+ comment.id + ')">';
		div.innerHTML = str;
		return div;
	}

	// 등록 ajax 호출
	function addComment() {
		let name = document.addForm.name.value;
		let content = document.addForm.content.value;

		if (name == null || name == "") {
			alert('이름을 입력해주세요');
			return; // return 값 없이 return만 해주면 종료된다.
		} else if (content == null || content == "") {
			alert('내용을 입력해주세요');
			return;
		}

		let params = 'name=' + encodeURIComponent(name) + '&content='
				+ encodeURIComponent(content) + '&cmd=insert';
		$.ajax({
			url : '../CommentsServ',
			dataType : 'xml',
			data : params,
			success : addResult,
			error : function(a, b) {
				alert('서버 에러: ' + b);
			}
		});
	}

	// callback 함수
	function addResult(req) {
		let xmlDoc = req;
		let code = xmlDoc.getElementsByTagName('code').item(0).firstChild.nodeValue;
		if (code == 'success') {
			let comment = eval('('
					+ xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue
					+ ')');
			let listDiv = document.getElementById('commentList');
			let commentDiv = makeCommentView(comment);
			listDiv.append(commentDiv);

			document.addForm.name.value = '';
			document.addForm.content.value = '';

			alert('등록했습니다! [' + comment.id + ']');
		}
	}

	//  내용 수정
	function viewUpdateForm(id) {
		let commentDiv = document.getElementById('c' + id);
		let updateFormDiv = document.getElementById('commentUpdate');
		commentDiv.after(updateFormDiv);

		let comment = commentDiv.comment; // {id:??, name:???, content:??}
		document.updateForm.id.value = comment.id;
		document.updateForm.name.value = comment.name;
		document.updateForm.content.value = comment.content;

		updateFormDiv.style.display = 'block';
	}

	// 수정
	function updateComment() {
		let id = document.updateForm.id.value;
		let name = document.updateForm.name.value;
		let content = document.updateForm.content.value;
		let params = 'id=' + id + '&name=' + name + '&content=' + content
				+ '&cmd=update';

		$.ajax({
			url : '../CommentsServ',
			data : params,
			dataType : 'xml',
			success : updateResult,
			error : function(a, b) {
				console.log(a, b);
			}
		});
	}

	function updateResult(req) {
		let xmlDoc = req;
		let code = xmlDoc.getElementsByTagName('code').item(0).firstChild.nodeValue;
		if (code == 'success') {
			let comment = eval('('
					+ xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue
					+ ')');
			let listDiv = document.getElementById('commentList');
			let commentDiv = makeCommentView(comment);
			let oldCommentDiv = document.getElementById('c' + comment.id);
			listDiv.replaceChild(commentDiv, oldCommentDiv);

			document.getElementById('commentUpdate').style.display = 'none';

			alert('수정했습니다! [' + comment.id + ']');
		}
	}

	// 수정 취소
	function cancelUpdate() {
		document.getElementById('commentUpdate').style.display = 'none';
	}

	// 삭제
	function confirmDelete(id) {
		$.ajax({
			url : '../CommentsServ',
			data : 'id=' + id + '&cmd=delete',
			dataType : 'xml',
			success : deleteResult,
			error : function(a, b) {
				console.log(a, b);
			}
		});
	}
	
	function deleteResult(req) {
		let xmlDoc = req;
		let code = xmlDoc.getElementsByTagName('code').item(0).firstChild.nodeValue;
		if (code == 'success') {
			let comment = eval('('
					+ xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue
					+ ')');
			let listDiv = document.getElementById('commentList');
			let commentDiv = document.getElementById('c'+comment.id);
			
			listDiv.removeChild(commentDiv);

			alert('삭제했습니다! [' + comment.id + ']');
		}
	}
</script>
</head>
<body>
	<div id='commentList' style='border-radius:15px; border:5px solid #e6f2de; padding:5px'></div>

	<!-- 글 등록 화면 -->
	<div id='commentAdd'>
		<form action="" name="addForm">
			이름: <input type="text" name="name" size="10"><br> 내용:
			<textarea name="content" cols="20" rows="2"></textarea>
			<br> <input type="button" value="등록" onclick="addComment()">
		</form>
	</div>

	<!-- 글 수정 화면 -->
	<div id='commentUpdate' style='display: none;'>
		<form action="" name="updateForm">
			<input type="hidden" name="id" value=""> 이름: <input
				type="text" name="name" size="10"><br> 내용 :
			<textarea name="content" cols="20" rows="2"></textarea>
			<br> <input type="button" value="변경" onclick="updateComment()">
			<input type="button" value="취소" onclick="cancelUpdate()">
		</form>
	</div>

</body>
</html>