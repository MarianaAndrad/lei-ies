package pt.ua.IES.s103823;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MyFirstServlet", urlPatterns = {"/MyFirstServlet"})
public class MyFirstServlet extends HttpServlet {

    private static final long serialVersionUID = -1915463532411657451L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {

        // alinea h)
        //adicionar o nome do utilizador, passando como parametro no pedido http
        String username = request.getParameter("username");

        //alinea i)
        //Null pointer exception
        //Object abc = null;
        //abc.hashCode();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            // Write some content
            out.println("<html>");
            out.println("<head>");
            out.println("<title>IES - LAB02.1 </title>");
            out.println("</head>");
            out.println("<body>");

            if(username != null){
                out.println(String.format("<h2>Welcome %s!</h2>",username));
            }else{
                out.println("<h2>Welcome Guest!</h2>");
                out.println("<p>Insert your name in the url</p>");
                out.println("<p>Example: http://localhost:8080/my_app_war_exploded/MyFirstServlet?username=YourName</p>");

            }
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        //Do some other work
    }
}
