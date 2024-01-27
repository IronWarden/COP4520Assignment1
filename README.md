# How to Compile 
javac ParallelPrimeFinder.java

java ParallelPrimeFinder

# How to Change Parameters
I use variables in the main method which can be changed as you see fit.  

# Proof of Correctness
This solution uses the Shared Counter method to allow load balancing. This technique is recommended by the book to be one of the better ways to balance the load between multiple threads for this problem. By utilizing locking, I allow mutual exclusion and prevent race conditions among the threads. Every thread can work on its number and get the next number once they're done checking if it's a prime.  In addition, the isPrime method I use is considered the most efficient way to evaluate if a number is prime. 
# Efficiency
The execution time is around 20-22s which is solid for a Java implementation. I have used the isPrime method which I found online to be the most efficient way to calculate if a number is prime. In addition, using the Shared Counter is also an efficient way to load balance. 

# Experimental Evaluation 
I have compiled and run code several times and it always lands in the 20-22 sec execution with consistent outputs. In addition, I have tested the output against another primefinder program, and the output matches, so I assumed it to be the correct result. 
