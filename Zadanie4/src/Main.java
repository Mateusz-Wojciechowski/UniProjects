import Algo.*;
import Frames.Frame;
import PageReplacementAlgo.LRUAlgo;
import Pages.Page;
import Pages.Process;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int lastFrameId = 0;
        int size = 10;
        Random generator = new Random();

        List<Page> globalSequence = new ArrayList<>();
        List<Frame> frameList = new ArrayList<>();
        List<Process> processList = new ArrayList<>();

        List<List<Page>> allPageLists = new ArrayList<>();

        for(int i=0; i<size; i++){
            allPageLists.add(new ArrayList<>());
        }

        for(int i=0; i<size; i++){
            processList.add(new Process(allPageLists.get(i), i));
        }

        for(int i=0; i<size; i++){
            lastFrameId = PageAssigningAlgo.createPageList(allPageLists.get(i), generator.nextInt(30)+1, processList.get(i), lastFrameId);
        }

        PageAssigningAlgo.createFrameList(frameList, generator.nextInt(11, 20));


        PageAssigningAlgo.generateSequence(processList, 1000, 10, 30);

        globalSequence = PageAssigningAlgo.generateGlobalSequence(processList);



        EqualAlgo equalAlgo = new EqualAlgo(globalSequence, processList, frameList,3,5);
        System.out.println("Equal Algo:");
        equalAlgo.algo();
        System.out.println();
        ProportionalAlgo proportionalAlgo = new ProportionalAlgo(globalSequence, processList, frameList,3,5);
        System.out.println("Proportional Algo:");
        proportionalAlgo.algo();
        System.out.println();
        PFFAlgo1 pffAlgo1 = new PFFAlgo1(globalSequence, processList, frameList, 0, 4, 32,3,5);
        System.out.println("Pff Algo: ");
        pffAlgo1.algo();
        System.out.println();
        WSSAlgo wssAlgo = new WSSAlgo(globalSequence, processList, frameList, 20,3,5);
        System.out.println("WSS Algo");
        wssAlgo.algo();
    }
}