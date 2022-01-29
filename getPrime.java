import java.util.*;
import java.lang.Math;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.PrintWriter;

class Counter implements Runnable {
    private final AtomicInteger counter;
    private long sum = 0;
    private int total = 0;
    private long n;
    private boolean finished = false;
    private ArrayList<Long> primeList = new ArrayList<Long>();

    public Counter (int i,long num) {
        counter = new AtomicInteger(i);
        n = num;
    }
    // Give a thread a number, work its way up
    public void run() {
        // Implement a loop.
        while (!finished) {
            // Iteration up to n 
            if (counter.get() == n) {
                finished = true;
                continue;
            }
            int i = counter.incrementAndGet();
            if (isPrime(i)) {
                addSum(i);
                addTotal();
                addToList(i);
            } 
        }
    }

    public synchronized void addToList(int i) {
        primeList.add((long) i);
    }

    public synchronized ArrayList<Long> getList() {
        return primeList;
    }

    public synchronized void addSum(int i) {
        sum += (long) i;
    }

    public synchronized long getSum() {
        return sum;
    }

    public synchronized void addTotal() {
        total++;
    }

    public synchronized int getTotal() {
        return total;
    }

    public boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num == 2 || num == 3) return true;
        if (num % 2 == 0 || num % 3 == 0) return false;
        long sqrtN = (long) Math.sqrt(num) + 1; 
        for (long i = 6L; i <= sqrtN; i += 6) 
            if (num % (i - 1) == 0 || num % (i + 1) == 0) return false;
        return true;
    }
}

public class getPrime {
    // Shared by all threads
    public static void main (String args[]) throws Exception {
       long n = 100000000;
       getPrime g = new getPrime();
       g.threadPrime(n);
    }

    public void threadPrime(long n) throws Exception{
        // If input is less than 2 or greater than 10e8
        if (n < 2 || n > 100000000) {
            System.out.println("Input out of bound");
            System.exit(0);
        }  
        // sum, total, and list for output
        long sum = 0;
        int total = 0;
        ArrayList<Long> primeList = new ArrayList<Long>();

        PrintWriter writer = new PrintWriter("prime.txt", "UTF-8");
        // -----------------------------------------------
        // Created a shared counter for all threads  
        Counter c = new Counter(1,n);
        // Create 8 threads and add into threads list + start time
        long startTime = System.nanoTime();
        List<Thread> thList = new ArrayList<Thread>();
        System.out.println("Computing.. please wait");
        for (int i = 0; i < 8; i++) {    
            Thread thread = new Thread(c);
            thList.add(thread);
            thread.start();
        }
        for (Thread t: thList) t.join();
        long endTime = System.nanoTime();
        // -----------------------------------------------

        long duration = (endTime - startTime) / 1000000;
        
        sum = c.getSum();
        total = c.getTotal();
        primeList = c.getList();

        // Sort primeList since threading puts value in RNG indexes.
        Collections.sort(primeList);
        

        // Write to file
        writer.println("<execution time: " + duration + "ms> " + "<total # of prime found: " + total + "> " + "<sum of all primes found:" + sum + ">");
        // If there are less than 10 prime numbers just print all.
        if (primeList.size() < 10) {
            writer.println(primeList.toString());
        }
        // Else get top 10
        else {
            List <Long> tail = primeList.subList(Math.max(primeList.size() - 10,0), primeList.size() - 1);
            writer.println(tail.toString());
        }
        writer.close();
    }
}