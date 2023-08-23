package Algorithm;

import Processes.Process;

import java.util.ArrayList;
import java.util.List;

public class FCFSAlgo extends Algorithm{

    public FCFSAlgo(List<Process> generatedProcesses, List<Process> activeProcesses, List<Process> finishedProcesses) {
        this.generatedProcesses = createNextList(generatedProcesses);
        this.activeProcesses = createNextList(activeProcesses);
        this.finishedProcesses = createNextList(finishedProcesses);
    }

    @Override
    public void performAlgo() {
        int currentTime = 0;
        addToActive(currentTime);

        if(!activeProcesses.isEmpty()) activeProcesses.get(0).setBeginTime(currentTime);

        while(!(generatedProcesses.isEmpty() && activeProcesses.isEmpty())){

            if(!activeProcesses.isEmpty()){
                activeProcesses.get(0).setTimeLeft(activeProcesses.get(0).getTimeLeft()-1);
                currentTime++;
                addToActive(currentTime);

                if(checkIfDone(activeProcesses.get(0), currentTime)){
                    if(!activeProcesses.isEmpty()){
                        setNumberOfSwitches(getNumberOfSwitches() + 1);
                        activeProcesses.get(0).setBeginTime(currentTime);
                    }
                }
            }

            else{
                currentTime++;
                addToActive(currentTime);
                if(!activeProcesses.isEmpty()){
                    setNumberOfSwitches(getNumberOfSwitches() + 1);
                    activeProcesses.get(0).setBeginTime(currentTime);
                }
            }
        }

        calculateData();
    }
}
