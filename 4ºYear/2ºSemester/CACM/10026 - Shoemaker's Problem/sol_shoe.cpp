#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct Job
{
    int days;
    int fine;
    int pos;
};

bool comp(const Job &j1, const Job&j2)
{
    return j1.days * j2.fine < j2.days * j1.fine;
}

int main()
{
    int n_cases, n_jobs;

    cin >> n_cases;

    while (n_cases--)
    {
        cin.ignore();
        cin >> n_jobs;

        vector<Job> jobs(n_jobs);
        for (int i = 0; i < n_jobs; ++i)
        {
            cin >> jobs[i].days >> jobs[i].fine;
            jobs[i].pos = i + 1;
        }
        
        stable_sort(jobs.begin(), jobs.end(), comp);

        cout << jobs[0].pos;

        for (int i = 1; i < n_jobs; ++i) cout << " " << jobs[i].pos;
        
        cout << endl;

        if (n_cases)
        {
            cout << endl;
        }

    }
    return 0;
}
