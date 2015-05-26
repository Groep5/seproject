/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
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

/**
 *
 * @author
 */
@WebServlet(urlPatterns = {"/vraagservlet"})
public class VraagServlet extends HttpServlet {

    //Initialize global variables
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Bestandslocatie van de XML file. Later als parameter meegeven
        String xmlUrl = "file:///H:/NetBeansProjects/WebApplication1/GMFM.xml";

        PrintWriter out = new PrintWriter(response.getOutputStream());

        //Maakt HTML pagina aan
        out.println("<html>");
        out.println("<head><title>Formulier</title></head>");
        out.println("<body>");

        try {
            //Maakt een URL aan die naar het XML document wijst. En een builder die vervolgens een document opbouwt.
            URL theDoc = new URL(xmlUrl);
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(theDoc);

            // Geeft het root element (Formulier)
            Element root = document.getRootElement();

            /* Maakt een lijst aan waarin alle XML staat die tussen <uitleg> </uitleg> staat. 
             De lijst wordt vervolgens geitereerd totdat alle elementen uit de uitleg zijn geweest en in de html staan.*/
            List uitleg = root.getChildren("UITLEG");
            Iterator uitlegItr = uitleg.iterator();
            while (uitlegItr.hasNext()) {
                Object u = uitlegItr.next();
                Element beschrijving = (Element) u;
                out.println(beschrijving.getChildText("BESCHRIJVING"));
            }

            /* Maakt een lijst aan waarin alle XML staat die tussen <regel> </regel> staat. 
             De lijst wordt vervolgens geitereerd totdat alle elementen uit de regel zijn geweest.*/
            List vragen = root.getChildren("REGEL");
            Iterator itr = vragen.iterator();

            //Er wordt net zolang doorgegaan totdat de laatste vraag is bereikt. Alle vragen worden in een tabel gestopt.
            out.println("<table border =1>");
            out.println("<form >");
            while (itr.hasNext()) {
                Object o = itr.next();
                Element vraag = (Element) o;
               
                /*Hieronder worden de verschillende invoermogelijkheden opgeslagen dus bijvoorbeeld 4 checkbox buttons naast elkaar.
                 Een variabele met NT erachter betekent dat deze een extra knop heeft voor niet getest.
                 VALUES NOG TOEVOEGEN AAN VARIABELEN
                 Uitbreiden indien nodig!!*/
                StringBuffer row = new StringBuffer("<tr>");
//                
    
                /*De vraag wordt opgehaald en in de eerste kolom van de tabel gezet.
                Vervolgens wordt het antwoord opgehaald en wordt gekeken waarmee dit antwoord overeen komt. 
                Dit bepaald vervolgens hoeveel checkbox buttons/tekstvakken er gemaakt worden of dat er gewoon tekst afgedrukt wordt. 
                Uitbreiden als nodig!!!
                */
                row.append("<td>" + vraag.getChildText("VRAAG") + "</td>");
                String antwoord = vraag.getChildText("INVOERMOGELIJKHEID");
                
                if (antwoord.equals("driecheckbox")) {
                    row.append("<td>" + "<input type=\"checkbox\" name=\"driecheckbox\" value=\"0\">" + "<input type=\"checkbox\" name=\"driecheckbox\" value=\"0\">" + "<input type=\"checkbox\" name=\"driecheckbox\" value=\"0\">");
                } else if (antwoord.equals("viercheckboxNT")) {
                    row.append("<td>" + "<input type=\"checkbox\" name=\"viercheckboxNT\" value=\"0\" id=\"0\">" + "<input type=\"checkbox\"name=\"viercheckboxNT\"value=\"1\"id=\"1\">" + "<input type=\"checkbox\"name=\"viercheckboxNT\"value=\"2\"id=\"2\">" + "<input type=\"checkbox\"name=\"viercheckboxNT\"value=\"3\"id=\"3\">" + "<input type=\"checkbox\"name=\"viercheckboxNT\"value=\"\"id=\"4\">" + "</td>");

                } else if (antwoord.equals("vijfcheckbox")) {
                    row.append("<td>" + "<input type=\"checkbox\" name=\"vijfcheckbox\" value=\"0\">" + "<input type=\"checkbox\"name=\"vijfcheckbox\"value=\"1\">" + "<input type=\"checkbox\"name=\"vijfcheckbox\"value=\"2\">" + "<input type=\"checkbox\"name=\"vijfcheckbox\"value=\"3\">" + "<input type=\"checkbox\"name=\"vijfcheckbox\"value=\"4\">");   
                } else if (antwoord.equals("tekstvak")) {
                    row.append("<td>" + "<INPUT TYPE=\"text\" NAME=\"tekstvak\" SIZE=\"13\" MAXLENGTH=\"20\">");
                } else {
                    row.append("<td>" + antwoord);
                }
                //Hier wordt de regel uitgeprint in de html 
                out.println(row.toString());  
                
      
            }
          
  
            //Sluiten van tabel en html 
            out.println("</table>");
            out.println("<input type=\"submit\" value=\"Formulier verzenden\">");
            String[] results = request.getParameterValues("viercheckboxNT");
            for (int i = 0; i < results.length; i++) 
            {
            out.println(results[i]);
            }
            out.println("</form>");
                              
            out.println("</body></html>");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
    
    
}
