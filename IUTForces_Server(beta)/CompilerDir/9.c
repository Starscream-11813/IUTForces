#include<stdio.h>

int main()
{
    int a,b,tests;
    scanf("%d",&tests);
    while(tests--)
    {
    scanf("%d %d", &a,&b);
    printf("%d\n",a+b);
    }
    return 0;
}