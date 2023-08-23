import java.util.Comparator;

public class SortByProcessLength implements Comparator<Process> {
    @Override
    public int compare(Process o1, Process o2) {
        return (int) (o1.getTimeAtCreation() - o2.getTimeAtCreation());
    }
}
