#include <stdio.h>

int main()
{
    int num;

    printf("Introduza um numero inteiro: ");
    scanf("%d", &num);

    printf("Valor introduzido: %d\n", num);
    printf("Endereco base: %p\n", (void *)&num);

    return 0;
}
