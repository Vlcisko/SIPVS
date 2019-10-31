/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vlkpa
 */
@XmlRootElement(name = "child")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Child{
	
    private String firstName;
    private String lastName;
    
    public Child(){
        this.firstName = "";
        this.lastName = "";
    };
             
    public Child(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
    
    public List<String> getChildTextFieldList(){
        List<String> childFieldsList = new ArrayList<String>();
        childFieldsList.add(this.firstName);
        childFieldsList.add(this.lastName);
        return childFieldsList;
    }
}
