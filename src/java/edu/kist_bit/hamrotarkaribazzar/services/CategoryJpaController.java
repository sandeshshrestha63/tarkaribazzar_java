/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist_bit.hamrotarkaribazzar.services;

import edu.kist_bit.hamrotarkaribazzar.entity.Category;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.kist_bit.hamrotarkaribazzar.entity.Subcategory;
import java.util.ArrayList;
import java.util.List;
import edu.kist_bit.hamrotarkaribazzar.entity.ConsumableItems;
import edu.kist_bit.hamrotarkaribazzar.services.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Administrator
 */
public class CategoryJpaController implements Serializable {

    public CategoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Category category) {
        if (category.getSubcategoryList() == null) {
            category.setSubcategoryList(new ArrayList<Subcategory>());
        }
        if (category.getConsumableItemsList() == null) {
            category.setConsumableItemsList(new ArrayList<ConsumableItems>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Subcategory> attachedSubcategoryList = new ArrayList<Subcategory>();
            for (Subcategory subcategoryListSubcategoryToAttach : category.getSubcategoryList()) {
                subcategoryListSubcategoryToAttach = em.getReference(subcategoryListSubcategoryToAttach.getClass(), subcategoryListSubcategoryToAttach.getId());
                attachedSubcategoryList.add(subcategoryListSubcategoryToAttach);
            }
            category.setSubcategoryList(attachedSubcategoryList);
            List<ConsumableItems> attachedConsumableItemsList = new ArrayList<ConsumableItems>();
            for (ConsumableItems consumableItemsListConsumableItemsToAttach : category.getConsumableItemsList()) {
                consumableItemsListConsumableItemsToAttach = em.getReference(consumableItemsListConsumableItemsToAttach.getClass(), consumableItemsListConsumableItemsToAttach.getId());
                attachedConsumableItemsList.add(consumableItemsListConsumableItemsToAttach);
            }
            category.setConsumableItemsList(attachedConsumableItemsList);
            em.persist(category);
            for (Subcategory subcategoryListSubcategory : category.getSubcategoryList()) {
                Category oldCategoryIdOfSubcategoryListSubcategory = subcategoryListSubcategory.getCategoryId();
                subcategoryListSubcategory.setCategoryId(category);
                subcategoryListSubcategory = em.merge(subcategoryListSubcategory);
                if (oldCategoryIdOfSubcategoryListSubcategory != null) {
                    oldCategoryIdOfSubcategoryListSubcategory.getSubcategoryList().remove(subcategoryListSubcategory);
                    oldCategoryIdOfSubcategoryListSubcategory = em.merge(oldCategoryIdOfSubcategoryListSubcategory);
                }
            }
            for (ConsumableItems consumableItemsListConsumableItems : category.getConsumableItemsList()) {
                Category oldCatgIdOfConsumableItemsListConsumableItems = consumableItemsListConsumableItems.getCatgId();
                consumableItemsListConsumableItems.setCatgId(category);
                consumableItemsListConsumableItems = em.merge(consumableItemsListConsumableItems);
                if (oldCatgIdOfConsumableItemsListConsumableItems != null) {
                    oldCatgIdOfConsumableItemsListConsumableItems.getConsumableItemsList().remove(consumableItemsListConsumableItems);
                    oldCatgIdOfConsumableItemsListConsumableItems = em.merge(oldCatgIdOfConsumableItemsListConsumableItems);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Category category) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category persistentCategory = em.find(Category.class, category.getId());
            List<Subcategory> subcategoryListOld = persistentCategory.getSubcategoryList();
            List<Subcategory> subcategoryListNew = category.getSubcategoryList();
            List<ConsumableItems> consumableItemsListOld = persistentCategory.getConsumableItemsList();
            List<ConsumableItems> consumableItemsListNew = category.getConsumableItemsList();
            List<Subcategory> attachedSubcategoryListNew = new ArrayList<Subcategory>();
            for (Subcategory subcategoryListNewSubcategoryToAttach : subcategoryListNew) {
                subcategoryListNewSubcategoryToAttach = em.getReference(subcategoryListNewSubcategoryToAttach.getClass(), subcategoryListNewSubcategoryToAttach.getId());
                attachedSubcategoryListNew.add(subcategoryListNewSubcategoryToAttach);
            }
            subcategoryListNew = attachedSubcategoryListNew;
            category.setSubcategoryList(subcategoryListNew);
            List<ConsumableItems> attachedConsumableItemsListNew = new ArrayList<ConsumableItems>();
            for (ConsumableItems consumableItemsListNewConsumableItemsToAttach : consumableItemsListNew) {
                consumableItemsListNewConsumableItemsToAttach = em.getReference(consumableItemsListNewConsumableItemsToAttach.getClass(), consumableItemsListNewConsumableItemsToAttach.getId());
                attachedConsumableItemsListNew.add(consumableItemsListNewConsumableItemsToAttach);
            }
            consumableItemsListNew = attachedConsumableItemsListNew;
            category.setConsumableItemsList(consumableItemsListNew);
            category = em.merge(category);
            for (Subcategory subcategoryListOldSubcategory : subcategoryListOld) {
                if (!subcategoryListNew.contains(subcategoryListOldSubcategory)) {
                    subcategoryListOldSubcategory.setCategoryId(null);
                    subcategoryListOldSubcategory = em.merge(subcategoryListOldSubcategory);
                }
            }
            for (Subcategory subcategoryListNewSubcategory : subcategoryListNew) {
                if (!subcategoryListOld.contains(subcategoryListNewSubcategory)) {
                    Category oldCategoryIdOfSubcategoryListNewSubcategory = subcategoryListNewSubcategory.getCategoryId();
                    subcategoryListNewSubcategory.setCategoryId(category);
                    subcategoryListNewSubcategory = em.merge(subcategoryListNewSubcategory);
                    if (oldCategoryIdOfSubcategoryListNewSubcategory != null && !oldCategoryIdOfSubcategoryListNewSubcategory.equals(category)) {
                        oldCategoryIdOfSubcategoryListNewSubcategory.getSubcategoryList().remove(subcategoryListNewSubcategory);
                        oldCategoryIdOfSubcategoryListNewSubcategory = em.merge(oldCategoryIdOfSubcategoryListNewSubcategory);
                    }
                }
            }
            for (ConsumableItems consumableItemsListOldConsumableItems : consumableItemsListOld) {
                if (!consumableItemsListNew.contains(consumableItemsListOldConsumableItems)) {
                    consumableItemsListOldConsumableItems.setCatgId(null);
                    consumableItemsListOldConsumableItems = em.merge(consumableItemsListOldConsumableItems);
                }
            }
            for (ConsumableItems consumableItemsListNewConsumableItems : consumableItemsListNew) {
                if (!consumableItemsListOld.contains(consumableItemsListNewConsumableItems)) {
                    Category oldCatgIdOfConsumableItemsListNewConsumableItems = consumableItemsListNewConsumableItems.getCatgId();
                    consumableItemsListNewConsumableItems.setCatgId(category);
                    consumableItemsListNewConsumableItems = em.merge(consumableItemsListNewConsumableItems);
                    if (oldCatgIdOfConsumableItemsListNewConsumableItems != null && !oldCatgIdOfConsumableItemsListNewConsumableItems.equals(category)) {
                        oldCatgIdOfConsumableItemsListNewConsumableItems.getConsumableItemsList().remove(consumableItemsListNewConsumableItems);
                        oldCatgIdOfConsumableItemsListNewConsumableItems = em.merge(oldCatgIdOfConsumableItemsListNewConsumableItems);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = category.getId();
                if (findCategory(id) == null) {
                    throw new NonexistentEntityException("The category with id " + id + " no longer exists.");
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
            Category category;
            try {
                category = em.getReference(Category.class, id);
                category.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The category with id " + id + " no longer exists.", enfe);
            }
            List<Subcategory> subcategoryList = category.getSubcategoryList();
            for (Subcategory subcategoryListSubcategory : subcategoryList) {
                subcategoryListSubcategory.setCategoryId(null);
                subcategoryListSubcategory = em.merge(subcategoryListSubcategory);
            }
            List<ConsumableItems> consumableItemsList = category.getConsumableItemsList();
            for (ConsumableItems consumableItemsListConsumableItems : consumableItemsList) {
                consumableItemsListConsumableItems.setCatgId(null);
                consumableItemsListConsumableItems = em.merge(consumableItemsListConsumableItems);
            }
            em.remove(category);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Category> findCategoryEntities() {
        return findCategoryEntities(true, -1, -1);
    }

    public List<Category> findCategoryEntities(int maxResults, int firstResult) {
        return findCategoryEntities(false, maxResults, firstResult);
    }

    private List<Category> findCategoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Category.class));
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

    public Category findCategory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Category> rt = cq.from(Category.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
