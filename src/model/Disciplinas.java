/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import interfaces.ModItemSelection;
import interfaces.ModJTable;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author thomaz
 */
@Entity
@Table(name = "disciplinas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Disciplinas.findAll", query = "SELECT d FROM Disciplinas d"),
    @NamedQuery(name = "Disciplinas.findById", query = "SELECT d FROM Disciplinas d WHERE d.id = :id"),
    @NamedQuery(name = "Disciplinas.findByDisciplina", query = "SELECT d FROM Disciplinas d WHERE d.disciplina = :disciplina"),
    @NamedQuery(name = "Disciplinas.findByDia", query = "SELECT d FROM Disciplinas d WHERE d.dia = :dia"),
    @NamedQuery(name = "Disciplinas.findByHorario", query = "SELECT d FROM Disciplinas d WHERE d.horario = :horario")})
public class Disciplinas implements Serializable, ModJTable, ModItemSelection {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "disciplina")
    private String disciplina;
    @Column(name = "dia")
    private Integer dia;
    @Column(name = "horario")
    @Temporal(TemporalType.TIME)
    private Date horario;
    @ManyToMany(mappedBy = "disciplinasList")
    private List<Alunos> alunosList;
    @JoinTable(name = "rel_funcionario_disciplina", joinColumns = {
        @JoinColumn(name = "id_disciplina", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "id_funcionario", referencedColumnName = "id")})
    @ManyToMany
    private List<Funcionarios> funcionariosList;
    @ManyToMany(mappedBy = "disciplinasList")
    private List<Cursos> cursosList;

    public Disciplinas() {
    }

    public Disciplinas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    @XmlTransient
    public List<Alunos> getAlunosList() {
        return alunosList;
    }

    public void setAlunosList(List<Alunos> alunosList) {
        this.alunosList = alunosList;
    }

    @XmlTransient
    public List<Funcionarios> getFuncionariosList() {
        return funcionariosList;
    }

    public void setFuncionariosList(List<Funcionarios> funcionariosList) {
        this.funcionariosList = funcionariosList;
    }

    @XmlTransient
    public List<Cursos> getCursosList() {
        return cursosList;
    }

    public void setCursosList(List<Cursos> cursosList) {
        this.cursosList = cursosList;
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
        if (!(object instanceof Disciplinas)) {
            return false;
        }
        Disciplinas other = (Disciplinas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Disciplinas[ id=" + id + " ]";
    }

    @Override
    public Object getFirst() {
        return getId();
    }
    
    @Override
    public Object getSecond() {
        return getDisciplina();
    }

    @Override
    public Object getThird() {
        return getDia() + "a-feira/" + getHorario().getHours() + ":" + getHorario().getMinutes();
    }

    @Override
    public String value() {
        return getDisciplina() + " - " + getThird();
    }

    
}
