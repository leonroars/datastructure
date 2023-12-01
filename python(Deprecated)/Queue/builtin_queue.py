from queue import Queue

q = Queue()

q.put(10)
q.put("가")
q.put("나")
q.put(49)

while not q.empty():
    item = q.get()
    print(item)
    
print(q.empty())