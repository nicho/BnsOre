<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>圣物管理</title>
</head>
<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	

	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				<label>名称：</label> <input type="text" name="search_LIKE_name" class="input-medium" value="${param.search_LIKE_name}"> 
				<button type="submit" class="btn" id="search_btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>地图</th><th>线路</th><th>名称</th><th>刷新时间<th></tr></thead>
		<tbody>
		<c:forEach items="${ores.content}" var="ore">
			<tr>
				<td>${ore.map}</td>
				<td>${ore.line}</td>
				<td><a href="${ctx}/ore/update/${ore.id}">${ore.name}</a></td>
				<td>
					<fmt:formatDate value="${ore.refurbishDate}" pattern="yyyy年MM月dd日  HH时mm分ss秒" />
				</td>
				<td><a href="${ctx}/ore/delete/${ore.id}">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${ores}" paginationSize="5"/>

	<div><a class="btn" href="${ctx}/ore/create?map_name=${map_name}">创建</a></div>
	
	
	<!-- 	<div>
		<img alt="" src="../static/images/1.png">
	</div> -->
</body>
 
</html>
