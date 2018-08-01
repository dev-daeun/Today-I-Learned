package Graph;


import java.util.Scanner;



public class Problem2606 {

	public static int [][] network;
	public static int n;
	public static int totalEdge;
	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		
		n = sc.nextInt();
		totalEdge = sc.nextInt();
		network = new int[n+1][n+1];
		
		for(int i=1; i<=totalEdge; i++){
			int from = sc.nextInt();
			int to = sc.nextInt();
			network[from][to] = network[to][from] = 1;
			
		}
		

		int [] cnt = new int[1];
		cnt[0] = 0;
		boolean [] visited = new boolean[n+1];
		visited[1] = true;
		dfs(visited, cnt, 1);
 		System.out.println(cnt[0]);
		
	}
	
	public static void dfs(final boolean [] visited, final int [] cnt, int num){
		for(int v=1; v<=n; v++){
			if(network[num][v]==1 && !visited[v]){
				visited[v] = true;
				cnt[0]++;
				dfs(visited, cnt, v);
			}		
		}	
		
	}

}
