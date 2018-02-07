package personal.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import personal.model.Persona;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Hyun Woo Son on 10/10/17.
 */
@Stateless
public class TestEJBImpl implements TestEJBLocal,TestEJBRemote {

   private static Logger log = LoggerFactory.getLogger(TestEJBImpl.class);


    List<String> nameList = new ArrayList<String>();

    @PersistenceContext
    private EntityManager em;


    @Override
    public void addName(String addName) {
        log.info("ADDED NAME:"+ addName);
        nameList.add(addName);

        Persona persona= new Persona();
        persona.setName(addName);
        em.persist(persona);


    }


    @Override
    public String obtainName() {
        return "FUNCIONA!!";
    }


    @Override
    public List<Persona> obtainAllPersonas() {
       return em.createQuery("select p from Persona p").getResultList();
    }

    @Override
    public void deletePersonaByName(String name) {
        Query q =em.createQuery("delete from Persona p where p.name = :name");
        q.setParameter("name",name);
        q.executeUpdate();

        log.info("deletePersonaByName | deleted {}: " ,name);

    }


    @Override
    public void deleteAll(){
        em.createQuery("delete from Persona p").executeUpdate();
    }
}
