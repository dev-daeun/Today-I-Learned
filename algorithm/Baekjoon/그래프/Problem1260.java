package Graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;


public class Problem1260 {
	
	public static void makeNewList(Map<Integer, LinkedList<Integer>> list, int first, int sec){
			LinkedList<Integer> adj = new LinkedList<Integer>();
			adj.add(sec);
			list.put(first, adj);
	}
	
	public static void insertList(LinkedList<Integer> sublist, int first, int sec){
		// 정점이 여러개 있을 때 번호가 작은 것 부터 방문하기 위해 오름차순으로 리스트에 삽입.
		int idx = sublist.size()-1;
		while(idx>=0){
			int num = sublist.get(idx);
			if(num<=sec) {
				sublist.add(idx+1, sec);
				break;
			}
					
			else idx--;
		}
		if(idx==-1) 
			sublist.addFirst(sec);	
	}
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		if(N>1000 || N<1) return;
		
		int M = sc.nextInt();
		if(M>10000 || M<1) return;
		
		int V = sc.nextInt();
		
		Map<Integer, LinkedList<Integer>> list = new HashMap<Integer, LinkedList<Integer>>();
		
		for(int i=1; i<=M; i++){
			int first = sc.nextInt();
			int sec = sc.nextInt();

			//처음 입력되는 정점이면
			if(list.get(first)==null)
				makeNewList(list, first, sec);
			else
				insertList(list.get(first), first, sec);
			
			
			if(list.get(sec)==null)
				makeNewList(list, sec, first);
			else 
				insertList(list.get(sec), sec, first);
			
			
		}
		

		
		//큐는 int형		
		Queue<Integer> q = new LinkedList<Integer>();
		
		// 각 노드의 방문여부 저장
		boolean [] DFSvisited = new boolean[N+1];
		boolean [] BFSvisited = new boolean[N+1];
		Arrays.fill(DFSvisited, false);
		Arrays.fill(BFSvisited, false);
		q.add(V);
		DFS(DFSvisited, list, V);
		System.out.println();
		BFS(BFSvisited, q, list);

	}
	
	
	public static void DFS(final boolean[] visited, Map<Integer, LinkedList<Integer>> list, int v){
		// 재귀호출 시간복잡도 = O(n+m)
		visited[v] = true;
		System.out.print(v+" ");
		LinkedList<Integer> sublist = list.get(v);
		if(sublist!=null){
			for(int element: sublist){
				if(!visited[element]){
					DFS(visited, list, element);
				}
			}
		}
		
	}
	
	public static void BFS(boolean[] visited, Queue<Integer> q, Map<Integer, LinkedList<Integer>> list){
		
		while(!q.isEmpty()){
			int num = q.poll();
				System.out.print(num+" ");						
				visited[num] = true;
				if(list.get(num)!=null){
					for(int node: list.get(num)){
						if(!q.contains(node) && !visited[node])
							q.add(node);
					}
				}	
			}
	}
}
