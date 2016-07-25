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
import model.Alunos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Funcionarios;
import model.Cursos;
import model.Disciplinas;

/**
 *
 * @author thomaz
 */
public class DisciplinasJpaController implements Serializable {
    private DisciplinasJpaController(){
    }

    public DisciplinasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private static DisciplinasJpaController instance;
    private static EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public static DisciplinasJpaController getInstance(){
        if(emf == null){
            instance = new DisciplinasJpaController(Persistence.createEntityManagerFactory("EnsinoMarioPU"));
        }
        return instance;
    }

    public void create(Disciplinas disciplinas) {
        if (disciplinas.getAlunosList() == null) {
            disciplinas.setAlunosList(new ArrayList<Alunos>());
        }
        if (disciplinas.getFuncionariosList() == null) {
            disciplinas.setFuncionariosList(new ArrayList<Funcionarios>());
        }
        if (disciplinas.getCursosList() == null) {
            disciplinas.setCursosList(new ArrayList<Cursos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Alunos> attachedAlunosList = new ArrayList<Alunos>();
            for (Alunos alunosListAlunosToAttach : disciplinas.getAlunosList()) {
                alunosListAlunosToAttach = em.getReference(alunosListAlunosToAttach.getClass(), alunosListAlunosToAttach.getFirst());
                attachedAlunosList.add(alunosListAlunosToAttach);
            }
            disciplinas.setAlunosList(attachedAlunosList);
            List<Funcionarios> attachedFuncionariosList = new ArrayList<Funcionarios>();
            for (Funcionarios funcionariosListFuncionariosToAttach : disciplinas.getFuncionariosList()) {
                funcionariosListFuncionariosToAttach = em.getReference(funcionariosListFuncionariosToAttach.getClass(), funcionariosListFuncionariosToAttach.getFirst());
                attachedFuncionariosList.add(funcionariosListFuncionariosToAttach);
            }
            disciplinas.setFuncionariosList(attachedFuncionariosList);
            List<Cursos> attachedCursosList = new ArrayList<Cursos>();
            for (Cursos cursosListCursosToAttach : disciplinas.getCursosList()) {
                cursosListCursosToAttach = em.getReference(cursosListCursosToAttach.getClass(), cursosListCursosToAttach.getFirst());
                attachedCursosList.add(cursosListCursosToAttach);
            }
            disciplinas.setCursosList(attachedCursosList);
            em.persist(disciplinas);
            for (Alunos alunosListAlunos : disciplinas.getAlunosList()) {
                alunosListAlunos.getDisciplinasList().add(disciplinas);
                alunosListAlunos = em.merge(alunosListAlunos);
            }
            for (Funcionarios funcionariosListFuncionarios : disciplinas.getFuncionariosList()) {
                funcionariosListFuncionarios.getDisciplinasList().add(disciplinas);
                funcionariosListFuncionarios = em.merge(funcionariosListFuncionarios);
            }
            for (Cursos cursosListCursos : disciplinas.getCursosList()) {
                cursosListCursos.getDisciplinasList().add(disciplinas);
                cursosListCursos = em.merge(cursosListCursos);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Disciplinas disciplinas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplinas persistentDisciplinas = em.find(Disciplinas.class, disciplinas.getFirst());
            List<Alunos> alunosListOld = persistentDisciplinas.getAlunosList();
            List<Alunos> alunosListNew = disciplinas.getAlunosList();
            List<Funcionarios> funcionariosListOld = persistentDisciplinas.getFuncionariosList();
            List<Funcionarios> funcionariosListNew = disciplinas.getFuncionariosList();
            List<Cursos> cursosListOld = persistentDisciplinas.getCursosList();
            List<Cursos> cursosListNew = disciplinas.getCursosList();
            List<Alunos> attachedAlunosListNew = new ArrayList<Alunos>();
            for (Alunos alunosListNewAlunosToAttach : alunosListNew) {
                alunosListNewAlunosToAttach = em.getReference(alunosListNewAlunosToAttach.getClass(), alunosListNewAlunosToAttach.getFirst());
                attachedAlunosListNew.add(alunosListNewAlunosToAttach);
            }
            alunosListNew = attachedAlunosListNew;
            disciplinas.setAlunosList(alunosListNew);
            List<Funcionarios> attachedFuncionariosListNew = new ArrayList<Funcionarios>();
            for (Funcionarios funcionariosListNewFuncionariosToAttach : funcionariosListNew) {
                funcionariosListNewFuncionariosToAttach = em.getReference(funcionariosListNewFuncionariosToAttach.getClass(), funcionariosListNewFuncionariosToAttach.getFirst());
                attachedFuncionariosListNew.add(funcionariosListNewFuncionariosToAttach);
            }
            funcionariosListNew = attachedFuncionariosListNew;
            disciplinas.setFuncionariosList(funcionariosListNew);
            List<Cursos> attachedCursosListNew = new ArrayList<Cursos>();
            for (Cursos cursosListNewCursosToAttach : cursosListNew) {
                cursosListNewCursosToAttach = em.getReference(cursosListNewCursosToAttach.getClass(), cursosListNewCursosToAttach.getFirst());
                attachedCursosListNew.add(cursosListNewCursosToAttach);
            }
            cursosListNew = attachedCursosListNew;
            disciplinas.setCursosList(cursosListNew);
            disciplinas = em.merge(disciplinas);
            for (Alunos alunosListOldAlunos : alunosListOld) {
                if (!alunosListNew.contains(alunosListOldAlunos)) {
                    alunosListOldAlunos.getDisciplinasList().remove(disciplinas);
                    alunosListOldAlunos = em.merge(alunosListOldAlunos);
                }
            }
            for (Alunos alunosListNewAlunos : alunosListNew) {
                if (!alunosListOld.contains(alunosListNewAlunos)) {
                    alunosListNewAlunos.getDisciplinasList().add(disciplinas);
                    alunosListNewAlunos = em.merge(alunosListNewAlunos);
                }
            }
            for (Funcionarios funcionariosListOldFuncionarios : funcionariosListOld) {
                if (!funcionariosListNew.contains(funcionariosListOldFuncionarios)) {
                    funcionariosListOldFuncionarios.getDisciplinasList().remove(disciplinas);
                    funcionariosListOldFuncionarios = em.merge(funcionariosListOldFuncionarios);
                }
            }
            for (Funcionarios funcionariosListNewFuncionarios : funcionariosListNew) {
                if (!funcionariosListOld.contains(funcionariosListNewFuncionarios)) {
                    funcionariosListNewFuncionarios.getDisciplinasList().add(disciplinas);
                    funcionariosListNewFuncionarios = em.merge(funcionariosListNewFuncionarios);
                }
            }
            for (Cursos cursosListOldCursos : cursosListOld) {
                if (!cursosListNew.contains(cursosListOldCursos)) {
                    cursosListOldCursos.getDisciplinasList().remove(disciplinas);
                    cursosListOldCursos = em.merge(cursosListOldCursos);
                }
            }
            for (Cursos cursosListNewCursos : cursosListNew) {
                if (!cursosListOld.contains(cursosListNewCursos)) {
                    cursosListNewCursos.getDisciplinasList().add(disciplinas);
                    cursosListNewCursos = em.merge(cursosListNewCursos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = disciplinas.getId();
                if (findDisciplinas(id) == null) {
                    throw new NonexistentEntityException("The disciplinas with id " + id + " no longer exists.");
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
            Disciplinas disciplinas;
            try {
                disciplinas = em.getReference(Disciplinas.class, id);
                disciplinas.getFirst();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The disciplinas with id " + id + " no longer exists.", enfe);
            }
            List<Alunos> alunosList = disciplinas.getAlunosList();
            for (Alunos alunosListAlunos : alunosList) {
                alunosListAlunos.getDisciplinasList().remove(disciplinas);
                alunosListAlunos = em.merge(alunosListAlunos);
            }
            List<Funcionarios> funcionariosList = disciplinas.getFuncionariosList();
            for (Funcionarios funcionariosListFuncionarios : funcionariosList) {
                funcionariosListFuncionarios.getDisciplinasList().remove(disciplinas);
                funcionariosListFuncionarios = em.merge(funcionariosListFuncionarios);
            }
            List<Cursos> cursosList = disciplinas.getCursosList();
            for (Cursos cursosListCursos : cursosList) {
                cursosListCursos.getDisciplinasList().remove(disciplinas);
                cursosListCursos = em.merge(cursosListCursos);
            }
            em.remove(disciplinas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Disciplinas> findDisciplinasEntities() {
        return findDisciplinasEntities(true, -1, -1);
    }

    public List<Disciplinas> findDisciplinasEntities(int maxResults, int firstResult) {
        return findDisciplinasEntities(false, maxResults, firstResult);
    }

    private List<Disciplinas> findDisciplinasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Disciplinas.class));
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

    public Disciplinas findDisciplinas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Disciplinas.class, id);
        } finally {
            em.close();
        }
    }

    public int getDisciplinasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Disciplinas> rt = cq.from(Disciplinas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
