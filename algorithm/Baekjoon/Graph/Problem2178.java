package Graph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Cube{
	int num;
	boolean visited;
	int dist;
	int x;
	int y;
	
	public Cube(int num, int x, int y){
		this.num = num;
		this.x = x;
		this.y = y;
		this.visited = false;
	}
}
public class Problem2178 {

	public static int N;
	public static int M;

	public static int [] dx = { 0, -1, 0, 1 };
	public static int [] dy = { -1, 0, 1, 0 }; // 동서남북으로 이동할 좌표
	
	public static void main(String [] args){
		Scanner sc = new Scanner(System.in);
		
		 N = sc.nextInt();
		 M = sc.nextInt();
		
		Cube [][] a = new Cube[N+1][M+1];
		sc.nextLine();
		for(int i=1; i<=N; i++){
			String line = sc.nextLine();
			for(int j=0; j<=M-1; j++){
					a[i][j+1] = new Cube(line.charAt(j)-'0', i, j+1);
	
			}
		}		

		Queue<Cube> q = new LinkedList<Cube>();
		
		a[1][1].dist = 1;
		q.add(a[1][1]);
		bfs(q, a);
		System.out.println(a[N][M].dist);
	}
	
	public static void bfs(final Queue<Cube> q, final Cube[][] a){
		
		while(!q.isEmpty()){
			Cube cube = q.poll();
//			cube.visited = true;
			
			if(cube.x==N && cube.y==M)
				break;
			
			for(int i=0; i<=3; i++){
				
				int movedX = cube.x + dx[i];
				int movedY = cube.y + dy[i];
				if(movedX >=1 && movedX <= N && movedY >=1 && movedY <= M){
					if(!a[movedX][movedY].visited && a[movedX][movedY].num==1){
						a[movedX][movedY].dist = a[cube.x][cube.y].dist + 1;
						
						//방문여부 갱신
						a[movedX][movedY].visited = true;
						q.add(a[movedX][movedY]);

					}
				}
			}
			
			
		}
	}
	

}
