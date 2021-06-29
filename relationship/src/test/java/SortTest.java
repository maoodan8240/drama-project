import dm.relationship.utils.RelationshipCommonUtils;
import dm.relationship.utils.RelationshipCommonUtils.SortConditionValues;
import dm.relationship.utils.RelationshipCommonUtils.SortRuleEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 17-5-9.
 */
public class SortTest {


    public static void main(String[] args) {
        List<XX> xs = new ArrayList<>();
        xs.add(new XX(100, 110, 180, 190));
        xs.add(new XX(110, 100, 120, 110));
        xs.add(new XX(100, 140, 150, 100));
        xs.add(new XX(100, 110, 120, 190));
        xs.add(new XX(100, 110, 120, 170));
        xs.add(new XX(120, 100, 130, 100));
        xs.add(new XX(100, 170, 110, 110));
        xs.add(new XX(130, 100, 120, 110));
        System.out.println(xs);
        RelationshipCommonUtils.mutliConditionSort(xs, new SortConditionValues<XX>() {
            @Override
            public long[] compareValues(XX o1, XX o2) {
                long[] v = new long[4];
                v[0] = o1.getAge() - o2.getAge();
                v[1] = o1.getLv() - o2.getLv();
                v[2] = o1.getDate() - o2.getDate();
                v[3] = o1.getHeight() - o2.getHeight();
                return v;
            }
        }, SortRuleEnum.ESC);
        System.out.println(xs);

    }

    public static class XX {
        private int age;
        private int lv;
        private int date;
        private int height;

        public XX(int age, int lv, int date, int height) {
            this.age = age;
            this.lv = lv;
            this.date = date;
            this.height = height;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getLv() {
            return lv;
        }

        public void setLv(int lv) {
            this.lv = lv;
        }

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return "age:" + age + " lv:" + lv + " date:" + date + " height:" + height;
        }
    }
}
