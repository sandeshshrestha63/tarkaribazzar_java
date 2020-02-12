/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist_bit.hamrotarkaribazzar.services;

import edu.kist_bit.hamrotarkaribazzar.entity.Orderitems;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.kist_bit.hamrotarkaribazzar.entity.Orders;
import edu.kist_bit.hamrotarkaribazzar.entity.Product;
import edu.kist_bit.hamrotarkaribazzar.services.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Administrator
 */
public class OrderitemsJpaController implements Serializable {

    public OrderitemsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orderitems orderitems) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orders orderId = orderitems.getOrderId();
            if (orderId != null) {
                orderId = em.getReference(orderId.getClass(), orderId.getId());
                orderitems.setOrderId(orderId);
            }
            Product productId = orderitems.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getId());
                orderitems.setProductId(productId);
            }
            em.persist(orderitems);
            if (orderId != null) {
                orderId.getOrderitemsList().add(orderitems);
                orderId = em.merge(orderId);
            }
            if (productId != null) {
                productId.getOrderitemsList().add(orderitems);
                productId = em.merge(productId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orderitems orderitems) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orderitems persistentOrderitems = em.find(Orderitems.class, orderitems.getId());
            Orders orderIdOld = persistentOrderitems.getOrderId();
            Orders orderIdNew = orderitems.getOrderId();
            Product productIdOld = persistentOrderitems.getProductId();
            Product productIdNew = orderitems.getProductId();
            if (orderIdNew != null) {
                orderIdNew = em.getReference(orderIdNew.getClass(), orderIdNew.getId());
                orderitems.setOrderId(orderIdNew);
            }
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
                orderitems.setProductId(productIdNew);
            }
            orderitems = em.merge(orderitems);
            if (orderIdOld != null && !orderIdOld.equals(orderIdNew)) {
                orderIdOld.getOrderitemsList().remove(orderitems);
                orderIdOld = em.merge(orderIdOld);
            }
            if (orderIdNew != null && !orderIdNew.equals(orderIdOld)) {
                orderIdNew.getOrderitemsList().add(orderitems);
                orderIdNew = em.merge(orderIdNew);
            }
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getOrderitemsList().remove(orderitems);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getOrderitemsList().add(orderitems);
                productIdNew = em.merge(productIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orderitems.getId();
                if (findOrderitems(id) == null) {
                    throw new NonexistentEntityException("The orderitems with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orderitems orderitems;
            try {
                orderitems = em.getReference(Orderitems.class, id);
                orderitems.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderitems with id " + id + " no longer exists.", enfe);
            }
            Orders orderId = orderitems.getOrderId();
            if (orderId != null) {
                orderId.getOrderitemsList().remove(orderitems);
                orderId = em.merge(orderId);
            }
            Product productId = orderitems.getProductId();
            if (productId != null) {
                productId.getOrderitemsList().remove(orderitems);
                productId = em.merge(productId);
            }
            em.remove(orderitems);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orderitems> findOrderitemsEntities() {
        return findOrderitemsEntities(true, -1, -1);
    }

    public List<Orderitems> findOrderitemsEntities(int maxResults, int firstResult) {
        return findOrderitemsEntities(false, maxResults, firstResult);
    }

    private List<Orderitems> findOrderitemsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orderitems.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Orderitems findOrderitems(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orderitems.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderitemsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orderitems> rt = cq.from(Orderitems.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
