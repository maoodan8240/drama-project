package drama.gameServer.features.actor.room.utils;

import dm.relationship.base.MagicWords;

import java.util.ArrayList;
import java.util.Arrays;
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


    public static boolean isListString(String str) {
        return str.contains(MagicWords.CONFIG_LIST_SEPARATOR);
    }


    public static List<String> splitStringToList(String str) {
        String[] split = str.split(MagicWords.CONFIG_LIST_SEPARATOR);
        return Arrays.asList(split);
    }
}
