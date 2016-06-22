/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import interfaces.ModItemSelection;
import interfaces.ModJTable;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author thomaz
 */
@Entity
@Table(name = "alunos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alunos.findAll", query = "SELECT a FROM Alunos a"),
    @NamedQuery(name = "Alunos.findById", query = "SELECT a FROM Alunos a WHERE a.id = :id"),
    @NamedQuery(name = "Alunos.findByAluno", query = "SELECT a FROM Alunos a WHERE a.aluno = :aluno")})
public class Alunos implements Serializable, ModJTable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "aluno")
    private String aluno;
    @JoinTable(name = "rel_alunos_disciplina", joinColumns = {
        @JoinColumn(name = "id_aluno", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "id_disciplina", referencedColumnName = "id")})
    @ManyToMany
    private List<Disciplinas> disciplinasList;
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    @ManyToOne
    private Cursos idCurso;

    public Alunos() {
    }

    public Alunos(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    @XmlTransient
    public List<Disciplinas> getDisciplinasList() {
        return disciplinasList;
    }

    public void setDisciplinasList(List<Disciplinas> disciplinasList) {
        this.disciplinasList = disciplinasList;
    }

    public Cursos getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Cursos idCurso) {
        this.idCurso = idCurso;
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
        if (!(object instanceof Alunos)) {
            return false;
        }
        Alunos other = (Alunos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Alunos[ id=" + id + " ]";
    }

    @Override
    public String getValue() {
        return getAluno();
    }

    @Override
    public String getDesc() {
        return getIdCurso().getCurso();
    }

    
}
