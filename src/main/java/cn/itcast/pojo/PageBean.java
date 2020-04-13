package cn.itcast.pojo;

import java.util.List;

/**
 * 封装分页所需参数数据
 */
public class PageBean<T>{

    /**当前页*/
    private Integer pageNum;
    /**每页显示的记录数*/
    private Integer pageSize;
    /**总条数*/
    private Long totalCount;
    /**总页数*/
    private Integer totalPage;
    /**当前页数据*/
    private List<T> pageList;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", pageList=" + pageList +
                '}';
    }
}
