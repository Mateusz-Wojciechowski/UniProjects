package Pages;

public class Page {
    private int pageId;
    private int timeInFrame;
    private int timeFromLastUsage;
    private int referenceBit;

    public Page(int pageId, int timeInFrame, int timeFromLastUsage, int referenceBit) {
        this.pageId = pageId;
        this.timeInFrame = timeInFrame;
        this.timeFromLastUsage = timeFromLastUsage;
        this.referenceBit = referenceBit;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getTimeInFrame() {
        return timeInFrame;
    }

    public void setTimeInFrame(int arrivalTime) {
        this.timeInFrame = arrivalTime;
    }

    public int getTimeFromLastUsage() {
        return timeFromLastUsage;
    }

    public void setTimeFromLastUsage(int timeFromLastUsage) {
        this.timeFromLastUsage = timeFromLastUsage;
    }

    public int getReferenceBit() {
        return referenceBit;
    }

    public void setReferenceBit(int referenceBit) {
        this.referenceBit = referenceBit;
    }
}
