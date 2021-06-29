package dm.relationship.topLevelPojos.example;

import java.io.Serializable;

public class Child implements Serializable {
    private static final long serialVersionUID = -6424985842965572586L;

    private String test = "test";

    public Child() {
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
