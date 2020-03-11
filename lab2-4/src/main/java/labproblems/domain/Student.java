package labproblems.domain;

/**
 * @author Marius
 */

public class Student extends BaseEntity<Long> {
    private String serialNumber;
    private String name;
    private int group;

    public Student(){}

    public Student(String serialNumber, String name, int group) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.group = group;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getName() {
        return name;
    }

    public int getGroup() {
        return group;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return group == student.group &&
                serialNumber.equals(student.serialNumber) &&
                name.equals(student.name);
    }

    @Override
    public int hashCode() {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + group;
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", group=" + group +
                '}' + super.toString();
    }

    public String toFileString(){
        return "" + this.getId() + "," + this.getSerialNumber() + "," + this.getName() + "," + this.getGroup() + '\n';
    }
}
