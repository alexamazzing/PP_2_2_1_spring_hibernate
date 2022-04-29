package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public User getUserByCar(String model, int series) {
      String hqlQuery = "Select user from User as user " +
              "left join user.car as car " +
              "where car.model = :model and car.series = :series";

      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hqlQuery, User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);
      User user = query.getResultList().get(0);
      return user;

   }

}
