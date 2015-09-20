package examples;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Random;

/**
 * @author ps Various sort programs for int arrays
 */
public class SortTest {

	public static long cnt;
	static Random rand = new Random();
	static int[] b;

	public static void mergeSort(int[] a) {
		b = new int[a.length];
		mSort(a, 0, a.length - 1);
	}

	private static void mSort(int[] a, int from, int to) {
		if (from == to)
			return;
		int mid = (from + to) / 2;
		mSort(a, from, mid);
		mSort(a, mid + 1, to);
		merge(a, from, mid, to);

	}

	private static void merge(int[] a, int from, int mid, int to) {
		// merge
		int fPtr = from,
		sPtr = mid + 1,
		toPtr = from;
		while (true) {
			if (sPtr >  to) {
				while (toPtr<=to)
					b[toPtr++]=a[fPtr++];
				break;
			}
			if (fPtr>mid) break;
			if (a[fPtr] > a[sPtr]) b[toPtr++] = a[sPtr++];
			else b[toPtr++]=a[fPtr++];
		}
		while(--toPtr >=from) a[toPtr] = b[toPtr];
	}

	/**
	 * @param a
	 *            int aray
	 * @return 'true' if 'a' is sorted
	 */
	public static boolean sortCheck(int[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] > a[i + 1])
				return false;
		}
		return true;
	}

	/**
	 * Non optimized bubble sort for an int array
	 * 
	 * @param a
	 */
	public static void bubbleSort(int[] a) {
		cnt = 0;
		int m = a.length - 1;
		for (int i = m; i > 0; i--) {

			for (int k = 0; k < i; k++) {
				if (a[k] > a[k + 1])
					swap(a, k, k + 1);
			}
			// now a[i] is on its final position!
		}
	}

	/**
	 * swap the array elements a[i] and a[k]
	 * 
	 * @param a
	 *            int array
	 * @param i
	 *            position in the array 'a'
	 * @param k
	 *            position in the array 'a'
	 */
	static void swap(int[] a, int i, int k) {
		int tmp = a[i];
		a[i] = a[k];
		a[k] = tmp;
		cnt++;
	}

	public static void main(String[] args) {
		long t1 = 0, t2 = 0, te1 = 0, te2 = 0, eTime = 0, time = 0;
		int n = 100000;
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
		bubbleSort(a);
		te2 = System.currentTimeMillis();
		t2 = threadBean.getCurrentThreadCpuTime();
		time = t2 - t1;
		eTime = te2 - te1;
		System.out.println("CPU-Time usage: " + time / 1000000.0 + " ms");
		System.out.println("elapsed time: " + eTime + " ms");
		System.out.println("sorted? " + sortCheck(a));
		System.out.println("swap operation needed: " + cnt);
	}

}
