package Algorithm;

import Processor.Processor;
import Process.Process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AmbitiousStudentAlgo extends BalancingAlgorithm{

    public AmbitiousStudentAlgo(List<Processor> processorList, List<Process> processSequence, int parametr, int searchParametr){
        this.processorList = copyProcessorList(processorList);
        this.processSequence = copyProcessSequence(processSequence);
        this.parametr = parametr;
        this.searchParametr = searchParametr;
        this.processQueue = new ArrayList<>();
    }

    @Override
    public void balancingAlgo() {
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
                int startingProcessId = generator.nextInt(0, processorList.size());
                process.setProcessorId(startingProcessId);
                if(processorList.get(startingProcessId).getPowerUsed()<parametr){
                    processorList.get(startingProcessId).getProcessList().add(process);
                    processorList.get(startingProcessId).setPowerUsed(processorList.get(startingProcessId).getPowerUsed() + process.getRequiredPower());
                    processSequence.remove(process);
                }

                else{
                    int n = findSlave(processorList.get(startingProcessId), process);
                    if(n==processorList.size()){
                        this.queries += n;
                        Processor processor = processorList.get(startingProcessId);
                        if(processor.getPowerUsed()+process.getRequiredPower()<=processor.getTotalPower()){
                            processor.getProcessList().add(process);
                            processor.setPowerUsed(processor.getPowerUsed()+process.getRequiredPower());
                            processSequence.remove(process);
                        }

                        else{
                            processSequence.remove(process);
                            processQueue.add(process);
                        }
                    }

                    else {
                        this.queries += n;
                        this.migrations++;
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

    public boolean tryToEmptyQueue(Process process){
        int startingProcessId = process.getProcessorId();
        Processor processor = processorList.get(startingProcessId);

        if(processorList.get(startingProcessId).getPowerUsed()<parametr){
            processorList.get(startingProcessId).getProcessList().add(process);
            processorList.get(startingProcessId).setPowerUsed(processorList.get(startingProcessId).getPowerUsed() + process.getRequiredPower());
            processQueue.remove(process);
            return true;
        }

        else{
            List<Processor> copiedProcessors = new ArrayList<>(processorList);
            Collections.shuffle(copiedProcessors);

            for (Processor potentialSlave : copiedProcessors) {
                this.queries++;
                if (potentialSlave != processor && potentialSlave.getPowerUsed() < parametr && potentialSlave.getPowerUsed()+process.getRequiredPower()<=potentialSlave.getTotalPower()) {
                    potentialSlave.getProcessList().add(process);
                    potentialSlave.setPowerUsed(potentialSlave.getPowerUsed() + process.getRequiredPower());
                    processQueue.remove(process);
                    this.migrations++;
                    return true;
                }
            }

            if(processor.getPowerUsed()+process.getRequiredPower()<=processor.getTotalPower()){
                processor.getProcessList().add(process);
                processor.setPowerUsed(processor.getPowerUsed()+process.getRequiredPower());
                processQueue.remove(process);
                return true;
            }
            return false;
        }
    }
}
