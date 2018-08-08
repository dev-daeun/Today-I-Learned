#include<stdio.h>
using namespace std;

class Person{
    private:
        char* name;
        int age;
        char gender;
    
    public:
        Person(char*name, int age, char gender);
        ~Person();
        void setAge(int age);
        char* getName();
        int getAge();
        char getGender();
};
Person::Person(char* name, int age, char gender){
    this -> name = name;
    this -> age = age;
    this -> gender = gender;
}

Person::~Person(){

}
char Person::getGender(){
    return this -> gender;
}
int Person::getAge(){
    return this-> age;
}
char* Person::getName(){
    return this -> name;
}
void Person :: setAge(int age){
    this -> age = age;
}

int main(){
    char name[10];
    int age;
    char gender;
    scanf("%s", name);
    scanf("%d", &age);
    scanf("%c", &gender);
    Person p = Person(name, age, gender);
    printf("name : %s\n", p.getName());
    printf("age : %d\n", p.getAge());
    printf("gender : %c", p.getGender());
    return 0;
}
