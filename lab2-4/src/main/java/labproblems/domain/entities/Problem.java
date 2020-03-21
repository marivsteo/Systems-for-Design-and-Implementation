package labproblems.domain.entities;

import java.util.Objects;

public class Problem extends BaseEntity<Long> {

    private int number;
    private String text;

    public Problem(){}

    public Problem(int _number, String _text){
        this.number = _number;
        this.text = _text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem = (Problem) o;
        return number == problem.number &&
                text.equals(problem.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, text);
    }

    @Override
    public String toString() {
        return "Problem{" +
                "number=" + number +
                ", text='" + text + '\'' +
                '}' + super.toString();
    }

    public String toFileString(){
        return this.getId() + "," + this.getNumber() + "," + this.getText() + "\n";
    }
}
