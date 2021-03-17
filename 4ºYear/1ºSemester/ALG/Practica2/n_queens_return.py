import sys

def nqueens(n):
    sol = [None]*n
    def show_solution(solution):
        output = ["    "+"".join(str((i+1) % 10) for i in  range(n))+"\n"]
        for i in range(n):
            output.append("%3d %s\n" % (i+1,"".join("X" if solution[j]==i else "." for j in range(n))))
        return "".join(output)
    def is_promising(longSol, queen):
        return all(queen != sol[i] and longSol-i != abs(queen-sol[i]) for i in range(longSol))
    def backtracking(longSol):
        if longSol == n:
            return show_solution(sol)
        else:
            for queen in range(n):
                if is_promising(longSol, queen):
                    sol[longSol] = queen
                    r = backtracking(longSol+1)
                    if r is not None:
                        return r
        return None # explicit
    return backtracking(0)

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print('\nUsage: %s N\n' % (sys.argv[0],))
        sys.exit()
    N = int(sys.argv[1])
    print(nqueens(N))
