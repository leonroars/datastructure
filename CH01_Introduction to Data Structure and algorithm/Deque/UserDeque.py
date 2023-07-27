class UserDeque:
    def __init__(self, capacity):
        self.left = 0
        self.right = 0
        self.ud = []
        self.capacity = capacity
    
    '''
    An empty-status can be defined in two way depends on how full-status is set to be.
    
    a) is_full() => one-less than capacity
        In this case, empty-status can be defined as the status that front == rear.
    b) is_full() => The number of elements == capacity
        In this case, empty-status should be defined base on the number of elements(size()).
        If not, full-status and empty-status cannot be discriminated.
    '''
    def is_empty(self):
        return self.left == self.right
    
    # I defined functions' name after built-in deque module.
    
    def is_full(self):
        return self.size() == self.capacity - 1
    
    def size(self):
        return ((self.right - self.left) + self.capacity) % self.capacity
    
    # append(self, item): Pushes item into rear position. Then update rear pointer.
    def append(self, item):
        self.ud[self.right] = item
        self.right = (self.right + 1) % self.capacity
    
    # appendFront(self, item): Moves front pointer in counter-clockwise first. Then pushes item into front position.
    def appendLeft(self, item):
        # Add capacity to prevent front pointer having negative integer.
        self.left = ((self.left - 1) + self.capacity) % self.capacity
        self.ud[self.left] = item
    
    def pop(self):
        self.right = ((self.right - 1) + self.capacity) % self.capacity
        popped = self.ud[self.right]
        self.ud[self.right] = None # Avoid loitering
        
        return popped

    def popLeft(self):
        popped = self.ud[self.left]
        self.ud[self.left] = None
        self.left = (self.left + 1) % self.capacity
        
        return popped

    def peek(self):
        return self.ud[((self.right - 1) + self.capacity) % self.capacity]
    
    def peekLeft(self):
        return self.ud[(self.left)]
    
    def __str__(self):
        return str(self.ud)