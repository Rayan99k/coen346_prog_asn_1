# :white_check_mark:  Generate a random txt file of processes 
Go into the python script if you want to adjust the file

# :white_check_mark:  Go through the file and create a process object
sorts according to arrival time
save in processList
The schedulers affect this list: reset() after running them

# :white_check_mark: FCFS scheduler

# :white_check_mark: RR scheduler

# :white_check_mark: RR With Priority scheduler

# PID Manager class
Added functions for allocating and releasing PIDs. Huge array of boolean values. 

Simple functions out of Main class. Thats what the teacher wanted with that API thing? 

Currently none of the schedulers release their PIDS. 

# :white_check_mark: Children management function
Children are assigned their respective parent

# :white_check_mark: Global Timer
accessed though Time.get()
Need to manually call Time.inc() to simulate the passing of time

# Other

Theres an edge case during scheduling where it is possible the current time is less than the arrival time of the next process. In that case the scheduler should Time.inc() until the next process arrives. Have to fix

Im realizing it would be much cleaner to have:
Parent class: Scheduler
Child class: FCFS
Child class: RR
Child class: RR with Priority

where Scheduler would have methods shared between all of them. Such as: releasing pids, performance calcs and readyQueue.

It wouldn't be too hard to implement but its more time.

Need to release PID once they enter the done queue


