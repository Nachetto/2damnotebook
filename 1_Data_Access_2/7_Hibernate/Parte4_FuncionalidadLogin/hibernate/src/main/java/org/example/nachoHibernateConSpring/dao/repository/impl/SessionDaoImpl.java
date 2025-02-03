package org.example.nachoHibernateConSpring.dao.repository.impl;
import org.example.nachoHibernateConSpring.common.config.Configuration;
import org.example.nachoHibernateConSpring.dao.model.Credential;
import org.example.nachoHibernateConSpring.dao.model.Session;
import org.example.nachoHibernateConSpring.dao.repository.SessionDao;
import com.google.gson.Gson;

public class SessionDaoImpl implements SessionDao {
    private final String pathSession;
    private Gson gson = new Gson();
    private Session session;

    private final Configuration config;


    public SessionDaoImpl(Configuration config) {
        this.config = config;
        this.pathSession = config.getPathSession();
    }

    @Override
    public int save(Credential credential) {
        //todo save into a json file saving the

    }

    @Override
    public Session load() {
        //todo load the session from the json file

        //if the patient id is null and the doctor id is null, then the type is admin

        //if the patient id is not null and the doctor id is null, then the type is patient

        //if the patient id is null and the doctor id is not null, then the type is doctor

    }
}
