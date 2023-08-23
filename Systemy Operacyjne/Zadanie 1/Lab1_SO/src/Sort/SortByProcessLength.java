package Sort;

import Processes.Process;

import java.util.Comparator;

public class SortByProcessLength implements Comparator<Process> {
    @Override
    public int compare(Process o1, Process o2) {

        if(o1.getTimeLeft() == o2.getTimeLeft()) return 0;
        return (int) (o1.getTimeLeft() - o2.getTimeLeft());
    }
}
