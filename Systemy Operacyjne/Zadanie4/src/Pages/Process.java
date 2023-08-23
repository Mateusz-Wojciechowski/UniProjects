package Pages;

import Frames.Frame;
import Pages.Page;

import java.util.ArrayList;
import java.util.List;

public class Process {
    private List<Page> pageList;
    private List<Page> localSequence;
    private List<Frame> processFrames;
    private int processId;
    private int amountOfFrames;
    private List<Integer> recentErrors;
    private int pff;
    private boolean isFrozen;
    private int WSS;
    private List<Page> waitingRequests;
    private List<Page> setOfRecentPages;

    public Process(List<Page> pageList, int processId) {
        this.pageList = pageList;
        this.localSequence = getLocalSequence();
        this.processFrames = new ArrayList<>();
        this.processId = processId;
        this.amountOfFrames = 0;
        this.pff = 0;
        this.isFrozen = false;
        this.recentErrors = new ArrayList<>();
        this.waitingRequests = new ArrayList<>();
        this.setOfRecentPages = new ArrayList<>();
        this.WSS = 0;
    }

    public List<Page> getPageList() {
        return pageList;
    }

    public void setPageList(List<Page> pageList) {
        this.pageList = pageList;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public List<Page> getLocalSequence() {
        return localSequence;
    }

    public void setLocalSequence(List<Page> localSequence) {
        this.localSequence = localSequence;
    }

    public List<Frame> getProcessFrames() {
        return processFrames;
    }

    public void setProcessFrames(List<Frame> processFrames) {
        this.processFrames = processFrames;
    }

    public int getAmountOfFrames() {
        return amountOfFrames;
    }

    public void setAmountOfFrames(int amountOfFrames) {
        this.amountOfFrames = amountOfFrames;
    }

    public int getPff() {
        return pff;
    }

    public void setPff(int pff) {
        this.pff = pff;
    }

    public List<Integer> getRecentErrors() {
        return recentErrors;
    }
    public void setRecentErrors(List<Integer> sumOfRecentErrors) {
        this.recentErrors = sumOfRecentErrors;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public List<Page> getWaitingRequests() {
        return waitingRequests;
    }

    public void setWaitingRequests(List<Page> waitingRequests) {
        this.waitingRequests = waitingRequests;
    }

    public int getWSS() {
        return WSS;
    }

    public void setWSS(int WSS) {
        this.WSS = WSS;
    }

    public List<Page> getSetOfRecentPages() {
        return setOfRecentPages;
    }

    public void setSetOfRecentPages(List<Page> setOfRecentPages) {
        this.setOfRecentPages = setOfRecentPages;
    }
}
