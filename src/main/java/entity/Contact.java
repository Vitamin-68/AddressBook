package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class Contact implements Comparable<Contact>{
    private int id;
    private String name;
    private String lastName;
    private int age;
    private int phoneNumber;
    private boolean married;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd  hh:mm:ss");


    public Contact() {
    }

    public Contact(String name, String lastName,
                   int age, int phoneNumber,
                   boolean married,
                   LocalDateTime createDate,
                   LocalDateTime updateTime) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.married = married;
        this.createDate = createDate;
        this.updateDate = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {

        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id &&
                age == contact.age &&
                phoneNumber == contact.phoneNumber &&
                married == contact.married &&
                Objects.equals(name, contact.name) &&
                Objects.equals(lastName, contact.lastName) &&
                Objects.equals(createDate, contact.createDate) &&
                Objects.equals(updateDate, contact.updateDate) &&
                Objects.equals(formatter, contact.formatter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, age, phoneNumber, married, createDate, updateDate, formatter);
    }

    @Override
    public String toString() {
        return  "ID=" + id +
                ",  Name='" + name + '\'' +
                ",  Last Name='" + lastName + '\'' +
                ",  Age=" + age +
                ",  Phone Number='" + phoneNumber + '\'' +
                ",  Status=" + (married ? "Married" : "No married") +
                ",  Create Date=" + createDate.format(formatter) +
                ",  Update Date=" + updateDate.format(formatter);
    }


    @Override
    public int compareTo(Contact o) {
        //todo make this metod late
        return 0;
    }
}
