package com.sda.javagda40.mavendbdemo.database;

import com.sda.javagda40.mavendbdemo.model.Service;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao {
    public List<Service> findAllServicesByName(String phrase) {
        List<Service> list = new ArrayList<>();

        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        try (Session session = sessionFactory.openSession()) {

            // narzędzie do tworzenia zapytań i kreowania klauzuli 'where'
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // obiekt reprezentujący zapytanie
            CriteriaQuery<Service> criteriaQuery = cb.createQuery(Service.class);

            // obiekt reprezentujący tabelę bazodanową.
            // do jakiej tabeli kierujemy nasze zapytanie?
            Root<Service> rootTable = criteriaQuery.from(Service.class);

            // wykonanie zapytania
            criteriaQuery.select(rootTable)
                    .where(
                            cb.like(
                                    rootTable.get("name"),
                                    "%" + phrase + "%"
                            )
                    );

            // specification
            list.addAll(session.createQuery(criteriaQuery).list());

            // poznanie uniwersalnego rozwiązania które działa z każdą bazą danych
            // używanie klas których będziecie używać na JPA (Spring)

        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }
}
