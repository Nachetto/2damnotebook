package ignacio.llorente.nachoHibernateConSpring.dao.repository;

import ignacio.llorente.nachoHibernateConSpring.dao.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CredentialDAO extends JpaRepository<Credential,Integer> {
}
