package examples;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Random;

public class BubbleSort {
	
	public static long cnt;
	static Random rand = new Random();
	static int[] b;
	
	public static void main(String[] args) {
		long t1 = 0, t2 = 0, te1 = 0, te2 = 0, eTime = 0, time = 0;
		int n = 100;
		// we need a random generator
		Random rand = new Random();
		// rand.setSeed(54326346); // initialize always in the same state
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		// new array
		int[] a = new int[n];
		// fill it randomly
		for (int i = 0; i < a.length; i++)
			a[i] = rand.nextInt(n);
		cnt = 0; // for statistcs reasons
		// get Time
		te1 = System.currentTimeMillis();
		t1 = threadBean.getCurrentThreadCpuTime();
		bubbleSort3(a);
		te2 = System.currentTimeMillis();
		t2 = threadBean.getCurrentThreadCpuTime();
		time = t2 - t1;
		eTime = te2 - te1;
		System.out.println("CPU-Time usage: " + time / 1000000.0 + " ms");
		System.out.println("elapsed time: " + eTime + " ms");
		System.out.println("sorted? " + sortCheck(a));
		System.out.println("swap operation needed: " + cnt);
	}
	
	public static boolean sortCheck(int[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] > a[i + 1])
				return false;
		}
		return true;
	}
	
	private static void bubbleSort3(int[] a) {
	  int n = a.length;
	  int newn;
	  do{
	    newn = 1;
	    for (int i=0; i<n-1; ++i){
	      if (a[i] > a[i+1]){
	        swap(a,i, i+1);
	        newn = i+1;
	      } // ende if
	    } // ende for
	    n = newn;
	  } while (n > 1);
	}
	
	private static void swap(int[] arr, int i, int k) {
		int tmp = arr[i];
		arr[i] = arr[k];
		arr[k] = tmp;
	}
}
