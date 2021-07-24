
#include <stdio.h>
#include <string.h>

#define MAX_CANDIDATES 20

static char candidates[MAX_CANDIDATES][100];

static int ballots[1000][MAX_CANDIDATES];

int getline( char *s )
{
    int ch, counter=0;

    while( (ch=getchar()) != '\n' && ch != EOF ) { *s++ = ch; ++counter; }

    *s = '\0';

    return counter;
}

int active_candidates [MAX_CANDIDATES]; 
int votes[MAX_CANDIDATES];

int main( int argc, char * argv[] )
{
    char temp[256], *ptr;
    int num_cases, num_candidates, num_ballots, c, i, rc, b, found;
    int max_votes, min_votes, concluded;

    rc=scanf( "%d", &num_cases );

    while( --num_cases >= 0 ) {

        rc=scanf( "%d", &num_candidates );
        getline(temp); /* For jumping to the next line */

        for( c=0; c < num_candidates; c++ ) getline( candidates[c] );

        num_ballots=0;
        while( getline(temp) > 0 ) {

            for( i=0,ptr = strtok( temp, " " ); ptr != NULL; ptr = strtok( NULL, " " ),i++ ) {
                ballots[num_ballots][i] = atoi( ptr )-1;
            }
            ++num_ballots;
        }

        for( c=0; c < num_candidates; c++ ) active_candidates[c]=1;
        concluded = !(num_candidates > 0 && num_ballots > 0);
        while( ! concluded ) {

            for( c=0; c < num_candidates; c++ ) votes[c]=0;
            for( b=0; b < num_ballots; b++ ) {
                found=0;
                for( i=0; i < num_candidates && !found; i++ ) {
                    c = ballots[b][i];
                    if ( active_candidates[c] ) {
                        votes[c]++;
                        found=1;
                    }
                }
            }

            min_votes = 1000;
            max_votes = -1;
            for( c=0; c < num_candidates; c++ ) {
                if ( active_candidates[c] ) {
                    min_votes = (votes[c] < min_votes) ? votes[c] : min_votes;
                    max_votes = (votes[c] > max_votes) ? votes[c] : max_votes;
                }
            }
            if ( min_votes == max_votes ) {
                for( c=0; c < num_candidates; c++ ) {
                    if ( active_candidates[c] ) {
                        printf( "%s\n", candidates[c] );
                    }
                }
                concluded=1;
            } else {
                for( c=0; c < num_candidates && !concluded; c++ ) {
                    if ( votes[c] == min_votes ) active_candidates[c] = 0;

                    if ( 2*votes[c] >= num_ballots ) {
                        printf( "%s\n", candidates[c] );
                        concluded=1;
                    }
                }
            }
        }
        if ( num_cases > 0 ) printf( "\n" );
    }

    return 0;
}
