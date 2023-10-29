#The input is a file where each line represents a process in the form 
#[name] [priority] [CPU burst] [arrival time] [n] 
#n being the number of child processes for the process.

import random

#Change this to adjust the output
num_entries = 5

priority_min = 1
priority_max = 5

burst_min = 1
burst_max = 100

arrival_min = 0
arrival_max = 20

children_min = 0
children_max = 3




entries = []
task_num = 0
i = 0

#Generate processes
while (i<num_entries+1):

    #There are two iterators that get incremented because: Children to have the same first number as their parents. Ex. T4.1,T4.2,T4.3...
    #We still want to keep track of the total number of tasks (stored in i)
    i+=1
    task_num+=1


    rand_priority = random.randint(priority_min,priority_max)
    rand_burst = random.randint(burst_min,burst_max)
    rand_arrival = random.randint(arrival_min,arrival_max)
    rand_children = random.randint(children_min,children_max)

    entry =  f"T{task_num}, {rand_priority}, {rand_burst}, {rand_arrival}, {rand_children}" 
    entries.append(entry)


    #If it has children, generate those with appropriate ranges 
    #(child cant have arrival time less than parent)
    if rand_children>0:
        child_arrival_times = [rand_arrival] + [random.randint(rand_arrival, arrival_max) for _ in range(1, rand_children)]
        for j, child_arrival in enumerate(sorted(child_arrival_times)):
        #for j in range(1, rand_children+1):                        
            rand_priority = random.randint(rand_priority, priority_max)
            rand_burst = random.randint(burst_min,burst_max)

            #Use parent's arrival time as a reference for child's arrival time
            rand_arrival_child = max(rand_arrival, random.randint(rand_arrival, arrival_max))
            entry =  f"T{task_num}.{j}, {rand_priority}, {rand_burst}, {rand_arrival_child}, {0}" 

            entries.append(entry)

        #Increment by the number of children to maintain the same total number of entries
        i+=rand_children
       
            
#Write the entries in a file
with open("process_list.txt", "w") as file:
    file.write("\n".join(entries))

print(f"Generated Processes File")    
