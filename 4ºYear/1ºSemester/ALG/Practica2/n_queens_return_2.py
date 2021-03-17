import sys

def nqueens(n, allSolutions):
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
            results.append(show_solution(sol))
        if allSolutions or len(results) == 0:
            for queen in range(n):
                if is_promising(longSol, queen):
                    sol[longSol] = queen
                    backtracking(longSol+1)
        return None # explicit
    results = []
    backtracking(0)
    return results
        
if __name__ == "__main__":
    if len(sys.argv) not in (2,3):
        print('\nUsage: %s N [TODAS]\n' % (sys.argv[0],))
        sys.exit()
    N = int(sys.argv[1])
    allSolutions = len(sys.argv)==3 and sys.argv[2]=='TODAS'
    for sol in nqueens(N,allSolutions):
        print(sol)
