package com.yimei.boot.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 11/3/14.
 */
public class Pager<T> implements Serializable {
    protected int count;
    protected int page;
    protected int pagesize;
    protected int maxpage;

    protected List<T> list;
    public Pager(int page, int pagesize, int count, List<T> list) {
        if(pagesize<1){
            pagesize=10;
        }
        this.count=count;
        this.page=page;
        this.pagesize=pagesize;
        this.list=list;
        this.maxpage=(int)Math.ceil((float)count/pagesize);
    }

    public Pager(Pager that){
        this(that.getPage(), that.getPagesize(), that.getCount(), that.getList());
    }

    public Pager(int count, LimitAndOffset<T> limitAndOffset, int page, int pagesize){
        this(page, pagesize, count, getListForConstruct(count, limitAndOffset, page, pagesize));
    }

    protected static <T> List<T> getListForConstruct(int count, LimitAndOffset<T> limitAndOffset, int page, int pagesize){
        List<T> list=null;
        int offset=(page-1)*pagesize;
        if(count>0 && count> offset){
            list=limitAndOffset.limit(pagesize, offset);
        }
        return list;
    }

    public void putToMap(Map<String, Object> map){
        map.put("count", count);
        map.put("page", page);
        map.put("pagesize", pagesize);
        map.put("maxpage", maxpage);
        map.put("dataList", list);
    }

    public int getCount() {
        return count;
    }

    public int getPage() {
        return page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public int getMaxpage() {
        return maxpage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public static interface LimitAndOffset<T>{
        public List<T> limit(int limit, int offset);
    }
    public static class PagerHelper<T> {
        protected int count;
        protected LimitAndOffset<T> limitAndOffset;
        public PagerHelper(int count, LimitAndOffset<T> limitAndOffset) {
            this.count=count;
            this.limitAndOffset=limitAndOffset;
        }
        public Pager<T> page(int page, int pagesize) {
            List<T> list=null;
            if(count % pagesize == 0 && page > count / pagesize){
                page = count / pagesize;
            } else if(count % pagesize != 0 && (page > count / pagesize + 1)){
                page = count / pagesize + 1;
            }
            if(page < 1){
                page = 1;
            }
            int offset=(page-1)*pagesize;
            if(count>0 && count>offset) {
                list = this.limitAndOffset.limit(pagesize, offset);
            }
            return new Pager<T>(page, pagesize, count, list);
        }
    }
    public static <T> PagerHelper<T> config(int count, LimitAndOffset<T> limitAndOffset){
        return new PagerHelper<T>(count, limitAndOffset);
    }
    public static <T> Pager<T> asPager(List<T> list){
        return new Pager<T>(1, 10, list.size(), list);
    }
    public static <T> Pager<T> wrapPager(T object){
        return asPager(object==null? null: Arrays.asList(object));
    }
	public static <T> Pager<T> wrapPager(List<T> object){
		return asPager(object==null? null: object);
	}
}
