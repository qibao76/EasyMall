<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/26
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="load" method="post" enctype="multipart/form-data">
        描述1:
        <input type="text" name="desc1"><br>
        描述2:
        <input type="text" name="desc2" /><br>
        附件:
        <input type="file" name="fx" ><br>
        <input type="submit" value="上传">

    </form>
</body>
</html>
