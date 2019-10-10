/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vlkpa
 */
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Person implements Serializable{
    private String firstName;
    private String lastName;
    private String birthDate;
    private List<Child> children = new ArrayList<Child>();
    private String status;
    private String gender;

    
    private static final long serialVersionUID = 1L;
    
    public Person(){
        super();
        this.firstName = "";
        this.lastName = "";
        this.birthDate = "";
        this.status = "";
        this.gender = "";
    };
             
    public Person(
                String firstName, 
                String lastName, 
                String birthDate, 
                String status, 
                String gender, 
                List<Child> children) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.status = status;
        this.gender = gender;
        setChildren(children);
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getStatus() {  
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Boolean isSetStatus() {
        return gender != "";
    }

    public String getGender() {
        return gender;
    }

    public Boolean isSetGender() {
        return gender != "";
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void setChild(Child child) {
        this.children.add(child);
    }
    
    public Child getChild(int index) {
        return children.get(index);
    }
    
    public void setChildren(List<Child> children){
        for(Child child : children){
            setChild(child);
        }
    }
    
    public List<String> getPersonTextFieldList(){
        List<String> personFieldList = new ArrayList<String>();
        personFieldList.add(this.firstName);
        personFieldList.add(this.lastName);
        personFieldList.add(this.status);
        personFieldList.add(this.gender);
        return personFieldList;
    }
    
    public List<Child> getChildren(){
        return children;
    }
    
    public void printPerson() {
        System.out.println(getFirstName());
        System.out.println(getLastName());
        System.out.println(getBirthDate());
        System.out.println(getStatus());
        System.out.println(getGender());
        for(Child child : children){
            System.out.println("Meno dietata "+child.getFirstName());
            System.out.println("Priezvisko dietata "+child.getLastName());
        }
        
    }
}