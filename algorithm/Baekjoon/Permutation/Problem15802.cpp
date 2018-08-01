#include<iostream>
#include<cstdlib>
#include<stdio.h>
#include<time.h>

using namespace std;

int main(){

    srand((unsigned int)time(0));
    
    int random = rand();
    int result = random % 2;
    cout << result;
    return 0;
}