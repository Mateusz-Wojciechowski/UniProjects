package Frames;

import Pages.Page;

public class Frame {

    private Page assignedPage;

    public Frame(Page assignedPage) {
        this.assignedPage = assignedPage;
    }

    public Page getAssignedPage() {
        return assignedPage;
    }

    public void setAssignedPage(Page assignedPage) {
        this.assignedPage = assignedPage;
    }
}
