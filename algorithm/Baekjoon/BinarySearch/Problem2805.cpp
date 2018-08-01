#include<iostream>
#include <bits/stdc++.h>
using namespace std;

int main(){


    int N,M;
    
    cin>>N;
    cin>>M;
    int *arr = new int[N];
    int n = sizeof(arr)/sizeof(arr[0]);
    

    for(int i=0; i<N; i++)
        cin >> arr[i];

    sort(arr, arr+n);
    
    for(int i=0; i<N; i++)
        cout << arr[i] << '\n';

    int min = 0;
    int max = N-1;
    int mid = (min + max) / 2;
    int result = 0;

        
    while(M>0){
        //mid를 기준으로 오른쪽, 왼쪽에 위치한 나무들의 길이 차이의 합
        int rightSum=0, leftSum=0;
        for(int i=0; i<mid; i++)
            leftSum += arr[i+1] - arr[i];
        
        for(int i=mid; i<max-1; i++)
            rightSum += arr[i+1] - arr[i];

        if(rightSum > M){
            min = mid;
            mid = (max+min)/2;
        }
        else{
            M -= rightSum;
            max = mid;
            mid = (max+min)/2;
            result = mid;
        }
    }

    cout << result;
    return 0;
}