package Graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

class Node implements Comparable<Node>{
	
	//현재노드의 정점번호
	public int vertex;
	
	//출발노드에서 현재노드까지의 가중치
	public int weight;
	
	public Node(int v, int w){
		this.vertex = v;
		this.weight = w;
	}
	
	@Override
	public int compareTo(Node target){
		if(this.weight>target.weight)
			return 1;
		else if(this.weight<target.weight)
			return -1;
		else 
			return 0;
	}
}


public class Problem1753 {
	
	private static final int INF = (int)Double.POSITIVE_INFINITY;


	public static void main(String[] args){
		 
		int V;
		int E;
		Map<Integer, LinkedList<Node>> w;
		boolean[] S;
		int start;
		int [] dist;
		PriorityQueue<Node> priorityQ;
		
		Scanner sc = new Scanner(System.in);
		
		//정점 개수
		V = sc.nextInt();
		if(V<1 || V>20000) {
			sc.close();
			return;
		}
		
		//엣지 개수
		E = sc.nextInt();
		if(E<1 || E>300000){
			sc.close();
			return;
		}
		
		//출발노드 번호
		start = sc.nextInt();
		if(start> V || start<1) {
			sc.close();
			return;
		}
		
		//각 엣지의 가중치를 저장할 링크리스트
		w = new HashMap<Integer, LinkedList<Node>>();	
		for(int i=1; i<=V; i++){
			w.put(i, new LinkedList<Node>());
		}
		
		
		//집합 S에 포함된 정점들의 번호를 저장할 배열
		S = new boolean[V+1];
		
		//출발노드에서 각 노드까지의 경로길이를 저정할 배열
		dist = new int[V+1];
		Arrays.fill(dist, INF);
		dist[start] = 0;
		
		//S집합에 포함되지 않은 최단경로 길이를 갖는 정점을 찾기위한 우선순위 큐 (min heap tree)
		priorityQ = new PriorityQueue<Node>();
		
	
		
		
		for(int n=1; n<=E; n++){
			
			int i = sc.nextInt();
			int j = sc.nextInt();
			int k = sc.nextInt();
			if(k < 1 || k > 10){
				sc.close();
				return;
			};	
				
			boolean duplicated = false;
			
			for(Node node: w.get(i)){
				if(node.vertex == j){
					node.weight = Math.min(node.weight, k);
					duplicated = true;
				}
			}
			
			if(!duplicated)
				w.get(i).add(new Node(j,k));
		
		}
		

		
		priorityQ.add(new Node(start, 0));
		for(int i=1; i<=V; i++){
			if(i!=start) 
				priorityQ.add(new Node(i, INF));
		}
		
		
		dijkstra(S, w, V, priorityQ, dist);
				
		for(int i=1; i<=V; i++){
			if(dist[i]==INF)
				System.out.println("INF");
			else 
				System.out.println((int)dist[i]);
		}
		
		sc.close();
		
	}
	
	
	public static void dijkstra(final boolean[] S, final Map<Integer, LinkedList<Node>> w, int V, final PriorityQueue<Node> q, final int[] dist){
		while(!q.isEmpty()){
			Node u = q.poll();
			S[u.vertex] = true;
			for(Node adj: w.get(u.vertex)){
				if(!S[adj.vertex] && dist[adj.vertex] > dist[u.vertex] + adj.weight){
					dist[adj.vertex] = dist[u.vertex] + adj.weight;
					q.add(new Node(adj.vertex, dist[adj.vertex]));
				}
			}	
		}
	}
}
