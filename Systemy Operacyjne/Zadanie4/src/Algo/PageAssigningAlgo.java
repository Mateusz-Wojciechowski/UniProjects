package Algo;

import Frames.Frame;
import PageReplacementAlgo.LRUAlgo;
import Pages.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import Pages.*;
import Pages.Process;

public abstract class PageAssigningAlgo {

    protected List<Page> globalSequence;
    protected List<Process> processList;
    protected List<Frame> frameList;
    protected int upperBoundry;
    protected int timeFrame;

    public abstract void algo();

    public static List<Page> generateLocalities(List<Page> pageList){ // trzeba zrobic dla wszystkich procesow po kolei
        Random generator = new Random();
        List<Integer> idList = new ArrayList<>();

        for(int i=0; i<pageList.size(); i++){
            idList.add(i);
        }

        Collections.shuffle(idList);

        int elementsInLocality = generator.nextInt(pageList.size()) + 1;
        List<Page> localPages = new ArrayList<>();

        for(int i=0; i<elementsInLocality; i++){
            localPages.add(pageList.get(idList.get(i)));
        }

        return localPages;
    }
    //method below can generate localSequence for all processes
    public static void generateSequence(List<Process> processList, int size, int localityDurationBound, int localityDurationOrigin){
        Random generator = new Random();
        boolean localitiesNow = true;
        int localityDuration = 0;
        List<Page> localityElements = new ArrayList<>();
         // ustawianie tej sekwencji zawsze zostanie wyclearowane

        for(Process process1: processList){
            List<Page> sequence = new ArrayList<>();
            for(int i=0; i<size ; i++){
                // local part
                if(localitiesNow){
                    if(localityDuration==0){
                        localityDuration = generator.nextInt(localityDurationBound, localityDurationOrigin);
                        localityElements = generateLocalities(process1.getPageList());
                    }

                    sequence.add(localityElements.get(generator.nextInt(localityElements.size())));
                    sequence.get(0).setAssignedProcess(process1);

                    localityDuration--;
                    if(localityDuration==0) localitiesNow=false;
                }

                else if(process1.getPageList().size()>0){
                    sequence.add(process1.getPageList().get(generator.nextInt(process1.getPageList().size())));
                    sequence.get(0).setAssignedProcess(process1);
                    if(generator.nextInt(0,100)>95) localitiesNow=true;
                }
            }
            process1.setLocalSequence(sequence);
        }

    }

    public static boolean globalSequenceReady(List<Process> processList){
        boolean allSequencesEmpty = true;

        for(Process process: processList){
            if (!process.getLocalSequence().isEmpty()) {
                allSequencesEmpty = false;
                break;
            }
            // break??
        }

        return allSequencesEmpty;
    }

    // da sie poprawic generator tak zeby sprawdzal czy lokalna sekwencja danego procesu
    // jest juz pusta
    public static List<Page> generateGlobalSequence(List<Process> processList){
        List<Page> globalSequence = new ArrayList<>();
        Random generator = new Random();

        while(!globalSequenceReady(processList)){
            int id = generator.nextInt(processList.size());

            if(!processList.get(id).getLocalSequence().isEmpty()){
                globalSequence.add(processList.get(id).getLocalSequence().get(0));
                processList.get(id).getLocalSequence().remove(0);
            }
        }
        return globalSequence;
    }

    public void replacePage(int index, Page newPage, Process process){
        process.getProcessFrames().get(index).setAssignedPage(newPage);
    }
    public void putInFrame(int frameId, Page newPage, Process process){
        process.getProcessFrames().get(frameId).setAssignedPage(newPage);
    }

    public boolean containsPage(Page page, Process process){
        for(Frame frame: process.getProcessFrames()){
            if(frame.getAssignedPage()!=null){
                if(frame.getAssignedPage().getPageId()==page.getPageId()) return true;
            }
        }
        return false;
    }

    public boolean hasFreeFrames(Process process){
        for(Frame frame: process.getProcessFrames()){
            if(frame.getAssignedPage()==null) return true;
        }
        return false;
    }

    public static int createPageList(List<Page> pageList, int listSize, Process process, int lastFrameId){
        for(int i=lastFrameId; i<lastFrameId+listSize; i++){
            pageList.add(new Page(i, process, 0));
        }
        return lastFrameId + listSize;
    }

    public static void createFrameList(List<Frame> frameList, int listSize){
        for(int i=0; i<listSize; i++){
            frameList.add(new Frame(i, null));
        }
    }

