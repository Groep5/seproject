/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Michael
 */
@WebServlet(urlPatterns = {"/xmlservlet"})
public class xmlServlet extends HttpServlet {

  //Initialize global variables
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse
response) throws ServletException, IOException {
    //URL to XML file
    String xmlUrl = "file:///C:/Users/Michael/Documents/NetBeansProjects/WebApplication1/adressbook.xml";
//    try { xmlUrl = request.getParameter("xmlDocument"); } catch (Exception e) { e.printStackTrace(); }
//    response.setContentType("text/html");
    PrintWriter out = new PrintWriter (response.getOutputStream());
    //
    // Send out the beginnings of the page
    //
    out.println("<html>");
    out.println("<head><title>JDOM Servlet Example</title></head>");
    //
    out.println("<body>");
    out.println("<table border = 1>");
    out.println("<tr><th>Last Name</th><th>First Name</th><th>Company</th><th>e-mail</th></tr>");
    //
    // Get a handle to the XML document
    //
    try
    {
      //
      // Construct a URL pointing to the XML document
      
      URL theDoc = new URL(xmlUrl);
      //
      SAXBuilder builder = new SAXBuilder(); // Note: NO Validation is performed here
      Document addressBook = builder.build(theDoc);
      //
      // addressBook now contains the JDOM model of the AddressBook document
      //
      // Get the root element
      //
      Element root = addressBook.getRootElement();
      //
      // Get all of the Person entries
      //
      List persons = root.getChildren("PERSON");
      //
      // Iterate through the persons list
      //
      Iterator itr = persons.iterator();
      //
      while (itr.hasNext())
      {
        Object o = itr.next();
        Element person = (Element)o;
        //
        StringBuffer row = new StringBuffer("<tr>");
        //
        // Note: getChildText is a convenience method to get the data
        // for a child of an element it is semantically equivalent to
        // person.getChild("LASTNAME").getText();
        //
        row.append("<td>" + person.getChildText("LASTNAME") + "</td>");
        row.append("<td>" + person.getChildText("FIRSTNAME") + "</td>");
        row.append("<td>" + person.getChildText("COMPANY") + "</td>");
        row.append("<td><A HREF=\"mailto:" + person.getChildText("EMAIL") +
"\">" +person.getChildText("EMAIL") + "</a></td>");
        row.append("</tr>");
        out.println(row.toString());
        //
       }
      out.println("</table>");
      out.println("</body></html>");
    }
    catch (MalformedURLException e) {
      e.printStackTrace();}
    catch (JDOMException e) {
      e.printStackTrace();}
    finally
    {
      out.close();
    }
  }
}