package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class Contact implements Comparable<Contact>, Serializable {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd  hh:mm:ss");
    private int id;
    private String name;
    private String lastName;
    private int age;
    private String phoneNumber;
    private boolean married;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;


    public Contact() {
    }

    public Contact(String name, String lastName,
                   int age, String phoneNumber,
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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
                married == contact.married &&
                Objects.equals(name, contact.name) &&
                Objects.equals(lastName, contact.lastName) &&
                Objects.equals(phoneNumber, contact.phoneNumber) &&
                Objects.equals(createDate, contact.createDate) &&
                Objects.equals(updateDate, contact.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, age, phoneNumber, married, createDate, updateDate);
    }

    @Override
    public String toString() {
        return "ID=" + id +
                ",  Name=" + name +
                ",  Last Name=" + lastName +
                ",  Age=" + age +
                ",  Phone Number=" + phoneNumber +
                ",  Status=" + (married ? "Married" : "No married") +
                ",  Create Date=" + createDate.format(formatter) +
                ",  Update Date=" + updateDate.format(formatter);
    }


    @Override
    public int compareTo(Contact o) {
        return Integer.compare(id, o.getId());
    }
}
