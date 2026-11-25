#include <stdio.h>

int average(int v1,int v2)
{
   return (v1 + v2) / 2;
}
int main()
{
  int a,b,avg;
  
  printf("Introduza o primeiro inteiro: ");
  scanf("%d", &a);

  printf(Introduza o segundo inteiro: ");
  scanf("%d", &b);

  avg = average(a, b);
  printf("A media inteira de %d e %d e %d\n", a, b, avg);  

  return 0;
}
