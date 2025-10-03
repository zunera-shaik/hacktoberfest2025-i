from collections import Counter


def majorityVotePoll(list):
    new_dict = dict.fromkeys(list, 0)
    for i in list:
        new_dict[i] += 1
    for contestant, vote in new_dict.items():
        if vote > len(list) // 2:
            print(f"value - {vote}")
            return contestant
    return None


print(majorityVotePoll(["A", "A", "A", "B", "C", "A"]))
print(majorityVotePoll(["A", "B", "B", "A", "C", "C"]))
print(majorityVotePoll(["A", "A", "B"]))
