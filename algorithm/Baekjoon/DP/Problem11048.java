package DP;

import java.util.Scanner;


class Candy{
	int x; 
	int y;
	int num;
	
	Candy(int x, int y, int num){
		this.x = x;
		this.y = y;
		this.num = num;
	}
}
public class Problem11048 {

	public static int N,M;
	public static Candy dp[][];
	
	public static int dx[] = {0, -1, -1};
	public static int dy[] = {-1, -1, 0};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		
		dp = new Candy[N+1][M+1];
		
		for(int i=1; i<=N;i++){
			for(int j=1; j<=M; j++){
				dp[i][j] = new Candy(i, j, sc.nextInt());
			}
		}
		for(int i=1; i<=N; i++){
			for(int j=1; j<=M; j++){
				int max = dp[i][j].num;
				for(int k=0; k<=2; k++){
					int movedX = dp[i][j].x + dx[k];
					int movedY = dp[i][j].y + dy[k];
					if((1<=movedX && movedX<=N) && (1<=movedY && movedY<=M))
						max = Math.max(max, dp[movedX][movedY].num + dp[i][j].num);
				}
				dp[i][j].num = max;
			}
		}
		System.out.println(dp[N][M].num);
		
	}
	
}
