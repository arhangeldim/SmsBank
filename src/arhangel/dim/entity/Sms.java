package arhangel.dim.entity;

/**
 *
 */
public class Sms {
    private String id;
    private String address;
    private String personId;
    private String message;
    private String date;
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", personId='" + personId + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
