/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist_bit.hamrotarkaribazzar.services;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.kist_bit.hamrotarkaribazzar.entity.Users;
import edu.kist_bit.hamrotarkaribazzar.entity.DeliveryAddress;
import edu.kist_bit.hamrotarkaribazzar.entity.Orderitems;
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
public class OrdersJpaController implements Serializable {

    public OrdersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orders orders) {
        if (orders.getOrderitemsList() == null) {
            orders.setOrderitemsList(new ArrayList<Orderitems>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users userId = orders.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                orders.setUserId(userId);
            }
            DeliveryAddress deliveryAddId = orders.getDeliveryAddId();
            if (deliveryAddId != null) {
                deliveryAddId = em.getReference(deliveryAddId.getClass(), deliveryAddId.getId());
                orders.setDeliveryAddId(deliveryAddId);
            }
            List<Orderitems> attachedOrderitemsList = new ArrayList<Orderitems>();
            for (Orderitems orderitemsListOrderitemsToAttach : orders.getOrderitemsList()) {
                orderitemsListOrderitemsToAttach = em.getReference(orderitemsListOrderitemsToAttach.getClass(), orderitemsListOrderitemsToAttach.getId());
                attachedOrderitemsList.add(orderitemsListOrderitemsToAttach);
            }
            orders.setOrderitemsList(attachedOrderitemsList);
            em.persist(orders);
            if (userId != null) {
                userId.getOrdersList().add(orders);
                userId = em.merge(userId);
            }
            if (deliveryAddId != null) {
                deliveryAddId.getOrdersList().add(orders);
                deliveryAddId = em.merge(deliveryAddId);
            }
            for (Orderitems orderitemsListOrderitems : orders.getOrderitemsList()) {
                Orders oldOrderIdOfOrderitemsListOrderitems = orderitemsListOrderitems.getOrderId();
                orderitemsListOrderitems.setOrderId(orders);
                orderitemsListOrderitems = em.merge(orderitemsListOrderitems);
                if (oldOrderIdOfOrderitemsListOrderitems != null) {
                    oldOrderIdOfOrderitemsListOrderitems.getOrderitemsList().remove(orderitemsListOrderitems);
                    oldOrderIdOfOrderitemsListOrderitems = em.merge(oldOrderIdOfOrderitemsListOrderitems);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orders orders) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orders persistentOrders = em.find(Orders.class, orders.getId());
            Users userIdOld = persistentOrders.getUserId();
            Users userIdNew = orders.getUserId();
            DeliveryAddress deliveryAddIdOld = persistentOrders.getDeliveryAddId();
            DeliveryAddress deliveryAddIdNew = orders.getDeliveryAddId();
            List<Orderitems> orderitemsListOld = persistentOrders.getOrderitemsList();
            List<Orderitems> orderitemsListNew = orders.getOrderitemsList();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                orders.setUserId(userIdNew);
            }
            if (deliveryAddIdNew != null) {
                deliveryAddIdNew = em.getReference(deliveryAddIdNew.getClass(), deliveryAddIdNew.getId());
                orders.setDeliveryAddId(deliveryAddIdNew);
            }
            List<Orderitems> attachedOrderitemsListNew = new ArrayList<Orderitems>();
            for (Orderitems orderitemsListNewOrderitemsToAttach : orderitemsListNew) {
                orderitemsListNewOrderitemsToAttach = em.getReference(orderitemsListNewOrderitemsToAttach.getClass(), orderitemsListNewOrderitemsToAttach.getId());
                attachedOrderitemsListNew.add(orderitemsListNewOrderitemsToAttach);
            }
            orderitemsListNew = attachedOrderitemsListNew;
            orders.setOrderitemsList(orderitemsListNew);
            orders = em.merge(orders);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getOrdersList().remove(orders);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getOrdersList().add(orders);
                userIdNew = em.merge(userIdNew);
            }
            if (deliveryAddIdOld != null && !deliveryAddIdOld.equals(deliveryAddIdNew)) {
                deliveryAddIdOld.getOrdersList().remove(orders);
                deliveryAddIdOld = em.merge(deliveryAddIdOld);
            }
            if (deliveryAddIdNew != null && !deliveryAddIdNew.equals(deliveryAddIdOld)) {
                deliveryAddIdNew.getOrdersList().add(orders);
                deliveryAddIdNew = em.merge(deliveryAddIdNew);
            }
            for (Orderitems orderitemsListOldOrderitems : orderitemsListOld) {
                if (!orderitemsListNew.contains(orderitemsListOldOrderitems)) {
                    orderitemsListOldOrderitems.setOrderId(null);
                    orderitemsListOldOrderitems = em.merge(orderitemsListOldOrderitems);
                }
            }
            for (Orderitems orderitemsListNewOrderitems : orderitemsListNew) {
                if (!orderitemsListOld.contains(orderitemsListNewOrderitems)) {
                    Orders oldOrderIdOfOrderitemsListNewOrderitems = orderitemsListNewOrderitems.getOrderId();
                    orderitemsListNewOrderitems.setOrderId(orders);
                    orderitemsListNewOrderitems = em.merge(orderitemsListNewOrderitems);
                    if (oldOrderIdOfOrderitemsListNewOrderitems != null && !oldOrderIdOfOrderitemsListNewOrderitems.equals(orders)) {
                        oldOrderIdOfOrderitemsListNewOrderitems.getOrderitemsList().remove(orderitemsListNewOrderitems);
                        oldOrderIdOfOrderitemsListNewOrderitems = em.merge(oldOrderIdOfOrderitemsListNewOrderitems);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orders.getId();
                if (findOrders(id) == null) {
                    throw new NonexistentEntityException("The orders with id " + id + " no longer exists.");
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
            Orders orders;
            try {
                orders = em.getReference(Orders.class, id);
                orders.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orders with id " + id + " no longer exists.", enfe);
            }
            Users userId = orders.getUserId();
            if (userId != null) {
                userId.getOrdersList().remove(orders);
                userId = em.merge(userId);
            }
            DeliveryAddress deliveryAddId = orders.getDeliveryAddId();
            if (deliveryAddId != null) {
                deliveryAddId.getOrdersList().remove(orders);
                deliveryAddId = em.merge(deliveryAddId);
            }
            List<Orderitems> orderitemsList = orders.getOrderitemsList();
            for (Orderitems orderitemsListOrderitems : orderitemsList) {
                orderitemsListOrderitems.setOrderId(null);
                orderitemsListOrderitems = em.merge(orderitemsListOrderitems);
            }
            em.remove(orders);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orders> findOrdersEntities() {
        return findOrdersEntities(true, -1, -1);
    }

    public List<Orders> findOrdersEntities(int maxResults, int firstResult) {
        return findOrdersEntities(false, maxResults, firstResult);
    }

    private List<Orders> findOrdersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orders.class));
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

    public Orders findOrders(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orders.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orders> rt = cq.from(Orders.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
