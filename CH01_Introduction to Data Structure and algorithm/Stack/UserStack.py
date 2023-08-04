class UserStack:
    
    def __init__(self):
        self.stack = []
        self.top = 0
        
    def is_empty(self):
        return len(self.stack) == 0
    
    def push(self, item):
        self.stack.append(self.top)
        self.top += 1
    
    def pop(self):
        if self.stack != self.is_empty():
            self.top -= 1
            return self.stack.pop()
        else:
            return None
    
    def size(self):
        return len(self.stack)
    
    def peek(self):
        return self.stack[self.top]
    
    def __str__(self):
        return str(self.stack)