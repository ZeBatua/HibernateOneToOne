package hiber.proj;

import hiber.proj.model.Principal;
import hiber.proj.model.School;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().
                addAnnotatedClass(Principal.class).addAnnotatedClass(School.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            // олучите любого директора, а затем получите его школу.
            Principal principal = session.get(Principal.class, 4);
            System.out.println(principal.getSchool());

            // Получите любую школу, а затем получите ее директора
            School school = session.get(School.class, 3);
            System.out.println(school.getPrincipal());

            // hql запрос который удаляет всех Директоров начинающихся на "test"
            System.out.println("\n\nHELLO");
            session.createQuery("DELETE FROM Principal WHERE name LIKE 'test%'").executeUpdate();
            session.createQuery("DELETE FROM School WHERE name = 720").executeUpdate();
            System.out.println("HELLO\n\n");

            // Создайте нового директора и новую школу и свяжите эти сущности.
            Principal principal1 = new Principal("testPrincipal1", 54);
            School school1 = new School(720, principal1);
            principal1.setSchool(school1);
            session.save(principal1);

            // Смените директора у существующей школы
            Principal principal2 = new Principal("principal2", 23);
            session.get(School.class, 4).setPrincipal(principal2);

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }
}