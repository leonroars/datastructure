from collections import deque

dq = deque()
dq.appendleft(-1)
dq.appendleft(-2)
dq.appendleft(-3)
dq.append(1)
dq.append(2)
dq.append(3)

# Check current elements status
print(dq)

# Check len(dq)
print(len(dq)) # 6

# Test count() & index(elem)
print(dq.count(1)) # 1
print(dq.count(0)) # 0
print(dq.index(-1)) # 2

# Test extend
dq.extend([4, 5, 6])
dq.extendleft([-4, -5, -6])
dq.extendleft("abc") # [c, b, a, -6, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6]

# Check current elements status
print(dq)
# Test pop*() operations
print(dq.pop()) # 6
print(dq.popleft()) # "c"
