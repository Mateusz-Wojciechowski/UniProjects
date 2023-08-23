package Processes;

public class Process {
    private int arrivalTime;
    private int timeLeft;
    private int timeAtCreation;
    private int waitingTime;
    private int wholeWaitingTime;
    private int beginTime;
    private int doneTime;
    private boolean isDone;
    private boolean started;

    public Process(int arrivalTime, int timeLeft){
        this.arrivalTime = arrivalTime;
        this.timeLeft = timeLeft;
        this.started = false;
        this.isDone = false;
        this.timeAtCreation = timeLeft;
    }

    public boolean isDone() {
        return isDone;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getWaitingTime(){
        return waitingTime;
    }

    public int getDoneTime(){
        return doneTime;
    }

    public boolean isStarted() {
        return started;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void setDoneTime(int doneTime) {
        this.doneTime = doneTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getTimeAtCreation() {
        return timeAtCreation;
    }

    public void setTimeAtCreation(int timeAtCreation) {
        this.timeAtCreation = timeAtCreation;
    }

    public int getWholeWaitingTime() {
        return wholeWaitingTime;
    }

    public void setWholeWaitingTime(int wholeWaitingTime) {
        this.wholeWaitingTime = wholeWaitingTime;
    }
}
