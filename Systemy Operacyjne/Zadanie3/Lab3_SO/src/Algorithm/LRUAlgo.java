package Algorithm;

import Frames.Frame;
import Pages.Page;

import java.util.List;

public class LRUAlgo extends PageReplacementAlgo{

    public LRUAlgo(List<Frame> frameList, List<Page> sequence, List<Page> pageList, int deltaT, int upperBoundry){
        this.frameList = createNewFrameList(frameList);
        this.sequence = sequence;
        this.pageList = pageList;
        this.deltaT = deltaT;
        this.upperBoundry = upperBoundry;
    }
    @Override
    public void algo() {
        int struggleCounter = 0;
        int pageErrorsCounter = 0;
        boolean struggleDetected = false;
        int amountOfExcessErrors = 0;
        int counter = 0;
        int relativeErrors = 0;

        for(Page page: sequence){
            incrementTimeFromLastUsage();
            page.setTimeFromLastUsage(0);
            if(!containsPage(page)){
                pageErrorsCounter++;
                relativeErrors++;
                if(relativeErrors==upperBoundry+1){
                    struggleCounter++;
                }

                if(hasFreeFrames()){
                    putInFrame(getFirstFreeIndex(), page);
                }

                else{
                    int index = findLeastRecentlyUsed();
                    replacePage(index, page);
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
        System.out.println("Page errors " + pageErrorsCounter);
        if(struggleDetected) {
            System.out.println("Struggle detected: " + amountOfExcessErrors + " exessive errors in total");
            System.out.println("Struggle occured: " + struggleCounter + " times");
        }

        else System.out.println("Struggle not detected");
    }

    public int findLeastRecentlyUsed(){
        int indexOfLRU = 0;
        for(int i=1; i<frameList.size(); i++){
            if(frameList.get(i).getAssignedPage().getTimeFromLastUsage()>frameList.get(indexOfLRU).getAssignedPage().getTimeFromLastUsage()){
                indexOfLRU = i;
            }
        }
        return indexOfLRU;
    }
}
