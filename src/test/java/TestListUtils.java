import cc.canyi.core.utils.ListUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestListUtils {
    @Test
    public void testList() {

        List<String> testList = new ArrayList<>();

        testList.add("11111");
        testList.add("22222");
        testList.add("33333");
        testList.add("44444");
        testList.add("55555");

        System.out.println(ListUtils.getListRange(testList, 1, 3));
        System.out.println(ListUtils.removeListRange(testList, 1, 3));
    }
}
