package cc.canyi.core.gui.page;

import cc.canyi.core.gui.page.model.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PageManager {

    /**
     * 分页
     * @param maxNumberOfOnePage 一页最大
     * @param maxReturnSize 最大返回页数
     * @param objects 需要分页的数组
     * @param <OUT> 泛型
     * @return 页面列表
     */
    public static <OUT> List<Page> pageObjectList(int maxNumberOfOnePage, int maxReturnSize, OUT[] objects) {
        //bug fix
        if(maxNumberOfOnePage > objects.length){
            Page onePage = new Page(1, new HashMap<Integer, Object>());
            for(int i = 0; i < objects.length; i++) onePage.putObject(i, objects[i]);
            return Collections.singletonList(onePage);
        }
        int pageNumber = 1;
        int nowSize = 0;
        int index = 0;
        List<Page> pages = new ArrayList<>();
        while(nowSize < maxReturnSize && index < objects.length) {
            Page page = new Page(pageNumber, new HashMap<Integer, Object>());
            int templateIndex = 0;
            for(int i = index; i < index + maxNumberOfOnePage; i++) {
                //bug fix
                if(i >= objects.length) break;
                page.putObject(templateIndex ++, objects[i]);
            }
            index += maxNumberOfOnePage;
            pages.add(page);
            nowSize ++;
            pageNumber ++;
        }
        return pages;
    }

    /**
     * 分页
     * @param maxNumberOfOnePage 一页最大
     * @param maxReturnSize 最大返回页数
     * @param objects 需要分页的列表
     * @param <OUT> 泛型
     * @return 页面列表
     */
    public static <OUT> List<Page> pageObjectList(int maxNumberOfOnePage, int maxReturnSize, List<OUT> objects) {
        //bug fix
        if(maxNumberOfOnePage > objects.size()){
            Page onePage = new Page(1, new HashMap<Integer, Object>());
            for(int i = 0; i < objects.size(); i++) onePage.putObject(i, objects.get(i));
            return Collections.singletonList(onePage);
        }
        int pageNumber = 1;
        int nowSize = 0;
        int index = 0;
        List<Page> pages = new ArrayList<>();
        while(nowSize < maxReturnSize && index < objects.size()) {
            Page page = new Page(pageNumber, new HashMap<Integer, Object>());
            int templateIndex = 0;
            for(int i = index; i < index + maxNumberOfOnePage; i++) {
                //bug fix
                if(i >= objects.size()) break;
                page.putObject(templateIndex ++, objects.get(i));
            }
            index += maxNumberOfOnePage;
            pages.add(page);
            nowSize ++;
            pageNumber ++;
        }
        return pages;
    }

}
