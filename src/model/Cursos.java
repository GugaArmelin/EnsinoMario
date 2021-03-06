/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import interfaces.ModItemSelection;
import interfaces.ModJTable;

/**
 *
 * @author thomaz
 */
@Entity
@Table(name = "cursos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cursos.findAll", query = "SELECT c FROM Cursos c"),
    @NamedQuery(name = "Cursos.findById", query = "SELECT c FROM Cursos c WHERE c.id = :id"),
    @NamedQuery(name = "Cursos.findByCurso", query = "SELECT c FROM Cursos c WHERE c.curso = :curso")})
public class Cursos implements Serializable, ModItemSelection, ModJTable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "curso")
    private String curso;
    @JoinTable(name = "rel_curso_disciplina", joinColumns = {
        @JoinColumn(name = "id_curso", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "id_disciplina", referencedColumnName = "id")})
    @ManyToMany
    private List<Disciplinas> disciplinasList;
    @OneToMany(mappedBy = "idCurso")
    private List<Alunos> alunosList;

    public Cursos() {
    }

    public Cursos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @XmlTransient
    public List<Disciplinas> getDisciplinasList() {
        return disciplinasList;
    }

    public void setDisciplinasList(List<Disciplinas> disciplinasList) {
        this.disciplinasList = disciplinasList;
    }

    @XmlTransient
    public List<Alunos> getAlunosList() {
        return alunosList;
    }

    public void setAlunosList(List<Alunos> alunosList) {
        this.alunosList = alunosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cursos)) {
            return false;
        }
        Cursos other = (Cursos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Cursos[ id=" + id + " ]";
    }

    @Override
    public String value() {
        return getCurso();
    }

    @Override
    public Object getFirst() {
        return getId();
    }
        
    @Override
    public Object getSecond() {
        return getCurso();
    }

    @Override
    public Object getThird() {
        return null;
    }
    
}
