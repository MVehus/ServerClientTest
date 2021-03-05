package Test1;

import java.io.Serializable;

public class TestObject implements Serializable {
    int id;
    String name;

    public TestObject(){
        id = 1;
        name = "Test object";
    }

    @Override
    public String toString() {
        return name + " with id " + id;
    }
}
