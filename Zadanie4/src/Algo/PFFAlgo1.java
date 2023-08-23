package Algo;

import Frames.Frame;
import PageReplacementAlgo.LRUAlgo;
import Pages.Page;
import Pages.Process;

import java.util.ArrayList;
import java.util.List;

public class PFFAlgo1 extends PageAssigningAlgo {

    private int low;
    private int high;
    private int deltaT;
    int numberOfFreezes = 0;
    int numberOfGiven = 0;
    int counter1;
    boolean struggleDetected;
    int struggleCounter;
    int relativeErrors;
    int amountOfExcessErrors;

    int pageErrors = 0;
    private List<Frame> freeFrames;
    public PFFAlgo1(List<Page> globalSequence, List<Process> processList, List<Frame> frameList,  int low, int high, int deltaT, int upperBoundry, int timeFrame){
        this.globalSequence = globalSequence; // copy the list
        this.processList = processList;
        this.frameList = frameList;
        this.low = low;
        this.high = high;
        this.deltaT = deltaT;
        this.freeFrames = new ArrayList<>();
        this.upperBoundry = upperBoundry;
        this.timeFrame = timeFrame;
        clearFrameList();
        clearProcessList();
        clearGlobalSequence();
    }

    @Override
    public void algo() {
        int counter = deltaT;
        this.struggleCounter = 0;
        this.struggleDetected = false;
        this.amountOfExcessErrors = 0;
        this.counter1 = 0;
        this.relativeErrors = 0;
        List<Process> processQueue = new ArrayList<>();
        assignFramesProportionally();

        for(Page page: globalSequence){
            if(counter==0){
                giveFrames(processQueue);
                unfreezeProcess(processQueue);
                freezeProcess(processQueue);

                counter = deltaT;
            }

            if(!page.getAssignedProcess().isFrozen()){
                incrementTimeFromLastUsage();
                page.setTimeFromLastUsage(0);
                Process process = page.getAssignedProcess();
                if(pageErrorOccured(page)){

                    this.pageErrors++;
                    this.relativeErrors++;
                    if(this.relativeErrors==this.upperBoundry+1){
                        this.struggleCounter++;
                    }

                    if(hasFreeFrames(process)){
                        putInFrame(getFirstFreeIndex(process), page, process);
                    }

                    else{
                        int index = findLeastRecentlyUsed(process);
                        replacePage(index, page, process);
                    }

                    addZeros(process);
                }
                else{
                    addAllZeros();
                }

                if(this.relativeErrors>this.upperBoundry){
                    this.struggleDetected = true;
                    this.amountOfExcessErrors++;
                }

                if(this.counter1<this.timeFrame) this.counter1++;
                else{
                    this.counter1 = 0;
                    this.relativeErrors = 0;
                }
            }

            else{
                page.getAssignedProcess().getWaitingRequests().add(page);
                addAllZeros();
            }

            counter--;
            if(counter==0){
                calculatePFF();
                removeLastElement();
            }

        }

        for(Process process: processList){
            for(int i=0; i<process.getProcessFrames().size(); i++){
                freeFrames.add(process.getProcessFrames().get(i));
                process.getProcessFrames().remove(process.getProcessFrames().get(i));
            }
        }

        for(Process process: processList){
            this.pageErrors+=process.getWaitingRequests().size();
            if(!process.getWaitingRequests().isEmpty()){
                process.setProcessFrames(freeFrames);
                for (Page page : process.getWaitingRequests()) {

                    incrementTimeFromLastUsage();
                    page.setTimeFromLastUsage(0);
                    if (!containsPage(page, process)) {
                        this.pageErrors++;
                        this.relativeErrors++;
                        if(this.relativeErrors==this.upperBoundry+1){
                            this.struggleCounter++;
                        }

                        if (hasFreeFrames(process)) {
                            putInFrame(getFirstFreeIndex(process), page, process);
                        }
                        else {
                            int index = findLeastRecentlyUsed(process);
                            replacePage(index, page, process);
                        }
                    }
                    if(this.relativeErrors>this.upperBoundry){
                        this.struggleDetected = true;
                        this.amountOfExcessErrors++;
                    }

                    if(this.counter1<this.timeFrame) this.counter1++;
                    else{
                        this.counter1 = 0;
                        this.relativeErrors = 0;
                    }
                }
                process.getWaitingRequests().clear();
                process.setProcessFrames(null);
            }
        }



        System.out.println("Total page errors: " + this.pageErrors);
        System.out.println("Processes were frozen " + this.numberOfFreezes + " times");
        if(this.struggleDetected){
            System.out.println("Trashing detected: " + this.amountOfExcessErrors + " excessive errors in total");
            System.out.println("Trashing occured: " + this.struggleCounter + " times");
        }
    }

