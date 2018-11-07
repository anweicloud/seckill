<%--
  Created by IntelliJ IDEA.
  User: Anwei
  Date: 2018/11/6
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="common/base.jsp"%>
    <title>秒杀列表</title>
</head>
<body>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h2>秒杀列表</h2>
            </div>
            <div class="panel-body">
                <ul class="list-group">
                    <c:forEach items="${list}" var="v" varStatus="vs">
                    <li class="list-group-item">${v.name}
                        <a href="/seckill/${v.seckillId}/detail" target="_blank" class="btn btn-link pull-right">查看详情</a>
                    </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
</body>
</html>
