package examples;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
		List<Integer> a = new ArrayList<Integer>();
		// fill it randomly
		for (int i = 0; i < a.size(); i++)
			a.add(rand.nextInt(n));
		cnt = 0; // for statistcs reasons
		// get Time
		te1 = System.currentTimeMillis();
		t1 = threadBean.getCurrentThreadCpuTime();
		System.out.println("Before sorting: " + a);
		a = mergeSort(a);
		System.out.println("After sorting: " + a);
		te2 = System.currentTimeMillis();
		t2 = threadBean.getCurrentThreadCpuTime();
		time = t2 - t1;
		eTime = te2 - te1;
		System.out.println("CPU-Time usage: " + time / 1000000.0 + " ms");
		System.out.println("elapsed time: " + eTime + " ms");
		System.out.println("sorted? " + isSorted(a));
	}

	public static boolean isSorted(List<Integer> a) {
		for (int i = 0; i < a.size() - 1; i++) {
			if (a.get(i) > a.get(i + 1))
				return false;
		}
		return true;
	}

	private static List<Integer> mergeSort(List<Integer> intList) {
		if (intList.size() <= 1)
			return intList;
		else {
			int half = intList.size() / 2;
			List<Integer> leftPart = intList.subList(0, half);
			List<Integer> rightPart = intList.subList(half, intList.size());
			mergeSort(leftPart);
			mergeSort(rightPart);
			return merge(leftPart,rightPart);
		}

	}

	private static List<Integer> merge(List<Integer> leftList, List<Integer> rightList) {
		List<Integer> sortedList = new ArrayList<Integer>();
		int insertIndex = 0;

		while (leftList.size() > 0 && rightList.size() > 0) {
			if (leftList.get(0) >= rightList.get(0)) {
				copyAndRemoveFirstElement(leftList, sortedList, insertIndex);
			} else {
				copyAndRemoveFirstElement(rightList, sortedList, insertIndex);
			}
			insertIndex++;
		}

		while (leftList.size() > 0) {
			copyAndRemoveFirstElement(leftList, sortedList, insertIndex);
		}

		while (rightList.size() > 0) {
			copyAndRemoveFirstElement(rightList, sortedList, insertIndex);
		}
		
		return sortedList;
	}

	private static void copyAndRemoveFirstElement(List listToRemove,
			List listToInsert, int insertIndex) {
		listToInsert.set(insertIndex, listToRemove.get(0));
		listToRemove.remove(0);
}
}