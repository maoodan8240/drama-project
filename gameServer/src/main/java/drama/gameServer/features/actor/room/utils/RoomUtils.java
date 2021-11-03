package drama.gameServer.features.actor.room.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2021/10/22
 */
public class RoomUtils {
    public static List<Integer> parseListStringToListInteger(List<String> list) {
        List<Integer> arr = new ArrayList<>();
        for (String str : list) {
            arr.add(Integer.valueOf(str));
        }
        return arr;

    }

}
