package com.codecool.elproyectegrande1.util;

import com.codecool.elproyectegrande1.entity.Dreamer;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SessionRemove {
    public void removeDreamer(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // Delete a persistent object
            Dreamer dreamer = session.get(Dreamer.class, id);
            if (dreamer != null) {
                session.remove(dreamer);
                System.out.println("dreamer is deleted");
            }

//            // Delete a transient object
//            Dreamer dreamer2 = new Dreamer();
//            dreamer2.setId(id);
//            session.remove(dreamer2);
//            System.out.println("dreamer2 is deleted");

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
