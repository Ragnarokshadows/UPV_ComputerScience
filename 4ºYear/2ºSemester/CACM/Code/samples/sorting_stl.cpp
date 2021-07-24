
#include <vector>
#include <algorithm>

#include <cstdio>


using namespace std;


class Pair {
private:
    int     height,
            weight;

public:
    Pair() : height(0), weight(0)
    {
    }
    Pair( int h, int w ) : height(h), weight(w)
    {
    }

    void set(int h, int w)
    {
        this->height = h;
        this->weight = w;
    }

    int getHeight() { return height; }
    int getWeight() { return weight; }


    // returns true when this is less than other
    bool operator < (Pair & other)
    {
        // For sorting in ascending order of height
        if (this->height < other.height) return true;
        if (this->height > other.height) return false;

        // and descending order of weight when same height
        if (this->weight > other.weight) return true;
        if (this->weight < other.weight) return false;

        return false;
    }
};

bool comparatorFunction(Pair *p1, Pair *p2) { return (*p1 < *p2); }

struct ComparatorClass {
    bool operator () (Pair *p1, Pair *p2) { return (*p1 < *p2); }
};


int main(int argc, char *argv[])
{
    vector<Pair *>  pairs;
    Pair p1;
    int n, i, h, w;
    bool found;
    ComparatorClass objectComparator;

    scanf("%d", &n);
    for (i = 0; i < n; i++) {
        scanf("%d%d", &h, &w);
        pairs.push_back(new Pair(h, w));
    }

    // Example of how to use comparison operators
    p1.set(178, 90);
    Pair p2(190, 100);
    if (p1 < p2) {
        printf("p1 should go before p2\n");
    } else {
        printf("p2 should go before p1\n");
    }
    if (objectComparator( &p1, &p2)) {
        printf("p1 should go before p2\n");
    } else {
        printf("p2 should go before p1\n");
    }

    sort(pairs.begin(), pairs.end(), comparatorFunction);
    //sort(pairs.begin(), pairs.end(), objectComparator);

    for (i = 0; i < n; i++ )
        printf(" %5d  %5d\n", pairs[i]->getHeight(), pairs[i]->getWeight());
    printf("\n");

    p1.set(185, 105);

    found = binary_search(pairs.begin(), pairs.end(), &p1, comparatorFunction);
    //found = binary_search(pairs.begin(), pairs.end(), &p1, objectComparator);

    if (found) {
        printf("( %d, %d)  found!\n", p1.getHeight(), p1.getWeight());
    } else {
        printf("( %d, %d)  not found!\n", p1.getHeight(), p1.getWeight());
    }

    p1.set(177, 74);

    //found = binary_search(pairs.begin(), pairs.end(), &p1, comparatorFunction);
    found = binary_search(pairs.begin(), pairs.end(), &p1, objectComparator);

    if (found) {
        printf("( %d, %d)  found!\n", p1.getHeight(), p1.getWeight());
    } else {
        printf("( %d, %d)  not found!\n", p1.getHeight(), p1.getWeight());
    }

    return EXIT_SUCCESS;
}
