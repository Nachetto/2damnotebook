
package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Employees{
    @XmlElement(name="employee")
    private List<Employee> employees;
            
    public Employees() {
        employees=new ArrayList<>();}

    public List<Employee> getEmployee() {
        return employees;
    }

    public void setEmployee(List<Employee> listEmp) {
        this.employees = listEmp;
    }
    
    public void addEmployee(Employee e) {
        employees.add(e);
    }
 
    @Override
    public String toString() {
        return "Employees{" + "employee=" + employees + '}';
    }
}