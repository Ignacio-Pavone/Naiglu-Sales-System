package PersonRelated;

import java.io.Serializable;
import java.util.Objects;

public class Supplier extends Entity implements Serializable {
    private String workingArea;

    public Supplier() {
    }

    public Supplier(String name, String taxpayerID, String phoneNumber, String workingArea) {
        super(name, taxpayerID, phoneNumber);
        this.workingArea = workingArea;
    }

    public String getWorkingArea() {
        return workingArea;
    }


    @Override
    public int hashCode() {
        return 1;
    }

    public void setWorkingArea(String workingArea) {
        this.workingArea = workingArea;
    }

    @Override
    public String toString() {
        return super.toString() + "|| Working area: " + workingArea;
    }
}

