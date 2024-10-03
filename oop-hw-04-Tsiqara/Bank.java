// Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;


public class Bank {
	public static final int ACCOUNTS = 20;	 // number of accounts
	private static final int BLOCKINGQUEUE_CAPACITY = 50;
	private static final int DEFAULT_BALANCE = 1000;
	private final Transaction nullTrans = new Transaction(-1,0,0);

	private static List<Account> accounts;
	private static BlockingQueue<Transaction> queue = new ArrayBlockingQueue<>(BLOCKINGQUEUE_CAPACITY);
	private int numberOfWorkers;
	private static CountDownLatch latch;

	public Bank(int numberOfWorkers){
		this.numberOfWorkers = numberOfWorkers;
		latch = new CountDownLatch(numberOfWorkers);
		accounts = new ArrayList<>();
		for (int i = 0; i < ACCOUNTS; i ++){
			accounts.add(new Account(this, i, DEFAULT_BALANCE));
		}
	}

	private class Worker extends Thread{
		@Override
		public void run(){
			while(true){
				try {
					Transaction transaction = queue.take();
					if(transaction == nullTrans) {
						break;
					}

					int amount = transaction.getAmount();
					accounts.get(transaction.getFrom()).makeTransaction(-amount);
					accounts.get(transaction.getTo()).makeTransaction(amount);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			latch.countDown();
		}
	}


	/*
	 Reads transaction data (from/to/amt) from a file for processing.
	 (provided code)
	 */
	public void readFile(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// Use stream tokenizer to get successive words from file
			StreamTokenizer tokenizer = new StreamTokenizer(reader);
			
			while (true) {
				int read = tokenizer.nextToken();
				if (read == StreamTokenizer.TT_EOF) break;  // detect EOF
				int from = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int to = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int amount = (int)tokenizer.nval;
				
				// Use the from/to/amount
				queue.put(new Transaction(from, to, amount));
			}
			reader.close();

			for(int i = 0; i < numberOfWorkers; i ++){
				queue.put(nullTrans);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*
	 Processes one file of transaction data
	 -fork off workers
	 -read file into the buffer
	 -wait for the workers to finish
	*/
	public void processFile(String file, int numWorkers) {
		for(int i = 0; i < numWorkers; i ++){
			new Worker().start();
		}

		readFile(file);

		try {
			latch.await();
		} catch (InterruptedException e) {}
	}

	
	
	/*
	 Looks at commandline args and calls Bank processing.
	*/
	public static void main(String[] args) {
		// deal with command-lines args
		if (args.length == 0) {
			System.out.println("Args: transaction-file [num-workers [limit]]");
			System.exit(1);
		}
		
		String file = args[0];
		
		int numWorkers = 1;
		if (args.length >= 2) {
			numWorkers = Integer.parseInt(args[1]);
		}

		Bank bank = new Bank(numWorkers);
		bank.processFile(file, numWorkers);

		for (Account acc: accounts) {
			System.out.println(acc.toString());
		}
	}

	public static StringBuilder mainTest(String[] args) {
		main(args);

		StringBuilder result = new StringBuilder();
		for (Account acc: accounts) {
			result.append(acc.toString() + "\n");
			if(args[0].equals("5k.txt") || args[0].equals("100k.txt")){
				if(acc.getBalance() != 1000){
					throw new AssertionError("Account balance should be 1000");
				}
			}
		}
//		System.out.println(result);
		return result;
	}


}

