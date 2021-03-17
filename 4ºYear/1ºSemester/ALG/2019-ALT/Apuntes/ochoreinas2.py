[InternetShortcut]
URL=class NQueensSolver1:
    def __init__ (self , n):
            self .n = n
    def is_complete(self , s):
        return len(s) == self.n
    def is_promising(self , s):
        return all(s[j] != s[i] and j-i != abs(s[j]-s[i]) for i in xrange(len(s))
            for j in xrange(i+1, len(s)))
    def backtracking(self , s):
        if self.is_complete(s): return s
        for row in range(self.n):
            s0 = s + [row]
            if self.is_promising(s0):
                found = self.backtracking(s0)
                print found
                if found != None: return found
        return None
    def solve(self ):
        return self.backtracking([])



class AllNQueensSolverSinYield(NQueensSolver1):
    def backtracking(self , s, soluciones):
        if self.is_complete(s):
            soluciones.append(s)
        for row in range(self.n):
            s0 = s + [row]
            if self.is_promising(s0):
                self.backtracking(s0, soluciones)

    def solve(self ):
        sol = []
        self.backtracking([], sol)
        return sol



for n in range(4, 8):
    print AllNQueensSolverSinYield(n).solve()
    print


