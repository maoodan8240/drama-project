import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lee on 17-5-9.
 */
public class SortTest {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        List<Integer> arr = new ArrayList<>();
        arr.add(3);
        arr.add(5);
        int i = randomShooter(arr);
        System.out.println(i);
    }

    private static int randomShooter(List<Integer> roleIds) {
        int roleId = 0;
        int sed = RANDOM.nextInt(roleIds.size());
        roleId = roleIds.get(sed);
        return roleId;
    }
}
