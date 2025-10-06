class NQueensSolver:
    
    def __init__(self, n):
        self.n = n
        self.solutions = []
        self.board = [-1] * n
    
    def is_safe(self, row, col):
        for i in range(row):
            if self.board[i] == col:
                return False
            
            if self.board[i] - i == col - row:
                return False
            
            if self.board[i] + i == col + row:
                return False
        
        return True
    
    def solve(self, row=0):
        if row == self.n:
            self.solutions.append(self.board[:])
            return
        
        for col in range(self.n):
            if self.is_safe(row, col):
                self.board[row] = col
                self.solve(row + 1)
                self.board[row] = -1
    
    def get_all_solutions(self):
        self.solutions = []
        self.solve()
        return self.solutions
    
    def print_board(self, solution):
        for row in range(self.n):
            line = ""
            for col in range(self.n):
                if solution[row] == col:
                    line += "Q "
                else:
                    line += ". "
            print(line)
        print()
    
    def print_all_solutions(self):
        solutions = self.get_all_solutions()
        print(f"Total solutions for {self.n}-Queens: {len(solutions)}\n")
        
        for idx, solution in enumerate(solutions):
            print(f"Solution {idx + 1}:")
            self.print_board(solution)
    
    def get_solution_count(self):
        self.solutions = []
        self.solve()
        return len(self.solutions)
    
    def get_first_solution(self):
        self.solutions = []
        self.solve_first()
        return self.solutions[0] if self.solutions else None
    
    def solve_first(self, row=0):
        if row == self.n:
            self.solutions.append(self.board[:])
            return True
        
        for col in range(self.n):
            if self.is_safe(row, col):
                self.board[row] = col
                if self.solve_first(row + 1):
                    return True
                self.board[row] = -1
        
        return False

if __name__ == "__main__":
    print("=== N-Queens Problem Solver ===\n")
    
    for n in range(4, 9):
        solver = NQueensSolver(n)
        count = solver.get_solution_count()
        print(f"{n}-Queens: {count} solutions")
    
    print("\n=== Detailed Solutions for 4-Queens ===\n")
    solver = NQueensSolver(4)
    solver.print_all_solutions()
    
    print("=== First Solution for 8-Queens ===\n")
    solver = NQueensSolver(8)
    first_solution = solver.get_first_solution()
    if first_solution:
        print("Board representation:")
        solver.print_board(first_solution)
        print(f"Queen positions (row, col): {[(i, first_solution[i]) for i in range(8)]}")
