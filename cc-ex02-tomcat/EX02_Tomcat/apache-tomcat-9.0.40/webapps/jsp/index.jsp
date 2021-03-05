<%@ page import = "java.io.*,java.util.*" %>
<html>
    <head><title>JSP</title></head>
    <body>
        <%
        Integer hitsCount = (Integer) application.getAttribute("hitCounter");

        boolean reset = Boolean.parseBoolean(request.getParameter("reset"));
        if(hitsCount == null || reset) {
            hitsCount = 0;
        }

        hitsCount++;
        application.setAttribute("hitCounter", hitsCount);
        %>
        <p>Total number of hits: <%=hitsCount%></p>

        <form>
            <input type="hidden" name="reset" value="true">
            <input type="submit" value="Reset hit counter">
        </form>
    </body>
</html>