import java.util.*;
public class RR_Scheduler {

    int quantum;
    int turnaroundAvg, responseAvg, waitingAvg;

    Queue<Process> readyQueue = new LinkedList<Process>(); 
    Queue<Process> waitingQueue = new LinkedList<Process>(); 
    Queue<Process> doneQueue = new LinkedList<Process>(); 

    
    RR_Scheduler(){
        quantum = 4; //Default time quantum
    }

    void setQuantum(int quantum){
    this.quantum = quantum;
    }

    void addProcess(Process process){
        //Add process to appropriate queue. Waiting as long as it has children
        if(process.getChildren()>0){
            process.setState("Waiting");
            waitingQueue.add(process);
        }
        else{
            process.setState("Ready");
            readyQueue.add(process);
        }       
    }

    void runScheduler(){
        System.out.println("\n\n    Round Robin Scheduling using Time Quantum\n\n");

        while (!readyQueue.isEmpty()) {

            //poll(): Removes and returns the element at the front of the readyQueue.
            Process currentProcess = readyQueue.poll();
            currentProcess.setState("Running");
            
            //Has it already gone through the scheduler? If not, set the first contact time
            if(!currentProcess.isFirstResponse()){
            currentProcess.setFirstResponse(true);
            currentProcess.setFirstResponseTime(Time.get()-currentProcess.getArrivalTime());
            }

            //Update waitingTime: waitingTime + (currentTime - LastServedTime)
            currentProcess.setWaitingTime(currentProcess.getWaitingTime()+
                                         (Time.get()-currentProcess.getLastServed())
                                         );

            int remainingTime = currentProcess.getBurstTime();

            //Adjust the current time and remaining time.        
            //Put the process back into queue if there is burstTime remaining            
            if (remainingTime > quantum){
                currentProcess.setBurstTime(remainingTime-quantum);
                Time.inc(quantum);                
                currentProcess.setState("Ready");
                readyQueue.add(currentProcess);
            }

            //Otherwise put the process into the Done queue
            else{            
                currentProcess.setBurstTime(0);            
                Time.inc(remainingTime);
                currentProcess.setState("Done");
                currentProcess.setCompletionTime(Time.get()-currentProcess.getArrivalTime());
                doneQueue.add(currentProcess);
            } 
            
            currentProcess.setLastServed(Time.get());

            System.out.println("Current Time: " + Time.get() + 
                               ", Process: " + currentProcess.getName() + 
                               ", Remaining CPU: " + currentProcess.getBurstTime() 
                              );
        }

        //At the end of the run, calculate performance
        avgTimes();

    }

    private void avgTimes(){
        //Calculates the performance of the scheduler.
        //Only looks at the done queue. Make sure the ready and waiting queues are empty.

        int processesTotal = 0;
        int waitingTotal = 0;
        int turnaroundTotal = 0;
        int responseTotal = 0;
        
            while (!doneQueue.isEmpty()) {

            Process currentProcess = doneQueue.poll();

            processesTotal++;
            waitingTotal += currentProcess.getWaitingTime();
            turnaroundTotal += currentProcess.getCompletionTime();
            responseTotal += currentProcess.getFirstResponseTime();        
            }

        turnaroundAvg = turnaroundTotal / processesTotal;
        waitingAvg = waitingTotal / processesTotal;
        responseAvg = responseTotal / processesTotal;

        System.out.println("\nTurnaround Avg: " + turnaroundAvg + 
                           ", Waiting Avg: " + waitingAvg + 
                           ", Response Avg: " + responseAvg + 
                           "\n"
                           );
       
    }


}
