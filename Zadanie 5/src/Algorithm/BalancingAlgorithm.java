package Algorithm;

import Comparator.ArrivalTimeComparator;
import Processor.Processor;
import Process.Process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class BalancingAlgorithm {

    protected List<Processor> processorList;
    protected List<Process> processSequence;
    protected List<Process> processQueue;
    protected int parametr;
    protected int searchParametr;
    protected int helpParametr;
    protected int migrations;
    protected int queries;
    public abstract void balancingAlgo();

    public static List<Processor> createProcessors(int amount, int totalPower, List<Processor> processorList){
        for(int i=0; i<amount; i++){
            processorList.add(new Processor(i, totalPower));
        }
        return processorList;
    }
    public static List<Process> createProcessSequence(int amount, int requiredPowerBound, int requiredPowerOrigin, int arrivalTimeOrigin, int requiredTimeBound, int requiredTimeOrigin, List<Process> processSequence){

        Random generator = new Random();
        for(int i=0; i<amount; i++){
            processSequence.add(new Process(generator.nextInt(requiredPowerBound, requiredPowerOrigin), generator.nextInt(requiredTimeBound, requiredTimeOrigin), generator.nextInt(arrivalTimeOrigin)));
        }
        Collections.sort(processSequence, new ArrivalTimeComparator());
        return processSequence;
    }

    public List<Processor> getZProcesses(int startingProcessId){

        List<Processor> copiedList = new ArrayList<>(processorList);
        Collections.shuffle(copiedList);

        List<Processor> processorsToAsk = new ArrayList<>();

        for(Processor processor: copiedList){
            if(processor.getProcessorId()!=startingProcessId){
                processorsToAsk.add(processor);
            }

            if(processorsToAsk.size()==searchParametr) break;
        }

        return processorsToAsk;
    }

    public boolean possibleGiveaway(Processor processor){
        if(processor.getPowerUsed()<parametr){
            return true;
        }
        return false;
    }

    public void assignProcess(Processor processor, Process process){
        processor.getProcessList().add(process);
        processor.setPowerUsed(processor.getPowerUsed()+process.getRequiredPower());
        processSequence.remove(process);
        process.setProcessorId(processor.getProcessorId());
    }


    public void decrementTimeLeft(){
        for(Processor processor: processorList){
            if(processor.getProcessList().size()>0){
                for(int i=0; i<processor.getProcessList().size(); i++){
                    processor.getProcessList().get(i).setRequiredTime(processor.getProcessList().get(i).getRequiredTime()-1);
                    if(processor.getProcessList().get(i).getRequiredTime()==0){
                        processor.setPowerUsed(processor.getPowerUsed() - processor.getProcessList().get(i).getRequiredPower());
                        processor.getProcessList().remove(processor.getProcessList().get(i));
                    }
                }
            }
        }
    }

    public int findSlave(Processor processor, Process process) {
        List<Processor> copiedProcessors = new ArrayList<>(processorList);
        Collections.shuffle(copiedProcessors);
        int counter = 0;

        for (Processor potentialSlave : copiedProcessors) {
            counter++;
            if (potentialSlave != processor && potentialSlave.getPowerUsed() < parametr) {
                potentialSlave.getProcessList().add(process);
                potentialSlave.setPowerUsed(potentialSlave.getPowerUsed() + process.getRequiredPower());
                process.setProcessorId(potentialSlave.getProcessorId());
                processSequence.remove(process);
                counter--;
                return counter;
            }
        }
        return counter;
    }

    public int findLoser(Processor processor){
        List<Processor> copiedProcessors = new ArrayList<>(processorList);
        Collections.shuffle(copiedProcessors);
        int counter = 0;

        for(Processor potentialLoser: copiedProcessors){
            if(potentialLoser != processor && potentialLoser.getPowerUsed()>parametr){
                int size = potentialLoser.getProcessList().size();
                int index = 0;
                for(int i=0; i<size; i++){
                    Process process = potentialLoser.getProcessList().get(index);
                    if(processor.getPowerUsed()+process.getRequiredPower()<=processor.getTotalPower()){
                        potentialLoser.setPowerUsed(potentialLoser.getPowerUsed() - process.getRequiredPower());
                        processor.getProcessList().add(process);
                        potentialLoser.getProcessList().remove(process);
                        processor.setPowerUsed(processor.getPowerUsed() + process.getRequiredPower());
                        process.setProcessorId(processor.getProcessorId());
                        counter++;
                    }
                    else{
                        index++;
                    }
                }
                break;
            }
        }
        return counter;
    }

    public List<Process> copyProcessSequence(List<Process> processSequence){
        List<Process> copiedProcesses = new ArrayList<>();
        for(Process process: processSequence){
            copiedProcesses.add(new Process(process.getRequiredPower(), process.getRequiredTime(), process.getArrivalTime()));
        }
        Collections.sort(copiedProcesses, new ArrivalTimeComparator());

        return copiedProcesses;
    }

    public void incrementSum(){
        for(Processor processor: processorList){
            processor.setAverageUsage(processor.getAverageUsage() + processor.getPowerUsed());
        }
    }

    public double calculateAverageUsage(int time){
        for(Processor processor: processorList){
            processor.setAverageUsage(processor.getAverageUsage()/time);
        }
        double sum = 0;
        for(Processor processor: processorList){
            sum += processor.getAverageUsage();
        }
        sum = sum/processorList.size();

        return sum;
    }

    public double calculateAverageDeviation(double sum){
        double averageDeviation = 0;
        for(Processor processor: processorList){
            processor.setAverageUsageDeviation(Math.abs(processor.getAverageUsage()-sum));
            averageDeviation+=Math.abs(processor.getAverageUsage()-sum);
        }

        averageDeviation = averageDeviation/processorList.size();
        return averageDeviation;
    }

    public List<Processor> copyProcessorList(List<Processor> processorList){
        List<Processor> copyList = new ArrayList<>();
        for(Processor processor: processorList){
            copyList.add(new Processor(processor.getProcessorId(), processor.getTotalPower()));
        }
        return copyList;
    }


}
