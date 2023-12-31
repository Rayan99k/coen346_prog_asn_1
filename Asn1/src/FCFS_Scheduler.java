import java.util.*;
public class FCFS_Scheduler {

   private Queue<Process> readyQueue = new LinkedList<Process>(); 
   private Queue<Process> waitingQueue = new LinkedList<Process>(); 
   private Queue<Process> doneQueue = new LinkedList<>();


   private int turnaroundAvg, responseAvg, waitingAvg;
        
    void addProcess(Process process) {
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

    String runScheduler() {
        String dashedLine = new String(new char[40]).replace('\0', '-');
        System.out.println(dashedLine);
        System.out.println("    First Come First Serve Scheduling");
        System.out.println(dashedLine);
        
        while (!readyQueue.isEmpty()) {

            System.out.print("\n  Ready Queue: ");
            printQueue(readyQueue);

            System.out.print("\n  Waiting Queue: ");
            printQueue(waitingQueue);

            //poll(): Removes and returns the element at the front of the readyQueue.  
            Process currentProcess = readyQueue.poll();
            currentProcess.setState("Running");

            //Set the first contact time
            currentProcess.setFirstResponse(true);
            currentProcess.setFirstResponseTime(Time.get()-currentProcess.getArrivalTime());

            //Update waitingTime: currentTime - arrivalTime. 
            currentProcess.setWaitingTime((Time.get()-currentProcess.getArrivalTime()));
            
            //Adjust the current time and remaining time. Add to doneQueue    
            Time.inc(currentProcess.getBurstTime());
            currentProcess.setBurstTime(0);            
            currentProcess.setState("Done");
            currentProcess.setCompletionTime(Time.get()-currentProcess.getArrivalTime());
            doneQueue.add(currentProcess);

            //Go get the parent 
            Process parent = currentProcess.getParent();

            if (parent != null) {
                
                parent.decChildren(); // Decrement the children count
                
                //If the current process was the last child put the parent in the readyQueue
                if (parent.getChildren() == 0) {
                    waitingQueue.remove(parent); // Remove the parent from waitingQueue
                    readyQueue.add(parent); // Add the parent to readyQueue
                    parent.setState("Ready");
                }
            }    

            System.out.println("\nCurrent Time: " + Time.get() + 
            "   Process: " + currentProcess.getName() + 
            "   Remaining CPU: " + currentProcess.getBurstTime()  +
            "\n"
            );
        }

        //At the end of the run, calculate performance
        return avgTimes();

    }

    private void printQueue(Queue<Process> queue) {
        for (Process process : queue) {
            System.out.print(process.getName() + ", ");
        }
    }

    private String avgTimes(){
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

        String result =   ("\nTurnaround Avg: " + turnaroundAvg + 
                           ", Waiting Avg: " + waitingAvg + 
                           ", Response Avg: " + responseAvg + 
                           "\n"
                           );
       return result;
    }

}
