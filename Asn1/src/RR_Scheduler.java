import java.util.*;
public class RR_Scheduler {

    int quantum;
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
        while (!readyQueue.isEmpty()) {

            //poll(): Removes and returns the element at the front of the readyQueue.
            Process currentProcess = readyQueue.poll();
            currentProcess.setState("Running");

            //Update waitingTime: waitingTime + (currentTime - LastServedTime)
            currentProcess.setWaitingTime(currentProcess.getWaitingTime()+
                                         (Time.get()-currentProcess.getLastServed())
                                         );

            int remainingTime = currentProcess.getBurstTime();

            //Adjust the current time and remaining time.        
            //Put the process back into queue if there is burstTime remaining
            //Otherwise put the process into the Done queue
            if (remainingTime > quantum){
                currentProcess.setBurstTime(remainingTime-quantum);
                Time.inc(quantum);                
                currentProcess.setState("Ready");
                readyQueue.add(currentProcess);
            }
            else{
                currentProcess.setBurstTime(0);
                Time.inc(remainingTime);
                currentProcess.setState("Done");
                doneQueue.add(currentProcess);
            } 
            
            currentProcess.setLastServed(Time.get());

            System.out.println("Current Time: " + Time.get() + 
                               ", Process: " + currentProcess.getName() + 
                               ", Remaining CPU: " + currentProcess.getBurstTime());
        }



}
