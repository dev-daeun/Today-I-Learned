package DP;

import java.util.Scanner;

public class Problem2193 {

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		
		// d0[n] = n번째 자리에 0을 채워서 만들 수 있는 이친수의 갯수
		// d1[n] = n번째 자리에 1을 채워서 만들 수 있는 이친수의 갯수
		
		long [] d0 = new long[n+1];
		long [] d1 = new long[n+1];
		
		
		d0[1] = 0;
		d1[1] = 1;
		
		
		for(int i=2; i<=n; i++){
			d0[i] = d0[i-1] + d1[i-1];
			d1[i] = d0[i-1];	
		}
		
		System.out.println(d0[n]+d1[n]);
	}
}