    public void freezeProcess(List<Process> processQueue){ // mozna wykorzystac free frames
        for(Process process:processList){
            if(process.getPff()>=high && !process.isFrozen()){
                process.setFrozen(true);
                processQueue.add(process);
                numberOfFreezes++;
                process.setAmountOfFrames(process.getProcessFrames().size());

                int size = process.getProcessFrames().size();
                for(int i=0; i<size; i++){
                    freeFrames.add(process.getProcessFrames().get(i));
                }

                for(int i=size-1; i>=0; i--){
                    process.getProcessFrames().remove(i);
                }
            }
        }
    }

    public void giveFrames(List<Process> processQueue){
        for(Process process:processList){
            if(process.getPff()<=low && !processQueue.isEmpty() && process.getProcessFrames().size()>1 && !process.isFrozen()){
               freeFrames.add(process.getProcessFrames().get(0));
               process.getProcessFrames().remove(0);
            }
        }
    }

    public void unfreezeProcess(List<Process> processQueue) {
        List<Process> processesToRemove = new ArrayList<>();
        for (Process process : processQueue) {
            if (process.isFrozen() && freeFrames.size() > process.getAmountOfFrames()) {
                process.setFrozen(false);
                for (int i = process.getAmountOfFrames(); i>=0; i--) {
                    process.getProcessFrames().add(freeFrames.get(i));
                    freeFrames.remove(i);
                }
                // wykonac wszystkie zalegle zapytania tego procesu
                for (Page page : process.getWaitingRequests()) {

                    incrementTimeFromLastUsage();
                    page.setTimeFromLastUsage(0);
                    if (!containsPage(page, process)) {
                        this.pageErrors++;
                        this.relativeErrors++;
                        if(this.relativeErrors==this.upperBoundry+1){
                            this.struggleCounter++;
                        }

                        if (hasFreeFrames(process)) {
                            putInFrame(getFirstFreeIndex(process), page, process);
                        }
                        else {
                            int index = findLeastRecentlyUsed(process);
                            replacePage(index, page, process);
                        }
                    }
                    if(this.relativeErrors>this.upperBoundry){
                        this.struggleDetected = true;
                        this.amountOfExcessErrors++;
                    }

                    if(this.counter1<this.timeFrame) this.counter1++;
                    else{
                        this.counter1 = 0;
                        this.relativeErrors = 0;
                    }
                }
                process.getWaitingRequests().clear();
                processesToRemove.add(process);
                this.numberOfGiven++;
            }
        }
        for(int i=processesToRemove.size()-1; i>=0; i--){
            processQueue.remove(processesToRemove.get(i));
        }
    }
    public boolean pageErrorOccured(Page page){
        Process process = page.getAssignedProcess();
        if(containsPage(page, process)) return false;
        else{
            process.getRecentErrors().add(1);
            return true;
        }
    }
    public void addAllZeros(){
        for(Process process:processList){
            process.getRecentErrors().add(0);
        }
    }
    public void addZeros(Process process1){
        for(Process process:processList){
            if(process!=process1){
                process.getRecentErrors().add(0);
            }
        }
    }
    public int calculateTheSum(Process process){
        int sum = 0;

        for(Integer integer:process.getRecentErrors()){
            sum += integer;
        }

        return sum;
    }
    public void calculatePFF(){
        for(Process process: processList){
            int sum = calculateTheSum(process);
            process.setPff(sum);
        }
    }
    public void removeLastElement(){
        for(Process process:processList){
            if(!process.getRecentErrors().isEmpty()){
                process.getRecentErrors().clear();
            }
        }
    }
}
