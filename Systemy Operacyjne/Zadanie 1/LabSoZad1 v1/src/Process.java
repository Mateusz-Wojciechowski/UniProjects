public class Process{

    private int processId;
    private int arrivalTime;
    private int timeLeft;
    private int timeAtCreation;
    private int waitingTime;
    private int beginTime;
    private int doneTime;

    private int timeToFinish;
    private boolean isDone;
    private boolean started;



    public Process(int processId, int arrivalTime, int timeAtCreation, boolean isDone){
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.timeAtCreation = timeAtCreation;
        timeLeft = timeAtCreation;
        started = false;
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

    public int getTimeAtCreation() {
        return timeAtCreation;
    }

    public int getWaitingTime(){
        return waitingTime;
    }

    public int getDoneTime(){
        return doneTime;
    }

    public int getProcessId() {
        return processId;
    }

    public int getTimeToFinish(){
        return timeToFinish;
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

    public void setTimeToFinish(int timeToFinish){
        this.timeToFinish = timeToFinish;
    }
}
