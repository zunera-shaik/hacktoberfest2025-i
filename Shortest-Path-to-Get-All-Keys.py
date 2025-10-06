from collections import deque

class Solution:
    def shortestPathAllKeys(self, grid: list[str]) -> int:
        m, n = len(grid), len(grid[0])
        keys_total = 0
        start = None
        
        # Find starting point and count keys
        for i in range(m):
            for j in range(n):
                c = grid[i][j]
                if c == '@':
                    start = (i, j)
                elif 'a' <= c <= 'f':
                    keys_total |= (1 << (ord(c) - ord('a')))
        
        queue = deque([(start[0], start[1], 0, 0)])  # (x, y, keys, steps)
        visited = set([(start[0], start[1], 0)])
        
        dirs = [(0,1),(1,0),(0,-1),(-1,0)]
        
        while queue:
            x, y, keys, steps = queue.popleft()
            
            if keys == keys_total:
                return steps  # all keys collected!
            
            for dx, dy in dirs:
                nx, ny = x + dx, y + dy
                if not (0 <= nx < m and 0 <= ny < n):
                    continue
                cell = grid[nx][ny]
                if cell == '#':
                    continue
                new_keys = keys
                
                # If it's a key, collect it
                if 'a' <= cell <= 'f':
                    new_keys |= (1 << (ord(cell) - ord('a')))
                # If it's a lock, check if we have the key
                if 'A' <= cell <= 'F' and not (keys & (1 << (ord(cell) - ord('A')))):
                    continue
                
                if (nx, ny, new_keys) not in visited:
                    visited.add((nx, ny, new_keys))
                    queue.append((nx, ny, new_keys, steps + 1))
        
        return -1  # impossible
