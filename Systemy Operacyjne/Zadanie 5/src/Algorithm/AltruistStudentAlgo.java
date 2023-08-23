package Algorithm;

import Processor.Processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Process.Process;
import Processor.Processor;

public class AltruistStudentAlgo extends BalancingAlgorithm {

    public AltruistStudentAlgo(List<Processor> processorList, List<Process> processSequence, int parametr, int searchParametr, int helpParametr){
        this.processorList = copyProcessorList(processorList);
        this.processSequence = copyProcessSequence(processSequence);
        this.parametr = parametr;
        this.searchParametr = searchParametr;
        this.helpParametr = helpParametr;
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
                int startingProcessId = generator.nextInt(0, processorList.size());
                Process process = processSequence.get(0);
                process.setProcessorId(startingProcessId);
                if(processorList.get(startingProcessId).getPowerUsed()<parametr){
                    processorList.get(startingProcessId).getProcessList().add(process);
                    processorList.get(startingProcessId).setPowerUsed(processorList.get(startingProcessId).getPowerUsed() + process.getRequiredPower());
                    processSequence.remove(process);
                    if(processorList.get(startingProcessId).getPowerUsed()<searchParametr){
                        this.migrations+=findLoser(processorList.get(startingProcessId));
                    }
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
                            processQueue.add(process);
                            processSequence.remove(process);
                        }
                    }

                    else{
                        this.migrations++;
                        this.queries += n;
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
            List<Processor> copiedProcessors = new ArrayList<>(processorList);
            Collections.shuffle(copiedProcessors);

            for(Processor potentialLoser: copiedProcessors){
                if(potentialLoser != processor && potentialLoser.getPowerUsed()>parametr && processor.getPowerUsed()<helpParametr){
                    this.queries++;
                    int size = potentialLoser.getProcessList().size();
                    int index = 0;
                    for(int i=0; i<size; i++){
                        Process process1 = potentialLoser.getProcessList().get(index);
                        if(processor.getPowerUsed()+process.getRequiredPower()<processor.getTotalPower()){
                            potentialLoser.setPowerUsed(potentialLoser.getPowerUsed() - process1.getRequiredPower());
                            processor.getProcessList().add(process1);
                            potentialLoser.getProcessList().remove(process1);
                            processor.setPowerUsed(processor.getPowerUsed() + process1.getRequiredPower());
                            this.migrations++;
                        }
                        else{
                            index++;
                        }
                    }
                }
            }
            return true;
        }

        else{
            List<Processor> copiedProcessors = new ArrayList<>(processorList);
            Collections.shuffle(copiedProcessors);

            for (Processor potentialSlave : copiedProcessors) {
                this.queries++;
                if (potentialSlave != processor && potentialSlave.getPowerUsed() < parametr) {
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
