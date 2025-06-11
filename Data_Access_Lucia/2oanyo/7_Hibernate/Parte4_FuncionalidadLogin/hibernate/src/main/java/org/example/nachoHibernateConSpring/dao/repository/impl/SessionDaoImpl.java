package org.example.nachoHibernateConSpring.dao.repository.impl;
import com.google.gson.Gson;
import org.example.nachoHibernateConSpring.common.config.Configuration;
import org.example.nachoHibernateConSpring.dao.model.Session;
import org.example.nachoHibernateConSpring.dao.repository.SessionDao;
import org.example.nachoHibernateConSpring.domain.error.InternalServerErrorException;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@Repository
public class SessionDaoImpl implements SessionDao {

    private final String pathSession;
    private final Gson gson;
    private final Configuration config;

    public SessionDaoImpl(Configuration config) {
        this.config = config;
        gson = new Gson();
        this.pathSession = config.getPathSession();
    }

    @Override
    public int save(Session session) {
        try (FileWriter writer = new FileWriter(pathSession)) {
            gson.toJson(session, writer);
            return 1;
        } catch (Exception e) {
            throw new InternalServerErrorException("Error saving session:  "+  e);
        }
    }

    @Override
    public Session load() {
        try {
            if (!Files.exists(Paths.get(pathSession))) {
                return null; // no session saved
            }
            try (FileReader reader = new FileReader(pathSession)) {
                return gson.fromJson(reader, Session.class);
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("Error loading session: "+e);
        }
    }
}
