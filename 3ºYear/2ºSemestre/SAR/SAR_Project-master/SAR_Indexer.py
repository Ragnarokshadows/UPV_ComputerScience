import argparse
import pickle
import sys
import time

from SAR_lib import SAR_Project


if __name__ == "__main__":

    parser = argparse.ArgumentParser(description='Index a directory with news in json format.')
    parser.add_argument('newsdir', metavar='newsdir', type=str,
                        help='directory with the news.')

    parser.add_argument('index', metavar='index', type=str,
                        help='name of the file to save the project object.')

    parser.add_argument('-S', '--stem', dest='stem', action='store_true', default=False, 
                    help='compute stem index.')

    parser.add_argument('-P', '--permuterm', dest='permuterm', action='store_true', default=False,
                    help='compute permuterm index.')

    parser.add_argument('-M', '--multifield', dest='multifield', action='store_true', default=False, 
                    help='compute index for all the fields.')

    parser.add_argument('-O', '--positional', dest='positional', action='store_true', default=False, 
                    help='compute positional index.')

    args = parser.parse_args()

    newsdir = args.newsdir
    indexfile = args.index

    indexer = SAR_Project()

    t0 = time.time()
    indexer.index_dir(newsdir, **vars(args))
    t1 = time.time()
    with open(indexfile, 'wb') as fh:
            pickle.dump(indexer, fh)
    t2 = time.time()
    indexer.show_stats()
    print("Time indexing: %2.2fs." % (t1 - t0))
    print("Time saving: %2.2fs." % (t2 - t1))
    print()