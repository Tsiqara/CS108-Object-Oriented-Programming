// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/

import java.security.*;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();
	private static final int MAX_THREADS = CHARS.length;
	private static CountDownLatch latch;
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}

	protected static byte[] generateHash(String password){
		MessageDigest hash;
		try {
			hash = MessageDigest.getInstance("SHA");
			hash.update(password.getBytes());
			return hash.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static class Worker extends Thread{
		int id;
		int step;
		int max_length;
		byte[] code;
		public Worker(int i, int s, int l, String c){
			id = i;
			step = s;
			max_length = l;
			code = hexToArray(c);
		}

		@Override
		public void run(){
			int start_ind = id * step;
			//if 6 workers, step = 6, last thread id = 5, so it's start_ind = 30,
			//but it is last thread and should do to the end, not just (5 + 1) * 6 = 36
			int end_ind = ((id + 1) * step + step > MAX_THREADS) ? MAX_THREADS : (id + 1) * step;
			for(int i = start_ind; i < end_ind; i ++){
				crack("" + CHARS[i]);
			}

			latch.countDown();
		}

		private void crack(String str){
			if(str.length() <= max_length){
				if(Arrays.equals(code, generateHash(str))){
					System.out.println(str);
				}
				for (char ch: CHARS) {
					crack(str + ch);
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		if (args.length < 1 || args.length > 3) {
			System.out.println("Args: target [length] [workers]");
			System.exit(1);
		}
		// args: targ [len] [num]
		if(args.length == 1){
			System.out.println(hexToString(generateHash(args[0])));
		}else{
			String target = args[0];
			int len = Integer.parseInt(args[1]);
			int num = 1;
			if (args.length > 2) {
				num = Integer.parseInt(args[2]);
			}
			latch = new CountDownLatch(num);

			if(num > 40){
				System.out.println("Max 40 worker threads");
				System.exit(1);
			}

			int step = MAX_THREADS / num;
			for(int i = 0; i < num; i ++){
				new Worker(i, step, len, target).start();
			}

			try {
				latch.await();
			} catch (InterruptedException e) {}

			System.out.println("All done");
		}
		// a! 34800e15707fae815d7c90d49de44aca97e2d759
		// xyz 66b27417d37e024c46526c2f6d358a754fc552f3
	}
}
