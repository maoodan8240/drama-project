package dm.relationship.topLevelPojos.example;

import dm.relationship.topLevelPojos.PlayerTopLevelPojo;

public class Example extends PlayerTopLevelPojo {
    private static final long serialVersionUID = -5242637270959279767L;

    private String test = "test";
    private Child child = new Child();

    public Example() {

    }

    public Example(String playerId) {
        super(playerId);
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }
}
