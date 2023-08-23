package Processor;
import Process.Process;

import java.util.ArrayList;
import java.util.List;

public class Processor {
    private int processorId;
    private List<Process> processList;
    private int totalPower;
    private int powerUsed;
    private double averageUsage;
    private double averageUsageDeviation;

    public Processor(int processId, int totalPower) {
        this.processorId = processId;
        this.processList = new ArrayList<Process>();
        this.totalPower = totalPower;
    }

    public double getAverageUsage() {
        return averageUsage;
    }

    public void setAverageUsage(double averageUsage) {
        this.averageUsage = averageUsage;
    }

    public double getAverageUsageDeviation() {
        return averageUsageDeviation;
    }

    public void setAverageUsageDeviation(double averageUsageDeviation) {
        this.averageUsageDeviation = averageUsageDeviation;
    }

    public int getProcessorId() {
        return processorId;
    }

    public void setProcessorId(int processorId) {
        this.processorId = processorId;
    }

    public List<Process> getProcessList() {
        return processList;
    }

    public void setProcessList(List<Process> processList) {
        this.processList = processList;
    }

    public int getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(int totalPower) {
        this.totalPower = totalPower;
    }

    public int getPowerUsed() {
        return powerUsed;
    }

    public void setPowerUsed(int powerUsed) {
        this.powerUsed = powerUsed;
    }

}
