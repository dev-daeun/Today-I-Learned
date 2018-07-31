
# Learn x In y Minutes 
### [Learn X in Y minutes](https://learnxinyminutes.com/) 으로 파이썬의 기초를 공부합니다.
  

## 2. Variables and Collections  

  
* Python has a print function  
	```python
	print("I'm Python. Nice to meet you!")  # => I'm Python. Nice to meet you!  
	```  
* By default, the print function also prints out a newline at the end.  Use the optional argument 'end' to change the end string.  
	```python
	print("Hello, world", end="!") # => Hello, world!  
	```  
* Simple way to get input data from console. 
* Note : In earlier versions of Python, input method was named as raw_input().  
	```python
	input_str = input("Input some data : ") # Returns data as a string.  
	```
* There are no declarations, only assignments.  Convention is to use lower_case_with_underscores.  
	```python
	some_var = 5 # => 5  
	```  
* Accessing a previously unassigned variable is an exception.  
	```python
	some_unknown_var # => Raises a NameError  
	```  
* 'if' statement can be used as an expression, equivalent of 'a ? b : c' ternary operator.  
	```python
	"yahoo!" if 3 > 2 else  2 # => yahoo!  
	```  
## 2-1. List 
* List store sequences. 
	```python
	li = []  
   ```
* You can start with a prefilled list.  
	```python
	other_li = [4,5,6]  
	```  
* Add stuff to the end of a list with 'append'  
	```python
	li.append(1) # li is now [1]  
	li.append(2) # li is now [1,2]  
	```  
  
* Remove from the end with 'pop'  
	```python
	li.pop() # li is now [1]  
	li.append(3) # li is now [1,3]  
	li.append(4) # li is now [1,3,4]  
	li.append(5) # li is now [1,3,4,5]  
	```
* Access a list like you would any array.  
	```python
	li[0] # => 1  
	```
* Look at the last element  
	```python
	li[-1] # => 5  
	```  
* Looking out of bounds is an IndexError.  
	```python
	li[4] # Raises an IndexError.
	```  
