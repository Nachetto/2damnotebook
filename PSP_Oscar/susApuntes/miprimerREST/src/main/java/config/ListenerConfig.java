package config;

import dao.DBConnectionPool;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener()
public class ListenerConfig implements ServletContextListener {
    Configuration config;

    DBConnectionPool pool;

    @Inject
    public ListenerConfig(Configuration config,DBConnectionPool pool) {
        this.config = config;
        this.pool = pool;

    }

    // Public constructor is required by servlet spec
    public ListenerConfig() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        config.cargar(sce.getServletContext().getResourceAsStream("/WEB-INF/config/config.yaml"));
        pool.cargarPool();
    }

    public void contextDestroyed(ServletContextEvent sce) {
       pool.closePool();
    }




}
