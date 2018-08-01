package DP;

import java.util.Arrays;
import java.util.Scanner;

public class Problem11052 {
	
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		
		//세트 가격 배열
		int [] p = new int[n+1];
		p[0] = 0;
		
		//시간복잡도 : O(n^2)
		for(int i=1; i<=n; i++)
			p[i] = sc.nextInt();
		
		
		// 붕어빵 갯수에 따른 최대 수익을 저장할 배열
		int [] d = new int[n+1];
		Arrays.fill(d, 0);
		d[0] = 0;
		d[1] = p[1];
		
		for(int i=2; i<=n; i++){
			for(int k=1; k<=i; k++)
				d[i] = Math.max(d[i], d[i-k]+p[k]);
		}
		
		System.out.println(d[n]);
		
	}
}
