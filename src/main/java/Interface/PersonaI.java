package Interface;

import Model.PersonaM;
import java.util.List;

public interface PersonaI {

    public void registrarPersona(PersonaM per) throws Exception;    //ESTE METODO VA REGISTRAR LA PERSONA

    List<PersonaM> listarPersona() throws Exception;    //SE MOSTRARA UN LISTADO DE LA PERSONA
}
