import java.util.ArrayList;

public class ExecutingProcesses {

    public static void FCFSAlgo(ArrayList<Process> generatedProcesses, ArrayList<Process> active, ArrayList<Process> done){

        int currentTime = 0;
        ManagingProcesses.addToActive(generatedProcesses, active, currentTime);

        if(!active.isEmpty()) active.get(0).setBeginTime(currentTime);

        while(!(generatedProcesses.isEmpty() && active.isEmpty())){

            if(!active.isEmpty()){
                active.get(0).setTimeLeft(active.get(0).getTimeLeft()-1);
                ManagingProcesses.addTime(active, 0, 1);
                currentTime++;
                ManagingProcesses.addToActive(generatedProcesses, active, currentTime);

                if(ManagingProcesses.checkIfDone(active.get(0), active, done, currentTime)){
                    if(!active.isEmpty()) active.get(0).setBeginTime(currentTime);
                }
            }

            else{
                currentTime++;
                ManagingProcesses.addToActive(generatedProcesses, active, currentTime);
                if(!active.isEmpty()) active.get(0).setBeginTime(currentTime);
            }
        }

        Calculations.calculateData(generatedProcesses, active, done);
    }

    public static void SJFAlgo(ArrayList<Process> generatedProcesses, ArrayList<Process> active, ArrayList<Process> done){
        int currentTime = 0;
        ManagingProcesses.sortByLength(generatedProcesses);

        ManagingProcesses.addToActive(generatedProcesses, active, currentTime);

        if(!active.isEmpty()) active.get(0).setBeginTime(currentTime);

        while(!(generatedProcesses.isEmpty() && active.isEmpty())){

            if(!active.isEmpty()){
                active.get(0).setTimeLeft(active.get(0).getTimeLeft()-1);
                ManagingProcesses.addTime(active, 0, 1);
                currentTime++;
                ManagingProcesses.addToActive(generatedProcesses, active, currentTime);

                if(ManagingProcesses.checkIfDone(active.get(0), active, done, currentTime)){
                    ManagingProcesses.sortByLength(active);
                    if(!active.isEmpty()) active.get(0).setBeginTime(currentTime);
                }
            }

            else{
                currentTime++;
                ManagingProcesses.addToActive(generatedProcesses, active, currentTime);
                ManagingProcesses.sortByLength(active);
                if(!active.isEmpty()) active.get(0).setBeginTime(currentTime);
            }
        }
        Calculations.calculateData(generatedProcesses, active, done);
    }

    public static void RoundRobinAlgo(ArrayList<Process> generatedProcesses, ArrayList<Process> active, ArrayList<Process> done){
        int timeQuantum = 3;
        System.out.println(timeQuantum);
        int indexInQueue = 0;

        int currentTime = 0;
        ManagingProcesses.sortByArrival(generatedProcesses);
        ManagingProcesses.addToActive(generatedProcesses, active, currentTime);

        while(!(generatedProcesses.isEmpty() && active.isEmpty())){
            if(!active.isEmpty()){
                if(!active.get(indexInQueue).isStarted()){
                    active.get(indexInQueue).setStarted(true);
                    active.get(indexInQueue).setBeginTime(currentTime);
                }

                if(active.get(indexInQueue).getTimeLeft() <= timeQuantum){
                    currentTime+=active.get(indexInQueue).getTimeLeft();
                    ManagingProcesses.addTime(active, indexInQueue, active.get(indexInQueue).getTimeLeft());
                    active.get(indexInQueue).setTimeLeft(0);
                    ManagingProcesses.checkIfDone(active.get(indexInQueue), active, done, currentTime);
                    ManagingProcesses.addToActive(generatedProcesses, active, currentTime);// zalezy czy cos sie dodalo i ile

                    if(indexInQueue>=active.size()) indexInQueue=0;
                }

                else if(active.get(indexInQueue).getTimeLeft() > timeQuantum){
                    currentTime+=timeQuantum;
                    active.get(indexInQueue).setTimeLeft(active.get(indexInQueue).getTimeLeft()-timeQuantum);
                    ManagingProcesses.addTime(active, indexInQueue, timeQuantum);
                    ManagingProcesses.addToActive(generatedProcesses, active, currentTime);
                    if(indexInQueue<active.size()-1) indexInQueue++;
                    else if(indexInQueue==active.size()-1) indexInQueue = 0;
                }
            }

            else{
                currentTime++;
                ManagingProcesses.addToActive(generatedProcesses, active, currentTime);
            }
        }
        Calculations.calculateData(generatedProcesses, active, done);
    }


    public static void RoundRobin(ArrayList<Process> generatedProcesses, ArrayList<Process> active, ArrayList<Process> done){
        int timeQuantum = 3;
        int iterationsLeft;
        int indexInQueue = 0;

        int currentTime = 0;
        ManagingProcesses.addToActive(generatedProcesses, active, currentTime);

        while(!(generatedProcesses.isEmpty() && active.isEmpty())){
            iterationsLeft = timeQuantum;
            if(!active.isEmpty()){
                if(!active.get(indexInQueue).isStarted()){
                    active.get(indexInQueue).setStarted(true);
                    active.get(indexInQueue).setBeginTime(currentTime);
                }

                while(iterationsLeft>0){
                    active.get(indexInQueue).setTimeLeft(active.get(indexInQueue).getTimeLeft()-1);
                    ManagingProcesses.addTime(active, indexInQueue, 1);
                    currentTime++;
                    ManagingProcesses.addToActive(generatedProcesses, active, currentTime);

                    if(ManagingProcesses.checkIfDone(active.get(indexInQueue), active, done, currentTime)){
                        if(indexInQueue<active.size()-1) indexInQueue++;
                        else indexInQueue = 0;
                        iterationsLeft = 0;
                    }
                    else iterationsLeft--;
                }
            }
            else{
                currentTime++;
                ManagingProcesses.addToActive(generatedProcesses, active, currentTime);
            }
        }
        Calculations.calculateData(generatedProcesses, active, done);
    }
}
