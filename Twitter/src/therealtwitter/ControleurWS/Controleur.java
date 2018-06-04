package therealtwitter.ControleurWS;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controleur extends HttpServlet {
    private String urlHomePage;
    private String urlGestionTemplate;

    public void init() throws ServletException {
        urlHomePage = getServletConfig().getInitParameter("urlHomePage");
        urlGestionTemplate = getServletConfig().getInitParameter("urlGestionTemplate");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // on passe la main au GET
        doGet(request, response);
    }

    // GET
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // On récupère la méthode d'envoi de la requête
        String methode = request.getMethod().toLowerCase();

        // On récupère l'action à exécuter
        String action = request.getPathInfo();

        if (action == null) {
            action = "home_page";
            System.out.println("action == null");
        }
        System.out.println(action);

        // Exécution action
        switch (action){
            case "/home_page":{
                doHomePage(request,response);
                break;
            }
            default:{
                doHomePage(request,response);
            }
        }

    }

    private void doHomePage(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {

        // Inclusion du content dans le template
        request.setAttribute("content", urlHomePage);
        loadJSP(urlGestionTemplate, request, response);;
    }



    /**
     * Charge la JSP indiquée en paramètre
     *
     * @param url
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    private void loadJSP(String url, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // L'interface RequestDispatcher permet de transférer le contrôle à une
        // autre servlet
        // Deux méthodes possibles :
        // - forward() : donne le contrôle à une autre servlet. Annule le flux
        // de sortie de la servlet courante
        // - include() : inclus dynamiquement une autre servlet
        // + le contrôle est donné à une autre servlet puis revient à la servlet
        // courante (sorte d'appel de fonction).
        // + Le flux de sortie n'est pas supprimé et les deux se cumulent
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
    }
}
