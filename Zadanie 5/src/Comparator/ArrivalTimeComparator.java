package Comparator;
import Process.Process;

import java.util.Comparator;

public class ArrivalTimeComparator implements Comparator<Process> {
    @Override
    public int compare(Process o1, Process o2) {
       if(o1.getArrivalTime()>o2.getArrivalTime()) return 1;
       if(o1.getArrivalTime()<o2.getArrivalTime()) return -1;
       else return 0;
    }
}
