package dao.impl;

import dao.SuscripcionDao;
import dao.common.ConstantesDao;
import dao.common.DBConnectionPool;
import domain.modelo.Juego;
import domain.modelo.Suscripcion;
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
public class SuscripcionDaoImpl implements SuscripcionDao {
    private final DBConnectionPool db;

    @Inject
    public SuscripcionDaoImpl(DBConnectionPool db) {
        this.db = db;
    }

    @Override
    public List<Suscripcion> getAll() throws BaseDatosCaidaException {
        List<Suscripcion> suscripciones = new ArrayList<>();
        try (Connection myConnection = db.getConnection();
             Statement stmt = myConnection.createStatement()) {
            ResultSet rs = stmt.executeQuery(ConstantesDao.SELECT_TODOS_LOS_SUBSCRIPTORES_QUERY);
            while (rs.next()) {
                suscripciones.add(nuevaSuscripcion(rs));
            }
        } catch (SQLException ex) {
            throw new BaseDatosCaidaException("Error al obtener todas las suscripciones: " + ex.getMessage());
        }
        return suscripciones;
    }

    private Suscripcion nuevaSuscripcion(ResultSet rs) throws SQLException{
        return new Suscripcion(
                UUID.fromString(rs.getString("UUID")),
                UUID.fromString(rs.getString("usuarioID")),
                UUID.fromString(rs.getString("juegoID")),
                rs.getDate("fechaSuscripcion").toLocalDate()
        );
    }


    //listar todas las suscripciones de un usuario
    public List<Suscripcion> getAll(String uuid) throws BaseDatosCaidaException, OtraException {
        if (uuid == null || uuid.length() != 36) { // Un UUID típico tiene 36 caracteres, incluyendo los guiones
            throw new OtraException(ConstantesDao.INVALIDUUID + uuid);
        }

        try (Connection myConnection = db.getConnection();
             PreparedStatement pstmt = myConnection.prepareStatement(ConstantesDao.SELECT_SUBSCRIPCIONES_POR_USUARIO_QUERY)) {
            pstmt.setString(1, uuid); // Establecer el UUID del usuario como parámetro

            ResultSet rs = pstmt.executeQuery();
            List<Suscripcion> suscripciones = new ArrayList<>();
            while (rs.next()) {
                Suscripcion resultSuscripcion = nuevaSuscripcion(rs);
                suscripciones.add(resultSuscripcion);
            }
            return suscripciones;
        } catch (SQLException ex) {
            throw new BaseDatosCaidaException("Error al obtener suscripciones para el usuario " + uuid + ": " + ex.getMessage());
        }
    }


    @Override
    public Suscripcion get(String uuid) throws BaseDatosCaidaException, NotFoundException, OtraException {
        if (uuid == null || uuid.length() != 36) { // Un UUID típico tiene 36 caracteres, incluyendo los guiones
            throw new OtraException(ConstantesDao.INVALIDUUID + uuid);
        }
        try (Connection con = db.getConnection();
             PreparedStatement pstmt = con.prepareStatement(ConstantesDao.SELECT_SUSCRIPCION_POR_UUID_QUERY)) {
            pstmt.setString(1, uuid); // Establecer el UUID como parámetro

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException(ConstantesDao.SUSCRIPCION_NO_ENCONTRADA_CON_UUID + uuid);
            }
            return nuevaSuscripcion(rs);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_OBTENER_LA_SUSCRIPCION_CON_UUID + uuid + ", " + ex.getMessage());
        }
    }


    @Override
    public Suscripcion save(Suscripcion s) throws BaseDatosCaidaException, OtraException {
        if (s == null) {
            throw new OtraException(ConstantesDao.ES_NULO);
        }
        if (s.getUuid() == null)
            s.setUuid(UUID.randomUUID());

        try(Connection con = db.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(ConstantesDao.ADD_SUSCRIPCION_QUERY))
        {
            // Set parameters
            if (preparedStatement.executeUpdate() != -1) {
                return s;
            } else {
                throw new OtraException(ConstantesDao.ERROR_AL_GUARDAR_LA_SUSCRIPCION + s.getUuid());
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_GUARDAR_LA_SUSCRIPCION + ex.getMessage());
        }
    }

    @Override
    public Suscripcion modify(Suscripcion initialSubscription, Suscripcion modifiedSubscription) throws BaseDatosCaidaException, OtraException {
        if (initialSubscription == null || modifiedSubscription == null) {
            throw new OtraException(ConstantesDao.ES_NULO);
        }
        modifiedSubscription.setUuid(initialSubscription.getUuid());
        if (delete(initialSubscription.getUuid().toString())) {
            return save(modifiedSubscription);
        } else {
            throw new OtraException(ConstantesDao.ERROR_AL_MODIFICAR_LA_SUSCRIPCION_CON_UUID + initialSubscription.getUuid().toString());
        }
    }

    @Override
    public boolean delete(String uuid) {
        if (uuid == null) {
            throw new OtraException(ConstantesDao.SUSCRIPCION_ELIMINAR_ES_NULO);
        }

        try (Connection con = db.getConnection()) {
            con.setAutoCommit(false);
            verifyExistence(uuid, con);

            try (PreparedStatement statement = con.prepareStatement(ConstantesDao.DELETE_SUSCRIPCION_QUERY)) {
                statement.setString(1, uuid);
                if (statement.executeUpdate() == 1) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                    throw new OtraException(ConstantesDao.ERROR_AL_ELIMINAR_LA_SUSCRIPCION + uuid);
                }
            }
        } catch (SQLException ex) {
            throw new OtraException(ConstantesDao.ERROR_AL_ELIMINAR_LA_SUSCRIPCION + ex.getMessage());
        }
    }

    private void verifyExistence(String uuid, Connection con) throws SQLException, OtraException {
        try (PreparedStatement statement = con.prepareStatement(ConstantesDao.CHECK_SUSCRIPCION_UUID_EXISTENCE_QUERY)) {
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new OtraException(ConstantesDao.SUSCRIPCION_NO_ENCONTRADA_CON_UUID + uuid);
            }
        }
    }

}
