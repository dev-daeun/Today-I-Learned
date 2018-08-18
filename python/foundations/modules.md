# Learn x In y Minutes
### [Learn X in Y minutes](https://learnxinyminutes.com/) 으로 파이썬의 기초를 공부합니다.

## 5. modules

* You can import modules
  ```python
  import math
  print(math.sqrt(16))  # => 4.0
  ```
* You can get specific functions from a module
  ```python
  from math import ceil, floor
  print(ceil(3.7))   # => 4.0
  print(floor(3.7))  # => 3.0
  ```

* You can import all functions from a module. Warning: this is not recommended
  ```python
  from math import *
  ```

* You can shorten module names.
  ```python
  import math as m
  math.sqrt(16) == m.sqrt(16)  # => True
  ```

* Python modules are just ordinary Python files. You can write your own, and import them. 
  <br>The name of the module is the same as the name of the file.
  <br>You can find out which functions and attributes are defined in a module.
  ```python
  import math
  dir(math)
  ```

* If you have a Python script named math.py in the same folder as your current script, the file math.py will be loaded instead of the built-in Python module.
 This happens because the local folder has priority over Python's built-in libraries.
