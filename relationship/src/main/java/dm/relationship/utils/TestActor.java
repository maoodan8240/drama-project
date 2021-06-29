package dm.relationship.utils;

import dm.relationship.base.actor.DmActor;

import java.io.Serializable;

/**
 * Created by lee on 17-4-26.
 */
public class TestActor extends DmActor {

    @Override
    public void onRecv(Object msg) throws Exception {
        if (msg instanceof TestMsg) {
            int i = 0;
            while (true) {
                i++;
                Thread.sleep(1000l);
                System.out.println("正在执行...----" + i + "----" + ((TestMsg) msg).getId());
                if (i > 2) {
                    break;
                }
            }
        }
    }


    public static class TestMsg implements Serializable {
        private static final long serialVersionUID = -4825765170095341782L;
        private int id;

        public TestMsg(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
