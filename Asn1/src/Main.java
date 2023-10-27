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
    

    public static void main(String[] args) {
                
        allocate_map(); //Setup the PID array
        process_file(); //Create process objects from file. Saved in processList     

        //Add all processes to the Scheduler
        for (Process process:processList){
            fcfs_scheduler.addProcess(process);            
        }
        fcfs_scheduler.runScheduler();

        //After each run. Need to reset and add the processes to the new scheduler
        reset();


        for (Process process:processList){        
            rr_scheduler.addProcess(process);
        }        
        rr_scheduler.runScheduler();
        reset();   

    }

    private static void process_file() {
        //Opens the file containing all the processes 
        //and creates a process object for each entry
        //saves them into an ArrayList

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

              int pid = allocate_pid();              
              
              //Create a new process for each line and add it to the list
              Process process = new Process(pid, name, priority, burstTime, arrivalTime, children);
              processList.add(process);

              Process.incTotalProcesses();
            }

            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the process file.");
            e.printStackTrace();
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
        //Have to reset the list because the schedulers directly modify its contents
        Time.reset();
        processList.clear();
        process_file();
    }

    



}