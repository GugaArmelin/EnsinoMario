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
import model.Alunos;
import model.Cursos;

/**
 *
 * @author thomaz
 */
public class CursosJpaController implements Serializable {

    public CursosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cursos cursos) {
        if (cursos.getDisciplinasList() == null) {
            cursos.setDisciplinasList(new ArrayList<Disciplinas>());
        }
        if (cursos.getAlunosList() == null) {
            cursos.setAlunosList(new ArrayList<Alunos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Disciplinas> attachedDisciplinasList = new ArrayList<Disciplinas>();
            for (Disciplinas disciplinasListDisciplinasToAttach : cursos.getDisciplinasList()) {
                disciplinasListDisciplinasToAttach = em.getReference(disciplinasListDisciplinasToAttach.getClass(), disciplinasListDisciplinasToAttach.getId());
                attachedDisciplinasList.add(disciplinasListDisciplinasToAttach);
            }
            cursos.setDisciplinasList(attachedDisciplinasList);
            List<Alunos> attachedAlunosList = new ArrayList<Alunos>();
            for (Alunos alunosListAlunosToAttach : cursos.getAlunosList()) {
                alunosListAlunosToAttach = em.getReference(alunosListAlunosToAttach.getClass(), alunosListAlunosToAttach.getId());
                attachedAlunosList.add(alunosListAlunosToAttach);
            }
            cursos.setAlunosList(attachedAlunosList);
            em.persist(cursos);
            for (Disciplinas disciplinasListDisciplinas : cursos.getDisciplinasList()) {
                disciplinasListDisciplinas.getCursosList().add(cursos);
                disciplinasListDisciplinas = em.merge(disciplinasListDisciplinas);
            }
            for (Alunos alunosListAlunos : cursos.getAlunosList()) {
                Cursos oldIdCursoOfAlunosListAlunos = alunosListAlunos.getIdCurso();
                alunosListAlunos.setIdCurso(cursos);
                alunosListAlunos = em.merge(alunosListAlunos);
                if (oldIdCursoOfAlunosListAlunos != null) {
                    oldIdCursoOfAlunosListAlunos.getAlunosList().remove(alunosListAlunos);
                    oldIdCursoOfAlunosListAlunos = em.merge(oldIdCursoOfAlunosListAlunos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cursos cursos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cursos persistentCursos = em.find(Cursos.class, cursos.getId());
            List<Disciplinas> disciplinasListOld = persistentCursos.getDisciplinasList();
            List<Disciplinas> disciplinasListNew = cursos.getDisciplinasList();
            List<Alunos> alunosListOld = persistentCursos.getAlunosList();
            List<Alunos> alunosListNew = cursos.getAlunosList();
            List<Disciplinas> attachedDisciplinasListNew = new ArrayList<Disciplinas>();
            for (Disciplinas disciplinasListNewDisciplinasToAttach : disciplinasListNew) {
                disciplinasListNewDisciplinasToAttach = em.getReference(disciplinasListNewDisciplinasToAttach.getClass(), disciplinasListNewDisciplinasToAttach.getId());
                attachedDisciplinasListNew.add(disciplinasListNewDisciplinasToAttach);
            }
            disciplinasListNew = attachedDisciplinasListNew;
            cursos.setDisciplinasList(disciplinasListNew);
            List<Alunos> attachedAlunosListNew = new ArrayList<Alunos>();
            for (Alunos alunosListNewAlunosToAttach : alunosListNew) {
                alunosListNewAlunosToAttach = em.getReference(alunosListNewAlunosToAttach.getClass(), alunosListNewAlunosToAttach.getId());
                attachedAlunosListNew.add(alunosListNewAlunosToAttach);
            }
            alunosListNew = attachedAlunosListNew;
            cursos.setAlunosList(alunosListNew);
            cursos = em.merge(cursos);
            for (Disciplinas disciplinasListOldDisciplinas : disciplinasListOld) {
                if (!disciplinasListNew.contains(disciplinasListOldDisciplinas)) {
                    disciplinasListOldDisciplinas.getCursosList().remove(cursos);
                    disciplinasListOldDisciplinas = em.merge(disciplinasListOldDisciplinas);
                }
            }
            for (Disciplinas disciplinasListNewDisciplinas : disciplinasListNew) {
                if (!disciplinasListOld.contains(disciplinasListNewDisciplinas)) {
                    disciplinasListNewDisciplinas.getCursosList().add(cursos);
                    disciplinasListNewDisciplinas = em.merge(disciplinasListNewDisciplinas);
                }
            }
            for (Alunos alunosListOldAlunos : alunosListOld) {
                if (!alunosListNew.contains(alunosListOldAlunos)) {
                    alunosListOldAlunos.setIdCurso(null);
                    alunosListOldAlunos = em.merge(alunosListOldAlunos);
                }
            }
            for (Alunos alunosListNewAlunos : alunosListNew) {
                if (!alunosListOld.contains(alunosListNewAlunos)) {
                    Cursos oldIdCursoOfAlunosListNewAlunos = alunosListNewAlunos.getIdCurso();
                    alunosListNewAlunos.setIdCurso(cursos);
                    alunosListNewAlunos = em.merge(alunosListNewAlunos);
                    if (oldIdCursoOfAlunosListNewAlunos != null && !oldIdCursoOfAlunosListNewAlunos.equals(cursos)) {
                        oldIdCursoOfAlunosListNewAlunos.getAlunosList().remove(alunosListNewAlunos);
                        oldIdCursoOfAlunosListNewAlunos = em.merge(oldIdCursoOfAlunosListNewAlunos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cursos.getId();
                if (findCursos(id) == null) {
                    throw new NonexistentEntityException("The cursos with id " + id + " no longer exists.");
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
            Cursos cursos;
            try {
                cursos = em.getReference(Cursos.class, id);
                cursos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cursos with id " + id + " no longer exists.", enfe);
            }
            List<Disciplinas> disciplinasList = cursos.getDisciplinasList();
            for (Disciplinas disciplinasListDisciplinas : disciplinasList) {
                disciplinasListDisciplinas.getCursosList().remove(cursos);
                disciplinasListDisciplinas = em.merge(disciplinasListDisciplinas);
            }
            List<Alunos> alunosList = cursos.getAlunosList();
            for (Alunos alunosListAlunos : alunosList) {
                alunosListAlunos.setIdCurso(null);
                alunosListAlunos = em.merge(alunosListAlunos);
            }
            em.remove(cursos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cursos> findCursosEntities() {
        return findCursosEntities(true, -1, -1);
    }

    public List<Cursos> findCursosEntities(int maxResults, int firstResult) {
        return findCursosEntities(false, maxResults, firstResult);
    }

    private List<Cursos> findCursosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cursos.class));
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

    public Cursos findCursos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cursos.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cursos> rt = cq.from(Cursos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
