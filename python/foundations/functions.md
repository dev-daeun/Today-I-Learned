
# Learn x In y Minutes
### [Learn X in Y minutes](https://learnxinyminutes.com/) 으로 파이썬의 기초를 공부합니다.


## 4. Functions

* Use 'def' to create new functions.
	```python
	def add(x,y):
	    print("x is {} and y is {}".format(x,y))
	    return x + y
	```
* Calling functions with parameters
	```python
	add(5,6) # => prints out "x is 5 and y is 6" and returns 11
	```
* Another way to call functions is with keyword arguments.
	```python
	add(y=6, x=5) # Keyword args can arrvie in any order.
	```
* You can define functions that take a variable number of positional args.
	```python
	def varargs(*args):
	    return args

	varargs(1,2,3) # => returns (1,2,3)
	```
* You can define functions that take a variable number of keyword args, as well.
	```python
	def keyword_args(**kwargs):
	    return kwargs

	keyword_args(big="foot", loch="ness") # returns {"big": "foot", "loch": "ness"}
	```
* You can do both at once, if you like.
	```python
	def all_the_args(*args, **kwargs):
	    print(args)
	    print(kwargs)

	all_the_args(1, 2, a=3, b=4)
	#(1, 2)
	#{"a": 3, "b": 4}
	```

* When calling functions, you can do the opposite of args/kwargs.  Use * to expand tuples and use ** to expand kwargs.
	```python
	args = (1,2,3,4)
	kwargs = {"a": 3, "b": 4}
	all_the_args(*args) # equivalent to all_the_args(1,2,3,4)
	all_the_args(**kwargs) # equivalent to all_the_args(a=3, b=4)
	all_the_args(*args, **kwargs) # equivalent to all_the_args(1,2,3,4,a=3,b=4)
	```
* Returning multiple values.(with tuple assignments.)
	```python
	def swap(x,y):
	    return y,x # Returns multiple values as a tuple without the parenthises.
	```
* Note: parenthesis has been excluded but can be included.
	```python
	x = 1
	y = 2
	x,y = swap(y,x) # Again parenthesis has been excluded but can be included.
	```
* Function scope.
	```python
	x = 5
	def set_x(num):
	    # Local var x is not the same as global var x.
	    x = num
	    print(x)

	def set_global_x(num):
	    global x
	    print(x) # 5
	    x = num # global x is now 6
	    print(x)

	set_x(43)  # prints 43
	set_global_x(6)  # prints 5 6
	```
* Python has first class functions.
	```python
	def create_adder(x):
	    def adder(y):
	        return x+y
	    return adder

	add_10 = create_adder(10)
	add_10(3) # 13
	```
* There are also anonymous functions
	```python
	(lambda x: x > 2)(3) # True
	(lambda x,y: x**2 + y**2)(2,3) # 13
	```
* There are built-in higher order functions
	1. 'map' function matches each number from second parameter to the first parameter.
	2. 'list' functions returns the result as a list.
	```python
	list(map(add_10, [1,2,3])) # => [11, 12, 13]
	```

	3. 'map' function matches each element in 2nd parameter to each element in 3rd parameter.
	4. 'max' function returns the  biggest number in parameters.
	5.  'list' functon returns the result as a list.

	```python
	list(map(max, [1,2,3], [4,2,1])) # => [4,2,3]
	```

	6. filter(function, list) returns result that consists of True returned by function from 1st parameter.
	7.   'list' functon returns the result as a list.
	```python
	list(filter(lambda x: x>5, [3,4,5,6,7])) # => [6,7]
	```

* We can use list comprehensions for nice maps and filters.
* List comprehensions stores the output as a list which can itself be a nested list.
	```python
	[add_10(i) for i in [1,2,3]] # [11,12,13]
	[x for x in [3,4,5,6,7] if x > 5] # [6,7]
	```
* You can construct set and dictionary comprehensions as well.
	```python
	{x for x in 'abcddeef' if x not in 'abc'} # {'d','e','f'}
	{x: x**2 for x in range(5)} # {0: 0, 1: 1, 2: 4, 3: 9, 4: 16}
	```