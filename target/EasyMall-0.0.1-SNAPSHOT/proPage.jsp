<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>全部商品</title>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
    <link href="${app }/css/prodList.css" rel="stylesheet" type="text/css">
    <link href="${app }/css/page.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
        function changePageNum(num){
            console.log(num);
            if(!/^[1-9][0-9]*$/.test(num)){
                alert("请输入正整数");
            //    document.getElementById("jumpto").value=${page.cpn};
                return;
            }
            if(num!='${page.cpn}'){
                //修改隐藏域cpn的值
                document.getElementById("cpn").value=num;
                //提交form表单
                document.getElementById("searchForm").submit();
            }

        }
        function changePage(){
            document.getElementById("cpn").value="1";
            document.getElementById("searchForm").submit();
        }
    </script>
</head>
<body>
<%@include file="/_head.jsp" %>
<div id="content">
    <div id="search_div">
        <form id="searchForm" method="post" action="${app }/servlet/ProdPageServlet">
            <input type="hidden" id="cpn" name="cpn" value="${page.cpn}">
            <input type="hidden" name="rpp" value="5">
            <span class="input_span">商品名：<input type="text" name="name" value="${page.name }"/></span>
            <span class="input_span">商品种类：<input type="text" name="category" value="${page.category }"/></span>
            <span class="input_span">商品价格区间：<input type="text" name="minprice" value="${page.minprice }"/> - <input type="text" name="maxprice" value="${page.maxprice }"/></span>
            <input type="button" value="查询" onclick="changePage()">
        </form>
    </div>
    <div id="prod_content">
        <c:forEach items="${page.list}" var="prod">
            <div id="prod_div">
                <a href="${app }/servlet/ProdInfoServlet?pid=${prod.id}">
                    <img src="${app}/img.do?imgUrl=${prod.imgurl}"></img>
                </a>
                <div id="prod_name_div">
                    <a href="${app }/servlet/ProdInfoServlet?pid=${prod.id}">
                            ${prod.name }
                    </a>
                </div>
                <div id="prod_price_div">
                    ￥${prod.price }元
                </div>
                <div>
                    <div id="gotocart_div">
                        <a href="${app }/servlet/CartAddServlet?id=${prod.id}&num=1">加入购物车</a>
                    </div>
                    <div id="say_div">
                        库存：${prod.pnum }
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div style="clear: both"></div>
</div>
<div id="fy_div">
    共${page.countrow }条记录 共${page.countpage}页
    <a href="javascript:void(0)" onclick="changePageNum(1)">首页</a>
    <a href="javascript:void(0)" onclick="changePageNum(${page.prepage})">上一页</a>
    <%-- 分页逻辑开始 --%>
    <c:set var="begin" value="0"/>
    <c:set var="end" value="0"/>
    <c:if test="${page.countpage<=5 }">
        <c:set var="begin" value="1"/>
        <c:set var="end" value="${page.countpage }"/>
    </c:if>
    <c:if test="${page.countpage>5}">
        <c:choose>
            <c:when test="${page.cpn<=3 }">
                <c:set var="begin" value="1"/>
                <c:set var="end" value="5"/>
            </c:when>
            <c:when test="${page.cpn>=page.countpage-2 }">
                <c:set var="begin" value="${page.cpn-4 }"/>
                <c:set var="end" value="${page.countpage}"/>
            </c:when>
            <c:otherwise>
                <c:set var="begin" value="${page.cpn-2 }"/>
                <c:set var="end" value="${page.cpn+2 }"/>
            </c:otherwise>
        </c:choose>
    </c:if>
    <c:forEach begin="${begin }" end="${end }" step="1" var="i">
        <c:if test="${i==page.cpn }">${i }</c:if>
        <c:if test="${i!=page.cpn }">
            <a href="javascript:void(0)" onclick="changePageNum(${i})">${i }</a></c:if>
    </c:forEach>
    <%-- 分页逻辑结束 --%>
    <a href="javascript:void(0)" onclick="changePageNum('${page.nextpage}')">下一页</a>
    <a href="javascript:void(0)" onclick="changePageNum('${page.countpage}')">尾页</a>
    跳转到<input type="text" id="jumpto" value="${page.cpn }" onblur="changePageNum(this.value)" />页
</div>
<%@include file="/_foot.jsp" %>
</body>
</html>