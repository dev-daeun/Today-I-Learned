```java
package Sort;

import java.util.Random;
import java.util.Scanner;

public class Sort11004_linearTime {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);

		int N = sc.nextInt();
		int K = sc.nextInt();
		
		if(1>N || N>5000000) return;
		if(1>K || K>N) return;
		
		int[] array = new int[N];
		for(int idx=0; idx<N; idx++){
			array[idx] = sc.nextInt();
		}
		
		System.out.println(quickSelect(array, 0, array.length-1, K-1));

	}
	

	public static int quickSelect(final int[] array, int start, int end, int K){ //K는 찾고자하는 수의 인덱스.
		if(start<=end){
			int pivot = partition(array, start, end);
			
			if(pivot==K) 
				return array[K];
			else if(pivot > K) //K가 피벗의 인덱스보다 작은 값이라면
				return quickSelect(array, start, pivot-1, K); //왼쪽부분 탐색
			else 
				return quickSelect(array, pivot+1, end, K); //K가 피벗의 인덱스보다 큰 값이면 오른쪽부분 탐색
		}
		
		return Integer.MIN_VALUE; 
	}
	
	// 퀵 정렬의 partition과 동일한 기능.
	public static int partition(final int[] array, int start, int end){
		int pivot = start + new Random().nextInt(end-start+1); //start에서 end까지를 범위로 하는 난수 생성.
		swap(array, end, pivot); // 피벗은 배열의 마지막 원소로 한다.
		
		int i = start - 1;
		
		for(int j=start; j<end; j++){
			if(array[j]<array[end]){
				i++;
				swap(array, i, j);
			}
		}
		swap(array, i+1, end);
		return i+1;
		
	}
	
	public static void swap(final int[] array, int a, int b){
		int tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}
}
```