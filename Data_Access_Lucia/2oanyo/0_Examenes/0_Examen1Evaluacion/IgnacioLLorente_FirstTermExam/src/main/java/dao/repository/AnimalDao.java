package dao.repository;

import common.Constants;
import common.configuration.Configuration;
import dao.model.Animal;
import dao.utilities.DbConnection;
import domain.error.InternalServerErrorException;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalDao {
    private final DbConnection pool;

    @Inject
    public AnimalDao(DbConnection pool) {
        this.pool = pool;
    }

    public int delete(Animal a, boolean confirmation) {
        if (confirmation) {//this animal has associated visits, delete them first
            try (Connection connection= pool.getHikariDataSource().getConnection();
                PreparedStatement stmn1 = connection.prepareStatement(Constants.DELETE_ANIMAL_VISITS_QUERY);
                PreparedStatement stmn2 = connection.prepareStatement(Constants.DELETE_ANIMALS_QUERY)
            ){
                connection.setAutoCommit(false);
                stmn1.setInt(1,a.getId());
                stmn1.executeUpdate();
                stmn2.setInt(1,a.getId());
                stmn2.executeUpdate();
                connection.commit();
            }catch (SQLException e){
                throw new InternalServerErrorException("error while deleting animals with associated visits: \n"+e.getMessage());
            }
        }else {//this animal does not have associated visits
            try(Connection con = pool.getHikariDataSource().getConnection();
                PreparedStatement deleteAnimalStatement = con.prepareStatement(Constants.DELETE_ANIMALS_QUERY)
            ){
                deleteAnimalStatement.setInt(1,a.getId());
                return deleteAnimalStatement.executeUpdate();
            }catch (SQLException e){
                throw new InternalServerErrorException("");
            }
        }
        return -1;
    }

    public boolean hasAssociatedVisits(Animal a) {
        try (Connection con = pool.getHikariDataSource().getConnection();
             PreparedStatement visitsStatement= con.prepareStatement(Constants.CHECK_VISITS_QUERY)
        ) {
            visitsStatement.setInt(1,a.getId());
            ResultSet resultSet = visitsStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new InternalServerErrorException("There was an error while trying to check if the animal has associated visits, see: \n"
                    + e.getMessage());
        }
    }

    public Animal getFromUsername(String username) {
        try (Connection con = pool.getHikariDataSource().getConnection();
             PreparedStatement getAnimalStatement = con.prepareStatement(Constants.GET_ANIMAL_FROM_USERNAME_QUERY)
        ) {
            getAnimalStatement.setString(1, username);
            ResultSet rs = getAnimalStatement.executeQuery();
            rs.next();
            return new Animal(
                    rs.getInt("Animal_ID"),
                    rs.getString("Name"),
                    rs.getString("Species"),
                    rs.getInt("Age"),
                    rs.getInt("Habitat_ID")
            );
        } catch (SQLException e) {
            throw new InternalServerErrorException("Database error, see: \n" + e.getMessage());
        }
    }
}
