package personal.ejb;

import personal.model.Persona;

import java.util.List;

/**
 * Created by Hyun Woo Son on 10/10/17.
 */
public interface TestEJBInterface {

    void addName(String addName);

    String obtainName();

    List<Persona> obtainAllPersonas();

    void deletePersonaByName(String name);

    void deleteAll();

}
