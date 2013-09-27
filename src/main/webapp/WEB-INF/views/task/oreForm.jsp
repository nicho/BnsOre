<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>圣物管理</title>
	
	<script>
		$(document).ready(function() {
			if($("#action").val()=="create")
			{
				$("#ore_map").val($("#map_name").val());	
			}
			//聚焦第一个输入框
			$("#ore_title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
			

		});

	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/ore/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${ore.id}"/>
		<input type="hidden" id="action" name="action" value="${action}"/>
		<input type="hidden" id="map_name" name="map_name" value="${param.map_name}"/>
		<fieldset>
			<legend><small>管理圣物</small></legend>
			<div class="control-group">
				<label for="ore_title" class="control-label">地图:</label>
				<div class="controls">
					<input type="text" id="ore_map" name="map"  value="${ore.map}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="ore_title" class="control-label">名称:</label>
				<div class="controls">
					<input type="text" id="ore_title" name="name"  value="${ore.name}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="description" class="control-label">线路:</label>
				<div class="controls">
					<input type="text" id="ore_line" name="line"  value="${ore.line}" class="input-large required" />
				</div>
			</div>	
			 
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
</body>
</html>
