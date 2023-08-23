package Pages;

import Pages.Page;

import java.util.List;

public class Process {
    private List<Page> pageList;

    public Process(List<Page> pageList) {
        this.pageList = pageList;
    }

    public List<Page> getPageList() {
        return pageList;
    }

    public void setPageList(List<Page> pageList) {
        this.pageList = pageList;
    }
}
