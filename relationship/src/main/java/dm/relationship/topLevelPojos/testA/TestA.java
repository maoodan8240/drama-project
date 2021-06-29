package dm.relationship.topLevelPojos.testA;

import dm.relationship.topLevelPojos.PlayerTopLevelPojo;

/**
 * Created by lee on 16-9-18.
 */
public class TestA extends PlayerTopLevelPojo {
    private static final long serialVersionUID = -2094739928977920750L;

    private int a;

    public TestA() {
    }

    public TestA(String playerId) {
        super(playerId);
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
