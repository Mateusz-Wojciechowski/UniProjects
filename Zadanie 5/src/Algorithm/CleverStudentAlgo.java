package Algorithm;

import Processor.Processor;
import Process.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CleverStudentAlgo extends BalancingAlgorithm{

    public CleverStudentAlgo(List<Processor> processorList, List<Process> processSequence, int parametr, int searchParametr){
        this.processorList = copyProcessorList(processorList);
        this.processSequence = copyProcessSequence(processSequence);
        this.parametr = parametr;
        this.searchParametr = searchParametr;
        this.processQueue = new ArrayList<>();
    }
    @Override
    public void balancingAlgo() {
        boolean processAssigned;
        Random generator = new Random();
        int time = 0;

        while(!processSequence.isEmpty() || !processQueue.isEmpty()){
            if(processSequence.isEmpty() || processSequence.get(0).getArrivalTime()>time){
                time++;
                decrementTimeLeft();
                incrementSum();
                if(!processQueue.isEmpty()){
                    int index = 0;
                    int size = processQueue.size();
                    for(int i=0; i<size; i++){
                        if(!tryToEmptyQueue(processQueue.get(index))){
                            index++;
                        }
                    }
                }
            }

            if(!processSequence.isEmpty() && processSequence.get(0).getArrivalTime()<=time){
                Process process = processSequence.get(0);
                processAssigned = false;
                int startingProcessId = generator.nextInt(0, processorList.size());
                process.setProcessorId(startingProcessId);
                List<Processor> processorsToAsk = getZProcesses(startingProcessId);

                for(Processor processor: processorsToAsk){
                    this.queries++;
                    if(possibleGiveaway(processor)){
                        assignProcess(processor, process);
                        processAssigned = true;
                        this.migrations++;
                        break;
                    }
                }

                if(!processAssigned){
                    if(processorList.get(startingProcessId).getPowerUsed()+process.getRequiredPower()<=processorList.get(startingProcessId).getTotalPower()){
                        processorList.get(startingProcessId).getProcessList().add(process);
                        processorList.get(startingProcessId).setPowerUsed(processorList.get(startingProcessId).getPowerUsed()+process.getRequiredPower());
                        processSequence.remove(process);
                    }
                    else{
                        processQueue.add(process);
                        processSequence.remove(process);
                    }
                }
            }
        }

        double averageUsage = (double) Math.round(calculateAverageUsage(time) * 100)/100;
        double averageUsageDeviation = (double) Math.round(calculateAverageDeviation(averageUsage) * 100)/100;

        System.out.println("Average usage: " + averageUsage);
        System.out.println("Average usage deviation: " + averageUsageDeviation);
        System.out.println("Migrations: " + this.migrations);
        System.out.println("Queries: " + this.queries);
    }

    public boolean tryToEmptyQueue(Process process) {
        int startingProcessId = process.getProcessorId();
        List<Processor> processorsToAsk = getZProcesses(startingProcessId);

        for (Processor processor : processorsToAsk){
            this.queries++;
            if (possibleGiveaway(processor)) {
                processor.getProcessList().add(process);
                processor.setPowerUsed(processor.getPowerUsed() + process.getRequiredPower());
                processQueue.remove(process);
                this.migrations++;
                return true;
            }
        }

        if (processorList.get(startingProcessId).getPowerUsed() + process.getRequiredPower() <= processorList.get(startingProcessId).getTotalPower()) {
            processorList.get(startingProcessId).getProcessList().add(process);
            processorList.get(startingProcessId).setPowerUsed(processorList.get(startingProcessId).getPowerUsed() + process.getRequiredPower());
            processQueue.remove(process);
            return true;
        }
        else {
            return false;
        }
    }
}
