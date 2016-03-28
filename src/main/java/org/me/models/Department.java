package org.me.models;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Department")
public class Department {
    
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    @Column(name="dept_name")
    private String deptName;
    
    @OneToMany(mappedBy="department")
    private Set<Employee> employees;
    
    public Department() {
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.id).append(",");
        sb.append(this.deptName);
        //sb.append(this.employees.toString());
        return sb.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
