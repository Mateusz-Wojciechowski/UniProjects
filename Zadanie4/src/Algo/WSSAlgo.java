package Algo;

import Frames.Frame;
import PageReplacementAlgo.LRUAlgo;
import Pages.Page;
import Pages.Process;

import java.util.ArrayList;
import java.util.List;

public class WSSAlgo extends PageAssigningAlgo {

    private int deltaT;
    int pageErrors = 0;
    private List<Frame> freeFrames;
    int counter1;
    boolean struggleDetected;
    int struggleCounter;
    int relativeErrors;
    int amountOfExcessErrors;
    public WSSAlgo(List<Page> globalSequence, List<Process> processList, List<Frame> frameList, int deltaT, int upperBoundry, int timeFrame){
        this.globalSequence = globalSequence;
        this.processList = processList;
        this.frameList = frameList;
        this.deltaT = deltaT;
        this.freeFrames = frameList;
        this.upperBoundry = upperBoundry;
        this.timeFrame = timeFrame;
        clearFrameList();
        clearProcessList();
        clearGlobalSequence();
    }
    @Override
    public void algo() {
        int counter = deltaT;
        List<Process> processQueue = new ArrayList<>();
        assignFramesEqually();

        for(Page page: globalSequence){

            if(counter==0){
                assignFrames(processQueue);
                counter = deltaT;
                clearWSS();
            }

            Process process = page.getAssignedProcess();
            incrementWSS(process, page);

            if(!page.getAssignedProcess().isFrozen()){
                incrementTimeFromLastUsage();
                page.setTimeFromLastUsage(0);

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
            }
            counter--;
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
        if(this.struggleDetected){
            System.out.println("Trashing detected: " + this.amountOfExcessErrors + " excessive errors in total");
            System.out.println("Trashing occured: " + this.struggleCounter + " times");
        }
    }

    public void clearWSS(){
        for(Process process:processList){
            process.setWSS(0);
            process.getSetOfRecentPages().clear();
        }
    }

    public void assignFrames(List<Process> processQueue){
        for(Process process: processList){
            if(process.getWSS()>process.getProcessFrames().size()){
                if(freeFrames.size()>=process.getWSS()-process.getProcessFrames().size()){
                    for(int i=process.getWSS()-process.getProcessFrames().size()-1; i>=0; i--){
                        process.getProcessFrames().add(freeFrames.get(i));
                        freeFrames.remove(i);
                    }
                }
                else{
                    freezeProcess(process, processQueue);
                }
            }

            else if(process.getWSS()<process.getProcessFrames().size() && process.getWSS()!=0){
                for(int i=process.getProcessFrames().size()-process.getWSS()-1; i>=0; i--){ // fix
                    freeFrames.add(process.getProcessFrames().get(i));
                    process.getProcessFrames().remove(i);
                }

                unfreezeProcess(processQueue);
            }

            else if(process.getWSS()==0){
                freezeProcess(process, processQueue);
            }
        }
    }

    public void freezeProcess(Process process, List<Process> processQueue){
        process.setFrozen(true);
        processQueue.add(process);
        process.setAmountOfFrames(process.getProcessFrames().size());
        for(int i=process.getProcessFrames().size()-1; i>=0; i--){
            freeFrames.add(process.getProcessFrames().get(i));
            process.getProcessFrames().remove(i);
        }
    }

    public void unfreezeProcess(List<Process> processQueue) {
        List<Process> processesToRemove = new ArrayList<>();
        for (int i = 0; i < processQueue.size(); i++) {
            if (processQueue.get(i).getAmountOfFrames() < freeFrames.size() && !freeFrames.isEmpty()) {
                processQueue.get(i).setFrozen(false);
                int size = freeFrames.size();
                for (int j = size - 1; j >= size - processQueue.get(i).getAmountOfFrames()-1; j--) { // ta petla dziala źle
                    processQueue.get(i).getProcessFrames().add(freeFrames.get(j));
                    freeFrames.remove(j);
                }

                for (Page page : processQueue.get(i).getWaitingRequests()) {
                    incrementTimeFromLastUsage();
                    page.setTimeFromLastUsage(0);
                    if (!containsPage(page, processQueue.get(i))) {
                        this.pageErrors++;
                        this.relativeErrors++;
                        if(this.relativeErrors==this.upperBoundry+1){
                            this.struggleCounter++;
                        }
                        if (hasFreeFrames(processQueue.get(i))) {
                            putInFrame(getFirstFreeIndex(processQueue.get(i)), page, processQueue.get(i));
                        } else {
                            int index = findLeastRecentlyUsed(processQueue.get(i));
                            replacePage(index, page, processQueue.get(i));
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
            }

            processQueue.get(i).getWaitingRequests().clear();
            processesToRemove.add(processQueue.get(i));
        }

        for(int i=processesToRemove.size()-1; i>=0; i--){
            processQueue.remove(processesToRemove.get(i));
        }
    }
    public void incrementWSS(Process process, Page page){
        for(Page page1: process.getSetOfRecentPages()){
            if(page1.getPageId()==page.getPageId()){
                return;
            }
        }
        process.getSetOfRecentPages().add(page);
        process.setWSS(process.getWSS()+1);
    }
    public boolean pageErrorOccured(Page page){
        Process process = page.getAssignedProcess();
        if(containsPage(page, process)) return false;
        else{
            process.getRecentErrors().add(1);
            return true;
        }
    }
    public void assignFramesEqually() {
        int totalFrames = frameList.size();
        int totalProcesses = processList.size();

        // Obliczanie liczby ramek przydzielanych równo dla każdego procesu
        int framesPerProcess = totalFrames / totalProcesses;

        // Przydział równy liczby ramek dla każdego procesu
        for (Process process : processList) {
            process.setAmountOfFrames(framesPerProcess);
        }

        // Przydzielenie dodatkowych ramek dla reszty procesów
        int remainingFrames = totalFrames - (framesPerProcess * totalProcesses);
        int processIndex = 0;
        while (remainingFrames > 0) {
            processList.get(processIndex).setAmountOfFrames(processList.get(processIndex).getAmountOfFrames() + 1);
            remainingFrames--;
            processIndex = (processIndex + 1) % totalProcesses;
        }

        // Sprawdzenie, czy każdy proces ma co najmniej jedną ramkę
        for (Process process : processList) {
            if (process.getAmountOfFrames() == 0) {
                process.setAmountOfFrames(1);
                framesPerProcess--;
                remainingFrames++;
                if (remainingFrames == 0) {
                    break;
                }
            }
        }

        // Przydzielenie ramek do procesów
        int frameIndex = 0;
        for (Process process : processList) {
            int framesToAllocate = process.getAmountOfFrames();
            while (framesToAllocate > 0 && frameIndex<frameList.size()) {
                process.getProcessFrames().add(frameList.get(frameIndex));
                frameIndex++;
                framesToAllocate--;
            }
        }
        freeFrames.clear();
    }
}
