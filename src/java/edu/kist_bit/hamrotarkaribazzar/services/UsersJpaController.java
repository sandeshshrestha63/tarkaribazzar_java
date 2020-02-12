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
import edu.kist_bit.hamrotarkaribazzar.entity.Orders;
import edu.kist_bit.hamrotarkaribazzar.entity.Users;
import edu.kist_bit.hamrotarkaribazzar.services.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author Administrator
 */
public class UsersJpaController implements Serializable {

    public UsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Users users) {
        if (users.getOrdersList() == null) {
            users.setOrdersList(new ArrayList<Orders>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Orders> attachedOrdersList = new ArrayList<Orders>();
            for (Orders ordersListOrdersToAttach : users.getOrdersList()) {
                ordersListOrdersToAttach = em.getReference(ordersListOrdersToAttach.getClass(), ordersListOrdersToAttach.getId());
                attachedOrdersList.add(ordersListOrdersToAttach);
            }
            users.setOrdersList(attachedOrdersList);
            em.persist(users);
            for (Orders ordersListOrders : users.getOrdersList()) {
                Users oldUserIdOfOrdersListOrders = ordersListOrders.getUserId();
                ordersListOrders.setUserId(users);
                ordersListOrders = em.merge(ordersListOrders);
                if (oldUserIdOfOrdersListOrders != null) {
                    oldUserIdOfOrdersListOrders.getOrdersList().remove(ordersListOrders);
                    oldUserIdOfOrdersListOrders = em.merge(oldUserIdOfOrdersListOrders);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Users users) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getId());
            List<Orders> ordersListOld = persistentUsers.getOrdersList();
            List<Orders> ordersListNew = users.getOrdersList();
            List<Orders> attachedOrdersListNew = new ArrayList<Orders>();
            for (Orders ordersListNewOrdersToAttach : ordersListNew) {
                ordersListNewOrdersToAttach = em.getReference(ordersListNewOrdersToAttach.getClass(), ordersListNewOrdersToAttach.getId());
                attachedOrdersListNew.add(ordersListNewOrdersToAttach);
            }
            ordersListNew = attachedOrdersListNew;
            users.setOrdersList(ordersListNew);
            users = em.merge(users);
            for (Orders ordersListOldOrders : ordersListOld) {
                if (!ordersListNew.contains(ordersListOldOrders)) {
                    ordersListOldOrders.setUserId(null);
                    ordersListOldOrders = em.merge(ordersListOldOrders);
                }
            }
            for (Orders ordersListNewOrders : ordersListNew) {
                if (!ordersListOld.contains(ordersListNewOrders)) {
                    Users oldUserIdOfOrdersListNewOrders = ordersListNewOrders.getUserId();
                    ordersListNewOrders.setUserId(users);
                    ordersListNewOrders = em.merge(ordersListNewOrders);
                    if (oldUserIdOfOrdersListNewOrders != null && !oldUserIdOfOrdersListNewOrders.equals(users)) {
                        oldUserIdOfOrdersListNewOrders.getOrdersList().remove(ordersListNewOrders);
                        oldUserIdOfOrdersListNewOrders = em.merge(oldUserIdOfOrdersListNewOrders);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = users.getId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            List<Orders> ordersList = users.getOrdersList();
            for (Orders ordersListOrders : ordersList) {
                ordersListOrders.setUserId(null);
                ordersListOrders = em.merge(ordersListOrders);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Users checkLogin(String username) throws NonexistentEntityException{
        EntityManager em = getEntityManager();
        Users results = null;
        try{
            results = (Users) em.createNamedQuery("Users.findByUsername").setParameter("username", username).getSingleResult();
        }catch(NullPointerException | NoResultException e){
            throw new NonexistentEntityException("the users with username"+username+"no longer eixst");
        }
             
        return results;
    }
}
