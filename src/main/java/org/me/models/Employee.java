package org.me.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "salary")
    private double salary;

    @JoinColumn(name = "department_id")
//    @ManyToOne(fetch = FetchType.LAZY) // No Join
    @ManyToOne(fetch = FetchType.EAGER) // LEFT OUTER JOIN
    private Department department;
    
    // If you want to see the foreign key, then you have to do this as well as the above.
    @Column(name = "department_id", updatable=false, insertable=false)
    private Long departmentId;

    public Employee() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.id).append(",");
        sb.append(this.firstName).append(",");
        sb.append(this.salary).append(",");
//        sb.append(this.departmentId);
        sb.append(this.department.toString());

        return sb.toString();
    }

    public Long getDepartmentId() {
        return departmentId;
    }
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
