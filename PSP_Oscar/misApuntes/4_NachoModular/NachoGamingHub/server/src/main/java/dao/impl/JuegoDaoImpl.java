package dao.impl;

import dao.JuegoDao;
import dao.common.ConstantesDao;
import dao.common.DBConnectionPool;
import domain.modelo.Juego;
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
public class JuegoDaoImpl implements JuegoDao {
    private final DBConnectionPool db;
    @Inject
    public JuegoDaoImpl(DBConnectionPool db) {
        this.db = db;
    }
    @Override
    public List<Juego> getAll() throws BaseDatosCaidaException{
        try (Connection myConnection = db.getConnection();
             Statement stmt = myConnection.createStatement()) {
            ResultSet rs = stmt.executeQuery(ConstantesDao.SELECT_TODOS_LOS_JUEGOS_QUERY);
            List<Juego> juegos = new ArrayList<>();
            while (rs.next()) {
                Juego resultJuego = new Juego(
                        UUID.fromString(rs.getString("UUID")),
                        rs.getString("titulo"),
                        rs.getString("desarrollador"),
                        rs.getDate("fechaLanzamiento").toLocalDate(),
                        rs.getString("genero"),
                        rs.getString("descripcion")
                );
                juegos.add(resultJuego);
            }
            return juegos;
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_OBTENER_TODOS_LOS_USUARIOS + ex.getMessage());
        }
    }

    @Override
    public Juego get(String uuid) throws BaseDatosCaidaException, NotFoundException, OtraException {
        if (uuid.length() != 32) {
            throw new OtraException(ConstantesDao.INVALIDUUID + uuid);
        }
        try (Connection con = db.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(ConstantesDao.SELECT_JUEGO_POR_UUID_QUERY);
            if (!rs.next()) {
                throw new NotFoundException(ConstantesDao.JUEGO_NO_ENCONTRADO_CON_UUID + uuid);
            }
            return new Juego(
                    UUID.fromString(rs.getString("UUID")),
                    rs.getString("titulo"),
                    rs.getString("desarrollador"),
                    rs.getDate("fechaLanzamiento").toLocalDate(),
                    rs.getString("genero"),
                    rs.getString("descripcion")
            );
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_OBTENER_EL_JUEGO_CON_UUID + uuid + ", " + ex.getMessage());
        }
    }

    @Override
    public Juego save(Juego o) throws BaseDatosCaidaException, OtraException {
        if (o == null) {
            throw new OtraException(ConstantesDao.ES_NULO);
        }
        if (o.getUuid() == null)
            o.setUuid(UUID.randomUUID());

        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(ConstantesDao.ADD_JUEGO_QUERY)) {
            preparedStatement.setString(1, o.getUuid().toString());
            preparedStatement.setString(2, o.getTitulo());
            preparedStatement.setString(3, o.getDesarrollador());
            preparedStatement.setDate(4, Date.valueOf(o.getFechaLanzamiento()));
            preparedStatement.setString(5, o.getGenero());
            preparedStatement.setString(6, o.getDescripcion());
            if (preparedStatement.executeUpdate() != -1) {
                return o;
            } else {
                throw new OtraException(ConstantesDao.ERROR_AL_GUARDAR_EL_USUARIO + o.getTitulo());
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_GUARDAR_EL_USUARIO + ex.getMessage());
        }
    }

    @Override
    public Juego modify(Juego o, Juego o2) throws BaseDatosCaidaException, OtraException {
        if (o == null || o2 == null) {
            throw new OtraException(ConstantesDao.ES_NULO);
        }
        o2.setUuid(o.getUuid());
        if (delete(o.getUuid())) {
            return save(o2);
        } else {
            throw new OtraException(ConstantesDao.ERROR_AL_MODIFICAR_EL_USUARIO_CON_UUID + o.getUuid().toString());
        }
    }


    @Override
    public boolean delete(UUID o) throws OtraException, NotFoundException {
        if (o == null) {
            throw new OtraException(ConstantesDao.CLIENTE_ELIMINAR_ES_NULO);
        }
        try (Connection con = db.getConnection()) {
            con.setAutoCommit(false);
            verifyExistence(o, con);

            try (PreparedStatement statement = con.prepareStatement(ConstantesDao.DELETE_JUEGO_QUERY)) {
                statement.setString(1, o.toString());
                if (statement.executeUpdate() == 1) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                    throw new OtraException(ConstantesDao.ERROR_AL_ELIMINAR_EL_JUEGO + o);
                }
            }

        } catch (SQLException ex) {
            throw new OtraException(ConstantesDao.ERROR_AL_ELIMINAR_EL_USUARIO + ex.getMessage());
        }
    }

    private void verifyExistence(UUID o, Connection con) {
        try (PreparedStatement statement = con.prepareStatement(ConstantesDao.CHECK_USUARIO_UUID_EXISTENCE_QUERY)) {
            statement.setString(1, o.toString());
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new OtraException(ConstantesDao.USUARIO_NO_ENCONTRADO_CON_UUID + o);
            }
        } catch (SQLException ex) {
            throw new OtraException(ConstantesDao.ERROR_AL_ELIMINAR_EL_USUARIO + ex.getMessage());
        }

    }
}
