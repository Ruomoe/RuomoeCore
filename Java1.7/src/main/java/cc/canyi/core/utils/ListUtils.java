package cc.canyi.core.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    public static <T> boolean isListEquals(List<T> list1, List<T> list2) {
        if(list1.size() != list2.size()) return false;
        return list1.containsAll(list2) && list2.containsAll(list1);
    }


    /**
     * 快速判断两个List是否一致 前提为必须存在顺序一致 两个List 完全一致
     * @param list1 list1
     * @param list2 list2
     * @param <T> 泛型
     * @return 是否一致
     */
    @Deprecated
    public static <T> boolean isListEqualsFast(List<T> list1, List<T> list2) {
        if(list1.size() != list2.size()) return false;
        for(int i = 0; i < list1.size(); i++) {
            if(!list1.get(i).equals(list2.get(i))) return false;
        }
        return true;
    }

    public static <T> List<T> getListRange(List<T> list, int start, int end) {
        List<T> returnList = new ArrayList<>();
        for(int i = start; i <= end; i++) {
            returnList.add(list.get(i));
        }
        return returnList;
    }

    public static <T> List<T> removeListRange(List<T> list, int start, int end) {
        /*
            11111
            22222
            33333
            44444
            55555
            66666

            index: 1 - 3
         */
        int step = end + 1 - start;
        //old
        //while(step -- > 0) list.remove(start);
        //优化效率
        while(step -- > 0) {
            list.remove(end);
            end --;
        }
        return list;
    }



}
