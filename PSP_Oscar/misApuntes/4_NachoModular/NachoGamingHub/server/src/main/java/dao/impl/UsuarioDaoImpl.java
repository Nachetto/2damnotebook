package dao.impl;

import dao.UsuarioDao;
import dao.common.ConstantesDao;
import dao.common.DBConnectionPool;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import domain.modelo.Usuario;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.errores.BaseDatosCaidaException;
import model.errores.NotFoundException;
import model.errores.OtraException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
public class UsuarioDaoImpl implements UsuarioDao {
    private final DBConnectionPool db;

    @Inject
    public UsuarioDaoImpl(DBConnectionPool db) {
        this.db = db;
    }

    public  List<Usuario> getAll() throws BaseDatosCaidaException, NotFoundException {
        try (Connection myConnection = db.getConnection();
             Statement stmt = myConnection.createStatement()) {
            ResultSet rs = stmt.executeQuery(ConstantesDao.SELECT_TODOS_LOS_USUARIOS_QUERY);

            List<Usuario> usuarios = new ArrayList<>();

            while (rs.next()) {
                Usuario resultUsuario = new Usuario(
                        UUID.fromString(rs.getString("UUID")),
                        rs.getString("nombre"),
                        rs.getString("correoElectronico"),
                        rs.getString("contrasena"),
                        rs.getDate("fechaNacimiento").toLocalDate()
                );
                usuarios.add(resultUsuario);
            }
            return usuarios;
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_OBTENER_TODOS_LOS_USUARIOS + ex.getMessage());
        }
    }

    public Usuario get(String uuid) throws BaseDatosCaidaException, NotFoundException, OtraException {
        if (uuid.length() != 32) {
            throw new OtraException(ConstantesDao.INVALIDUUID + uuid);
        }
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(ConstantesDao.SELECT_USUARIO_POR_UUID_QUERY)) {
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException(ConstantesDao.USUARIO_NO_ENCONTRADO_CON_UUID + uuid);
            }
            return new Usuario(
                    UUID.fromString(rs.getString("UUID")),
                    rs.getString("nombre"),
                    rs.getString("correoElectronico"),
                    rs.getString("contrasena"),
                    rs.getDate("fechaNacimiento").toLocalDate()
            );
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_OBTENER_EL_USUARIO_CON_UUID + uuid + ", " + ex.getMessage());
        }
    }

    @Override
    public Usuario save(Usuario c) throws BaseDatosCaidaException, OtraException {
        if (c == null) {
            throw new OtraException(ConstantesDao.ES_NULO);
        }
        if (c.getUuid() == null)
            c.setUuid(UUID.randomUUID());

        try(Connection con = db.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(ConstantesDao.ADD_USUARIO_QUERY))
        {
            preparedStatement.setString(1, c.getUuid().toString());
            preparedStatement.setString(2, c.getNombre());
            preparedStatement.setString(4, c.getCorreoElectronico());
            preparedStatement.setString(5, c.getContrasena());
            preparedStatement.setDate(6, Date.valueOf(c.getFechaNacimiento()));
            if (preparedStatement.executeUpdate() != -1) {
                return c;
            } else {
                throw new OtraException(ConstantesDao.ERROR_AL_GUARDAR_EL_USUARIO + c.getNombre());
            }

        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_GUARDAR_EL_USUARIO + ex.getMessage());
        }
    }

    @Override
    public Usuario modify(Usuario initialcustomer, Usuario modifiedcustomer) throws BaseDatosCaidaException, OtraException {
        if (initialcustomer == null || modifiedcustomer == null) {
            throw new OtraException(ConstantesDao.ES_NULO);
        }
        modifiedcustomer.setUuid(initialcustomer.getUuid());
        if (delete(initialcustomer.getUuid())) {
            return save(modifiedcustomer);
        } else {
            throw new OtraException(ConstantesDao.ERROR_AL_MODIFICAR_EL_USUARIO_CON_UUID + initialcustomer.getUuid().toString());
        }
    }

    @Override
    public boolean delete(UUID c) throws OtraException, NotFoundException {
        if (c == null) {
            throw new OtraException(ConstantesDao.JUEGO_ELIMINAR_ES_NULO);
        }

        try (Connection con = db.getConnection()) {
            con.setAutoCommit(false);
            verifyExistence(c, con);

            eliminarSuscripcionesDelUsuario(c, con);
            eliminarArticulosDelUsuario(c, con);

            try (PreparedStatement statement = con.prepareStatement(ConstantesDao.DELETE_USUARIO_QUERY)) {
                statement.setString(1, c.toString());
                if (statement.executeUpdate() == 1) {
                    con.commit();
                    return true;
                } else {
                    throw new SQLException("No se pudo eliminar el usuario.");
                }
            }
        } catch (SQLException ex) {
            log.error("Error al eliminar usuario: " + ex.getMessage());
            throw new OtraException(ConstantesDao.ERROR_AL_ELIMINAR_EL_USUARIO + ex.getMessage());
        }
    }

    private void eliminarSuscripcionesDelUsuario(UUID c, Connection con) throws SQLException {
        try (PreparedStatement statement = con.prepareStatement(ConstantesDao.DELETE_SUSCRIPCIONES_DEL_USUARIO_QUERY)) {
            statement.setString(1, c.toString());
            statement.executeUpdate();
        }
    }

    private void eliminarArticulosDelUsuario(UUID c, Connection con) throws SQLException {
        try (PreparedStatement statement = con.prepareStatement(ConstantesDao.DELETE_ARTICULOS_DEL_USUARIO_QUERY)) {
            statement.setString(1, c.toString());
            statement.executeUpdate();
        }
    }

    private void verifyExistence(UUID c, Connection con) throws SQLException, OtraException {
        try (PreparedStatement statement = con.prepareStatement(ConstantesDao.CHECK_USUARIO_UUID_EXISTENCE_QUERY)) {
            statement.setString(1, c.toString());
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new OtraException(ConstantesDao.USUARIO_NO_ENCONTRADO_CON_UUID + c);
            }
        }
    }

    public String authenticate(String user) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(ConstantesDao.SELECT_USUARIO_POR_NOMBRE_QUERY)) {
            preparedStatement.setString(1, user);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getString("contrasena");
            }

            return null;
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_OBTENER_EL_USUARIO_CON_NOMBRE + user + ", " + ex.getMessage());
        }
    }

    public Usuario getFromName(String name) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(ConstantesDao.SELECT_USUARIO_POR_NOMBRE_QUERY)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException(ConstantesDao.USUARIO_NO_ENCONTRADO);
            }
            return new Usuario(
                    UUID.fromString(rs.getString("UUID")),
                    rs.getString("nombre"),
                    rs.getString("correoElectronico"),
                    rs.getString("contrasena"),
                    rs.getDate("fechaNacimiento").toLocalDate()
            );
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_OBTENER_EL_USUARIO_CON_NOMBRE + name + ", " + ex.getMessage());
        }
    }
}
