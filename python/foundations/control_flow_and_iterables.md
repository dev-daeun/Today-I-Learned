# Learn x In y Minutes
### [Learn X in Y minutes](https://learnxinyminutes.com/) 으로 파이썬의 기초를 공부합니다.

## 3. Control Flow and Iterables


* Let's just make a variable
	```python
	some_var = 5
	```
* Here is an if statement. Indentation is significant in Python!
* Convention is to use four spaces, not tabs. This prints "some_var is smaller than 10"
	```python
	if some_var > 10:
	    print("some_var is bigger than 10")
	elif some_var < 10:
	    print("some_var is smaller than 10")
	else:
	    print("some_var is indeed 10")
	```
* For loops iterate over lists
prints:
    dog is a mammal
    cat is a mammal
    mouse is a mammal
	```python
	for animal in ["dog", "cat", "mouse"]:
	    # You can use format() to interpolate formatted strings
	    print("{} is an mammal".format(animal))
	```
* "range(number)" returns an iterable of numbers from zero to the given number
prints:
    0
    1
    2
    3
	```python
	for i in range(4):
	    print(i)
	```

* "range(lower, upper)" returns an iterable of numbers from the lower number to the upper number
prints:
    4
    5
    6
    7

	```python
	for i in range(4, 8):
	    print(i)
	```

* "range(lower, upper, step)" returns an iterable of numbers from the lower number to the upper number, while incrementing by step. If step is not indicated, the default value is 1.
prints:
    4
    6
	```python
	for i in range(4,8,2):
	    print(i)
	```
* 'While' loops go until a condition is no longer met.
prints:
    0
    1
    2
    3
	```python
	x = 0
	while x < 4:
	    print(x)
	    x += 1  # Shorthand for x = x + 1
	```

* Handle exceptions with a 'try/except' block.
	```python
	try:
	    # Use "raise" to raise an error.
	    raise IndexError("This is an index error")
	except IndexError as e:
	    pass # Pass is just a no-op. Usually you would do recovery here.
	except(TypeError, NameError):
	    pass # Multiple exceptions can be handled together, if required.
	else:
	    print("all good!") # Runs only if the code in try raises no exceptions.
	finally:
	    print("We can clean up resources here")
	```

* Python offers a fundamental abstraction called the Iterable. An iterable is an object that can be treated as a sequence. The object returned by the range function, is an iterable.
	```python
	filled_dic = {"one": 1, "two": 2, "three": 3}
	out_iter = filled_dic.keys()
	print(out_iter) # This is an object that implements our Iterable interface.
	```
* We can loop over it.
	```python
	for i in our_iter:
	    print(i)  # prints one, two, three
	```
* However, we cannot address elements by index.
	```python
	out_iter[1] # Raises a TypeError.
	```
* An iterable is an object that can remember the state as we traverse through it. We get the next object with 'next()'.
	```python
	next(out_iter) # "one"
	```

* It maintains state as we iterate.
	```python
	next(out_iter) # "two"
	next(out_iter) # "three"
	```
* After the iterator has returned all of its data, it raises a StopIteration exception.
	```python
	next(out_iter) # Raises StopIteration
	```
* You can grab all the elements of an iterator by calling list() on it.
	```python
	list(filled_dic.keys()) # Returns ["one", "two", "three"]
	```