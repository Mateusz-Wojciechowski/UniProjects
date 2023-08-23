package Algo;

import Frames.Frame;
import PageReplacementAlgo.LRUAlgo;
import Pages.Page;
import Pages.Process;

import java.util.List;

public class EqualAlgo extends PageAssigningAlgo{

    public EqualAlgo(List<Page> globalSequence, List<Process> processList, List<Frame> frameList, int upperBoundry, int timeFrame){
        this.globalSequence = globalSequence; // copy the list
        this.processList = processList;
        this.frameList = frameList;
        this.upperBoundry = upperBoundry;
        this.timeFrame = timeFrame;
        clearFrameList();
        clearProcessList();
        clearGlobalSequence();
    }

    @Override
    public void algo() {
        assignFramesEqually();
        performPageReplacement();
    }
}
