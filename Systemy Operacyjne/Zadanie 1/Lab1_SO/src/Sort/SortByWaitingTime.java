package Sort;

import Processes.Process;

import java.util.Comparator;

public class SortByWaitingTime implements Comparator<Process> {
    @Override
    public int compare(Process o1, Process o2) {
        if(o2.getWaitingTime()==o1.getWaitingTime()) return 0;
        return (int) (o2.getWaitingTime()-o1.getWaitingTime());
    }
}
