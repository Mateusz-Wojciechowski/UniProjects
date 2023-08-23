package Sort;

import Processes.Process;

import java.util.Comparator;

public class SortByWholeWaitingTime implements Comparator<Process> {
    @Override
    public int compare(Process o1, Process o2) {
        if(o2.getWholeWaitingTime()==o1.getWholeWaitingTime()) return 0;
        return (int) (o2.getWholeWaitingTime()-o1.getWholeWaitingTime());
    }
}
