<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@include file="tag.jsp" %>
<%@include file="head.jsp" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
