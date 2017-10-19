package personal.rest;


import personal.ejb.TestEJBLocal;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;


/**
 * Created by HW on 10/19/16.
 */
@WebServlet(urlPatterns = "/produces")
public class ProducerMsgServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(ProducerMsgServlet.class.getName());



    @EJB
    private TestEJBLocal testEJBLocal;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("called s");

        String name = req.getParameter("msg");

        testEJBLocal.addName(name);

    }

}
