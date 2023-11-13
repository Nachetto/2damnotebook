package jakarta.rest;

public class Constantes {
    public static final String BORRADO = "Borrado Correctamente";
    public static final String USERNOTFOUND = "No se ha encontrado el usuario";
    public static final String NOTANINT = "ID must be an integer";
    public static final String USERADDED = "Usuario añadido correctamente, sus datos son los siguientes: \n";
    public static final String ORDERADDED = "Pedido añadido correctamente, sus datos son los siguientes: \n";
    public static final String ORDERSPARAM = "/orders";
    public static final String ORDERIDPARAM = "/{orderid}";
    public static final String IDPARAM = "/{id}";
    public static final String ID_HASANYORDERSPARAM = "/{id}/hasanyorders";
    public static final String ERRORADDINGORDER = "Error al añadir el pedido, puede que el pedido ya exista";
    public static final String ORDERNOTFOUND = "No se ha encontrado el pedido";

    private Constantes() {}
    public static final String ID = "id";
    public static final String ORDERID = "orderid";
    public static final String HAS_ORDERS = "El usuario tiene pedidos";
    public static final String NO_ORDERS = "El usuario no tiene pedidos";
}
