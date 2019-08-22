package entity;

import java.time.LocalDateTime;

public class Contact implements Comparable{
    private int id;
    private String name;
    private String lastName;
    private int age;
    private String phoneNumber;
    private boolean married;
    private LocalDateTime createDate;
    private LocalDateTime updateTime;

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
        this.updateTime = updateTime;
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

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
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
        return updateTime != null ? updateTime.equals(contact.updateTime) : contact.updateTime == null;
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
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "ID=" + id +
                ", Name='" + name + '\'' +
                ", Last Name='" + lastName + '\'' +
                ", Age=" + age +
                ", PhoneNumber='" + phoneNumber + '\'' +
                ", Status=" + (married ? "Married" : "No married") +
                ", CreateDate=" + createDate +
                ", UpdateDate=" + updateTime +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
