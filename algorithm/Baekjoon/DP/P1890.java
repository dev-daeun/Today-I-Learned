package DP;
import java.util.Scanner;

class P1890{

	static  int N;
	static int val[][];
   static long d[][];
	public static void main(String args[]){

        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        val = new int [N+1][N+1];
        d = new long [N+1][N+1];

        for(int i=1; i<=N; i++){
            for(int j=1; j<=N; j++){
                val[i][j] = sc.nextInt();
                d[i][j] = 0;
            }
        }

        d[1][1] = 1;
        for(int x=1; x<=N; x++){
            for(int y=1; y<=N; y++){
            	if(d[x][y]==0 || (x==N && y == N)) // 왼쪽에 조건문 필요한 이유 : val[N][N]은 0이고, d[N][N]에 이미 값이 있다고 할 때, 
            		continue;
               if(x+val[x][y] <= N) // x + 0 = x 이므로 d[N][N] += d[N][N] 이 되버린다.
                    d[x+val[x][y]][y] += d[x][y];
               if(y+val[x][y] <= N) // y + 0 = y 이므로 d[N][N] += d[N][N] 이 되버린다.
                    d[x][y+val[x][y]] += d[x][y];

            }
        }

        System.out.println(d[N][N]);

    }

    
}