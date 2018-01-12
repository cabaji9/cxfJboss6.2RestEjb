package personal.enumeration;

/**
 * Created by Hyun Woo Son on 11/1/17.
 */
public enum RestExcepcionEnum {



    PARAMETROS_EXCEPCION("Se encontro los siguientes errores con los parámetros : ");
    
    private String value;

    RestExcepcionEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
