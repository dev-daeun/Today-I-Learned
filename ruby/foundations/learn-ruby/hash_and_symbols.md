## Hash and Symbols 

* 루비에서 `symbol`은 스트링과 다릅니다.(
[ruby의 심볼](http://guruble.com/%EB%A3%A8%EB%B9%84-%EC%98%A8-%EB%A0%88%EC%9D%BC%EC%8A%A4ruby-on-rails-%EB%A9%B4%EC%A0%91%EC%97%90%EB%8A%94-%EC%96%B4%EB%96%A4-%EC%A7%88%EB%AC%B8%EB%93%A4%EC%9D%B4-%EB%82%98%EC%98%AC%EA%B9%8C/), [How Are Symbols And Strings Different?](http://www.rubyguides.com/2018/02/ruby-symbols/) 참고)

* 위 레퍼런스들을 참고했을 때 해시에서 심볼을 사용하면 좋은 점은 스트링으로 key를 삽입/삭제할 경우<br>
  인터프리트되기 전까지는 스트링의 위치를 알수 없으므로 탐색 오버헤드가 발생합니다.<br> 
  그리고 같은 값을 갖는 두 스트링이더라도 서로 다른 객체로 취급되므로 매번 메모리에 값이 쌓이게 됩니다. <br>
  그러나 심볼은 힙 영역의 심볼딕셔너리에서 관리되고 한번 생성하고 나면 불변의 id를 갖게 되므로<br>
  탐색시간도 스트링에 비해 빠르고 메모리 효율성도 좋은 것으로 볼 수 있습니다.
* `symbol`을 활용하여 콘솔입출력으로 해시에 CRUD를 적용하는 코드를 짰습니다.


```ruby
movies = {
  StarWars: 4.8, 
  Divergent: 4.7
  }

puts "What would you like to do? "

choice = gets.chomp

case choice   # 루비에서 `case`는 다른 언어의 `switch`와 같은 역할을 합니다.
when "add"    # `case` 구문에서 `when`으로 조건을 구분합니다.
  puts "What movie would you like to add? "
  title = gets.chomp
  puts movies[:title]
  if movies[title.to_sym] != nil    # 스트링을 `symbol`타입으로 캐스팅 하기 위해서는 `to_sym`을 사용합니다.
    puts "already exists"
  else
    puts "What rating does the movie have? "
    rating = gets.chomp
    movies[title.to_sym] = rating.to_i  # 숫자로 캐스팅하기 위해서는 `to_i`메소드를 활용합니다.
  end
when "update"
  puts "input title:"
  title = gets.chomp
  if movies[title.to_sym] == nil
    	puts "not exists"
  else
    movies[title.to_sym] = gets.chomp.to_i
  end
when "display"
  movies.each do |movie, rating|
    puts "#{movie}: #{rating}"
  end
when "delete"
  title = gets.chomp
  if movies[title.to_sym] == nil
    puts "not exists"
  else
    movies.delete(title)
  end
else
  puts "Error!"
end
```
