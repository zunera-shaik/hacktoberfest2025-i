'''
Count the Number of Potential Pivots
Medium
xp icon
20/40
Time Limit: 2, Memory Limit: 256000
You are given an array arr of n distinct positive integers. This array represents the state of an original array after performing some partitioning steps of the QuickSort algorithm. Your task is to count how many elements could have been chosen as pivots during these steps to transform the original array into the current array arr.

Input
The first line contains an integer n - the size of the array.

The next line contains n distinct space separated integers arr1, arr2, arr3, .... ., arrn - the elements of the array.
Output
Print a single integer — the count of all elements that could have been chosen as pivots during the partitioning steps of the QuickSort algorithm.
Constraints
1 ≤ n ≤ 105

1 ≤ arri ≤ 105
Example
Input
8
6 1 4 7 13 10 17 19

Output
3

Explanation
The elements 7, 17, and 19 can serve as potential pivots.
'''

# Solution: 

n = int(input())
count = 0
arr = list(map(int,input().split()))

max_val = arr[0]
min_val = arr[-1]

min_max = [[] for _ in range(n)]
min_max[0].append(arr[0])

for i in range(1,n):
    max_val = max(max_val,arr[i])
    min_max[i].append(max_val)

for i in range(n-1,-1,-1):
    min_val = min(min_val,arr[i])
    min_max[i].append(min_val)

for small,big in min_max:
    if small == big:
        count+=1 

print(count)