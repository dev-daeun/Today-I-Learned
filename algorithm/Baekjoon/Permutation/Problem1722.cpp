#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;



int factorial(int n){
    if(n==0)
      return 0;
    int result = 1;
    for(int i=1; i<=n; i++)
        result *= i;
    return result;
}

int main()
{
   int N;
   cin>>N;

   
   int pNum;
   cin >> pNum;

   int k;
   vector<int> v;
   vector<int> input;
   for(int i=1; i<=N; i++){
        v.push_back(i);
    }
   
   if(pNum==1){
       cin >> k;
       int cnt = 0;
       
       do{
          if(k==0){
              for(int i=0; i<N; i++)
                cout << v[i] << ' ';
              break;
          }
          else{
              
          }
       }while(next_permutation(v.begin(), v.end()));
   }
   if(pNum==2){
        string inputString;
        getline(cin, inputString);
        for(int i=0; i<inputString.length(); i++){
            if(inputString[i]!=' ') input.push_back(inputString[i]-48);
        }
        
        int cnt = (input[0]-1) * factorial(N-1);
        for(int i=1; i<N; i++){
            int cha = 0;
            if(input[i-1]>input[i])
                cha = input[i] - 1;
            else
                cha = input[i] - (i+1);
            
            // cout << "cha: "<<cha<<'\n';
            // cout << "fac : "<<cha * factorial(N-(i+1))<<'\n';
            if(cha>0)
                cnt += cha * factorial(N-(i+1));
         
        }

        cout << cnt+1;
   }
   
    return 0;
}