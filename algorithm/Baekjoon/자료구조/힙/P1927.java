import java.util.PriorityQueue;
import java.util.Scanner;

class Node implements Comparable<Node>{
    int num;

    public Node(int num){
        super();
        this.num = num;
    }

    @Override
    public int compareTo(Node target){
        if(this.num > target.num)
            return 1;
        else if(this.num < target.num)
            return -1;
        
        return 0;
    }
}

class P1927{

    public static void main(String []args){
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int x;
        int arr[] = new int[N];
        PriorityQueue<Node> q = new PriorityQueue<Node>();
    

        for(int i=0; i<N; i++)
            arr[i] = sc.nextInt();
         
        for(int i=0; i<N; i++){
            if(arr[i]==0){
                if(q.isEmpty())
                    System.out.println(0);
                else
                    System.out.println(q.poll().num);
            } 
            else{
                q.offer(new Node(arr[i]));
            }
                
        }

    }
}