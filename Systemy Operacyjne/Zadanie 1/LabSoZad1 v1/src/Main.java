import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main { // github copilot // obiekty customowej klasy które zawierają listy // zamienic timeAtCreation na timeLefit

    public static void main(String[] args) {
       ArrayList<Process> generatedProcesses = new ArrayList<>();
       ArrayList<Process> done = new ArrayList<>();
       ArrayList<Process> active = new ArrayList<>();
       ArrayList<Process> active1 = new ArrayList<>();
       ArrayList<Process> done1 = new ArrayList<>();
        ArrayList<Process> active2 = new ArrayList<>();
        ArrayList<Process> done2 = new ArrayList<>();

         //generatedProcesses = ManagingProcesses.processesGenerator(10);
       generatedProcesses.add(new Process(1, 2, 3, false));
       generatedProcesses.add(new Process(2, 3, 4, false));
       generatedProcesses.add(new Process(3, 5, 6, false));
       generatedProcesses.add(new Process(4, 4, 8, false));
       generatedProcesses.add(new Process(5, 6, 2, false));
       ArrayList<Process> generatedProcesses1 = ManagingProcesses.createNextList(generatedProcesses);
       ArrayList<Process> generatedProcesses2 = ManagingProcesses.createNextList(generatedProcesses);

       System.out.println("FCFS: ");
       ExecutingProcesses.FCFSAlgo(generatedProcesses1, active1, done1);
       System.out.println();
       System.out.println("SJF");
       ExecutingProcesses.SJFAlgo(generatedProcesses, active, done);
       System.out.println();
       System.out.println("Round Robin");
       ExecutingProcesses.RoundRobinAlgo(generatedProcesses2, active2, done2);
    }
}