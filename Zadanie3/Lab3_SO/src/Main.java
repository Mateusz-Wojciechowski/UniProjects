import Algorithm.*;
import Frames.Frame;
import Pages.Page;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // dodac zliczanie dlugosci przebywania w ramce
        // wyzerowac czasy po fifo
        // wyzerowac timeFromLastUsage po LRU

        // sprawdzic czy ilosc elementow lokalnosci jest ustawiona poprawnie

        int deltaT = 50;
        int upperBoundry = 30;

        List<Page> pageList = new ArrayList<>();
        PageReplacementAlgo.createPageList(pageList, 50);

        List<Frame> frameList = new ArrayList<>();
        PageReplacementAlgo.createFrameList(frameList, 10);


        List<Page> sequence = new ArrayList<>();
        sequence = PageReplacementAlgo.generateSequence(sequence, pageList, 10000, 10, 30);

        FifoAlgo fifoAlgo = new FifoAlgo(frameList, sequence, pageList, deltaT, upperBoundry);
        System.out.println("FIFO algo:");
        fifoAlgo.algo();
        System.out.println();
        OptAlgo optAlgo = new OptAlgo(frameList, sequence, pageList, deltaT, upperBoundry);
        System.out.println("OPT algo:");
        optAlgo.algo();
        System.out.println();
        LRUAlgo lruAlgo = new LRUAlgo(frameList, sequence, pageList, deltaT, upperBoundry);
        System.out.println("LRU algo:");
        lruAlgo.algo();
        System.out.println();
        RANDAlgo randAlgo = new RANDAlgo(frameList, sequence, pageList, deltaT, upperBoundry);
        System.out.println("RAND algo");
        randAlgo.algo();
        System.out.println();
        System.out.println("ALRU algo:");
        ALRUAlgo alruAlgo = new ALRUAlgo(frameList, sequence, pageList, deltaT, upperBoundry);
        alruAlgo.algo();
    }
}