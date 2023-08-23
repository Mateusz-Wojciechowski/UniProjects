package Algorithm;

import Frames.Frame;
import Pages.Page;

import java.util.List;
import java.util.Random;

public class RANDAlgo extends PageReplacementAlgo {
    public RANDAlgo(List<Frame> frameList, List<Page> sequence, List<Page> pageList, int deltaT, int upperBoundry){
        this.frameList = createNewFrameList(frameList);
        this.sequence = sequence;
        this.pageList = pageList;
        this.deltaT = deltaT;
        this.upperBoundry = upperBoundry;
    }

    @Override
    public void algo() {

        int pageErrorsCounter = 0;
        boolean struggleDetected = false;
        int amountOfExcessErrors = 0;
        int counter = 0;
        int struggleCounter = 0;
        int relativeErrors = 0;

        Random generator = new Random();

        for(Page page: sequence){
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
                    replacePage(generator.nextInt(frameList.size()), page);
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
        System.out.println("Page Errors: " + pageErrorsCounter);
        if(struggleDetected){
            System.out.println("Struggle detected: " + amountOfExcessErrors + " exessive errors in total");
            System.out.println("Struggle occured: " + struggleCounter + " times");
        }
        else System.out.println("Struggle not detected");
    }
}
