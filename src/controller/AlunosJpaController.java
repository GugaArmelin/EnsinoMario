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
import model.Cursos;
import model.Disciplinas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Alunos;

/**
 *
 * @author thomaz
 */
public class AlunosJpaController implements Serializable {

    public AlunosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alunos alunos) {
        if (alunos.getDisciplinasList() == null) {
            alunos.setDisciplinasList(new ArrayList<Disciplinas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cursos idCurso = alunos.getIdCurso();
            if (idCurso != null) {
                idCurso = em.getReference(idCurso.getClass(), idCurso.getId());
                alunos.setIdCurso(idCurso);
            }
            List<Disciplinas> attachedDisciplinasList = new ArrayList<Disciplinas>();
            for (Disciplinas disciplinasListDisciplinasToAttach : alunos.getDisciplinasList()) {
                disciplinasListDisciplinasToAttach = em.getReference(disciplinasListDisciplinasToAttach.getClass(), disciplinasListDisciplinasToAttach.getId());
                attachedDisciplinasList.add(disciplinasListDisciplinasToAttach);
            }
            alunos.setDisciplinasList(attachedDisciplinasList);
            em.persist(alunos);
            if (idCurso != null) {
                idCurso.getAlunosList().add(alunos);
                idCurso = em.merge(idCurso);
            }
            for (Disciplinas disciplinasListDisciplinas : alunos.getDisciplinasList()) {
                disciplinasListDisciplinas.getAlunosList().add(alunos);
                disciplinasListDisciplinas = em.merge(disciplinasListDisciplinas);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alunos alunos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alunos persistentAlunos = em.find(Alunos.class, alunos.getId());
            Cursos idCursoOld = persistentAlunos.getIdCurso();
            Cursos idCursoNew = alunos.getIdCurso();
            List<Disciplinas> disciplinasListOld = persistentAlunos.getDisciplinasList();
            List<Disciplinas> disciplinasListNew = alunos.getDisciplinasList();
            if (idCursoNew != null) {
                idCursoNew = em.getReference(idCursoNew.getClass(), idCursoNew.getId());
                alunos.setIdCurso(idCursoNew);
            }
            List<Disciplinas> attachedDisciplinasListNew = new ArrayList<Disciplinas>();
            for (Disciplinas disciplinasListNewDisciplinasToAttach : disciplinasListNew) {
                disciplinasListNewDisciplinasToAttach = em.getReference(disciplinasListNewDisciplinasToAttach.getClass(), disciplinasListNewDisciplinasToAttach.getId());
                attachedDisciplinasListNew.add(disciplinasListNewDisciplinasToAttach);
            }
            disciplinasListNew = attachedDisciplinasListNew;
            alunos.setDisciplinasList(disciplinasListNew);
            alunos = em.merge(alunos);
            if (idCursoOld != null && !idCursoOld.equals(idCursoNew)) {
                idCursoOld.getAlunosList().remove(alunos);
                idCursoOld = em.merge(idCursoOld);
            }
            if (idCursoNew != null && !idCursoNew.equals(idCursoOld)) {
                idCursoNew.getAlunosList().add(alunos);
                idCursoNew = em.merge(idCursoNew);
            }
            for (Disciplinas disciplinasListOldDisciplinas : disciplinasListOld) {
                if (!disciplinasListNew.contains(disciplinasListOldDisciplinas)) {
                    disciplinasListOldDisciplinas.getAlunosList().remove(alunos);
                    disciplinasListOldDisciplinas = em.merge(disciplinasListOldDisciplinas);
                }
            }
            for (Disciplinas disciplinasListNewDisciplinas : disciplinasListNew) {
                if (!disciplinasListOld.contains(disciplinasListNewDisciplinas)) {
                    disciplinasListNewDisciplinas.getAlunosList().add(alunos);
                    disciplinasListNewDisciplinas = em.merge(disciplinasListNewDisciplinas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alunos.getId();
                if (findAlunos(id) == null) {
                    throw new NonexistentEntityException("The alunos with id " + id + " no longer exists.");
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
            Alunos alunos;
            try {
                alunos = em.getReference(Alunos.class, id);
                alunos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alunos with id " + id + " no longer exists.", enfe);
            }
            Cursos idCurso = alunos.getIdCurso();
            if (idCurso != null) {
                idCurso.getAlunosList().remove(alunos);
                idCurso = em.merge(idCurso);
            }
            List<Disciplinas> disciplinasList = alunos.getDisciplinasList();
            for (Disciplinas disciplinasListDisciplinas : disciplinasList) {
                disciplinasListDisciplinas.getAlunosList().remove(alunos);
                disciplinasListDisciplinas = em.merge(disciplinasListDisciplinas);
            }
            em.remove(alunos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alunos> findAlunosEntities() {
        return findAlunosEntities(true, -1, -1);
    }

    public List<Alunos> findAlunosEntities(int maxResults, int firstResult) {
        return findAlunosEntities(false, maxResults, firstResult);
    }

    private List<Alunos> findAlunosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alunos.class));
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

    public Alunos findAlunos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alunos.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlunosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alunos> rt = cq.from(Alunos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
