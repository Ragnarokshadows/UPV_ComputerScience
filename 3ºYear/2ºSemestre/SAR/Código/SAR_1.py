import numpy as np
import sys

def k(x: int, B:int):
    return (x % (B-2)) + 1

def hashing(elementos: [], B:int):
    print("x    H(x)    h1(x)   h2(x)")
    res = np.zeros(B)
    for e in elementos:
        h1 = -1
        h2 = -1
        h = H(e,B)
        if res[h] == 0:
            res[h] = e
        else:
            h1 = (h + k(e,B)) % B
            if res[h1] == 0:
                res[h1] = e
            else:
                h2 = (h1 + k(e,B)) % B
                res[h2] = e

        print(str(e) + "    " + str(h) + "       " + str(h1) + "      " + str(h2))

    print()
    print(res)



def H(x: int, B:int):
    return x % B

if __name__ == "__main__":
    args = sys.argv[1:]

    if len(args)==0:
        print("python ./SAR_1.py -h <elementos> <B>' per a executar Hashing \n" +
              "python ./SAR_8.py -bu <text> <patro>' per a executar búsqueda amb errors")

    elif args[0] == "-h":
        hashing(map(int, args[1].strip('[]').split(',')), int(args[2]))
    else:
        print("'python ./SAR_8.py -bm <text> <patro>' per a executar Boyer-Moore \n'python ./SAR_8.py -bu <text> <patro>' per a executar búsqueda amb errors")


