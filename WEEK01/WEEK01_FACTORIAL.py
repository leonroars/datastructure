
"1. Recursive Solution"
def factorial(n):
    if n == 1:
        return 1 #종료조건 : 순환이 멈추는 곳 명시
    else:
        return n * factorial(n-1)
factorial(3)

"2. Iterative Solution"
def factorial_2(n):
    result = 1
    for k in range(n, 0, -1):
        result = result * k
    return result