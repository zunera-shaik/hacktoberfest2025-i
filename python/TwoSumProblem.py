def two_sum(nums, target):
    num_to_index = {}
    for i, num in enumerate(nums):
        complement = target - num
        if complement in num_to_index:
            return [num_to_index[complement], i]
        num_to_index[num] = i
    return []

# User input
nums = list(map(int, input("Enter the array elements separated by space: ").split()))
target = int(input("Enter the target value: "))

result = two_sum(nums, target)
if result:
    print("Indices of numbers adding up to target:", result)
else:
    print("No two numbers add up to the target.")
