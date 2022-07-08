import cc.canyi.core.gui.page.PageManager;
import cc.canyi.core.gui.page.model.Page;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TestPage {
    @Test
    public void test() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Page> pages = PageManager.pageObjectList(1, 11, integerList);
        System.out.println(pages);
        pages.forEach(Page::print);
    }
}
