package com.yimei.boot.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jack on 14/12/2.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageUtils<T> implements Serializable {
  private int page = 1;
  private int pagesize = 10;
  //开始检索的地方
  private int indexNum;
  private int count;                 //总条数

  private List<T> list;

  public int getPage() {
    //如果page 太大，超过了 maxPage(最大页数),
    //如果pagesize > 10， 设置page 为最大页， 如果pagesize <= 10, 设置page 为第一页
    if (count % pagesize == 0 && page > (count / pagesize)) {
      this.page = count / pagesize;
    } else if (page > (count / pagesize + 1)) {
      this.page = count / pagesize + 1;
    }
    if (page == 0) page = 1;
    return this.page;
  }

  public int getIndexNum() {
    return (getPage() - 1) * getPagesize();
  }

  public void setPage(int page) {
    this.page = page < 1 ? 1 : page;
  }

}

