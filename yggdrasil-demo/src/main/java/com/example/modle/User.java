package com.example.modle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty username;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty age;
    private final SimpleStringProperty goe;
    private final SimpleStringProperty country;

    private final SimpleStringProperty password;
    private final SimpleStringProperty thump;
    private final SimpleStringProperty eleWf;
    private final SimpleStringProperty eleEl;


    public User(int id, String username, String gender, String age, String goe, String country, String password, String thump, String eleWf, String eleEl) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.gender =new SimpleStringProperty(gender);
        this.age = new SimpleStringProperty(age);
        this.goe = new SimpleStringProperty(goe);
        this.country = new SimpleStringProperty(country);
        this.password = new SimpleStringProperty(password);
        this.thump = new SimpleStringProperty(thump);
        this.eleWf = new SimpleStringProperty(eleWf);
        this.eleEl= new SimpleStringProperty(eleEl);
    }

    public String getThump() {
        return thump.get();
    }

    public SimpleStringProperty thumpProperty() {
        return thump;
    }

    public void setThump(String thump) {
        this.thump.set(thump);
    }

    public String getEleWf() {
        return eleWf.get();
    }

    public SimpleStringProperty eleWfProperty() {
        return eleWf;
    }

    public void setEleWf(String eleWf) {
        this.eleWf.set(eleWf);
    }

    public String getEleEl() {
        return eleEl.get();
    }

    public SimpleStringProperty eleElProperty() {
        return eleEl;
    }

    public void setEleEl(String eleEl) {
        this.eleEl.set(eleEl);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + username +
                ", gender=" + gender +
                ", age=" + age +
                ", goe=" + goe +
                ", country=" + country +
                ", password=" + password +
                '}';
    }

    public void setId(int id) {
        this.id.set(id);
    }
    public int getId(){
        return this.id.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }
    public String getGender(){
        return this.gender.get();
    }


    public void setAge(String age) {
        this.age.set(age);
    }
    public String getAge(){
        return this.age.get();
    }


    public void setGoe(String goe) {
        this.goe.set(goe);
    }

    public String getGoe(){
        return this.goe.get();
    }


    public void setCountry(String country) {
        this.country.set(country);
    }
    public String getCountry(){
        return this.country.get();
    }


    public void setUsername(String username) {
        this.username.set(username);
    }
    public String getUsername(){
        return this.username.get();
    }

}
