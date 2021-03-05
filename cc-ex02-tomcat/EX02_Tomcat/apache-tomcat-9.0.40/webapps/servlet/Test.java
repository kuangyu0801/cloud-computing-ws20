import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;

public class Test extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head><title>E2T4</title></head>");
            out.println("<body>");
            out.println("<h1>Hello, world!</h1>");
            out.println("<h2>" + new Date() + "</h2>");
            out.println("</body></html>");
        } finally {
            out.close();
        }
    }
}