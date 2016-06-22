package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Disciplinas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-22T01:57:17")
@StaticMetamodel(Funcionarios.class)
public class Funcionarios_ { 

    public static volatile SingularAttribute<Funcionarios, Short> professor;
    public static volatile ListAttribute<Funcionarios, Disciplinas> disciplinasList;
    public static volatile SingularAttribute<Funcionarios, Integer> id;
    public static volatile SingularAttribute<Funcionarios, String> funcionario;

}