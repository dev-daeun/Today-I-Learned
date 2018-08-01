package DP;

import java.util.Scanner;

public class Problem10844 {

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		
		long [][] dp = new long[N+1][10];
		dp[1][0] = 0;
		
		for(int i=1; i<=9; i++)
			dp[1][i] = 1;
		
		for(int i=2; i<=N; i++){
			for(int j=0; j<=9; j++){
				dp[i][j] = 0;
				if(j-1>=0)
					dp[i][j] += dp[i-1][j-1] % 1000000000;
				if(j+1<=9)
					dp[i][j] += dp[i-1][j+1] % 1000000000;
				
				
			}
		}
		
		long sum = 0;
		for(int i=0; i<=9; i++)
			sum += dp[N][i];
			
		System.out.println(sum % 1000000000);

	}
}
