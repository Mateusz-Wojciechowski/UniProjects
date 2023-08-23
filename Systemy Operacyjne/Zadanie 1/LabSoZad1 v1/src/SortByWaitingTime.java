import java.util.Comparator;

public class SortByWaitingTime implements Comparator<Process> {
    @Override
    public int compare(Process o1, Process o2) {
        return (int) (o2.getWaitingTime()-o1.getWaitingTime());
    }
}
