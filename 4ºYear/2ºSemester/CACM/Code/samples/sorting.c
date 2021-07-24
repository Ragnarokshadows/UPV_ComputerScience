
#include <stdio.h>
#include <stdlib.h>

struct pair {
    int     weight,
            height;
};

int compare(const struct pair *p1, const struct pair *p2)
{
    // For sorting in ascending order of height
    if (p1->height < p2->height) return -1;
    if (p1->height > p2->height) return  1;

    // and descending order of weight when same height
    if (p1->weight > p2->weight) return -1;
    if (p1->weight < p2->weight) return  1;

    return 0;
}
int compare2(const void *v1, const void *v2)
{
    struct pair *p1 = (struct pair *)v1;
    struct pair *p2 = (struct pair *)v2;

    // For sorting in ascending order of height
    if (p1->height < p2->height) return -1;
    if (p1->height > p2->height) return  1;

    // and descending order of weight when same height
    if (p1->weight > p2->weight) return -1;
    if (p1->weight < p2->weight) return  1;

    return 0;
}
int compare_double(const void *v1, const void *v2)
{
    double *d1 = (double *)v1;
    double *d2 = (double *)v2;

    if ( *d1 < *d2 )      return -1;
    else if ( *d1 > *d2 ) return  1;
    else                  return  0;
}

int main(int argc, char *argv[])
{
    struct pair *pairs, *p, p1;
    int n, i;

    scanf("%d", &n);
    pairs = (struct pair *)calloc(n, sizeof(struct pair));
    for (i = 0; i < n; i++) {
        scanf("%d%d", &pairs[i].height, &pairs[i].weight);
    }

    /*
    qsort(pairs, n, sizeof(struct pair), (int (*)(const void *, const void*))compare);
    */
    qsort(pairs, n, sizeof(struct pair), compare2);

    for (i = 0; i < n; i++) printf(" %5d  %5d\n", pairs[i].height, pairs[i].weight);
    printf("\n");

    p1.height = 185;
    p1.weight = 105;

    /*
    p = bsearch(&p1, pairs, n, sizeof(struct pair), (int (*)(const void *, const void*))compare);
    */
    p = bsearch(&p1, pairs, n, sizeof(struct pair), compare2);

    if (p == NULL) {
        printf("( %d, %d)  not found!\n", p1.height, p1.weight);
    } else {
        printf("( %d, %d)  found!\n", p1.height, p1.weight);
    }

    p1.height = 177;
    p1.weight = 74;

    p = bsearch(&p1, pairs, n, sizeof( struct pair ), (int (*)(const void *, const void*))compare);

    if (p == NULL) {
        printf("( %d, %d)  not found!\n", p1.height, p1.weight);
    } else {
        printf("( %d, %d)  found!\n", p1.height, p1.weight);
    }

    return EXIT_SUCCESS;
}
