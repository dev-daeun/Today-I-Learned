#include<stdio.h>
#include<string.h>
using namespace std;
struct PERSON
{
    char* name;
    int age;
    bool gender;
};


int main()
{
    PERSON p1;
    strcpy(p1.name, "홍길동");
    p1.age = 18;
    p1.gender = true;
 
    printf("이름 : %s, 나이 : %d",p1.name, p1.age);
    
    if(p1.gender == true)
        printf("성별 : 남성\n");
    else
        printf("성별 : 여성\n");
 
    return 0;
}

