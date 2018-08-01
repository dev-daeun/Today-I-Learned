package DP;

import java.util.Scanner;

public class Problem11727 {
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		long [] dp = new long [n+1];
	
		
     
		for(int i=1; i<=n; i++){
		  if(i==1) dp[i]= 1;
		  else if(i==2) dp[i] = 3;
		  else dp[i] = (dp[i-1] + 2 * dp[i-2])%10007;
		}
		
		System.out.println(dp[n]);
		
	}
}
