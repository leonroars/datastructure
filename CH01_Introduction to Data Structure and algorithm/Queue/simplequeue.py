class UserQueue:
    def __init__(self):
        self.q = []
        self.rear = 0
        
    def is_empty(self):
        return len(self.q) == 0
    
    def enqueue(self, item):
        self.q.append(item)
    
    def dequeue(self):
        if not self.is_empty():
            self.rear -= 1
            return self.q.pop(0) #A list in Python automatically re-allocate items after dequeue() process.
        else:
            return None
    
    def size(self):
        return len(self.q)
    
    def peek(self):
        return self.q[0]
    
    def __str__(self):
        return str(self.q)