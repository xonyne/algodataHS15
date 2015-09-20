package examples;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.Random;

public class MergeSort {

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
		System.out.println("Before sorting: " + Arrays.toString(a));
		a = mergeSort(a);
		System.out.println("After sorting: " + Arrays.toString(a));
		te2 = System.currentTimeMillis();
		t2 = threadBean.getCurrentThreadCpuTime();
		time = t2 - t1;
		eTime = te2 - te1;
		System.out.println("CPU-Time usage: " + time / 1000000.0 + " ms");
		System.out.println("elapsed time: " + eTime + " ms");
		System.out.println("sorted? " + isSorted(a));
	}

	public static boolean isSorted(int[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] > a[i + 1])
				return false;
		}
		return true;
	}

	private static int[] mergeSort(int[] intArray) {
		if (intArray.length <= 1)
			return intArray;
		else {
			int half = intArray.length / 2;
			int[] leftList = Arrays.copyOfRange(intArray, 0, half);
			int[] rightList = Arrays.copyOfRange(intArray, half,
					intArray.length);
			mergeSort(leftList);
			mergeSort(rightList);
			return merge(leftList, rightList);
		}

	}

	private static int[] merge(int[] leftList, int[] rightList) {
		int[] sortedList = new int[leftList.length + rightList.length];
		int insertIndex = 0;

		while (leftList.length > 0 && rightList.length > 0) {
			if (leftList[0] >= rightList[0]) {
				copyAndRemoveFirstElement(leftList, sortedList, insertIndex);
			} else {
				copyAndRemoveFirstElement(rightList, sortedList, insertIndex);
			}
			insertIndex++;
		}

		while (leftList.length > 0) {
			copyAndRemoveFirstElement(leftList, sortedList, insertIndex);
		}

		while (rightList.length > 0) {
			copyAndRemoveFirstElement(rightList, sortedList, insertIndex);
		}

		return sortedList;
	}

	private static void copyAndRemoveFirstElement(int[] arrayToRemove,
			int[] arrayToInsert, int insertIndex) {
		arrayToInsert[insertIndex] = arrayToRemove[0];
		
		int[] tmpArray = new int[arrayToRemove.length - 1];
		for (int i=1; i<arrayToRemove.length;i++){
			tmpArray[i] = arrayToRemove[i];
		}
		arrayToRemove=tmpArray;
		System.out.println(Arrays.toString(arrayToRemove));
}
}