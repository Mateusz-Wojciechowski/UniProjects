package Algorithm;

import Frames.Frame;
import Frames.PhysicalMemory;
import Pages.Page;
import Pages.Process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class PageReplacementAlgo {
    protected List<Page> sequence;
    protected List<Frame> frameList;
    protected List<Page> pageList;
    protected int deltaT;
    protected int upperBoundry;
    public abstract void algo();


    // page management methods
    public void replacePage(int index, Page newPage){
        frameList.get(index).getAssignedPage().setTimeInFrame(0);
        frameList.get(index).setAssignedPage(newPage);
    }

    public void putInFrame(int frameId, Page newPage){
        frameList.get(frameId).setAssignedPage(newPage);
    }

    public boolean containsPage(Page page){
        for(Frame frame: frameList){
            if(frame.getAssignedPage()!=null){
                if(frame.getAssignedPage().getPageId()==page.getPageId()) return true;
            }
        }
        return false;
    }

    public boolean hasFreeFrames(){
        for (Frame frame : frameList) {
            if (frame.getAssignedPage() == null) return true;
        }
        return false;
    }

    // sequence generating methods

    public static List<Page> generateLocalities(List<Page> pageList){
        Random generator = new Random();
        List<Integer> idList = new ArrayList<>();

        for(int i=0; i<pageList.size(); i++){
            idList.add(i);
        }

        Collections.shuffle(idList);

        int elementsInLocality = generator.nextInt(5, 15) ;
        List<Page> localPages = new ArrayList<>();

        for(int i=0; i<elementsInLocality; i++){
            localPages.add(pageList.get(idList.get(i)));
        }

        return localPages;
    }

    public static List<Page> generateSequence(List<Page> sequence, List<Page> pageList, int sequenceSize, int localityDurationBound, int localityDurationOrigin){
        Random generator = new Random();
        boolean localitiesNow = true;
        int localityDuration = 0;
        List<Page> localityElements = new ArrayList<>();

        for(int i=0; i<sequenceSize; i++){
            // local part
            if(localitiesNow){
                if(localityDuration==0){
                    localityDuration = generator.nextInt(localityDurationBound, localityDurationOrigin);
                    localityElements = generateLocalities(pageList);
                }

                sequence.add(localityElements.get(generator.nextInt(0, localityElements.size())));

                localityDuration--;
                if(localityDuration==0) localitiesNow=false;
            }

            else{
                sequence.add(pageList.get(generator.nextInt(0, pageList.size())));
                if(generator.nextInt(0,100)>95) localitiesNow=true;
            }
        }
        return sequence;
    }

    // list management methods

    public List<Frame> createNewFrameList(List<Frame> frameList){
        List<Frame> newList = new ArrayList<>();

        for(int i=0; i<frameList.size(); i++){
            newList.add(new Frame(null));
        }
        return newList;
    }

    public int getIndexOfPage(Page page){
        for(Frame frame: frameList){
            if(frame.getAssignedPage().getPageId()==page.getPageId()) return frameList.indexOf(frame);
        }
        return -1;
    }

    public int getFirstFreeIndex(){
        for(Frame frame:frameList){
            if(frame.getAssignedPage()==null){
                return frameList.indexOf(frame);
            }
        }
        return -1;
    }

    // for FIFO algo
    public void incrementTimeInFrame(){
        for(Frame frame: frameList){
            if(frame.getAssignedPage()!=null){
                frame.getAssignedPage().setTimeInFrame(frame.getAssignedPage().getTimeInFrame()+1);
            }
        }
    }

    // for LRU algo
    public void incrementTimeFromLastUsage(){
        for(Frame frame: frameList){
            if(frame.getAssignedPage()!=null){
                frame.getAssignedPage().setTimeFromLastUsage(frame.getAssignedPage().getTimeFromLastUsage()+1);
            }
        }
    }

    public static void createPageList(List<Page> pageList, int listSize){
        for(int i=0; i<listSize; i++){
            pageList.add(new Page(i, 0, 0, 0));
        }
    }

    public static void createFrameList(List<Frame> frameList, int listSize){
        for(int i=0; i<listSize; i++){
            frameList.add(new Frame(null));
        }
    }

}
