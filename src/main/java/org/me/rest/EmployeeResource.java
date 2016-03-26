package org.me.rest;

import java.util.Iterator;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.me.models.Department;
import org.me.models.Employee;
import org.me.HibernateUtil;

@Path("employee")
public class EmployeeResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        Session session = null;
        Transaction tx = null;

        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

            session = sessionFactory.getCurrentSession();

            tx = session.getTransaction();
            tx.setTimeout(60);
            tx.begin();

            // START unit of work
            List<Employee> employeeList = session.createCriteria(Employee.class).list();
            
            Iterator<Employee> itr = employeeList.iterator();
            while(itr.hasNext()) {
                Employee employee = itr.next();
                System.out.println("           id:" + employee.getId());
                System.out.println("    firstName:" + employee.getFirstName());
                System.out.println("       salary: " + employee.getSalary());
                System.out.println("department_id:"  + employee.getDepartment().getId());
                System.out.println("     deptName: " + employee.getDepartment().getDeptName());
            }
            // END unit of work

            session.flush();
            session.clear();
            tx.commit();
            System.out.println("\n*** COMMIT !!!\n");
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
                System.out.println("\nROLLBACK!!\n");
            }
            System.out.println("\n*** EXCEPTION \n" + ex.getMessage());
            throw ex;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
                System.out.println("\n*** CLOSE session\n");
            }
        }

        return "hello world dude!";
       
    }
    
    
    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_PLAIN)
    @SuppressWarnings("ConvertToTryWithResources")
    public String insert() {
        Session session = null;
        Transaction tx = null;

        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

            session = sessionFactory.getCurrentSession();

            tx = session.getTransaction();
            tx.setTimeout(60);
            tx.begin();

            // START unit of work
            Department department = new Department();
            department.setDeptName("Produce");
            
            session.save(department);
            System.out.println(" Department saved, id: " + department.getId());

            Employee employee = new Employee();
            employee.setFirstName("Bugs Bunny");
            employee.setSalary(15000);
            employee.setDepartment(department);

            session.save(employee);
            System.out.println(" Employee saved, id: " + employee.getId());
            // END unit of work

            // This will keep memory leaks from happening.
            // Must be called at the end of a unit of work, before committing the transaction and closing the session
            session.flush();
            session.clear();

            tx.commit();
            System.out.println("\n*** COMMIT !!!\n");

        } catch (Exception ex) {
            // If the Session throws an exception, the transaction must be 
            // rolled back and the session discarded. The internal state of the 
            // Session might not be consistent with the database after the 
            // exception occurs.
            if (tx != null) {
                tx.rollback();
                System.out.println("\nROLLBACK!!\n");
            }
            System.out.println("\n*** EXCEPTION \n" + ex.getMessage());
            throw ex;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
                System.out.println("\n*** CLOSE session\n");
            }
        }

        return "Got employees!!";
    }
}
