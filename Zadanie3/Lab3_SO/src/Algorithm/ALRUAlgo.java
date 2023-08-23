package Algorithm;

import Frames.Frame;
import Pages.Page;

import java.util.List;

public class ALRUAlgo extends PageReplacementAlgo{

    public ALRUAlgo(List<Frame> frameList, List<Page> sequence, List<Page> pageList, int deltaT, int upperBoundry){
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
        int recentlyReplacedPageIndex = 0;
        boolean struggleDetected = false;
        int amountOfExcessErrors = 0;
        int counter = 0;
        int relativeErrors = 0;

        for(Page page:sequence){

            if(!containsPage(page)){
                pageErrorsCounter++;
                relativeErrors++;
                if(relativeErrors==upperBoundry+1){
                    struggleCounter++;
                }

                if(hasFreeFrames()){
                    putInFrame(getFirstFreeIndex(), page);
                    page.setReferenceBit(1);
                }

                else{
                    recentlyReplacedPageIndex = secondChanceSearch(recentlyReplacedPageIndex);
                    replacePage(recentlyReplacedPageIndex, page);
                    page.setReferenceBit(1);
                }
            }

            else{
                page.setReferenceBit(1);
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
        System.out.println("Page Errors: " + pageErrorsCounter);
        if(struggleDetected){
            System.out.println("Struggle detected: " + amountOfExcessErrors + " exessive errors in total");
            System.out.println("Struggle occured: " + struggleCounter + " times");
        }
        else System.out.println("Struggle not detected");
    }

    // trzeba zmienic na 1 jesli strona jest uzywana
    public int secondChanceSearch(int currentIndex){
        int replacedPageIndex = currentIndex;

        while(frameList.get(replacedPageIndex).getAssignedPage().getReferenceBit()!=0){
            frameList.get(replacedPageIndex).getAssignedPage().setReferenceBit(0);
            if(replacedPageIndex<frameList.size()-1) replacedPageIndex++;
            else replacedPageIndex = 0;
        }
        return replacedPageIndex;
    }


}
