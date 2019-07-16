/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upv.inf;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

    private final StringProperty fullName = new SimpleStringProperty();
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final ObjectProperty<Residence> residence = new SimpleObjectProperty<>();
    private final StringProperty pathImage = new SimpleStringProperty();

    public String getPathImage() {
        return pathImage.get();
    }

    public void setPathImage(String value) {
        pathImage.set(value);
    }

    public StringProperty pathImageProperty() {
        return pathImage;
    }
    
    
    public Residence getResidence() {
        return residence.get();
    }

    public void setResidence(Residence value) {
        residence.set(value);
    }

    public ObjectProperty residenceProperty() {
        return residence;
    }
    
    
    
    
    
    public Person(int id, String fullName, Residence residence, String pathImage) {
        this.id.set(id);
        this.fullName.set(fullName);
        this.residence.set(residence);
        this.pathImage.set(pathImage);
    }
   
    public int getId() {
        return id.get();
    }

    public void setId(int value) {
        id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    
    public String getFullName() {
        return fullName.get();
    }

    public void setFullName(String value) {
        fullName.set(value);
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }


}
