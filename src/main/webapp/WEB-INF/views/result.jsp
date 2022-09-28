<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<title> 처리 결과 </title>
</head>
<body>
	처리 결과
		<!-- jstl의 c문법으로는 if ~ else if문을 사용할 수 가 없이게 choose문을 이용하여 표현 -->
		<c:choose>
		<%-- Controller의 model중 faultLine값을 받은 뒤 해당 값의 내용이 null이 아닌 경우 출력 --%>
		<c:when test="${faultLine != null}">
			<p>▼ 전체 / 일부 실패</p>
			<p>성공 ${clearCnt}건, 실패 ${faultCnt}건</p>
			<p><button type="button" onClick="location='/'"> 처음으로 </button></p>
			<!-- faultLine의 값과 DataBase의 id값만을 비교하여 일치하지 않는 값을 찾은 뒤, 그 결과를 출력하기 위해 분기문 -->
			<c:if test="${not fn:contains('${faultLine}', '${id.id}')}">
				<c:forEach var="line" items="${faultLine}">
					<p>${line}</p>
				</c:forEach>
			</c:if>
		</c:when>
		<%-- when의 조건식이 아닌 경우에 출력 --%>
		<c:otherwise>
			<p>▼ 전체 성공</p>
			<p>전체 레코드 ${clearCnt}건 입력 성공</p>
			<p><button type="button" onClick="getJSON()"> 조회 </button>
			   <input type="button" onClick="location='/'" value="처음으로"></p>
		<div id="disp"></div>
		</c:otherwise>
		</c:choose>
	<!-- 전체 성공 하였을 경우 DataBase에서 Data값을 받기 위해 작성, ajax로 요청하고, 응답은 json으로 받도록 함 -->
	<script type="text/javascript">
		function getJSON() {
			// 아래 Alert는 초기 정상적으로 동작하는지에 대한 Test용 Alert
			//alert ("Test Alert");
			
			// ajax요청식
			$.ajax({
				type : "get",				// data를 받을 타입 설정 - 정보을 얻기위해 get방식 사용(post의 경우 server에 정보를 게시 or 업데이트 할 경우 사용)
				url : "/data.json",			// 해당 data를 요청할 주소 - 여기서 주소는 Controller에 Mapping된 주소를 요청함
				dataType : "json",			// 응답할 dataType - json 으로 받기 위해 설정
				success : getDB				// 성공하였을 경우 실행할 함수 이름
			});
			
			// 요청에 성공하였을 경우 실행할 함수 - data를 받게됨
			function getDB(data) {
				var html = "<table class='table' border='1'>";
				html += "<tr>";
				html += "<td>id</td>";
				html += "<td>pwd</td>";
				html += "<td>name</td>";
				html += "<td>level</td>";
				html += "<td>desc</td>";
				html += "<td>reg_date</td>";
				html += "</tr>";
				
				$.each(data, (index, obj) => {
					html += "<tr>";
					html += "<td>" + obj.id + "</td>";
					html += "<td>" + obj.pwd + "</td>";
					html += "<td>" + obj.name + "</td>";
					html += "<td>" + obj.level + "</td>";
					html += "<td>" + obj.desc + "</td>";
					html += "<td>" + obj.reg_date + "</td>";
					html += "</tr>";
				})
				html += "</table>";
				
				// 출력할 위치를 지정 - 위의 <div> Teg안의 id와 일치한 값에 출력
				$("#disp").html(html)
			}
		}
	</script>
</body>
</html>