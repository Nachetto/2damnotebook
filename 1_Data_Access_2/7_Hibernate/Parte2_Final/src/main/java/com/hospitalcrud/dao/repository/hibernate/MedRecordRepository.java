package com.hospitalcrud.dao.repository.hibernate;

import com.hospitalcrud.dao.connection.JPAUtil;
import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.repository.MedRecordDAO;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("hibernate")
@Log4j2
public class MedRecordRepository implements MedRecordDAO {

    private final JPAUtil jpautil;
    private EntityManager em;

    public MedRecordRepository(JPAUtil jpautil) {
        this.jpautil = jpautil;
    }

    @Override
    public List<MedRecord> getAll() {
        List<MedRecord> list;
        try {
            em = jpautil.getEntityManager();
            list = em.createNamedQuery("MedRecord.getAll", MedRecord.class)
                    .getResultList();
        }catch (Exception e) {
            log.error("Error getting all medications", e);
            return List.of();
        }
        finally {
            if (em != null) em.close();
        }
        return list;
    }

    @Override
    public List<MedRecord> get(int patientId) {
        List<MedRecord> list;
        try {
            em = jpautil.getEntityManager();
            list = em.createNamedQuery("MedRecord.get", MedRecord.class)
                    .setParameter("patientId", patientId)
                    .getResultList();
        }catch (Exception e) {
            log.error("Error getting all medications", e);
            return List.of();
        }
        finally {
            if (em != null) em.close();
        }
        return list;
    }

    @Override
    public int save(MedRecord m) {
        int result = 0;
        try {
            em = jpautil.getEntityManager();
            em.getTransaction().begin();
            em.persist(m);
            em.getTransaction().commit();
            result = m.getId();
            return result;
        } catch (Exception e) {
            log.error("Error saving medication: {}", m, e);
            if (em != null) em.getTransaction().rollback();
            return result;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void update(MedRecord m) {
        try {
            em = jpautil.getEntityManager();
            em.getTransaction().begin();
            em.merge(m);
            em.getTransaction().commit();
        } catch (Exception e) {
            log.error("Error updating medication: {}", m, e);
            if (em != null) em.getTransaction().rollback();
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            em = jpautil.getEntityManager();
            em.getTransaction().begin();
            MedRecord m = em.find(MedRecord.class, id);
            em.remove(m);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            log.error("Error deleting medication: {}", id, e);
            if (em != null) em.getTransaction().rollback();
            return false;
        } finally {
            if (em != null) em.close();
        }
    }
}
