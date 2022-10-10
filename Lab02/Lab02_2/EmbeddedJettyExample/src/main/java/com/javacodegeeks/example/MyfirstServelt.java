package com.javacodegeeks.example;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

import java.io.IOException;
import java.io.PrintWriter;

public class MyfirstServelt {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8480);

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        servletHandler.addServletWithMapping(MyfirstServelt.Username.class, "/");

        server.start();
        server.join();

    }

    public static class Username extends HttpServlet
    {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            String username = request.getParameter("username");
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>IES - LAB02.1 </title>");
            out.println("</head>");
            out.println("<body>");
            if(username != null){
                out.println("<h1>Hello " + username + "</h1>");
            }else{
                out.println("<h2>Welcome Guest!</h2>");
                out.println("<p>Insert your name in the url</p>");
                out.println("<p>Example: http://localhost:8480?username=YourName</p>");

            }
            out.println("</body>");
            out.println("</html>");
        }
    }
}
