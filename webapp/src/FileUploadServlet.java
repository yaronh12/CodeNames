import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import engine.engine.Engine;
import jakarta.servlet.http.Part;

import javax.xml.bind.JAXBException;
import java.io.*;


import java.nio.file.InvalidPathException;
import java.util.Scanner;

@WebServlet(name = "File Upload Servlet", urlPatterns = "/upload-file")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
    private Engine engine;
    @Override
    public void init() throws ServletException {
        engine = (Engine)getServletContext().getAttribute("myEngine");
        if(engine == null){
            throw new ServletException("Engine not initiated in servlet context!");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/plain");

        Part filePart = req.getPart("file");

        if(filePart != null){
            StringBuilder fileContent = new StringBuilder();
            fileContent.append(readFromInputStream(filePart.getInputStream()));
            String fileString = fileContent.toString();
            try {
                engine.readXmlFile(fileString);
                res.getWriter().write(engine.getBoardRows() + "X" + engine.getBoardCols());
            } catch (FileNotFoundException e) {
                res.getWriter().write("The file was not found. Please check the path and try again.");
            } catch (InvalidPathException e) {
                res.getWriter().write("The file path is invalid. Please enter a correct path.");
            } catch (JAXBException e) {
                res.getWriter().write("JAXB Exception - Error in processing the XML file.");
            } catch (RuntimeException e) {
                res.getWriter().write("An unexpected error occurred: " + e.getMessage());
            }

        }
        else{
            res.getWriter().write("No file uploaded.");
        }
    }

    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }
}


