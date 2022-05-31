package PersonRelated;

import java.io.Serializable;
import java.util.Objects;

public abstract class Entity implements Serializable {
    private String name;
    private String taxpayerID;
    private String phoneNumber;

    public Entity() {
    }

    public Entity(String name, String taxpayerID, String phoneNumber) {
        this.name = name;
        this.taxpayerID = taxpayerID;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(name, entity.name);
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxpayerID() {
        return taxpayerID;
    }

    public void setTaxpayerID(String taxpayerID) {
        this.taxpayerID = taxpayerID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Name:" + name;

    }
}
