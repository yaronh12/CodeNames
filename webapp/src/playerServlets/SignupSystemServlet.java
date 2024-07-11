package playerServlets;


import engine.data.exception.UsernameAlreadyExistInSystemException;
import engine.engine.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "Sign Up System Servlet", urlPatterns = "/system-signup")
public class SignupSystemServlet extends HttpServlet {
    private Engine engine;
    @Override
    public void init() throws ServletException {
        engine = (Engine)getServletContext().getAttribute("myEngine");
        if(engine == null){
            throw new ServletException("Engine not initiated in servlet context!");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String username = req.getParameter("username");
        try{
            engine.addPlayer(username);
            res.getWriter().write("Signup Succeeded!");
        }catch(UsernameAlreadyExistInSystemException e){
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            //res.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
            res.getWriter().write(e.getMessage());
        }

    }

}
