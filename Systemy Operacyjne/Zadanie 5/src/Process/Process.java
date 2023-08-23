package Process;

import Processor.Processor;

public class Process {
    private int requiredPower;
    private int requiredTime;
    private int arrivalTime;
    private int processorId;

    public Process(int requiredPower, int requiredTime, int arrivalTime) {
        this.requiredPower = requiredPower;
        this.requiredTime = requiredTime;
        this.arrivalTime = arrivalTime;
    }

    public int getRequiredPower() {
        return requiredPower;
    }

    public void setRequiredPower(int requiredPower) {
        this.requiredPower = requiredPower;
    }

    public int getRequiredTime() {
        return requiredTime;
    }

    public void setRequiredTime(int requiredTime) {
        this.requiredTime = requiredTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getProcessorId() {
        return processorId;
    }

    public void setProcessorId(int processorId) {
        this.processorId = processorId;
    }
}
