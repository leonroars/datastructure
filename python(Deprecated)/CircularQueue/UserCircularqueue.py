class UserCircularqueue:
    def __init__(self):
        self.front = 0
        self.rear = 0
        self.scq = []
        self.size = 0
    
    def enqueue(self, item):
        self.scq[self.rear] = item
        self.rear = ((self.rear + 1) + len(self.scq)) % len(self.scq)
        self.size += 1
    
    def dequeue(self):
        poppedItem = self.scq[self.front]
        self.scq[self.front] = None # To avoid loitering
        self.front  = ((self.front + 1) + len(self.scq)) % len(self.scq)
        self.size -= 1
        return poppedItem
    
    def size(self):
        return self.size
    
    def peek(self):
        return self.scq[0]
    
    def is_empty(self):
        return self.size() == 0
    
    def display(self):
        print(self.scq)
    
    def __str__(self):
        return str(self.scq)