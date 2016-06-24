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
import javax.persistence.ManyToMany;
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
@Table(name = "funcionarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcionarios.findAll", query = "SELECT f FROM Funcionarios f"),
    @NamedQuery(name = "Funcionarios.findById", query = "SELECT f FROM Funcionarios f WHERE f.id = :id"),
    @NamedQuery(name = "Funcionarios.findByFuncionario", query = "SELECT f FROM Funcionarios f WHERE f.funcionario = :funcionario"),
    @NamedQuery(name = "Funcionarios.findByProfessor", query = "SELECT f FROM Funcionarios f WHERE f.professor = :professor")})
public class Funcionarios implements Serializable, ModJTable, ModItemSelection {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "funcionario")
    private String funcionario;
    @Column(name = "professor")
    private Short professor;
    @ManyToMany(mappedBy = "funcionariosList")
    private List<Disciplinas> disciplinasList;

    public Funcionarios() {
    }

    public Funcionarios(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public Short getProfessor() {
        return professor;
    }

    public void setProfessor(Short professor) {
        this.professor = professor;
    }

    @XmlTransient
    public List<Disciplinas> getDisciplinasList() {
        return disciplinasList;
    }

    public void setDisciplinasList(List<Disciplinas> disciplinasList) {
        this.disciplinasList = disciplinasList;
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
        if (!(object instanceof Funcionarios)) {
            return false;
        }
        Funcionarios other = (Funcionarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Funcionarios[ id=" + id + " ]";
    }

    @Override
    public Object getFirst() {
        return getId();
    }
    
    @Override
    public Object getSecond() {
        return getFuncionario();
    }

    @Override
    public Object getThird() {
        return ( getProfessor() == 1 ) ? "Sim" : "Não";
    }

    @Override
    public String value() {
        return getFuncionario();
    }
    
}
