/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist_bit.hamrotarkaribazzar.services;

import edu.kist_bit.hamrotarkaribazzar.entity.DeliveryAddress;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.kist_bit.hamrotarkaribazzar.entity.Orders;
import edu.kist_bit.hamrotarkaribazzar.services.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Administrator
 */
public class DeliveryAddressJpaController implements Serializable {

    public DeliveryAddressJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DeliveryAddress deliveryAddress) {
        if (deliveryAddress.getOrdersList() == null) {
            deliveryAddress.setOrdersList(new ArrayList<Orders>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Orders> attachedOrdersList = new ArrayList<Orders>();
            for (Orders ordersListOrdersToAttach : deliveryAddress.getOrdersList()) {
                ordersListOrdersToAttach = em.getReference(ordersListOrdersToAttach.getClass(), ordersListOrdersToAttach.getId());
                attachedOrdersList.add(ordersListOrdersToAttach);
            }
            deliveryAddress.setOrdersList(attachedOrdersList);
            em.persist(deliveryAddress);
            for (Orders ordersListOrders : deliveryAddress.getOrdersList()) {
                DeliveryAddress oldDeliveryAddIdOfOrdersListOrders = ordersListOrders.getDeliveryAddId();
                ordersListOrders.setDeliveryAddId(deliveryAddress);
                ordersListOrders = em.merge(ordersListOrders);
                if (oldDeliveryAddIdOfOrdersListOrders != null) {
                    oldDeliveryAddIdOfOrdersListOrders.getOrdersList().remove(ordersListOrders);
                    oldDeliveryAddIdOfOrdersListOrders = em.merge(oldDeliveryAddIdOfOrdersListOrders);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DeliveryAddress deliveryAddress) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DeliveryAddress persistentDeliveryAddress = em.find(DeliveryAddress.class, deliveryAddress.getId());
            List<Orders> ordersListOld = persistentDeliveryAddress.getOrdersList();
            List<Orders> ordersListNew = deliveryAddress.getOrdersList();
            List<Orders> attachedOrdersListNew = new ArrayList<Orders>();
            for (Orders ordersListNewOrdersToAttach : ordersListNew) {
                ordersListNewOrdersToAttach = em.getReference(ordersListNewOrdersToAttach.getClass(), ordersListNewOrdersToAttach.getId());
                attachedOrdersListNew.add(ordersListNewOrdersToAttach);
            }
            ordersListNew = attachedOrdersListNew;
            deliveryAddress.setOrdersList(ordersListNew);
            deliveryAddress = em.merge(deliveryAddress);
            for (Orders ordersListOldOrders : ordersListOld) {
                if (!ordersListNew.contains(ordersListOldOrders)) {
                    ordersListOldOrders.setDeliveryAddId(null);
                    ordersListOldOrders = em.merge(ordersListOldOrders);
                }
            }
            for (Orders ordersListNewOrders : ordersListNew) {
                if (!ordersListOld.contains(ordersListNewOrders)) {
                    DeliveryAddress oldDeliveryAddIdOfOrdersListNewOrders = ordersListNewOrders.getDeliveryAddId();
                    ordersListNewOrders.setDeliveryAddId(deliveryAddress);
                    ordersListNewOrders = em.merge(ordersListNewOrders);
                    if (oldDeliveryAddIdOfOrdersListNewOrders != null && !oldDeliveryAddIdOfOrdersListNewOrders.equals(deliveryAddress)) {
                        oldDeliveryAddIdOfOrdersListNewOrders.getOrdersList().remove(ordersListNewOrders);
                        oldDeliveryAddIdOfOrdersListNewOrders = em.merge(oldDeliveryAddIdOfOrdersListNewOrders);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = deliveryAddress.getId();
                if (findDeliveryAddress(id) == null) {
                    throw new NonexistentEntityException("The deliveryAddress with id " + id + " no longer exists.");
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
            DeliveryAddress deliveryAddress;
            try {
                deliveryAddress = em.getReference(DeliveryAddress.class, id);
                deliveryAddress.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The deliveryAddress with id " + id + " no longer exists.", enfe);
            }
            List<Orders> ordersList = deliveryAddress.getOrdersList();
            for (Orders ordersListOrders : ordersList) {
                ordersListOrders.setDeliveryAddId(null);
                ordersListOrders = em.merge(ordersListOrders);
            }
            em.remove(deliveryAddress);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DeliveryAddress> findDeliveryAddressEntities() {
        return findDeliveryAddressEntities(true, -1, -1);
    }

    public List<DeliveryAddress> findDeliveryAddressEntities(int maxResults, int firstResult) {
        return findDeliveryAddressEntities(false, maxResults, firstResult);
    }

    private List<DeliveryAddress> findDeliveryAddressEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DeliveryAddress.class));
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

    public DeliveryAddress findDeliveryAddress(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DeliveryAddress.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeliveryAddressCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DeliveryAddress> rt = cq.from(DeliveryAddress.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
