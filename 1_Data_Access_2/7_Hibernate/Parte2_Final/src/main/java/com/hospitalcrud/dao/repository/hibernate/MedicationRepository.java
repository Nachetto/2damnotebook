package com.hospitalcrud.dao.repository.hibernate;

import com.hospitalcrud.dao.connection.JPAUtil;
import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.repository.MedicationDAO;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("hibernate")
@Log4j2
public class MedicationRepository implements MedicationDAO {

    private final JPAUtil jpautil;
    private EntityManager em;

    public MedicationRepository(JPAUtil jpautil) {
        this.jpautil = jpautil;
    }


    @Override
    public List<Medication> getAll() {
        List<Medication> list;
        try {
            em = jpautil.getEntityManager();
            list = em.createNamedQuery("Medication.getAll", Medication.class)
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
    public int save(Medication m) {
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
    public void update(Medication m) {
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
            Medication m = em.find(Medication.class, id);
            em.remove(m);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            log.error("Error deleting medication with id: {}", id, e);
            if (em != null) em.getTransaction().rollback();
            return false;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public List<Medication> get(int id) {
        List<Medication> list;
        try {
            em = jpautil.getEntityManager();
            list = em.createNamedQuery("Medication.getMedicationByRecordId", Medication.class)
                    .setParameter("record_id", id)
                    .getResultList();
        }catch (Exception e) {
            log.error("Error getting medication with id: {}", id, e);
            return List.of();
        }
        finally {
            if (em != null) em.close();
        }
        return list;
    }
}
