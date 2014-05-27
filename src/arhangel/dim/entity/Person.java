package arhangel.dim.entity;

/**
 *
 */
public class Person {
    String contactId;
    String phoneNumber;
    String name;

    public Person() {
    }

    Person(String contactId, String phoneNumber, String name) {
        this.contactId = contactId;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "contactId=" + contactId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

