package DP;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Loc{

    int loc;
    int dis;
    boolean visited;

    public Loc(int loc){
        this.loc = loc;
        this.dis = 0;
        this.visited = false;
    }
}

public class P1697 {
    static int N;
    static int K;

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();
        Loc loc[] = new Loc[100001];
        for (int i = 0; i <= 100000; i++)
            loc[i] = new Loc(i);
        Queue<Loc> q = new LinkedList<Loc>();
        loc[N].visited = true;
        q.add(loc[N]);
        bfs(q, loc);
        System.out.println(loc[K].dis);
    }
    public static void bfs(Queue<Loc> q, Loc[] arr){
        while(!q.isEmpty()){
            Loc x = q.poll();
            if(x.loc==K)
                return;
            if(x.loc>=0 && x.loc<=100000){
                if(x.loc+1 >=0 && x.loc+1 <= 100000) {
                    if(!arr[x.loc+1].visited){
                        arr[x.loc+1].dis = x.dis + 1;
                        arr[x.loc+1].visited = true;
                        q.add(arr[x.loc+1]);
                    }
                }
                if(x.loc-1 >=0 && x.loc-1 <= 100000){
                    if(!arr[x.loc-1].visited){
                        arr[x.loc-1].dis = x.dis + 1;
                        arr[x.loc-1].visited = true;
                        q.add(arr[x.loc-1]);
                    }

                }
                if(x.loc*2 >=0 && x.loc*2 <= 100000){
                    if(!arr[x.loc*2].visited){
                        arr[x.loc*2].dis = x.dis + 1;
                        arr[x.loc*2].visited = true;
                        q.add(arr[x.loc*2]);
                    }
                }
            }
        }

    }
}
