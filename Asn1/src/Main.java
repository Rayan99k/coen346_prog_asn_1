import java.util.*;
import java.io.*;


public class Main{ 
    public static final int MIN_PID = 300;
    public static final int MAX_PID = 5000;
    public static final int NUM_PIDS = (MAX_PID - MIN_PID + 1);
    public static boolean[] pid_map;
    static List<Process> processList = new ArrayList<>();
    static FCFS_Scheduler fcfs_scheduler = new FCFS_Scheduler();
    static RR_Scheduler rr_scheduler = new RR_Scheduler();
    static RR_Priority_Scheduler rr_priority_scheduler = new RR_Priority_Scheduler();
    

    public static void main(String[] args) {
        
                
        allocate_map(); //Setup the PID array
        sortInputs(); //Create process objects from file. Saved in processList     

        //Add all processes to the Scheduler
        for (Process process:processList){
            fcfs_scheduler.addProcess(process);            
        }
        String fcfs = fcfs_scheduler.runScheduler();

        //After each run. Need to reset and add the processes to the new scheduler
        reset();

        //Add all processes to the Scheduler
        for (Process process:processList){        
            rr_scheduler.addProcess(process);
        }        
        String rr = rr_scheduler.runScheduler();

        reset();   

        //Add all processes to the Scheduler
        for (Process process:processList){        
            rr_priority_scheduler.addProcess(process);
        }        
        String rr_priority = rr_priority_scheduler.runScheduler();

       
        String dashedLine = new String(new char[50]).replace('\0', '-');
    
        String output = dashedLine + 
                        "\n             RESULTS\n\n" + 
                        "First Come First Serve: " + fcfs + 
                        "\nRound Robin: " + rr + 
                        "\nRound Robin With Priority: " + rr_priority + 
                        "\n" + dashedLine;
        
        System.out.println(output);

    }

    private static void sortInputs() {
        //Opens the file containing all the processes 
        //Creates a process object for each entry
        //Saves them into an ArrayList
        //Sorts them according to arrival time

        try {
            File myObj = new File("process_list.txt");
            Scanner myReader = new Scanner(myObj);

            //Go through each line and divide them into String & int
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();              
              String[] parts = data.split(", ");

              String name = parts[0];
              int priority = Integer.valueOf(parts[1]);
              int burstTime = Integer.valueOf(parts[2]);
              int arrivalTime = Integer.valueOf(parts[3]);
              int children = Integer.valueOf(parts[4]);

              int pid = allocate_pid(); //Fetch a pid for each process. That pid is not longer in use             
              
              //Create a new process for each line in the file and add it to the list
              Process process = new Process(pid, name, priority, burstTime, arrivalTime, children);
              processList.add(process);

              Process.incTotalProcesses();
            }

            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the process file.");
            e.printStackTrace();
          }



        //Go through each process in the list. If it has children, add a reference to the parent in each child
        for (int i = 0; i < processList.size(); i++) {
            Process currentProcess = processList.get(i);
            int childrenCount = currentProcess.getChildren();

            if (childrenCount > 0) {

                // Loop through the subsequent items and set the parent. Do this as many times as there are children
                
                for (int j = i + 1; j <= i + childrenCount; j++) {
                    if (j < processList.size()) {
                        Process childProcess = processList.get(j);
                        childProcess.setParent(currentProcess); // Save a reference to the parent in each child
                    }
                }
            }
        }

        //Custom comparator to sort based on arrivalTime
        Comparator<Process> arrivalTimeComparator = Comparator.comparingInt(Process::getArrivalTime);

        //Sort the processList using the custom comparator
        Collections.sort(processList, arrivalTimeComparator);

        
    }

    static int allocate_map(){ 
        //Creates and initializes a data structure for representing pids
        //returns −1 if unsuccessful, 1 if successful

        pid_map = new boolean[NUM_PIDS];

        for (int i = 0; i < NUM_PIDS; i++) {
            pid_map[i] = false;
        }

        return 1;
    }

    static int allocate_pid(){
        //Allocates and returns a pid
        //returns −1 if unable to allocate a pid (all pids are in use)
        for (int i = 0; i < NUM_PIDS; i++) {
            if (!pid_map[i]) {
                pid_map[i] = true;
                return MIN_PID + i; //Add the MIN_PID to the current index (300 < pid < 5000)
            }
        }
        return -1; // No available PIDs
    }

    static void release_pid(int pid){
        //Releases a pid
        if (pid >= MIN_PID && pid <= MAX_PID) { //Make sure it is a valid entry
            pid_map[pid - MIN_PID] = false;
        }
    }

    static void reset() {
        //Have to reset the list of processes because the schedulers directly modify its contents
        Time.reset();
        processList.clear();
        sortInputs();
    }

    



}