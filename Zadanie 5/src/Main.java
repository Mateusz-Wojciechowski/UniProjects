import Algorithm.AltruistStudentAlgo;
import Algorithm.AmbitiousStudentAlgo;
import Algorithm.BalancingAlgorithm;
import Algorithm.CleverStudentAlgo;
import Processor.Processor;
import Process.Process;

import java.util.ArrayList;
import java.util.List;

import static Algorithm.BalancingAlgorithm.createProcessSequence;
import static Algorithm.BalancingAlgorithm.createProcessors;

public class Main {
    public static void main(String[] args) {
        List<Processor> processorList = new ArrayList<>();
        List<Process> processSequence = new ArrayList<>();

        processorList = createProcessors(50, 100, processorList);
        processSequence = createProcessSequence(3000, 1, 30,350, 10, 40, processSequence);
        int parametr = 60;

        CleverStudentAlgo cleverStudentAlgo = new CleverStudentAlgo(processorList, processSequence, parametr, 5);
        AmbitiousStudentAlgo ambitiousStudentAlgo = new AmbitiousStudentAlgo(processorList, processSequence, parametr, 5);
        AltruistStudentAlgo altruistStudentAlgo = new AltruistStudentAlgo(processorList, processSequence, parametr, 5, 30);

        System.out.println("Clever Student Strategy:");
        cleverStudentAlgo.balancingAlgo();
        System.out.println("Ambitious Student Strategy:");
        ambitiousStudentAlgo.balancingAlgo();
        System.out.println("Altruist Student Strategy:");
        altruistStudentAlgo.balancingAlgo();
    }
}