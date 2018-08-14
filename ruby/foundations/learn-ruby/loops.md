
# Learn Ruby
* [codecademy](https://www.codecademy.com/learn/learn-ruby)에서 ruby의 기초를 배웁니다.


## Loops

### for
* `for`문에서는 범위를 `.`으로 나타냅니다.
* for문에서 `..`는 끝 값을 포함합니다.
* for문에서 `...`는 끝 값을 포함하지 않습니다. 
```ruby
for i in 1..10 
   puts i
end
# 1 2 3 .. 10 

for i in 1...10
   puts i
end
# 1 2 3 ... 9 
```

### loop
* `loop`키워드로 `loop { 코드 }`와 같이 블록 내부의 코드를 반복할 수 있습니다. 
* `loop`는 `do`와 같이 쓰일 수 있습니다.
* `break`문 뒤에 `if`문이 올 수 있습니다.
```ruby
i = 20
loop do 
  i -= 1
  puts "#{i}"
  break if i <= 0 
end

# 20 19 18 ... 1
```

### next
* `next`는 `continue`와 같습니다.
* `next`뒤에도 조건문이 올 수 있습니다. 
```ruby
i = 20
loop do 
  i -= 1
  next if i % 2 == 1
  print "#{i}"
  break if i <= 0 
end

# 20 18 16 ... 2
```

### each
* 배열을 순회합니다. (JS의 foreach같은)
* `arr.each { |item| ... }` 또는 `arr.each do |item| ... end` 와 같이 쓰입니다.
```ruby
numbers = [1, 2, 3, 4, 5]

# one way to loop
numbers.each { |item| puts item }

# another way to loop
numbers.each do |item|
  puts item
end
```

### until
* `while`문은 조건문이 `true`를 만족하는동안 순회한다면, `until`은 조건문이 `false`를 만족하는동안 순회합니다.
```ruby
i = 1
until i == 51 do
  print i
  i += 1
end
# 1 2 3 ... 50
```
