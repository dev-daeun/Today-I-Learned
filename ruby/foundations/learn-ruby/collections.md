
# Learn Ruby
* [codecademy](https://www.codecademy.com/learn/learn-ruby)에서 ruby의 기초를 배웁니다.

## Collections

### Hashes
* `hash`는 JS의 json객체, 파이썬의 딕셔너리처럼 키-밸류 형태의 구조를 갖습니다.
* `{}`나 `Hash.new`로 empty hash를 만들 수 있습니다. 
```ruby
my_hash = { 
  "name" => "Eric",
  "age" => 26,
  "hungry?" => true
}

my_hash2 = Hash.new
my_hash2["name"] = "Judith"
my_hash2["age"] = 24
my_hash2["hungry?"] = false

puts my_hash["name"] # Eric
puts my_hash["age"] # 26
puts my_hash["hungry?"] # true

puts my_hash2["name"] # Judith
puts my_hash2["age"] # 24
puts my_hash2["hungry?"] # false
```

* `hash`도 배열처럼 `each`메소드를 통해 순회할 수 있습니다. 해시는 키,값을 갖기 때문에 iterator 2개를 가지고 순회합니다.
```ruby

family = { "Homer" => "dad",
  "Marge" => "mom",
  "Lisa" => "sister",
  "Maggie" => "sister",
  "Abe" => "grandpa",
  "Santa's Little Helper" => "dog"
}

family.each { |x, y| puts "#{x}: #{y}" }
=begin
Homer: dad
Marge: mom
Lisa: sister
Maggie: sister
Abe: grandpa
Santa's Little Helper: dog
=end
```
