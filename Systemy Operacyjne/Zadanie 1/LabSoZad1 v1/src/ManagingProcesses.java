import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;

public class ManagingProcesses {
    public static ArrayList<Process> processesGenerator(int amountOfProcesses){
        Random generator = new Random();
        ArrayList<Process> generatedProcesses = new ArrayList<>();
        int arrivalTime;
        int timeLeft;

        for(int i=0; i<amountOfProcesses; i++){
            arrivalTime = generator.nextInt(0, 9);
            timeLeft = generator.nextInt(0, 10);
            generatedProcesses.add(new Process(i, arrivalTime, timeLeft, false));
        }

        return generatedProcesses;
    }

    public static ArrayList<Process> createNextList(ArrayList<Process> generatedProcesses){
        ArrayList<Process> newProcessList = new ArrayList<>();

        for(Process process: generatedProcesses){
            newProcessList.add(new Process(process.getProcessId(), process.getArrivalTime(), process.getTimeAtCreation(), false));
        }
        return  newProcessList;
    }

    public static void addToActive(ArrayList<Process> notActive, ArrayList<Process> active, float currentTime){

        for(int i=0; i<notActive.size(); i++){
            if(notActive.get(i).getArrivalTime()<=currentTime){
                active.add(notActive.get(i));
                notActive.remove(notActive.get(i));
            }
        }
    }

    public static boolean checkIfDone(Process currentProcess, ArrayList<Process> active, ArrayList<Process> done, int currentTime){
        if(currentProcess.getTimeLeft()<=0){
            done.add(currentProcess);
            currentProcess.setDoneTime(currentTime-1);
            currentProcess.setWaitingTime(currentProcess.getBeginTime()-currentProcess.getArrivalTime());
            currentProcess.setDone(true);
            active.remove(currentProcess);
            return true;
        }

        return false;
    }

    public static void sortByArrival(ArrayList<Process> generatedProcesses){
        generatedProcesses.sort(new SortByArrivalTime());
    }

    public static ArrayList<Process> sortByLength(ArrayList<Process> generatedProcesses){
        generatedProcesses.sort(new SortByProcessLength());
        return generatedProcesses;
    }

    public static float findAverageTime(ArrayList<Process> done){
        float averageWaitingTime = 0;
        for(Process doneProcess: done){
            averageWaitingTime = averageWaitingTime + doneProcess.getWaitingTime();
        }
        averageWaitingTime = averageWaitingTime/done.size();
        return averageWaitingTime;
    }

    public static float findDurationTime(ArrayList<Process> done){
        float taskTime = 0;
        for(Process doneProcess: done){
            taskTime = taskTime + doneProcess.getTimeAtCreation();
        }
        return taskTime;
    }

    public static void addTime(ArrayList<Process> active, int index, int time){
        for(Process process: active){
            if(active.indexOf(process)!=index){
                process.setTimeToFinish(process.getTimeToFinish()+time);
            }
        }
    }

}
