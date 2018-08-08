package Graph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Vertex{
	int x;
	int y;
	int h;
	int num;
	int dist;
	boolean visited;
	
	Vertex(int x, int y, int h, int num){
		this.x = x;
		this.y = y;
		this.h = h;
		this.num = num;
		dist = 0;
		visited = false;
	}
}
public class Problem7569 {

	//하나의 정점과 인접한 정점들을 순회하기 위한 배열들
	static int [] adjX = {0, -1, 0, 1, 0, 0};
	static int [] adjY = {-1, 0, 1, 0, 0, 0};
	static int [] adjH = {0, 0, 0, 0, -1, 1};
	
	//입력받을 가로, 세로, 높이 
	static int Y;
	static int X;
	static int H;
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		Y = sc.nextInt();
		X = sc.nextInt();
		H = sc.nextInt();
		
		Vertex [][][] box = new Vertex[X+1][Y+1][H+1];
		
		//입력
		for(int h=1; h<=H; h++){
			for(int x=1; x<=X; x++){
				for(int y=1; y<=Y; y++){
					box[x][y][h] = new Vertex(x, y, h, sc.nextInt());
				}
			}
		}
		
		Queue<Vertex> q = new LinkedList<Vertex>();
		int zeroCnt = 0;
		
		//모든 정점을 순회하면서 1이면 큐에 삽입하고, 0이면 0의 갯수를 세는 zeroCnt를 증가한다.
		//zeroCnt는 처음에 숫자가 0이었던 정점의 갯수와 0에서 1로 변경된 정점의 갯수를 비교하여, -1의 출력여부를 결정하기 위함.
		for(int h=1; h<=H; h++){
			for(int x=1; x<=X; x++){
				for(int y=1; y<=Y; y++){
					if(box[x][y][h].num==1){
						q.add(box[x][y][h]);
					}
					else if(box[x][y][h].num==0)
						zeroCnt++;
					else 
						continue;
				}
			}
		}
		if(zeroCnt > bfs(q, box))
			System.out.println(-1);
		else{
			int max = 0;
			for(int h=1; h<=H; h++){
				for(int x=1; x<=X; x++){
					for(int y=1; y<=Y; y++){
						max = Math.max(max, box[x][y][h].dist);
					}
				}
			}
			System.out.println(max);
		}
			
		
	}
	
	//3차원배열의 Index범위 조건을 체크하는 함수.
	public static boolean isInRange(int point, int dimension){
		switch(dimension){
			case 1: return point <= X && point >=1 ? true : false;
			case 2: return point <= Y && point >=1 ? true : false;
			case 3: return point <= H && point >=1 ? true : false;
			default : return false;
		}
	}
	
	public static int bfs(Queue<Vertex> q, final Vertex[][][] box){
		//zeroToOne은 0에서 1로 바뀐 정점의 갯수를 저장하는 변수
		int zeroToOne = 0;
		
		while(!q.isEmpty()){
			Vertex v = q.poll();
			v.visited = true;
			
			for(int i=0; i<6; i++){
				int movedX = v.x + adjX[i];
				int movedY = v.y + adjY[i];
				int movedH = v.h + adjH[i];
				if(isInRange(movedX, 1)&&isInRange(movedY, 2)&&isInRange(movedH, 3)){
					if(box[movedX][movedY][movedH].num==0 && !box[movedX][movedY][movedH].visited){
						box[movedX][movedY][movedH].num = 1;
						//dist는 인접한 정점에 의해 1로 변경되는 최단길이.(토마토가 익는 데 걸린 일수)
						box[movedX][movedY][movedH].dist =  v.dist + 1;
						zeroToOne++;
						q.add(box[movedX][movedY][movedH]);
					}
				}
				
			}
		}
		return zeroToOne;
	}

}
