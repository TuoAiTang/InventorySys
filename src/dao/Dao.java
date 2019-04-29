package dao;

import bean.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import sun.security.krb5.Confounder;

import java.util.List;
import java.util.Queue;

public class Dao {
    SessionFactory sf;
    Session s;
    public Dao(){
        sf = new Configuration().configure().buildSessionFactory();
        s = sf.openSession();
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        s.close();
        sf.close();
    }

    public void add(Object o){
        s.beginTransaction();
        s.save(o);
        s.getTransaction().commit();
    }

    public void update(Object o){
        s.beginTransaction();
        s.update(o);
        s.getTransaction().commit();
    }

    public Object get(Class c, int id){
        s.beginTransaction();
        Object o = s.get(c, id);
        s.getTransaction().commit();
        return o;
    }

    public List<Object> allEq(String classname, String propertyName, String key){
        s.beginTransaction();

        String sql = "from " + classname + " x where " + "x." + propertyName
                        + " = '"  + key + "'";
        Query q = s.createQuery(sql);
        List<Object> objects = q.list();

        s.getTransaction().commit();
        return objects;
    }

    public List<Object> orEq(String classname, String propertyName, String... key){
        s.beginTransaction();
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(classname).append(" x where");
        for(String k : key){
            sb.append(" x.").append(propertyName).append(" = '").append(k);
            sb.append("' ");
            sb.append("or");
        }

        String sql = sb.substring(0, sb.length() - 2);

        Query q = s.createQuery(sql);
        List<Object> objects = q.list();

        s.getTransaction().commit();
        return objects;
    }

    public List<Object> all(String classname){
        s.beginTransaction();

        String sql = "from " + classname;
        Query q = s.createQuery(sql);
        List<Object> objects = q.list();

        s.getTransaction().commit();
        return objects;
    }

    public List<Object> allInorder(String classname, String propertyName){
        s.beginTransaction();

        String sql = "from " + classname + " x order by x." + propertyName;
        Query q = s.createQuery(sql);
        List<Object> objects = q.list();

        s.getTransaction().commit();
        return objects;
    }

    public int getCount(String classname, String propertyName, String key){
        s.beginTransaction();

        String sql = "select count(*) from " + classname + " x where " + "x." + propertyName
                + " = '"  + key + "'";
        Query q = s.createQuery(sql);
        int count = ((Number) q.uniqueResult()).intValue();
        s.getTransaction().commit();
        return count;
    }
}
