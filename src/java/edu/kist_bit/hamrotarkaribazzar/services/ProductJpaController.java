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
import edu.kist_bit.hamrotarkaribazzar.entity.Category;
import edu.kist_bit.hamrotarkaribazzar.entity.Orderitems;
import edu.kist_bit.hamrotarkaribazzar.entity.Product;
import java.util.ArrayList;
import java.util.List;
import edu.kist_bit.hamrotarkaribazzar.entity.Stock;
import edu.kist_bit.hamrotarkaribazzar.services.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author Administrator
 */
public class ProductJpaController implements Serializable {

    public ProductJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) {
        if (product.getOrderitemsList() == null) {
            product.setOrderitemsList(new ArrayList<Orderitems>());
        }
        if (product.getStockList() == null) {
            product.setStockList(new ArrayList<Stock>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category categoryId = product.getCategoryId();
            if (categoryId != null) {
                categoryId = em.getReference(categoryId.getClass(), categoryId.getId());
                product.setCategoryId(categoryId);
            }
            List<Orderitems> attachedOrderitemsList = new ArrayList<Orderitems>();
            for (Orderitems orderitemsListOrderitemsToAttach : product.getOrderitemsList()) {
                orderitemsListOrderitemsToAttach = em.getReference(orderitemsListOrderitemsToAttach.getClass(), orderitemsListOrderitemsToAttach.getId());
                attachedOrderitemsList.add(orderitemsListOrderitemsToAttach);
            }
            product.setOrderitemsList(attachedOrderitemsList);
            List<Stock> attachedStockList = new ArrayList<Stock>();
            for (Stock stockListStockToAttach : product.getStockList()) {
                stockListStockToAttach = em.getReference(stockListStockToAttach.getClass(), stockListStockToAttach.getId());
                attachedStockList.add(stockListStockToAttach);
            }
            product.setStockList(attachedStockList);
            em.persist(product);
            if (categoryId != null) {
                categoryId.getProductList().add(product);
                categoryId = em.merge(categoryId);
            }
            for (Orderitems orderitemsListOrderitems : product.getOrderitemsList()) {
                Product oldProductIdOfOrderitemsListOrderitems = orderitemsListOrderitems.getProductId();
                orderitemsListOrderitems.setProductId(product);
                orderitemsListOrderitems = em.merge(orderitemsListOrderitems);
                if (oldProductIdOfOrderitemsListOrderitems != null) {
                    oldProductIdOfOrderitemsListOrderitems.getOrderitemsList().remove(orderitemsListOrderitems);
                    oldProductIdOfOrderitemsListOrderitems = em.merge(oldProductIdOfOrderitemsListOrderitems);
                }
            }
            for (Stock stockListStock : product.getStockList()) {
                Product oldProductIdOfStockListStock = stockListStock.getProductId();
                stockListStock.setProductId(product);
                stockListStock = em.merge(stockListStock);
                if (oldProductIdOfStockListStock != null) {
                    oldProductIdOfStockListStock.getStockList().remove(stockListStock);
                    oldProductIdOfStockListStock = em.merge(oldProductIdOfStockListStock);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getId());
            Category categoryIdOld = persistentProduct.getCategoryId();
            Category categoryIdNew = product.getCategoryId();
            List<Orderitems> orderitemsListOld = persistentProduct.getOrderitemsList();
            List<Orderitems> orderitemsListNew = product.getOrderitemsList();
            List<Stock> stockListOld = persistentProduct.getStockList();
            List<Stock> stockListNew = product.getStockList();
            if (categoryIdNew != null) {
                categoryIdNew = em.getReference(categoryIdNew.getClass(), categoryIdNew.getId());
                product.setCategoryId(categoryIdNew);
            }
            List<Orderitems> attachedOrderitemsListNew = new ArrayList<Orderitems>();
            for (Orderitems orderitemsListNewOrderitemsToAttach : orderitemsListNew) {
                orderitemsListNewOrderitemsToAttach = em.getReference(orderitemsListNewOrderitemsToAttach.getClass(), orderitemsListNewOrderitemsToAttach.getId());
                attachedOrderitemsListNew.add(orderitemsListNewOrderitemsToAttach);
            }
            orderitemsListNew = attachedOrderitemsListNew;
            product.setOrderitemsList(orderitemsListNew);
            List<Stock> attachedStockListNew = new ArrayList<Stock>();
            for (Stock stockListNewStockToAttach : stockListNew) {
                stockListNewStockToAttach = em.getReference(stockListNewStockToAttach.getClass(), stockListNewStockToAttach.getId());
                attachedStockListNew.add(stockListNewStockToAttach);
            }
            stockListNew = attachedStockListNew;
            product.setStockList(stockListNew);
            product = em.merge(product);
            if (categoryIdOld != null && !categoryIdOld.equals(categoryIdNew)) {
                categoryIdOld.getProductList().remove(product);
                categoryIdOld = em.merge(categoryIdOld);
            }
            if (categoryIdNew != null && !categoryIdNew.equals(categoryIdOld)) {
                categoryIdNew.getProductList().add(product);
                categoryIdNew = em.merge(categoryIdNew);
            }
            for (Orderitems orderitemsListOldOrderitems : orderitemsListOld) {
                if (!orderitemsListNew.contains(orderitemsListOldOrderitems)) {
                    orderitemsListOldOrderitems.setProductId(null);
                    orderitemsListOldOrderitems = em.merge(orderitemsListOldOrderitems);
                }
            }
            for (Orderitems orderitemsListNewOrderitems : orderitemsListNew) {
                if (!orderitemsListOld.contains(orderitemsListNewOrderitems)) {
                    Product oldProductIdOfOrderitemsListNewOrderitems = orderitemsListNewOrderitems.getProductId();
                    orderitemsListNewOrderitems.setProductId(product);
                    orderitemsListNewOrderitems = em.merge(orderitemsListNewOrderitems);
                    if (oldProductIdOfOrderitemsListNewOrderitems != null && !oldProductIdOfOrderitemsListNewOrderitems.equals(product)) {
                        oldProductIdOfOrderitemsListNewOrderitems.getOrderitemsList().remove(orderitemsListNewOrderitems);
                        oldProductIdOfOrderitemsListNewOrderitems = em.merge(oldProductIdOfOrderitemsListNewOrderitems);
                    }
                }
            }
            for (Stock stockListOldStock : stockListOld) {
                if (!stockListNew.contains(stockListOldStock)) {
                    stockListOldStock.setProductId(null);
                    stockListOldStock = em.merge(stockListOldStock);
                }
            }
            for (Stock stockListNewStock : stockListNew) {
                if (!stockListOld.contains(stockListNewStock)) {
                    Product oldProductIdOfStockListNewStock = stockListNewStock.getProductId();
                    stockListNewStock.setProductId(product);
                    stockListNewStock = em.merge(stockListNewStock);
                    if (oldProductIdOfStockListNewStock != null && !oldProductIdOfStockListNewStock.equals(product)) {
                        oldProductIdOfStockListNewStock.getStockList().remove(stockListNewStock);
                        oldProductIdOfStockListNewStock = em.merge(oldProductIdOfStockListNewStock);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = product.getId();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
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
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            Category categoryId = product.getCategoryId();
            if (categoryId != null) {
                categoryId.getProductList().remove(product);
                categoryId = em.merge(categoryId);
            }
            List<Orderitems> orderitemsList = product.getOrderitemsList();
            for (Orderitems orderitemsListOrderitems : orderitemsList) {
                orderitemsListOrderitems.setProductId(null);
                orderitemsListOrderitems = em.merge(orderitemsListOrderitems);
            }
            List<Stock> stockList = product.getStockList();
            for (Stock stockListStock : stockList) {
                stockListStock.setProductId(null);
                stockListStock = em.merge(stockListStock);
            }
            em.remove(product);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Product> getAllData(int id) throws NonexistentEntityException{
        EntityManager em = getEntityManager();
        List<Product> results = null;
        try{
            results = em.createQuery("SELECT p FROM Product p, Stock s WHERE p.id = s.productId.id AND p.id = :id")
                    .setParameter("id", id).getResultList();
        }catch(NullPointerException | NoResultException e){
            throw new NonexistentEntityException("the users with username" + id + "no longer eixst");
        }
             
        return results;
    }
}
