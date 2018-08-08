package Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

class House{
	
	public int x; 
	public int y;
	public int num;
	public boolean visited;
	
	public House(int x, int y, int num){
		this.x = x;
		this.y = y;
		this.num = num;
		this.visited = false;
	}
	
}

public class Problem2667 {
	
	public static int N;

	public static int [] dx = { 0, -1, 0, 1 };
	public static int [] dy = { -1, 0, 1, 0 }; // 동서남북으로 이동할 좌표
	
	//단지번호를 인덱스로 갖는 원소에 단지의 크기를 저장할 어레이리스트.
	public static List<Integer> size = new ArrayList<Integer>();

	
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		sc.nextLine();
		
		House[][] a = new House[N][N];
		Queue<House> q = new LinkedList<House>();
		
		//입력부분
		for(int i=0; i<=N-1; i++){
			String line = sc.nextLine();
			for(int j=0; j<=N-1; j++){
				a[i][j] = new House(i, j, line.charAt(j)-'0');
			}
		}
		

	   CountAndNumber(q, a);
	   Collections.sort(size);
		
	   
	   //출력부
		System.out.println(size.size());
		for(int i = 0; i<= size.size()-1; i++){
			System.out.println(size.get(i));
		}
	}
	
	public static void CountAndNumber(final Queue<House> q, final House[][] a){
		
		int num = 1;
		for(int i=0; i<=N-1; i++){
			for(int j=0; j<=N-1; j++){
				
				//처음으로 방문하는 집을 발견하면 size배열에 새로 저장. 큐에 삽입 후 인접한 집들을 탐색하기 위해 BFS 호출.
				if(!a[i][j].visited && a[i][j].num==1){
					size.add(num-1, 1);
					a[i][j].visited = true;
					a[i][j].num = num;
					q.add(a[i][j]);
					BFS(q, a, num);
					num++;
				}
			}
		}		
	}
	
	public static void BFS(final Queue<House> q, final House[][] a, int num){
	
		
		while(!q.isEmpty()){
			House house = q.poll();
			if(house.x==N && house.y == N)
				break;
	
			for(int k=0; k<=3; k++){
				int movedX = house.x + dx[k];
				int movedY = house.y + dy[k];
				if(movedX>=0 && movedX <= N-1 && movedY >= 0 && movedY <= N-1){
					
					//CountAndNumber에서 삽입된 집과 인접한 집들에 대해 실행.
					if(!a[movedX][movedY].visited && a[movedX][movedY].num == 1){

						a[movedX][movedY].visited = true;
						a[movedX][movedY].num = num;
						size.set(num-1, size.get(num-1)+1);
						q.add(a[movedX][movedY]);		
					}	
				}
			}
		}
	}
}
