import java.util.Comparator;

public class SortByArrivalTime implements Comparator<Process>{
    @Override
    public int compare(Process o1, Process o2) {
        return (int) (o1.getArrivalTime() - o2.getArrivalTime());
    }
}
