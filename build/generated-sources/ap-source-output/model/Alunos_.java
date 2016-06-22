package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cursos;
import model.Disciplinas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-22T01:57:17")
@StaticMetamodel(Alunos.class)
public class Alunos_ { 

    public static volatile SingularAttribute<Alunos, String> aluno;
    public static volatile ListAttribute<Alunos, Disciplinas> disciplinasList;
    public static volatile SingularAttribute<Alunos, Cursos> idCurso;
    public static volatile SingularAttribute<Alunos, Integer> id;

}