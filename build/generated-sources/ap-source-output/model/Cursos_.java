package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Alunos;
import model.Disciplinas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-22T01:57:17")
@StaticMetamodel(Cursos.class)
public class Cursos_ { 

    public static volatile ListAttribute<Cursos, Disciplinas> disciplinasList;
    public static volatile SingularAttribute<Cursos, String> curso;
    public static volatile ListAttribute<Cursos, Alunos> alunosList;
    public static volatile SingularAttribute<Cursos, Integer> id;

}