package Frames;
import Pages.*;

public class Frame {
    private int frameId;
    private Page assignedPage;

    public Frame(int frameId, Page assignedPage) {
        this.frameId = frameId;
        this.assignedPage = assignedPage;
    }

    public int getFrameId() {
        return frameId;
    }

    public void setFrameId(int frameId) {
        this.frameId = frameId;
    }

    public Page getAssignedPage() {
        return assignedPage;
    }

    public void setAssignedPage(Page assignedPage) {
        this.assignedPage = assignedPage;
    }
}
