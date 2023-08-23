package Algorithm;

import Frames.Frame;
import Pages.Page;

import java.util.List;

public class FifoAlgo extends PageReplacementAlgo{

    public FifoAlgo(List<Frame> frameList, List<Page> sequence, List<Page> pageList, int deltaT, int upperBoundry){
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

                else {
                    replacePage(findOldestPageIndex(), page);
                }
            }
            incrementTimeInFrame();

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
        System.out.println("Page errors: " + pageErrorsCounter);
        if(struggleDetected){
            System.out.println("Struggle detected: " + amountOfExcessErrors + " exessive errors in total");
            System.out.println("Struggle occured: " + struggleCounter + " times");
        }
        else System.out.println("Struggle not detected");
     // calculation method
    }

    public int findOldestPageIndex(){
        Page oldestPage = frameList.get(0).getAssignedPage();
        int indexOfOldest = 0;
        for(int i=1; i<frameList.size(); i++){
            if(frameList.get(i).getAssignedPage().getTimeInFrame()>oldestPage.getTimeInFrame()){
                oldestPage = frameList.get(i).getAssignedPage();
                indexOfOldest = i;
            }
        }
        return indexOfOldest;
    }
}
