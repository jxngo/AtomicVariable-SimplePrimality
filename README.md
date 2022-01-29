# AtomicVariable-SimplePrimality
Atomic variable and an implementation of a simple primality test algorihm were my approach to finding all prime numbers from 1 to 10e8.
Atomic variable is shared among the threads that we are required to use, in this specific assignment we assigned 8 threads to do the work concurrently that was
sequential. I first spawned 8 threads and use them to work one number at a time using Atomic Integer type counter and increment so that other threads can work simultaneously. While threads are each assigned by incremented counter, the threads then with an implementation of prime test checks if it is a prime and then do calculates to get the output. 

Behind the computation, depending on the size of the input it's efficiency is running worst time of O(n*n)
The threads are running worst case O(n^2) due to the nested loop of primalty test inside the while loop that is required to increment up to n values. Without the while loop, it will increment only 8 times total (which was my first bug encounter)
The primalty algorithm cuts the iteration by square root of the given number which is faster than iterating up to n. 
It also checks if the counter or i is divisable by 2 or 3 which cuts some corners as well. 
Depending on the current iteration, some times can be saved but overall the expected run-time is O(n*n).

Experiemental Evaluation:
I started the assignment 2 days prior to the deadline (my first mistake)
Before I dove into writing the code, I first research on what is needed to be done:
1. I read the portion of the textbook that showcase parellel computing with prime numbers in chapter 1. 
   - It took a while for me to understand what the book was explaining
   - It didn't click until awhile later when I started creating threads
2. My initial understand were based on the Sieve of Eratosthenes (which is another mistake because now my assumptions of 
   the assignment were based from Sieve of Eratosthenes)
3. Prior knowledge of threads from Operating System class, I had an assignment that requires using multiple threads with semaphore. That was also one of my based 
   understanding for the assignment. So I revisted the assignment I have done in the past.

First experiment - ???
I first tried to implement the figure 1.2 into my code but I didn't understand completely of the functionality behind it. So I decided to go for Sieve of Eratosthenes instead. I first solved the primal test by using Eratosthenes and finished in O(n*log(n). 

Second experiment - O(n*log(n) but no concurrent method
Sieve of Eratosthenes was a clever algorithm to solve primalty test however, it doesn't include any concurrent methods which is an emphasis to the assignment. I couldn't think of ways to implement threads into Sieve of Eratosthenes so I switched my ideas. However, I was still thinking of applying boolean array to my next method of using atomic variable which I wasted whole day figuring out how to do it. Nope it didn't work. 

Third experiment - ??? -> O(n^3)
After slowly taking bits by bits apart and rebuild the concepts back, I finally understood how threads work with counter class and Atomic Variables. My Atomic Variable was successfully shared among the threads. However I couldn't find the syntax to build the rest. At this part I haven't applied the final primalty test yet, instead with my understanding of S.o.E, I tried to use parts of it to find the primal test. Which is getting the current integer and find multiples of it. 
2 errors I found eventually, 
- I had no flag for my run() to keep the computation going, it only ends after 8 times which was when I spawn the 8 threads. I incorporate the flag and can run it to N times
- The while loop under the flag to find multiples wasn't necessary since I didn't need a boolean array to mark. I removed it and now my runtime is better. 

Fourth experiment - O(n^2)
I finally applied a primalty test that helps cut corners for numbers that are easily found to be prime. It cuts the time from 40 seconds to 20 seconds.

The rest of the requirements was easy, I learned how to use synchronized to have the threads share the sum, total, and list. Then file writing took few minutes since it just needs to be googled for the syntax. 

I finished few hours before deadline! 

*Note- there is a small bug where the threads sometimes gets a prime number after n. It can be seen sometimes at the end of the top 10 max prime numbers. You will see it when there are less elements than 10 elements for max prime numbers
