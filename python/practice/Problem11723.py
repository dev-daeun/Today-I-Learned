
import sys


M = int(sys.stdin.readline())
S = 0b0


for i in range(0, M):
    line = sys.stdin.readline().split(' ')
    command = line[0]
    if command != 'all\n' and command != 'empty\n': 
        x = int(line[1].split('\n')[0]) - 1
    if command == 'empty\n':
        S = 0b0
    if command == 'all\n':
        S = (1 << 20) - 1
    if command == 'check':
        result = S & 1 << x
        if(result == 0):
            print(0)
        else:
            print(1)
    if command == 'toggle':
        result = S & 1 << x
        if(result == 0):
            S = S | 1 << x
        else:
            S = S & (1 << x) - 1
    if command == 'add':
        S = S | 1 << x
    if command == 'remove':
        S = S & (1 << x) - 1 
        
    

exit()