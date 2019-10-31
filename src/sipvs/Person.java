/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvs;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vlkpa
 */
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"firstName", "lastName", "gender", "birthDate", "status", "children"})
public class Person implements Serializable {

    @XmlAttribute(name = "personID")
    private String personID;

    private String firstName;
    private String lastName;
    private String birthDate;

    @XmlElement(name = "child")
    private List<Child> children = new ArrayList<Child>();

    private String status;
    private String gender;


    private static final long serialVersionUID = 1L;

    public Person() {
        super();
        this.personID = "HRXXXXXX";
        this.firstName = "";
        this.lastName = "";
        this.birthDate = "";
        this.status = "";
        this.gender = "";
    }

    public Person(
            String firstName,
            String lastName,
            String birthDate,
            String status,
            String gender,
            String personID,
            List<Child> children) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.status = status;
        this.gender = gender;
        this.personID = personID;
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

    public void setChildren(List<Child> children) {
        for (Child child : children) {
            setChild(child);
        }
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String IdNum) {
        this.personID = IdNum;
    }

    public List<String> getPersonTextFieldList() {
        List<String> personFieldList = new ArrayList<String>();
        personFieldList.add(this.firstName);
        personFieldList.add(this.lastName);
        personFieldList.add(this.status);
        personFieldList.add(this.gender);
        return personFieldList;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void printPerson() {
        System.out.println(getFirstName());
        System.out.println(getLastName());
        System.out.println(getBirthDate());
        System.out.println(getStatus());
        System.out.println(getGender());
        System.out.println(getPersonID());
        for (Child child : children) {
            System.out.println("Meno dietata " + child.getFirstName());
            System.out.println("Priezvisko dietata " + child.getLastName());
        }

    }


}