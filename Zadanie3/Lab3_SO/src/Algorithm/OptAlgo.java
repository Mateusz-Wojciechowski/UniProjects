package Algorithm;

import Frames.Frame;
import Pages.Page;

import java.util.ArrayList;
import java.util.List;

public class OptAlgo extends PageReplacementAlgo{

    public OptAlgo(List<Frame> frameList, List<Page> sequence, List<Page> pageList, int deltaT, int upperBoundry){
        this.frameList = createNewFrameList(frameList);
        this.sequence = sequence;
        this.pageList = pageList;
        this.deltaT = deltaT;
        this.upperBoundry = upperBoundry;
    }
    @Override
    public void algo() {

        boolean struggleDetected = false;
        int struggleCounter = 0;
        int amountOfExcessErrors = 0;
        int counter = 0;
        int relativeErrors = 0;
        int pageErrorsCounter = 0;
        int indexCounter = 0;

        for (Page page : sequence) {
            if (!containsPage(page)) {
                pageErrorsCounter++;
                relativeErrors++;
                if(relativeErrors==upperBoundry+1){
                    struggleCounter++;
                }
                if (hasFreeFrames()){
                    putInFrame(getFirstFreeIndex(), page);
                }

                else{
                    int index = findLastlyUsedIndex(indexCounter);
                    replacePage(index, page);
                }
            }
            indexCounter++;

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
        System.out.println("Page errors " + pageErrorsCounter);
        if(struggleDetected){
            System.out.println("Struggle detected: " + amountOfExcessErrors + " exessive errors in total");
            System.out.println("Struggle occured: " + struggleCounter + " times");
        }
        else System.out.println("Struggle not detected");

    }

    public int findLastlyUsedIndex(int startingSequenceIndex){
        List<Page> pagesInMemory = new ArrayList<>();
        for(Frame frame: frameList){
            pagesInMemory.add(frame.getAssignedPage());
        }

        for(int i=startingSequenceIndex; i<sequence.size(); i++){
            if(pagesInMemory.size()==1) break;
            if(containsPage(sequence.get(i))){
                pagesInMemory.remove(sequence.get(i));
            }
        }

        return getIndexOfPage(pagesInMemory.get(0));
    }
}
