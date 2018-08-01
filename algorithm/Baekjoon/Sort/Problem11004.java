package Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Sort11004 {

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);

		int N = sc.nextInt();
		int K = sc.nextInt();
		ArrayList<Integer> array = new ArrayList<Integer>();
		
		if(1>N || N>5000000) return;
		if(1>K || K>N) return;
		
		for(int idx=0; idx<N; idx++){
			array.add(sc.nextInt());
		}
		
//		quickSort(array, 0, array.size()-1);
//		mergeSort(array, 0, array.size()-1);
		System.out.println(array.get(K));
		
	}
	
	/*합병정렬*/
	public static void mergeSort(ArrayList<Integer> array, int first, int last){
		if(first < last){
			int mid = (first+last)/2;
			mergeSort(array, first, mid);
			mergeSort(array, mid+1, last);
			merge(array, first, mid, last);
		}
	}
	
	public static void merge(ArrayList<Integer> array, int first, int mid, int last){
		if(first < last){
			int i = first;
			int j = mid+1;
			ArrayList<Integer> temp = new ArrayList<Integer>();
			while(i<=mid && j<=last){
				if(array.get(i)<array.get(j)){
					temp.add(array.get(i));
					i++;
				}
				else if(array.get(i)>array.get(j)){
					temp.add(array.get(j));
					j++;
				}
				else {
					temp.add(array.get(j));
					i++;
					j++;
				}
			}
			while(i<=mid){
				temp.add(array.get(i));
				i++;
			}
			while(j<=last){
				temp.add(array.get(j));
				j++;
			}
			array = temp;
		}
	}
	
	/*퀵 정렬*/
	public static void quickSort(ArrayList<Integer> array, int left, int right){
		if(left < right){
			int pivotLoc = partition(array, left, right);
			quickSort(array, left, pivotLoc-1);
			quickSort(array, pivotLoc+1, right);
		}
	}
	
	public static int partition(ArrayList<Integer> array, int left, int right){
		int pivot = array.get(right); 
		int i = left - 1;
		for(int j = left; j<=right-1; j++){
			if(array.get(j)<pivot){
				i++;
				Collections.swap(array, i, j);
			}
		}
		Collections.swap(array, i+1, right);
		return i+1;
	}
	

}
