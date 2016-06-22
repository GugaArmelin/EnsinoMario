package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Alunos;
import model.Cursos;
import model.Funcionarios;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-22T01:57:17")
@StaticMetamodel(Disciplinas.class)
public class Disciplinas_ { 

    public static volatile SingularAttribute<Disciplinas, Date> horario;
    public static volatile SingularAttribute<Disciplinas, String> disciplina;
    public static volatile ListAttribute<Disciplinas, Cursos> cursosList;
    public static volatile ListAttribute<Disciplinas, Alunos> alunosList;
    public static volatile SingularAttribute<Disciplinas, Integer> id;
    public static volatile SingularAttribute<Disciplinas, Integer> dia;
    public static volatile ListAttribute<Disciplinas, Funcionarios> funcionariosList;

}