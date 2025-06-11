package dao;

import model.BasicSupplier;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import java.util.List;

public class BasicSupplierHibDAO {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public BasicSupplierHibDAO(JPAUtil jpaUtil){
        this.jpaUtil =jpaUtil;
    }

    public List<BasicSupplier> getAll() {

        List list = null;
        em = jpaUtil.getEntityManager();

        try {

            //Option 1
            list = em
                    .createNativeQuery( "select * from suppliers", BasicSupplier.class )
                    .getResultList();

            //Option 2
            list = em.createQuery("from BasicSupplier b", BasicSupplier.class).getResultList();

            //Option 3
            list = em
                    .createNamedQuery( "HQL_GET_ALL_SUPPLIERS", BasicSupplier.class )
                    .getResultList();

        }
        catch(PersistenceException e) {
            e.printStackTrace();
        }
        finally {
            if (em != null)  em.close();
        }

        return list;
    }


    public BasicSupplier get(int id) {
        BasicSupplier s=null;
        em = jpaUtil.getEntityManager();

        try {

            s = em.find(BasicSupplier.class,id);

            // Hibernate session uses get instead of find
            //Session session= em.unwrap(Session.class);
            //s = session.get(BasicSupplier.class,id);

        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (em != null)  em.close();
        }
        return s;
    }


    public void add(BasicSupplier supp) {
       //Transaction is needed for persistence to DB
        em = jpaUtil.getEntityManager();

        EntityTransaction tx=null;

        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(supp);
            tx.commit();
//            tx.begin();
//            supp.setStreet("Cambio");
//            tx.commit();  // Street is modified because persist attaches the object
        }
        catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
        finally {
                if (em != null)  em.close();
            }

    }

    public void delete(BasicSupplier supp) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            //Reattach the object before removing
            em.remove(em.merge(supp));
            tx.commit();
        }
        catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
        finally {
            if (em != null)  em.close();
        }
    }

    public void update(BasicSupplier supp) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
//Find to reattach and then modify value can also be used
            em.merge(supp);
            tx.commit();
//            tx.begin();
//            supp.setStreet("Cambio3");
//            tx.commit();  //Does not modify the street because merge does not attatch supp, only returns an attached object
        }
        catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
        finally {
            if (em != null)  em.close();
        }
    }

 }
