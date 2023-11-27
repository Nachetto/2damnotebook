package dao.common;

public class ConstantesDao {
    private ConstantesDao() {
    }
    public static final String CLIENTE_ELIMINAR_ES_NULO = "Cliente a eliminar es nulo";


    public static final String SELECT_TODOS_LOS_USUARIOS_QUERY = "SELECT * FROM Usuario";
    public static final String ERROR_AL_OBTENER_TODOS_LOS_USUARIOS = "Error al obtener todos los usuarios: ";
    public static final String INVALIDUUID = "El UUID proporcionado no es válido: ";
    public static final String SELECT_USUARIO_POR_UUID_QUERY = "SELECT * FROM Usuario WHERE UUID = ?";
    public static final String USUARIO_NO_ENCONTRADO_CON_UUID = "Usuario no encontrado con UUID: ";
    public static final String ERROR_AL_OBTENER_EL_USUARIO_CON_UUID = "Error al obtener el usuario con UUID: ";
    public static final String ADD_USUARIO_QUERY = "INSERT INTO usuarios (UUID, nombre, correoElectronico, contrasena, fechaNacimiento) VALUES (?, ?, ?, ?, ?)";
    public static final String ERROR_AL_GUARDAR_EL_USUARIO = "Error al guardar el usuario: ";
    public static final String CHECK_USUARIO_UUID_EXISTENCE_QUERY = "SELECT COUNT(*) as usuario_count FROM Usuario WHERE UUID = ?";
    public static final String DELETE_USUARIO_QUERY = "DELETE FROM Usuario WHERE UUID = ?";
    public static final String ERROR_AL_ELIMINAR_EL_USUARIO = "Error al eliminar el usuario con el ID: ";
    public static final String ES_NULO = "Usuario proporcionado para modificar es nulo o tiene un valor invalido";
    public static final String ERROR_AL_MODIFICAR_EL_USUARIO_CON_UUID = "Error al modificar el usuario con UUID: ";
    public static final String SELECT_TODOS_LOS_JUEGOS_QUERY = "SELECT * FROM Juego";
    public static final String SELECT_JUEGO_POR_UUID_QUERY = "SELECT * FROM Juego WHERE UUID = ?";
    public static final String JUEGO_NO_ENCONTRADO_CON_UUID = "Juego no encontrado con UUID: ";
    public static final String ERROR_AL_OBTENER_EL_JUEGO_CON_UUID = "Error al obtener el juego con UUID: ";
    public static final String ADD_JUEGO_QUERY = "INSERT INTO juegos (UUID, titulo, desarrollador, fechaLanzamiento, genero, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String JUEGO_ELIMINAR_ES_NULO = "Juego a eliminar es nulo";
    public static final String DELETE_JUEGO_QUERY = "DELETE FROM Juego WHERE UUID = ?";
    public static final String ERROR_AL_ELIMINAR_EL_JUEGO = "Error al eliminar el juego con el UUID: ";
    public static final String SELECT_TODOS_LOS_SUBSCRIPTORES_QUERY = "SELECT * FROM Suscripcion";
    public static final String SELECT_SUSCRIPCION_POR_UUID_QUERY = "SELECT * FROM Suscripcion WHERE UUID = ?";
    public static final String SUSCRIPCION_NO_ENCONTRADA_CON_UUID = "Suscripcion no encontrada con UUID: ";
    public static final String ERROR_AL_OBTENER_LA_SUSCRIPCION_CON_UUID = "Error al obtener la suscripcion con UUID: ";
    public static final String SELECT_SUBSCRIPCIONES_POR_USUARIO_QUERY = "SELECT * FROM Suscripcion WHERE usuarioID = ?";
    public static final String ADD_SUSCRIPCION_QUERY = "INSERT INTO suscripciones (UUID, usuarioID, fechaInicio, fechaFin, precio) VALUES (?, ?, ?, ?, ?)";
    public static final String ERROR_AL_GUARDAR_LA_SUSCRIPCION = "Error al guardar la suscripcion: ";
    public static final String ERROR_AL_MODIFICAR_LA_SUSCRIPCION_CON_UUID = "Error al modificar la suscripcion con UUID: ";
    public static final String SUSCRIPCION_ELIMINAR_ES_NULO = "Suscripcion a eliminar es nulo";
    public static final String DELETE_SUSCRIPCION_QUERY = "DELETE FROM Suscripcion WHERE UUID = ?";
    public static final String ERROR_AL_ELIMINAR_LA_SUSCRIPCION = "Error al eliminar la suscripcion con el UUID: ";
    public static final String CHECK_SUSCRIPCION_UUID_EXISTENCE_QUERY = "SELECT COUNT(*) as suscripcion_count FROM Suscripcion WHERE UUID = ?";
    public static final String DELETE_SUSCRIPCIONES_DEL_USUARIO_QUERY = "DELETE FROM Suscripcion WHERE usuarioID = ?";
    public static final String DELETE_ARTICULOS_DEL_USUARIO_QUERY = "DELETE FROM Articulo WHERE usuarioID = ?";
    public static final String SELECT_TODOS_LOS_ARTICULOS_QUERY = "SELECT * FROM Articulo";
    public static final String SELECT_ARTICULOS_POR_JUEGO_QUERY = "SELECT * FROM Articulo WHERE juegoID = ?";
    public static final String SELECT_ARTICULOS_POR_USUARIO_QUERY = "SELECT * FROM Articulo WHERE usuarioID = ?";
    public static final String SELECT_ARTICULO_POR_UUID_QUERY = "SELECT * FROM Articulo WHERE UUID = ?";
    public static final String ARTICULO_NO_ENCONTRADO_CON_UUID = "Articulo no encontrado con UUID: ";
    public static final String ERROR_AL_OBTENER_EL_ARTICULO_CON_UUID = "Error al obtener el articulo con UUID: ";
    public static final String ADD_ARTICULO_QUERY = "INSERT INTO articulos (UUID, titulo, contenido, juegoID, usuarioID, fechaPublicacion) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String ERROR_AL_GUARDAR_EL_ARTICULO = "Error al guardar el articulo: ";
    public static final String ERROR_AL_MODIFICAR_EL_ARTICULO_CON_UUID = "Error al modificar el articulo con UUID: ";
    public static final String ARTICULO_ELIMINAR_ES_NULO = "Articulo a eliminar es nulo";
    public static final String DELETE_ARTICULO_QUERY = "DELETE FROM Articulo WHERE UUID = ?";
    public static final String ERROR_AL_ELIMINAR_EL_ARTICULO = "Error al eliminar el articulo con el UUID: ";
    public static final String SELECT_USUARIO_POR_NOMBRE_QUERY = "SELECT * FROM Usuario WHERE nombre = ?";
    public static final String URL_DB = "urlDB";
    public static final String USER_NAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String DRIVER = "driver";
    public static final String CACHE_PREP_STMTS = "cachePrepStmts";
    public static final String PREP_STMT_CACHE_SIZE = "prepStmtCacheSize";
    public static final String PREP_STMT_CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit";
}
