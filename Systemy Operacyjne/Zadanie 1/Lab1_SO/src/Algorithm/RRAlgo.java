package Algorithm;

import Processes.Process;

import java.lang.management.ManagementFactory;
import java.util.List;

public class RRAlgo extends Algorithm{

    public RRAlgo(List<Process> generatedProcesses, List<Process> activeProcesses, List<Process> finishedProcesses) {
        this.generatedProcesses = createNextList(generatedProcesses);
        this.activeProcesses = createNextList(activeProcesses);
        this.finishedProcesses = createNextList(finishedProcesses);
    }

    @Override
    public void performAlgo() {
        int timeQuantum = 3;
        System.out.println("Time quantum: " + timeQuantum);
        int indexInQueue = 0;

        int currentTime = 0;

        addToActive(currentTime);
        Algorithm.sortByArrival(activeProcesses);

        while(!(generatedProcesses.isEmpty() && activeProcesses.isEmpty())){
            if(!activeProcesses.isEmpty()){
                if(!activeProcesses.get(indexInQueue).isStarted()){
                    activeProcesses.get(indexInQueue).setStarted(true);
                    activeProcesses.get(indexInQueue).setBeginTime(currentTime);
                }

                if(activeProcesses.get(indexInQueue).getTimeLeft() <= timeQuantum){
                    currentTime+=activeProcesses.get(indexInQueue).getTimeLeft();
                    activeProcesses.get(indexInQueue).setTimeLeft(0);
                    checkIfDone(activeProcesses.get(indexInQueue), currentTime);
                    addToActive(currentTime);
                    Algorithm.sortByArrival(activeProcesses);
                    setNumberOfSwitches(getNumberOfSwitches() + 1);
                    if(indexInQueue>=activeProcesses.size()) indexInQueue=0;
                }

                else if(activeProcesses.get(indexInQueue).getTimeLeft() > timeQuantum){
                    currentTime+=timeQuantum;
                    activeProcesses.get(indexInQueue).setTimeLeft(activeProcesses.get(indexInQueue).getTimeLeft()-timeQuantum);
                    addToActive(currentTime);
                    Algorithm.sortByArrival(activeProcesses);
                    setNumberOfSwitches(getNumberOfSwitches() + 1);
                    if(indexInQueue<activeProcesses.size()-1) indexInQueue++;
                    else if(indexInQueue==activeProcesses.size()-1) indexInQueue = 0;
                }
            }

            else{
                currentTime++;
                addToActive(currentTime);
                Algorithm.sortByArrival(activeProcesses);
            }
        }
        calculateData();
    }
}
