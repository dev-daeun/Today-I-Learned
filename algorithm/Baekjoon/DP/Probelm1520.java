package DP;

import java.util.Scanner;

class Cube{
	int num;
	int path;
	boolean visited;
	
	Cube(int num){
		this.num = num;
		this.path = 0;
		this.visited = false;
	}
}
public class Probelm1520 {

	public static int [] dx = {0, -1, 0, 1};
	public static int [] dy = {-1, 0, 1, 0};
	public static int M, N;
	public static Cube [][] mat; 
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		M = sc.nextInt();
		N = sc.nextInt();

		mat = new Cube[M+1][N+1];
		
		for(int i=1; i<=M; i++)
			for(int j=1; j<=N; j++)
				mat[i][j] = new Cube(sc.nextInt());
		
		mat[1][1].path = 1;
	
		
		
		System.out.println(dfs(M,N));
		
	}
	
	public static int dfs(int x, int y){
		if(x==1 && y==1)
			return mat[1][1].path;
		
		if(mat[x][y].visited)
			return mat[x][y].path;
		
		mat[x][y].visited = true;
		
		for(int k=0; k<=3; k++){
			int prevX = x + dx[k];
			int prevY = y + dy[k];
			if((1<=prevX && M>=prevX) && (1<=prevY && N>=prevY)){
				if(mat[x][y].num < mat[prevX][prevY].num)
						mat[x][y].path += dfs(prevX, prevY);
				else 
					continue;
			}
			else 
				continue;
		}
		
		return mat[x][y].path;
		

	}
}
