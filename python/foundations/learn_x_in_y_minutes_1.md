# Learn x In y Minutes
### [Learn X in Y minutes](https://learnxinyminutes.com/) 으로 파이썬의 기초를 공부합니다.

* Single line comments start with a number symbol(#).
  ```python
   multiline_string = """Multiline strings can be written
                      using three "s, and are often used
                      as documentation."""
   # this is single-line commment
  ```

## 1. primitive datatypes and operators.
* Math is what I expected
	``` python
	1 + 1 # => 2
	8 - 1 # => 7
	10 * 2 # => 20
	7 % 3 # => 1
	```
*  Result of division is always a float
	```python
	35 / 5 # => 7.0
	10.0 / 3 # => 3.33333333333335
	```
* Result of integer division truncated down both for positive and negative.
	```python
	5 // 3 # => 1
	5.0 // 3.0 # => 1.0 (works on float too)
	-5 // 3 # => -2
	-5.0 // 3.0 # => -2.0
	```
* Exponentiation(x ** y, x to the yth power)
	```python
	2**3  # => 8
	```
* Boolean values are primitives(Note : the capitalization)
	```python
	True
	False
	```
*  Negate with not
	```python
	not True # => False
	not False # => True
	```
* Boolean operators  - Note 'and' and 'or' are case-sensitive(대소문자 구분)
	```python
	True and False # => False
	False or True # => True
	```
* Using bool operators with int
* False is 0, True is 1
* Don't mix up with bool(int) and bitwise and/or(&, |)
	```python
	0 and 2 # => 0
	-5 or 0 # => -5
	0 == False # => True
	2 == True # => False
	1 == True # => True
	-5 != False != True # => True
	 ```
* Comparisons
	```python
	2 <= 2 # => True
	2 >= 2 # => True
	```
* Comparisons can be chained!
	```python
	1 < 2 < 3 # => True
	2 < 3 < 2 # => False
	```
* (is vs ==)
* 'is' checks if two variables refer to same object, but
*  '==' checks if the objects pointed to have same values/
	```python
	a = [1,2,3,4] # point at a new list.
	b = a # point b at what a is pointing to.
	b is a # => True, a and b refer to the same object.
	b == a # => True, a's and b's objects are equal.
	b = [1,2,3,4] # point b at a new list.
	b is a # => False, a and b do not refer to the same object.
	b == a # => True, a's and b's objects are equal.
	```
* Strings are created with " or '
	```python
	"This is a string."
	'This is also a string.'
  ```
* Strings can be added too. But try not to do this.
	```python
	"Hello" + " World."
	```
* String literals(not variables) can be concatenated without using '+'
	```python
	"Hello" " World." # => Hello World.
	```
* A string can be treated like a list of characters.
	```python
	"This is a string"[0] # => 'T'
* You can find the length of string
	```python
	len("This is a string") # => 16
  ```
*  '.format' can be used to format strings, like this:
	```python
	"{} can be {}".format("Strings", "interpolated")
	# => "Strings can be interpolated"
	```
* You can repeat the formatting args to save some typing.
	```python
	"{0} be nimble, {0} be quick, {0} jump over the {1}".format("Jack", "candie stick")
	# => "Jack be nimble, Jack be quick, jump over the candie stick"
	```
* You can use keywords if you don't want to count.
	```python
	"{name} wants to eat {food}".format(name="Bob", food="lasagna")
	# => "Bob wants to eat lasagna"
	```
* If your Pyhthon3 code also needs to run on Python2.5 and below,  you can also still use the old style of formatting:
	```python
	"%s can be %s the %s way" % ("Strings", "interpolated", "old")
	# => "String can be interpolated old way"
	```
* None is an object.  Don't youse '==' symbol to compare objects to None. Use 'is' instead. This checks for equality of object identity.
	```python
	"etc" is None # => False
	None is None # => True
	```
* None, 0, and empty strings/lists/dicts/tuples all evaluates to False.  All other values are True.
	```python
	bool(0) # => False
	bool([]) # => False
	bool({}) # => False
	bool(()) # => False
	```