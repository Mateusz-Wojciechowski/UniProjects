import java.util.ArrayList;
import java.util.Collections;

public class Calculations {
    public static void calculateData(ArrayList<Process> generatedProcesses, ArrayList<Process> active, ArrayList<Process> done){

        float averageWaitingTime = 0;
        float taskTime = 0;
        float longestWaitingTime = 0;

        averageWaitingTime = ManagingProcesses.findAverageTime(done);
        taskTime = ManagingProcesses.findDurationTime(done);

        Collections.sort(done, new SortByWaitingTime());
        longestWaitingTime = done.get(0).getWaitingTime();

        System.out.println("All processes took: " + taskTime);
        System.out.println("Average waiting time was: " + averageWaitingTime);
        System.out.println("Longest waiting time was: " + longestWaitingTime);

    }
}
