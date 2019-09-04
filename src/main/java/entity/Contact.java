package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import dao.ContactDao;

public class Contact implements Comparable{
    private int id;
    private String name;
    private String lastName;
    private int age;
    private String phoneNumber;
    private boolean married;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd  hh:mm:ss");


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
//        String formattedDateTime = createDate.format(formatter);
//        this.createDate = LocalDateTime.parse(formattedDateTime, formatter);
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
//        String formattedDateTime = this.updateDate.format(formatter);
//        this.updateDate = LocalDateTime.parse(formattedDateTime, formatter);

        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (id != contact.id) return false;
        if (age != contact.age) return false;
        if (married != contact.married) return false;
        if (name != null ? !name.equals(contact.name) : contact.name != null) return false;
        if (lastName != null ? !lastName.equals(contact.lastName) : contact.lastName != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(contact.phoneNumber) : contact.phoneNumber != null) return false;
        if (createDate != null ? !createDate.equals(contact.createDate) : contact.createDate != null) return false;
        return updateDate != null ? updateDate.equals(contact.updateDate) : contact.updateDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (married ? 1 : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
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
    public int compareTo(Object o) {
        return 0;
    }
}
