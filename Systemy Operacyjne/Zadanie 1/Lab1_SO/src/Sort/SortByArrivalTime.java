package Sort;

import Processes.Process;

import java.util.Comparator;

public class SortByArrivalTime implements Comparator<Process>{
    @Override
    public int compare(Process o1, Process o2) {
        if(o1.getArrivalTime() == o2.getArrivalTime()) return 0;
        return (int) (o1.getArrivalTime() - o2.getArrivalTime());
    }
}
