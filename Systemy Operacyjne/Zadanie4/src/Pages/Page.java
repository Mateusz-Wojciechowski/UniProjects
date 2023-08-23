package Pages;

public class Page {
    private int pageId;
    private Process assignedProcess;
    private int timeFromLastUsage;

    public Page(int pageId, Process assignedProcess, int timeFromLastUsage) {
        this.pageId = pageId;
        this.assignedProcess = assignedProcess;
        this.timeFromLastUsage = timeFromLastUsage;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public Process getAssignedProcess() {
        return assignedProcess;
    }

    public void setAssignedProcess(Process assignedProcess) {
        this.assignedProcess = assignedProcess;
    }

    public int getTimeFromLastUsage() {
        return timeFromLastUsage;
    }

    public void setTimeFromLastUsage(int timeFromLastUsage) {
        this.timeFromLastUsage = timeFromLastUsage;
    }
}
