package com.power.common.model;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

/**
 * 主要用于前端内容展示分页,分页链接由后台输出
 * PageVo根据自定义的url直接生成链接，messageFormat占位替换生成页码
 * USAGE:
 * RequestMapping(value="news/p{pageIndex}.htm",method = RequestMethod.GET)
 * public String list(@PathVariable int pageIndex,Model model){
 * try{
 * PageVo pageDto = this.newsService.getPage(0,pageIndex,12);
 * pageDto.setUrl("news/p{0}.htm");
 * model.addAttribute("list",pageDto);
 * }catch (Exception e){
 * return "";
 * }
 * return "forward:/news.jsp";
 * }
 *
 * @param <T> Generics
 * @author sunyu
 */
public class PageVo<T> implements Serializable {
    private static final long serialVersionUID = 1923401118856169487L;

    /**
     * total record
     */
    private Long total;

    /**
     * 使用messageFormat占位替换
     */
    private String url;

    /**
     * 页码,当前页数
     */
    private Integer pageIndex;

    /**
     * 分页条数
     */
    private Integer pageSize;

    /**
     * 查询结果
     */
    private List<T> items;

    /**
     * 连续分页主体部分显示的分页条目数
     */
    private int displayEntries = 5;

    /**
     * 两侧显示的首尾分页的条目数
     */
    private int edgeEntries = 1;

    //default
    public PageVo() {
        super();
    }

    public PageVo(Long total, Integer pageIndex, Integer pageSize, List<T> items) {
        super();
        this.total = total;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.items = items;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PageVo [total=" + total + ", pageIndex=" + pageIndex
                + ", pageSize=" + pageSize + ", items=" + items + "]";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNavigation() {
        Integer totalPage = (int) (total % pageSize > 0 ? (total
                / pageSize + 1) : (total / pageSize));
        //From jQuery pagination
        //int ne_half = ((displayEntries&1)==1)?(displayEntries/2+1):displayEntries/2;
        int ne_half = ((displayEntries & 1) == 1) ? (displayEntries >> 1) + 1 : displayEntries >> 1;
        int upper_limit = totalPage - displayEntries;
        pageIndex = (pageIndex < 1) ? 1 : pageIndex;
        int currentPage = pageIndex - 1;

        int start = currentPage > ne_half ? Math.max(Math.min(currentPage - ne_half, upper_limit), 0) : 0;
        int end = currentPage > ne_half ? Math.min(currentPage + ne_half, totalPage) : Math.min(displayEntries, totalPage);
        StringBuffer buffer = new StringBuffer();
        buffer.append("<div	id=\"pagination\" class=\"pagination\">");
        // Generate "Previous"-Link
        if (pageIndex == 1 || totalPage == 0) {
            buffer.append("<span class=\"disabled\">上一页</span>");
        } else {
            String str = MessageFormat.format("<a href=\"" + this.url + "\"", pageIndex - 1);
            buffer.append(str).append(">上一页</a>");
        }
        // Generate starting points
        if (start > 0 && edgeEntries > 0) {
            int end1 = Math.min(edgeEntries, start);
            for (int i = 0; i < end1; i++) {
                String str = MessageFormat.format("<a href=\"" + this.url + "\"", i + 1);
                buffer.append(str).append(">").append(i + 1).append("</a>");
            }
            if (edgeEntries < start) {
                buffer.append("<span>...</span>");
            }
        }
        // Generate interval links
        for (int i = start; i < end; i++) {
            if (i + 1 == pageIndex) {
                //current page
                buffer.append("<span class=\"current\">").append(i + 1).append("</span>");
            } else {
                String str = MessageFormat.format("<a href=\"" + this.url + "\"", i + 1);
                buffer.append(str).append(">").append(i + 1).append("</a>");
            }
        }
        // Generate ending points
        if (end < totalPage && edgeEntries > 0) {
            if (totalPage - edgeEntries > end) {
                buffer.append("<span>...</span>");
            }
            int begin = Math.max(totalPage - edgeEntries, end);
            for (int i = begin; i < totalPage; i++) {
                String str = MessageFormat.format("<a href=\"" + this.url + "\"", i + 1);
                buffer.append(str).append(">").append(i + 1).append("</a>");
            }
        }
        // Generate "Next"-Link
        if (pageIndex == totalPage || totalPage == 0) {
            buffer.append("<span class=\"disabled\">下一页</span>");
        } else {
            String str = MessageFormat.format("<a href=\"" + this.url + "\"", pageIndex + 1);
            buffer.append(str).append(">下一页</a>");
        }
        buffer.append("</div>");
        return buffer.toString();
    }
}
