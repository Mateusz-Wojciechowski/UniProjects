package PageReplacementAlgo;

import Frames.Frame;
import Pages.Page;
import Pages.Process;

import java.util.List;
import Algo.PageAssigningAlgo;

public class LRUAlgo extends PageAssigningAlgo{
    private int deltaT;
    private int upperBoundry;

    public LRUAlgo(List<Frame> frameList, int deltaT, int upperBoundry) {
        this.deltaT = deltaT;
        this.upperBoundry = upperBoundry;
    }

    @Override
    public void algo() {

    }

    public int performPageReplacement(List<Process> processList, List<Page> globalSequence, List<Frame> frameList){
        int struggleCounter = 0;
        int pageErrorsCounter = 0;
        boolean struggleDetected = false;
        int amountOfExcessErrors = 0;
        int counter = 0;
        int relativeErrors = 0;

        Process process;

        this.processList = processList;
        this.globalSequence = globalSequence;
        this.frameList = frameList;

        for(Page page: globalSequence){
            int id = page.getAssignedProcess().getProcessId();
            page.getAssignedProcess().setProcessFrames(processList.get(id).getProcessFrames());
            process = page.getAssignedProcess();
            incrementTimeFromLastUsage();
            page.setTimeFromLastUsage(0);
            if(!containsPage(page, process)){
                pageErrorsCounter++;
                relativeErrors++;
                if(relativeErrors==upperBoundry+1){
                    struggleCounter++;
                }

                if(hasFreeFrames(process)){
                    putInFrame(getFirstFreeIndex(process), page, process);
                }

                else{
                    int index = findLeastRecentlyUsed(process);
                    replacePage(index, page, process);
                }
            }
            if(relativeErrors>upperBoundry){
                struggleDetected = true;
                amountOfExcessErrors++;
            }

            if(counter<deltaT) counter++;
            else{
                counter = 0;
                relativeErrors = 0;
            }
        }

        return pageErrorsCounter;
//        System.out.println("Page errors " + pageErrorsCounter);
//        if(struggleDetected) {
//            System.out.println("Struggle detected: " + amountOfExcessErrors + " exessive errors in total");
//            System.out.println("Struggle occured: " + struggleCounter + " times");
//        }
//
//        else System.out.println("Struggle not detected");
    }

    public int findLeastRecentlyUsed(Process process){
        int indexOfLRU = 0;
        for(int i=1; i<process.getProcessFrames().size(); i++){
            if(process.getProcessFrames().get(i).getAssignedPage().getTimeFromLastUsage()>process.getProcessFrames().get(indexOfLRU).getAssignedPage().getTimeFromLastUsage()){
                indexOfLRU = i;
            }
        }
        return indexOfLRU;
    }

    public void incrementTimeFromLastUsage(){
        for(Frame frame: frameList){
            if(frame.getAssignedPage()!=null){
                frame.getAssignedPage().setTimeFromLastUsage(frame.getAssignedPage().getTimeFromLastUsage()+1);
            }
        }
    }

    public int getFirstFreeIndex(Process process){
        for(Frame frame: process.getProcessFrames()){
            if(frame.getAssignedPage()==null){
                return process.getProcessFrames().indexOf(frame);
            }
        }
        return -1;
    }

    public List<Page> getGlobalSequence() {
        return globalSequence;
    }

    public void setGlobalSequence(List<Page> globalSequence) {
        this.globalSequence = globalSequence;
    }

    public List<Process> getProcessList() {
        return processList;
    }

    public void setProcessList(List<Process> processList) {
        this.processList = processList;
    }

    public List<Frame> getFrameList() {
        return frameList;
    }

    public void setFrameList(List<Frame> frameList) {
        this.frameList = frameList;
    }

    public int getDeltaT() {
        return deltaT;
    }

    public void setDeltaT(int deltaT) {
        this.deltaT = deltaT;
    }

    public int getUpperBoundry() {
        return upperBoundry;
    }

    public void setUpperBoundry(int upperBoundry) {
        this.upperBoundry = upperBoundry;
    }
}
