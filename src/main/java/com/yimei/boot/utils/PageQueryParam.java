package com.yimei.boot.utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jack on 14/12/2.
 */

public class PageQueryParam<T> implements Serializable {
  private int page = 1;
  private int pagesize = 10;
  //前多少页
  private int rowNum;

  // 总记录数
  private Integer totalCount;
  // 总页数
  private Integer totalPage;

  private List<T> list;

  //开始检索的地方
  private int indexNum;

  private int count;                 //总条数

  public PageQueryParam() {
  }

  public int getPage() {
    //如果page 太大，超过了 maxPage(最大页数),
    //如果pagesize > 10， 设置page 为最大页， 如果pagesize <= 10, 设置page 为第一页
//    if (this.pagesize > 10) {
//      if (count % pagesize == 0 && page > (count / pagesize)) {
//        page = count / pagesize;
//      } else if (page > (count / pagesize + 1)) {
//        page = count / pagesize + 1;
//      }
//    }
//    if (pagesize < 10 && (count % pagesize == 0 && page > (count / pagesize)) || (count % pagesize != 0 && page > (count / pagesize + 1))) {
//      page = 1;
//    }
//    if (page < 1) page = 1;
    return this.page;
  }

  public int getIndexNum() {
    this.indexNum = (getPage() - 1) * getPagesize();
    return indexNum;
  }

  public void setPage(int page) {

    this.page = page < 1 ? 1 : page;
  }

  public int getPagesize() {
    return pagesize;
  }

  public void setPagesize(int pagesize) {
    this.pagesize = pagesize;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public PageQueryParam(int page) {
    this.page = page;
  }

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public Integer getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(Integer totalPage) {
    this.totalPage = totalPage;
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public int getRowNum() {
    return this.page * this.getPagesize();
  }

}