    public List<Frame> copyFrameList(List<Frame> frameList){
        List<Frame> newFrameList = new ArrayList<>();

        for(int i=0; i<frameList.size(); i++){
            newFrameList.add(new Frame(frameList.get(i).getFrameId(), null));
        }
        return newFrameList;
    }

    public List<Process> copyProcessList(List<Process> processList){
        List<Process> newProcessList = new ArrayList<>();

        for(int i=0; i<processList.size(); i++){
            newProcessList.add(new Process(processList.get(i).getPageList(), processList.get(i).getProcessId()));
        }
        return newProcessList;
    }

    public List<Page> copyGlobalSequence(List<Page> globalSequence){
        List<Page> newGlobalSequence = new ArrayList<>();

        for(int i=0; i<globalSequence.size(); i++){
            newGlobalSequence.add(globalSequence.get(i));
        }
        return newGlobalSequence;
    }

    public void clearFrameList(){
        for(int i=0; i<frameList.size(); i++){
            frameList.get(i).setAssignedPage(null);
        }
    }

    public void clearProcessList(){
        for(int i=0; i<processList.size(); i++){

            if(processList.get(i).getProcessFrames()!=null){
                processList.get(i).getProcessFrames().clear();
            }

            else{
                processList.get(i).setProcessFrames(new ArrayList<>());
            }

            if(processList.get(i).getRecentErrors()!=null){
                processList.get(i).getRecentErrors().clear();
            }
            if(processList.get(i).getWaitingRequests()!=null){
                processList.get(i).getWaitingRequests().clear();
            }

            processList.get(i).setPff(0);
            processList.get(i).setAmountOfFrames(0);
            processList.get(i).setFrozen(false);
        }
    }

    public void clearGlobalSequence(){
        for(Page page: globalSequence){
            page.setTimeFromLastUsage(0);
        }
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
            while (framesToAllocate > 0) {
                process.getProcessFrames().add(frameList.get(frameIndex));
                frameIndex++;
                framesToAllocate--;
            }
        }
    }

    public void performPageReplacement(){

        int pageErrorsCounter = 0;
        int struggleCounter = 0;
        boolean struggleDetected = false;
        int amountOfExcessErrors = 0;
        int counter = 0;
        int relativeErrors = 0;

        Process process;

        for(Page page: globalSequence){
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

            if(counter<timeFrame) counter++;
            else{
                counter = 0;
                relativeErrors = 0;
            }

        }
        System.out.println("Total page errors: " + pageErrorsCounter);
        if(struggleDetected){
            System.out.println("Trashing detected: " + amountOfExcessErrors + " excessive errors in total");
            System.out.println("Trashing occured: " + struggleCounter + " times");

        }

    }

    public void assignFramesProportionally() {
        int totalFrames = frameList.size();
        int totalProcesses = processList.size();

        // Obliczanie sumy wszystkich stron
        int totalPages = 0;
        for (Process process : processList) {
            totalPages += process.getPageList().size();
        }

        // Obliczanie minimalnej liczby ramek dla każdego procesu
        int minFramesPerProcess = totalProcesses;

        // Obliczanie proporcjonalnej liczby ramek dla każdego procesu
        int proportionalFramesPerProcess = totalFrames - minFramesPerProcess;

        // Przydział proporcjonalny liczby ramek dla każdego procesu
        for (Process process : processList) {
            int framesPerProcess = 1 + (int) Math.round((float) process.getPageList().size() / totalPages * proportionalFramesPerProcess);
            process.setAmountOfFrames(framesPerProcess);
        }

        // Sprawdzenie, ile ramek zostało już przydzielonych
        int allocatedFrames = minFramesPerProcess;
        for (Process process : processList) {
            allocatedFrames += process.getAmountOfFrames();
        }

        // Przydział dodatkowych ramek dla procesów, które otrzymały mniej niż jedną ramkę
        int remainingFrames = totalFrames - allocatedFrames;
        int additionalFramesPerProcess = Math.max(0, remainingFrames / totalProcesses);

        // Przydzielenie ramek do procesów
        int frameIndex = 0;
        for (Process process : processList) {
            int framesToAllocate = process.getAmountOfFrames();
            while (framesToAllocate > 0 && frameIndex < totalFrames) {
                process.getProcessFrames().add(frameList.get(frameIndex));
                frameIndex++;
                framesToAllocate--;
            }
            if (framesToAllocate > 0 && additionalFramesPerProcess > 0) {
                while (framesToAllocate > 0 && remainingFrames > 0) {
                    process.getProcessFrames().add(frameList.get(frameIndex));
                    frameIndex++;
                    framesToAllocate--;
                    remainingFrames--;
                }
            }
        }
    }





}
