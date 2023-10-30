import java.util.*;
public class RR_Priority_Scheduler {

    int quantum;
    int turnaroundAvg, responseAvg, waitingAvg;
    
    Queue<Process> doneQueue = new LinkedList<Process>();

    Queue<Process>[] readyQueues;
    Queue<Process>[] waitingQueues;

    public RR_Priority_Scheduler() {
        quantum = 4; //Default time quantum

        //Initialize the queues
        readyQueues = new Queue[5];
        waitingQueues = new Queue[5];

        for(int i = 0; i < 5; i++) {
            readyQueues[i] = new LinkedList<Process>();
            waitingQueues[i] = new LinkedList<Process>();
        }
    } 

    void setQuantum(int quantum){
    this.quantum = quantum;
    }

    void addProcess(Process process){
        //Add process to appropriate queue
        int priority = process.getPriority();

        if (priority >= 1 && priority <= 5) {
            // Add process to the appropriate ready or waiting queue based on priority
            if (process.getChildren() > 0) {
                waitingQueues[priority - 1].add(process);
            } else {
                readyQueues[priority - 1].add(process);
            }
        } else {
            System.out.println("Error: Make sure the priorities are between 1 and 5");
        }
    }

    void runScheduler(){
        String dashedLine = new String(new char[40]).replace('\0', '-');
        System.out.println(dashedLine);
        System.out.println("    Round Robin with Priority Scheduling");
        System.out.println(dashedLine);

            //Run the scheduler for each algorithm
            for (int i = readyQueues.length -1 ; i >= 0 ; i--) { //Note reverse order because smaller is higher priority
                Queue<Process> readyQueue = readyQueues[i];
                Queue<Process> waitingQueue = waitingQueues[i];
                
                System.out.println("\n              Priority:" + (i+1));

                while (!readyQueue.isEmpty()) {                    

                    System.out.print("\n  Ready Queue: ");
                    printQueue(readyQueue);

                    System.out.print("\n  Waiting Queue: ");
                    printQueue(waitingQueue);

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

                    //Process is done 
                    //Put into the Done queue and get its parent if it was the last child
                    else{            
                        currentProcess.setBurstTime(0);            
                        Time.inc(remainingTime);
                        currentProcess.setState("Done");
                        currentProcess.setCompletionTime(Time.get()-currentProcess.getArrivalTime());
                        doneQueue.add(currentProcess);

                        //Go get the parent 
                        Process parent = currentProcess.getParent();

                        if (parent != null) {
                            parent.decChildren(); // Decrement the children count
                            
                            //If the current process was the last child, put the parent in the ready queue
                            if (parent.getChildren() == 0) {
                                waitingQueue.remove(parent); // Remove the parent from waitingQueue
                                readyQueue.add(parent); // Add the parent to readyQueue
                                parent.setState("Ready");
                            }
                        }    
                    
                    }

                    currentProcess.setLastServed(Time.get());

                    System.out.println("\nCurrent Time: " + Time.get() + 
                    "   Process: " + currentProcess.getName() + 
                    "   Remaining CPU: " + currentProcess.getBurstTime()  +
                    "\n"
                    );
                }
            } 
        
    
        //At the end of the run, calculate performance
        //avgTimes();
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

    private void printQueue(Queue<Process> queue) {
        for (Process process : queue) {
            System.out.print(process.getName() + ", ");
        }
    }
}
