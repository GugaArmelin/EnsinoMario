/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import interfaces.ModJTable;
import java.io.Serializable;

/**
 *
 * @author thomaz
 */
public class AlunoDisciplina implements Serializable, ModJTable {
    
    private Alunos aluno;
    private Disciplinas disciplina;

    public AlunoDisciplina(Alunos aluno, Disciplinas disciplina) {
        this.aluno = aluno;
        this.disciplina = disciplina;
    }
    
    public Alunos getAluno() {
        return aluno;
    }

    public Disciplinas getDisciplina() {
        return disciplina;
    }
   
    @Override
    public Object getFirst() {
        return aluno.getAluno();
    }

    @Override
    public Object getSecond() {
        return disciplina.getDisciplina();
    }

    @Override
    public Object getThird() {
        return aluno.getIdCurso().getCurso();
    }
    
}
