import Algorithm.Algorithm;
import Algorithm.FCFSAlgo;
import Algorithm.RRAlgo;
import Algorithm.SJFAlgo;
import Processes.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    // dodac rozklad normalny, metoda generateProcesses (done)
    // poprawic komparator dla rownych wartosci (done)
    // naprawic time to finish dla RR (done)
    // czas oczekiwania od rozpoczęcia do zakończenia dla RR

    //
    public static void main(String[] args) {
        List<Process> generatedProcesses = new ArrayList<>();
        List<Process> activeProcessses = new ArrayList<>();
        List<Process> finishedProcesses = new ArrayList<>();

        // Procesy do potwierdzenia czasu dla konkretnych algorytmow

//        generatedProcesses.add(new Process(2, 3));
//        generatedProcesses.add(new Process(3, 4));
//        generatedProcesses.add(new Process(5, 6));
//        generatedProcesses.add(new Process(4, 8));
//        generatedProcesses.add(new Process(6, 2));

        generatedProcesses = Algorithm.generateProcesses(1000, 10, 10000, 8, 2);
        generatedProcesses.add(new Process(10, 1000));
        //generatedProcesses.add(new Process(10, 10000));
        //generatedProcesses.add(new Process(0,1000));
        //generatedProcesses.add(new Process(0,1000));

        FCFSAlgo fcfsAlgo = new FCFSAlgo(generatedProcesses, activeProcessses, finishedProcesses);
        SJFAlgo sjfAlgo = new SJFAlgo(generatedProcesses, activeProcessses, finishedProcesses);
        RRAlgo rrAlgo = new RRAlgo(generatedProcesses, activeProcessses, finishedProcesses);

        System.out.println("FCFS algo: ");
        fcfsAlgo.performAlgo();
        System.out.println("SJF algo: ");
        sjfAlgo.performAlgo();
        System.out.println("RR algo: ");
        rrAlgo.performAlgo();
    }
}