package Algo;

import Frames.Frame;
import PageReplacementAlgo.LRUAlgo;
import Pages.Page;
import Pages.Process;

import javax.swing.event.InternalFrameEvent;
import java.util.List;

public class ProportionalAlgo extends PageAssigningAlgo{
    public ProportionalAlgo(List<Page> globalSequence, List<Process> processList, List<Frame> frameList, int upperBoundry, int timeFrame){
        this.globalSequence = globalSequence;
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
        assignFramesProportionally();
        performPageReplacement();
    }
}