* You can look at ranges with slice syntax.  The start index is included, the end index is not.  
(It's a closed/open range for mathy types.)  
	```python
	li[1:3] # => [3,4]  
	```  
* Omit the beginning and return the list.  
	```python
	li[2:] # => [4,5]  
	```  
* Omit the end and return the list.  
	```python
	li[:3] # => [1,3,4]  
	```  
* li[::x] returns a list that consists of elements whose the remainder of each index being divided by x is 0  
	```python
	li[::2] # => [1,4]  
	print(li[::3]) # => [1,5]
	```  
* Return a reversed copy of the list.  
	```python
	li[::-1] # => [5,4,3,1]  
	```  
* Use any combination of these to make advanced slices.    
  ```python
  li[start:end:step]
  li[0:3:1] # => [1,3,4]
  ```
* Make a layer deep copy using slices  
	```python
	li2 = li[:] # li2 is now [1,3,4,5] but (li2 is li) results in False.  
	```  
* Remove arbitrary elements from a list with "del"  
	```python
	del li[2]  # li is now [1,3,5]  
	```	  
* Remove first occurrence of a value  
	```python
	li.remove(3) # li is now [1,5]  
	li.remove(3) # Raises a valueError since value 3 in not in the list.  
	```  
* Insert element at a specific index  
	```python
	li.insert(1,3) # li is now [1,3,5] again.  
	```  
* Get the index of the first item found matching the argument  
	```python
	li.index(3)  # => 1  
	li.index(4)  # Raises a ValueError as 4 is not in the list  
	```
* You can add lists. Note: values for li and for other_li are not modified.  
	```python
	li + other_li  # => [1, 2, 3, 4, 5, 6]
	```
* Concatenate lists with "extend()"  
	```python
	li.extend(other_li)  # Now li is [1, 2, 3, 4, 5, 6]  
	```  
* Check for existence in a list with "in"  
	```python
	1 in li  # => True  
	```  
* Examine the length with "len()"  
	```python
	len(li)  # => 6  
	```  

## 2-2. Tuple  
  
* Tuples are like lists but are immutable.  
	```python
	tup = (1,2,3)  
	tup[0] # => 1  
	tup[2] = 4 # Raises a TypeError.  
	```  
* Note that a tuple whose length is 1 has to have a comma after its last element, but tuples of other lengths, even 0 do not.  
	```python
	type((1)) # <class 'int'>  
	type((1,)) # <class 'tuple'>  
	type(()) # <class 'tuple'>  
	```  
* You can do most of the operations on tuples too.  
	```python
	len(tup) # 3  
	tup + (4,5,6) # (1,2,3,4,5,6)  
	tup[:2] # (1,2)  
	2 in tup # True  
	```  
* You can unpack tuples into variables.  
	```python
	a,b,c = tup # a is now 1, b is now 2, c is now 3.  
	```  
* You can also do extended unpacking.  
	```python
	a,*b,c = (1,2,3,4) # a is now 1, b is now [2,3] c is now 4.  
	```  
* Tuples are created by default if you leave out parentheses  
	```python
	a,b,c = 4,5,6  
	```  
* Look how easy it is to swap two values.  
	```python
	a,b = b,a # a is now [2,3] and b is now 1.  
	```  
## 2-3. Dictionary  
* Dictionaries store mappings from keys to values  
	```python 
	empty_dict = {}  
	```
* Here is a prefilled dictionary  
	```python
	filled_dict = {  
	    "one": 1,  
	    "two": 2,  
	    "three": 3  
	}  
	```  
* Note keys for dictionaries have to be immutable types.  This is to ensure that the key can be converted to a constant hash value for quick look-ups. Immutables types include int, float, string, tuple.  
	```python
	invalid_dict = {  
	    [1,2,3]: "123"  
	} # Raises a TypeError: unhashable type: 'list'  
	  
	valid_dict = {  
	    (1,2,3): [1,2,3]  
	} # Values can be of any type, however.  
	```  
* Look up values with []  
	```python
	filled_dict["one"] # => 1  
	```  
* Get all keys as an iterable with 'keys()'.  
We need to wrap the call in list() to turn it into a list. We'll talk about those later.  
* Dictionary key ordering is not guaranteed. Your results might not match this exactly.  
	```python
	list(filled_dict.keys()) # ["three", "two" ,"one"]  
	```  
* Get all values as an iterable with "values()". Once again we need to wrap it in list() to get it out of the iterable. Note - Same as above regarding key ordering.  
	```python
	list(filled_dict.values())  # => [3, 2, 1]  
	```
* Check for existence of keys in a dictionary with "in".  
	```python
	"one" in filled_dict # => True  
	1 in filled_dict # => False  
	```  
* Looking up a non-existing is a KeyError.  
	```python
	filled_dict["four"] # KeyError  
	```  
* Use 'get()' method to avoid the KeyError.  
	```python
	filled_dict.get("one") # 1  
	filled_dict.get("two") # 2  
	filled_dict.get("three") # 3  
	filled_dict.get("four") # None  
	```  
* The 'get()' method supports a default argument when the value is missing.  
	```python
	filled_dict.get("one", 4) # => 1  
	filled_dict.get("four", 4) # => 4  
	```  
* 'setDefault()' inserts into a dictionary only if the given key isn't present.  
	```python	
	filled_dict.setDefault("five", 5) # filled_dict["five"] is set to 5.  
	filled_dict.setDefault("five", 6) # filled```python
	2 in filled_set   # => True  
	10 in filled_set  # => False
	```_dict["five"] is still 5.  
	```  
* Adding to a dictionary  
	```python
	filled_dict.update({"four": 4}) # => {"one": 1, "two": 2, "three": 3, "four": 4}  
	filled_dict["four"] = 4         # another way to add to dict  
	```  
* Remove keys from a dictionary with 'del'  
	```python
	del filled_dict["one"] # Removes the key "one" from filled_dict.  
	```
* From Python3.5 you can also use the additional unpacking options.  
	```python
	{'a': 1, **{'b': 2}} # => {'a': 1, 'b': 2}  
	{'a': 1, **{'a': 2}}  # => {'a': 2}  
	```  
## 2-4. Set
* Set stores... well, set.
	```python
	empty_set = set()  
	#Initialize a set with a bunch of values. Yeah, it looks a bit like a dict.  
	some_set = {1,1,2,2,3,4}  # some_set is now {1,2,3,4}  
	```  
* Similar to keys of a dictionary, elements of a set have to be immutable.  
	```python
	invalid_set = {[1], 1} # Raises a TypeError: unhashable type: 'list'  
	valid_set = {(1,), 1}  
	```  
* Add one more item to the set  
	```python
	filled_set = some_set  
	filled_set.add(5) # filled_set is now {1,2,3,4,5}  
	```  
* Do set intersection with **&**  
	```python
	other_set = {3,4,5,6}  
	filled_set & other_set  # => {3,4,5}  
	```  
* Do set union with **|**  
	```python
	filled_set | other_set # => {1,2,3,4,5,6}
	```
* Do set difference with **-**  
	```python
	{1,2,3,4} - {2,3,5}  # => {1,4}  
	```  
* Check if set on the left is a superset of set on the right.  
	```python
	{1,2} >= {1,2,3} # False  
	```  
* Check if set on the right is a superset of set on the left.  
	```python
	{1,2} <= {1,2,3} # True  
	```  
* Check for existence in a set with in  
	```python
	2 in filled_set   # => True  
	10 in filled_set  # => False
	```