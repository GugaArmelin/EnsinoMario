/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Disciplinas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Funcionarios;

/**
 *
 * @author thomaz
 */
public class FuncionariosJpaController implements Serializable {

    public FuncionariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcionarios funcionarios) {
        if (funcionarios.getDisciplinasList() == null) {
            funcionarios.setDisciplinasList(new ArrayList<Disciplinas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Disciplinas> attachedDisciplinasList = new ArrayList<Disciplinas>();
            for (Disciplinas disciplinasListDisciplinasToAttach : funcionarios.getDisciplinasList()) {
                disciplinasListDisciplinasToAttach = em.getReference(disciplinasListDisciplinasToAttach.getClass(), disciplinasListDisciplinasToAttach.getId());
                attachedDisciplinasList.add(disciplinasListDisciplinasToAttach);
            }
            funcionarios.setDisciplinasList(attachedDisciplinasList);
            em.persist(funcionarios);
            for (Disciplinas disciplinasListDisciplinas : funcionarios.getDisciplinasList()) {
                disciplinasListDisciplinas.getFuncionariosList().add(funcionarios);
                disciplinasListDisciplinas = em.merge(disciplinasListDisciplinas);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcionarios funcionarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionarios persistentFuncionarios = em.find(Funcionarios.class, funcionarios.getId());
            List<Disciplinas> disciplinasListOld = persistentFuncionarios.getDisciplinasList();
            List<Disciplinas> disciplinasListNew = funcionarios.getDisciplinasList();
            List<Disciplinas> attachedDisciplinasListNew = new ArrayList<Disciplinas>();
            for (Disciplinas disciplinasListNewDisciplinasToAttach : disciplinasListNew) {
                disciplinasListNewDisciplinasToAttach = em.getReference(disciplinasListNewDisciplinasToAttach.getClass(), disciplinasListNewDisciplinasToAttach.getId());
                attachedDisciplinasListNew.add(disciplinasListNewDisciplinasToAttach);
            }
            disciplinasListNew = attachedDisciplinasListNew;
            funcionarios.setDisciplinasList(disciplinasListNew);
            funcionarios = em.merge(funcionarios);
            for (Disciplinas disciplinasListOldDisciplinas : disciplinasListOld) {
                if (!disciplinasListNew.contains(disciplinasListOldDisciplinas)) {
                    disciplinasListOldDisciplinas.getFuncionariosList().remove(funcionarios);
                    disciplinasListOldDisciplinas = em.merge(disciplinasListOldDisciplinas);
                }
            }
            for (Disciplinas disciplinasListNewDisciplinas : disciplinasListNew) {
                if (!disciplinasListOld.contains(disciplinasListNewDisciplinas)) {
                    disciplinasListNewDisciplinas.getFuncionariosList().add(funcionarios);
                    disciplinasListNewDisciplinas = em.merge(disciplinasListNewDisciplinas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = funcionarios.getId();
                if (findFuncionarios(id) == null) {
                    throw new NonexistentEntityException("The funcionarios with id " + id + " no longer exists.");
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
            Funcionarios funcionarios;
            try {
                funcionarios = em.getReference(Funcionarios.class, id);
                funcionarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionarios with id " + id + " no longer exists.", enfe);
            }
            List<Disciplinas> disciplinasList = funcionarios.getDisciplinasList();
            for (Disciplinas disciplinasListDisciplinas : disciplinasList) {
                disciplinasListDisciplinas.getFuncionariosList().remove(funcionarios);
                disciplinasListDisciplinas = em.merge(disciplinasListDisciplinas);
            }
            em.remove(funcionarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcionarios> findFuncionariosEntities() {
        return findFuncionariosEntities(true, -1, -1);
    }

    public List<Funcionarios> findFuncionariosEntities(int maxResults, int firstResult) {
        return findFuncionariosEntities(false, maxResults, firstResult);
    }

    private List<Funcionarios> findFuncionariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionarios.class));
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

    public Funcionarios findFuncionarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcionarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcionarios> rt = cq.from(Funcionarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
