package dao.impl;
import dao.ArticuloDao;
import domain.modelo.Articulo;
import dao.common.DBConnectionPool;
import dao.common.ConstantesDao;
import jakarta.inject.Inject;
import model.errores.BaseDatosCaidaException;
import model.errores.NotFoundException;
import model.errores.OtraException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArticuloDaoImpl implements ArticuloDao {
    private final DBConnectionPool db;

    @Inject
    public ArticuloDaoImpl(DBConnectionPool db) {
        this.db = db;
    }

    @Override
    public List<Articulo> getAll() throws BaseDatosCaidaException {
        List<Articulo> articulos = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(ConstantesDao.SELECT_TODOS_LOS_ARTICULOS_QUERY);
            while (rs.next()) {
                articulos.add(new Articulo(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("titulo"),
                        rs.getString("contenido"),
                        UUID.fromString(rs.getString("juegoID")),
                        UUID.fromString(rs.getString("usuarioID")),
                        rs.getDate("fechaPublicacion").toLocalDate()
                ));
            }
        } catch (SQLException ex) {
            throw new BaseDatosCaidaException("Error al obtener todos los artículos: " + ex.getMessage());
        }
        return articulos;
    }

    @Override
    public List<Articulo> getAllByJuego(String uuid) throws BaseDatosCaidaException, OtraException {
        if (uuid == null || uuid.length() != 36) {
            throw new OtraException(ConstantesDao.INVALIDUUID + uuid);
        }

        List<Articulo> articulos = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement pstmt = con.prepareStatement(ConstantesDao.SELECT_ARTICULOS_POR_JUEGO_QUERY)) {
            pstmt.setString(1, uuid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                articulos.add(new Articulo(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("titulo"),
                        rs.getString("contenido"),
                        UUID.fromString(rs.getString("juegoID")),
                        UUID.fromString(rs.getString("usuarioID")),
                        rs.getDate("fechaPublicacion").toLocalDate()
                ));
            }
        } catch (SQLException ex) {
            throw new BaseDatosCaidaException("Error al obtener artículos por juego: " + ex.getMessage());
        }
        return articulos;
    }

    @Override
    public List<Articulo> getAllByUsuario(String uuid) throws BaseDatosCaidaException, OtraException {
        if (uuid == null || uuid.length() != 36) {
            throw new OtraException(ConstantesDao.INVALIDUUID + uuid);
        }

        List<Articulo> articulos = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement pstmt = con.prepareStatement(ConstantesDao.SELECT_ARTICULOS_POR_USUARIO_QUERY)) {
            pstmt.setString(1, uuid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                articulos.add(new Articulo(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("titulo"),
                        rs.getString("contenido"),
                        UUID.fromString(rs.getString("juegoID")),
                        UUID.fromString(rs.getString("usuarioID")),
                        rs.getDate("fechaPublicacion").toLocalDate()
                ));
            }
        } catch (SQLException ex) {
            throw new BaseDatosCaidaException("Error al obtener artículos por usuario: " + ex.getMessage());
        }
        return articulos;
    }

    @Override
    public Articulo get(String uuid) throws BaseDatosCaidaException, NotFoundException, OtraException {
        if (uuid == null || uuid.length() != 36) {
            throw new OtraException(ConstantesDao.INVALIDUUID + uuid);
        }
        try (Connection con = db.getConnection();
             PreparedStatement pstmt = con.prepareStatement(ConstantesDao.SELECT_ARTICULO_POR_UUID_QUERY)) {
            pstmt.setString(1, uuid);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException(ConstantesDao.ARTICULO_NO_ENCONTRADO_CON_UUID + uuid);
            }
            return new Articulo(
                    UUID.fromString(rs.getString("uuid")),
                    rs.getString("titulo"),
                    rs.getString("contenido"),
                    UUID.fromString(rs.getString("juegoID")),
                    UUID.fromString(rs.getString("usuarioID")),
                    rs.getDate("fechaPublicacion").toLocalDate()
            );
        } catch (SQLException ex) {
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_OBTENER_EL_ARTICULO_CON_UUID + uuid + ", " + ex.getMessage());
        }
    }

    @Override
    public Articulo save(Articulo o) throws BaseDatosCaidaException, OtraException {
        if (o == null) {
            throw new OtraException(ConstantesDao.ES_NULO);
        }
        if (o.getUuid() == null) {
            o.setUuid(UUID.randomUUID());
        }

        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(ConstantesDao.ADD_ARTICULO_QUERY)) {
            // Set parameters and execute
            if (preparedStatement.executeUpdate() != -1) {
                return o;
            } else {
                throw new OtraException(ConstantesDao.ERROR_AL_GUARDAR_EL_ARTICULO + o.getUuid());
            }
        } catch (SQLException ex) {
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_GUARDAR_EL_ARTICULO + ex.getMessage());
        }
    }

    @Override
    public Articulo modify(Articulo initialArticulo, Articulo modifiedArticulo) throws BaseDatosCaidaException, OtraException {
        if (initialArticulo == null || modifiedArticulo == null) {
            throw new OtraException(ConstantesDao.ES_NULO);
        }
        modifiedArticulo.setUuid(initialArticulo.getUuid());
        if (delete(initialArticulo.getUuid().toString())) {
            return save(modifiedArticulo);
        } else {
            throw new OtraException(ConstantesDao.ERROR_AL_MODIFICAR_EL_ARTICULO_CON_UUID + initialArticulo.getUuid().toString());
        }
    }

    @Override
    public boolean delete(String uuid) throws OtraException, NotFoundException {
        if (uuid == null || uuid.length() != 36) {
            throw new OtraException(ConstantesDao.ARTICULO_ELIMINAR_ES_NULO);
        }

        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement(ConstantesDao.DELETE_ARTICULO_QUERY)) {
            statement.setString(1, uuid);
            if (statement.executeUpdate() == 1) {
                return true;
            } else {
                throw new OtraException(ConstantesDao.ERROR_AL_ELIMINAR_EL_ARTICULO + uuid);
            }
        } catch (SQLException ex) {
            throw new OtraException(ConstantesDao.ERROR_AL_ELIMINAR_EL_ARTICULO + ex.getMessage());
        }
    }
}
