package Algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Processes.*;
import Processes.Process;
import Sort.*;

public abstract class Algorithm {
    protected List<Process> generatedProcesses;
    protected List<Process> activeProcesses;
    protected List<Process> finishedProcesses;
    protected int numberOfSwitches;

    // abstract method
    public abstract void performAlgo();

    // list management methods

   public static List<Process> generateProcesses(int amount, int arrivalMean, int arrivalStddev, int timeLeftMean, int timeLeftStddev){
        Random generator = new Random();
        List<Process> generatedProcesses = new ArrayList<>();

        for(int i=0; i<amount; i++){
            generatedProcesses.add(new Process (Math.abs((int)generator.nextGaussian(arrivalMean, arrivalStddev)), Math.abs((int)generator.nextGaussian(timeLeftMean, timeLeftStddev))));
        }

        return generatedProcesses;
   }

    public void addToActive(int currentTime){
        for(int i=0; i<generatedProcesses.size(); i++){
            if(generatedProcesses.get(i).getArrivalTime()<=currentTime){
                activeProcesses.add(generatedProcesses.get(i));
                generatedProcesses.remove(generatedProcesses.get(i));
            }
        }
    }

    public boolean checkIfDone(Process currentProcess, int currentTime){
        if(currentProcess.getTimeLeft()==0){
            finishedProcesses.add(currentProcess);
            currentProcess.setDoneTime(currentTime);
            currentProcess.setWaitingTime(currentProcess.getBeginTime()-currentProcess.getArrivalTime());
            currentProcess.setWholeWaitingTime(currentProcess.getDoneTime() - currentProcess.getArrivalTime() - currentProcess.getTimeAtCreation());
            currentProcess.setDone(true);
            activeProcesses.remove(currentProcess);
            return true;
        }
        return false;
    }

    public ArrayList<Process> createNextList(List<Process> generatedProcesses){
        ArrayList<Process> newProcessList = new ArrayList<>();

        for(Process process: generatedProcesses){
            newProcessList.add(new Process(process.getArrivalTime(), process.getTimeLeft()));
        }
        return  newProcessList;
    }


    // calculation methods

    public float findAverageTime(){
        float averageWaitingTime = 0;
        for(Process doneProcess: finishedProcesses){
            averageWaitingTime = averageWaitingTime + doneProcess.getWaitingTime();
        }
        averageWaitingTime = averageWaitingTime/finishedProcesses.size();
        return averageWaitingTime;
    }

    public float findDurationTime(){
        int taskTime = 0;
        for(Process doneProcess: finishedProcesses){
            taskTime = taskTime + doneProcess.getTimeAtCreation();
        }
        return taskTime;
    }

    public float findAverageTimeToFinish(){
        float averageWaitingTime = 0;
        for(Process doneProcess: finishedProcesses){
            averageWaitingTime = averageWaitingTime + doneProcess.getWholeWaitingTime();
        }
        averageWaitingTime = averageWaitingTime/finishedProcesses.size();
        return averageWaitingTime;
    }

    public void calculateData(){

        float averageWaitingTime = 0;
        float taskTime = 0;
        float longestWaitingTime = 0;
        float longestWholeWaitingTime = 0;
        float averageWaitingTimeToFinish = 0;


        averageWaitingTime = findAverageTime();
        taskTime = findDurationTime();
        averageWaitingTimeToFinish = findAverageTimeToFinish();

        Collections.sort(finishedProcesses, new SortByWaitingTime());
        longestWaitingTime = finishedProcesses.get(0).getWaitingTime();
        Collections.sort(finishedProcesses, new SortByWholeWaitingTime());
        longestWholeWaitingTime = finishedProcesses.get(0).getWholeWaitingTime();



        System.out.println("All processes took: " + taskTime);
        System.out.println("Average waiting time (to begin) was: " + averageWaitingTime);
        System.out.println("Longest waiting time (to begin) was: " + longestWaitingTime);
        System.out.println("Average waiting time (to finish) was: " + averageWaitingTimeToFinish);
        System.out.println("Longest waiting time (to finish) was: " + longestWholeWaitingTime);
        System.out.println("Number of switches: " + numberOfSwitches);
    }

    //include add time method !!!


    // sorting methods
    public static void sortByArrival(List<Process> processesList){
        processesList.sort(new SortByArrivalTime());
    }

    public void sortByLength(List<Process> processList){
        processList.sort(new SortByProcessLength());
    }


    //// getters, setters
    public List<Process> getGeneratedProcesses() {
        return generatedProcesses;
    }

    public void setGeneratedProcesses(ArrayList<Process> generatedProcesses) {
        this.generatedProcesses = generatedProcesses;
    }

    public List<Process> getActiveProcesses() {
        return activeProcesses;
    }

    public void setActiveProcesses(ArrayList<Process> activeProcesses) {
        this.activeProcesses = activeProcesses;
    }

    public List<Process> getFinishedProcesses() {
        return finishedProcesses;
    }

    public void setFinishedProcesses(ArrayList<Process> finishedProcesses) {
        this.finishedProcesses = finishedProcesses;
    }

    public int getNumberOfSwitches() {
        return numberOfSwitches;
    }

    public void setNumberOfSwitches(int numberOfSwitches) {
        this.numberOfSwitches = numberOfSwitches;
    }
}
