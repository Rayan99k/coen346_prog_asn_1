# :white_check_mark:  Generate a random txt file of processes 
Go into the python script if you want to adjust the file

# :white_check_mark:  Go through the file and create a process object
sorts according to arrival time
save in processList
The schedulers affect this list: reset() after running them

# :white_check_mark: FCFS scheduler

# RR scheduler
Missing handling parents

# RR W/ Priority scheduler
Not started

# PID Manager class
Added functions for allocating and releasing PIDs. Huge array of boolean values. 

Simple functions out of Main class. Thats what the teacher wanted with that API thing? 

Currently none of the schedulers release their PIDS. 

# Children management function
Need a way to keep track of who is the parent 
& Implement with children: ready, waiting, running, done

# :white_check_mark: Global Timer
accessed though Time.get()
Need to manually call Time.inc() to simulate the passing of time

# Other

Theres an edge case during scheduling where it is possible the current time is less than the arrival time of the next process. In that case the scheduler should Time.inc() until the next process arrives. Have to fix

In the python script its possible: T2.1 has a greater arrival time than T2.2. Which is impossible. Have to fix

Im realizing it would be much cleaner to have:
Parent class: Scheduler
Child class: FCFS
Child class: RR
Child class: RR with Priority

where Scheduler would have methods shared between all of them. Such as: releasing pids, performance calcs and readyQueue.

It wouldn't be too hard to implement but its just more time.

Need to release PID once they enter the done queue


