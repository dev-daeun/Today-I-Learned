package DP;

import java.util.Scanner;


public class Problem1932 {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);


        int n = sc.nextInt();
        if(n>500 || n<1) return;
        
        int[][] arr = new int[n+1][n+1];

        for(int i=1; i<=n; i++){
        	for(int j=1; j<=i; j++){
        		arr[i][j] = sc.nextInt();
        		if(arr[i][j]<0 || arr[i][j]>10000) return;
        	}
        }
        for(int row=n-1; row>=1; row--){
        	for(int col=1; col<=n-1; col++){
        		arr[row][col] = Math.max(arr[row][col]+arr[row+1][col], arr[row][col]+arr[row+1][col+1]);
        	}
         }
        
 
       System.out.println(arr[1][1]);
    
    }

}
