package top.getidea.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageInfo {
    private List<QuestionDTO> questionDTOList; //页面的所有问题列表
    private boolean showPrevious; //前一页
    private boolean showFirst; //第一页
    private boolean showNext; //下一页
    private boolean showEnd; //最后一页
    private long currentPage; //当前页
    private List<Integer> pageList = new ArrayList<>();//页表
    private long totalPage; //页数总和
    private Integer count;

    // 判断当前页码
    public void init(long currentPage, long totalPage) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        for (int i = 0; i <= this.currentPage + 3 && i <= this.totalPage; i++) {
            if (i >= this.currentPage - 3 && i > 0 && i < this.currentPage) {
                this.pageList.add(i);
            }
            if (i >= this.currentPage && i <= this.currentPage + 3) {
                this.pageList.add(i);
            }
        }
        if (this.currentPage > 1) {//当前页不是第一页，展示前一页按钮
            this.showPrevious = true;
        }
        if (!pageList.contains(1)) {//不包含第一页，展示第一页按钮
            this.showFirst = true;
        }
        if (this.currentPage != totalPage) { //当前页不是最后一页，展示下一页按钮
            this.showNext = true;
        }
        if (!pageList.contains(totalPage)) {//不包含最后一页，展示最后一页按钮
            this.showEnd = true;
        }
    }
}
