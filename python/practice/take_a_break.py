import time
import webbrowser

totalBreaks = 3
breakCount = 0
print("Take A Break program started on "+time.ctime())

for num in range(breakCount, totalBreaks):
    time.sleep(2*60*60)
    webbrowser.open("https://www.youtube.com/watch?v=jgMfYgsRvQ8")