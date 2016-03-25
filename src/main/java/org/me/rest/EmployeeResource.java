package org.me.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.me.orm.Department;
import org.me.orm.Employee;
import org.me.orm.HibernateUtil;

@Path("employee")
public class EmployeeResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @SuppressWarnings("ConvertToTryWithResources")
    public String index() {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        
        // begin a transaction 
        session.getTransaction().begin();
        
        // creating a department object 
        Department department = new Department();

        department.setDeptName("Produce");
        // save department object 
        session.save(department);
        System.out.println(" Department saved, id: " + department.getId()); 

        // creating an employee object 
        Employee employee = new Employee(); 

        employee.setFirstName("Bugs Bunny"); 
        employee.setSalary(10000); 
        // set department of employee 
        employee.setDepartment( department); 
        // save employee object 
        session.save( employee); 
        System.out.println(" Employee saved, id: " + employee.getId()); 


        // commit transaction 
        session.getTransaction().commit(); 
        
        session.close(); 
        HibernateUtil.shutdown();

        return "Got employees!!";
    }
}
