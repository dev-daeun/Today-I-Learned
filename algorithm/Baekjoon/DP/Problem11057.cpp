#include<stdio.h>
#include<iostream>
#include<string.h>
using namespace std;

int main(){
    
    int N;
    int **arr;
    const int mod = 10007;
    cin >> N;
    arr = new int*[N+1];
    
    for(int i=0; i<=N; i++){

        arr[i] = new int[10];
        //0으로 초기화
        memset(arr[i], 0, sizeof(int)*10);
        
    }
    
    //1자리 수일 때 오르막 수는 0~9 각각 1개다.
    for(int i=0; i<10; i++)
            arr[1][i] = 1;

    
    for(int i=2; i<=N; i++){
        for(int j=0; j<=9; j++){
            for(int k=0; k<=j; k++){
                arr[i][j] += arr[i-1][k];
                arr[i][j] %= mod;
            }
        }
    }

    //자릿 수가 N일 때 만들 수 있는 오르막 수의 갯수를 구하기 때문에 N자리에 0~9가 올 경우의 모든 경우의 수를 더한다.
    int result = 0;
    for(int i=0; i<=9; i++)
        result += arr[N][i];

    cout << result % mod;
    delete []arr;
    return 0;
}