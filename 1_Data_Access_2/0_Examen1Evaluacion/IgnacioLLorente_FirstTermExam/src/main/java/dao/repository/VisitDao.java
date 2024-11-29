package dao.repository;

import common.Constants;
import common.configuration.Configuration;
import dao.model.AnimalVisitsXml;
import dao.model.Visit;
import dao.utilities.DbConnection;
import domain.error.InternalServerErrorException;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class VisitDao {
    private static final Logger log = LoggerFactory.getLogger(VisitDao.class);
    private final Configuration config;
    private final DbConnection pool;

    @Inject
    public VisitDao(DbConnection pool) {
        this.pool = pool;
        this.config = Configuration.getInstance();
    }

    public int loadFromXmlToDatabase() {
        try (Connection con = pool.getHikariDataSource().getConnection();
             PreparedStatement addStatement = con.prepareStatement(Constants.ADD_VISIT_QUERY)
        ) {
            List<Visit> visitsFromXml = readXML();
            con.setAutoCommit(false);
            for (Visit visit : visitsFromXml) {
                try {
                    addStatement.setInt(1, visit.getAnimalId());
                    addStatement.setInt(2, visit.getVisitorId());
                    addStatement.setDate(3, Date.valueOf(visit.getDate()));
                    if (addStatement.executeUpdate() == 0)
                        throw new InternalServerErrorException("Error while trying to add the visit " + visit);

                } catch (SQLException e) {
                    con.rollback();
                }
            }
            con.commit();
            return 1;

        } catch (SQLException e) {
            log.info("There was an error while with the Mysql database: " + e.getMessage());
        } catch (RuntimeException e) {
            log.info("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    private List<Visit> readXML() {
        File file = new File(config.getPathVisitsXML());
        if (!file.exists()) {

            throw new InternalServerErrorException("Error leyendo el archivo: " + file.getName());
        }
        try {
            JAXBContext context = JAXBContext.newInstance(AnimalVisitsXml.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return ((AnimalVisitsXml) unmarshaller.unmarshal(file)).getVisits();
        } catch (JAXBException e) {
            throw new InternalServerErrorException("Error while reading the xml, see more details:\n" +
                    e.getMessage());
        }
    }

}

