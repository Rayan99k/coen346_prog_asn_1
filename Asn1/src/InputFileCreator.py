#The input is a file where each line represents a process in the form 
#[name] [priority] [CPU burst] [arrival time] [n] 
#n being the number of child processes for the process.

import random

#Change this to limit the toal number of processes
num_entries = 10
entries = []
task_num = 0
i = 0

#Generate processes
while (i<num_entries+1):

    #There are two iterators that get incremented because: Children to have the same first number as their parents. Ex. T4.1,T4.2,T4.3...
    #We still want to keep track of the total number of tasks (stored in i)
    i+=1
    task_num+=1


    rand_priority = random.randint(1,5)
    rand_burst = random.randint(1,50)
    rand_arrival = random.randint(0,100)
    rand_children = random.randint(0,3)

    entry =  f"T{task_num}, {rand_priority}, {rand_burst}, {rand_arrival}, {rand_children}" 
    entries.append(entry)


    #If it has children, generate those with appropriate ranges
    if rand_children>0:
        for j in range(1, rand_children+1):                        
            rand_priority = random.randint(rand_priority, 5)
            rand_burst = random.randint(1,50)
            rand_arrival = random.randint(rand_arrival, 100)
            entry =  f"T{task_num}.{j}, {rand_priority}, {rand_burst}, {rand_arrival}, {0}" 
            entries.append(entry)

        #Increment by the number of children to maintain the same total number of entries
        i+=rand_children
       
            
#Write the entries in a file
with open("process_list.txt", "w") as file:
    file.write("\n".join(entries))

print(f"Generated Processes")    
