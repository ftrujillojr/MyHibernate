package org.me.rest;

import java.util.Iterator;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.me.HibernateResourceAbstract;
import org.me.models.Department;
import org.me.models.Employee;

@Path("/employee")
public class EmployeeResource extends HibernateResourceAbstract {

    public EmployeeResource() {
        this.setDebug(true);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {

        try {
            this.beginTransaction(60);

            // START unit of work
            List<Employee> employeeList = session
                    .createCriteria(Employee.class)
                    .list();

            Iterator<Employee> itr = employeeList.iterator();
            while (itr.hasNext()) {
                Employee employee = itr.next();
//                Long departmentId = employee.getDepartmentId();
//                Department department = (Department) session.get(Department.class, departmentId);
//                String row = employee.toString() + " | " + department.toString();
//                System.out.println(row);
                System.out.println(employee.toString());
            }
            // END unit of work

            this.commitTransaction();
        } catch (Exception ex) {
            this.rollbackTransaction(ex);
            throw ex;
        } finally {
            this.closeSession();
        }

        return "Index() Employees";
    }

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_PLAIN)
    @SuppressWarnings("ConvertToTryWithResources")
    public String insert() {

        try {
            this.beginTransaction(60);

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

            this.commitTransaction();
        } catch (Exception ex) {
            this.rollbackTransaction(ex);
            throw ex;
        } finally {
            this.closeSession();
        }

        return "Insert() employee";
    }
}
