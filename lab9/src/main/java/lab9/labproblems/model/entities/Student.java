package lab9.labproblems.model.entities;

import javax.persistence.Entity;

/**
 * @author Marius
 */
@Entity
public class Student extends BaseEntity<Long> {
    private String serialNumber;
    private String name;
    private int groupNumber;

    public Student(){}

    public Student(String serialNumber, String name, int group) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.groupNumber = group;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getName() {
        return name;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroupNumber(int group) {
        this.groupNumber = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return groupNumber == student.groupNumber &&
                serialNumber.equals(student.serialNumber) &&
                name.equals(student.name);
    }

    @Override
    public int hashCode() {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + groupNumber;
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", group=" + groupNumber +
                '}' + super.toString();
    }

    public String toFileString(){
        return "" + this.getId() + "," + this.getSerialNumber() + "," + this.getName() + "," + this.getGroupNumber() + '\n';
    }
}
