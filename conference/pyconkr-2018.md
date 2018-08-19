
## pyconkr 2018 후기

  

pycon에서 들은 세션 중에 여러 연사님들이 언급하신 'zen of python'(파이썬의 철학)은 아래와 같습니다.

```python
Beautiful is better than ugly.
Explicit is better than implicit.
Simple is better than complex.
Complex is better than complicated.
Flat is better than nested.
Sparse is better than dense.
Readability counts.
Special cases aren't special enough to break the rules.
Although practicality beats purity.
Errors should never pass silently.
Unless explicitly silenced.
In the face of ambiguity, refuse the temptation to guess.
There should be one--  and preferably only one --obvious way to do it.
Although that way may not be obvious at first unless you're Dutch.
Now is better than never.
Althoug never is often better than *right* now.
If the implementation is hard to explain, it's a bad idea.
If the implementation is easy to explain, it may be a good idea.
Namespaces are one honking great idea -- let's do more of those!
```

정말 많은 사람들이 파이콘에 찾아왔고 가족단위로 참석하는 사람들도 꽤 있었습니다. 8월 18일 토요일 [Women Who Code Seoul](https://www.facebook.com/wwcodeseoul/)의 이수진 연사님이 키노트에서 언급하신 것처럼 파이썬 커뮤니티는 다양성과 포용을 지향합니다. 귀도 반 로섬을 포함한 파이썬 코어개발자들은 '초보자가 아주 쉽고 기본적인 것을 묻더라도 적극적으로 도움을 줘라', '파이썬 오픈소스 커뮤니티는 여성개발자로부터 날아온 PR을 우선적으로 확인한다'와 같이 파이썬 이용자들에게 다양성과 포용을 강조해왔습니다. 파이썬 커뮤니티의 이러한 모토는 파이썬을 공부하려는 의지를 자극합니다.<br><br>

가장 기억에 남는 세션은 두 가지가 있습니다. 두 세션 모두 18일에 진행된 세션이었는데, 첫번째는 뱅크샐러드의 황성현 님이 강연하신 ['학습하는 조직'](https://www.pycon.kr/2018/program/82)입니다. 연사님이 언급하신 인상적인 구절들이 몇 개 있습니다.

*  성능을 제고하는 것보다 우리 제품의 도메인과 사용자를 이해하는 게 더 가치있는 일이다.
* 일에 직접 뛰어들기 전 까지는 일의 핵심을 알 수 없다. (학습하는 조직이 필요한 이유)
* 학습하는 조직은 팀원이 시행착오를 통한 실험을 할 수 있도록 하는 조직이다.
* 실행만 하는 조직은 팀이 일을 제대로 했는지만 찾는다.
* 학습하는 조직은 실패를 통해 배운것이 있는지 찾는다.



두 번째는 피플펀드컴퍼니의 한섬기 님이 강연하신 ['effective tips for django ORM in Practice'](https://www.pycon.kr/2018/program/53)입니다. django의 ORM을 잘 사용하는 팁을 알려주셨는데, 연사님이 겪었던 고민들이 제가 django를 알지 못하더라도 백엔드 개발을 하면서 느꼈던 것들과 비슷해서 공감이 되고 그에 대한 해결법도 제시하셔서 도움이 되었습니다. 제가 정리한 effective tips는 아래와 같습니다. 나중에 django를 다루게 되면 유용하게 써먹을 듯 합니다. (일부는 놓치거나 내용에 오류가 있을 수 있습니다.)

**1. model & QuerySet**<br>
&nbsp;&nbsp; 멱등성 보장하는 함수 : `update_or_create`<br>
&nbsp;&nbsp; 역정규화 : 예) `count(*)`를 매번 불러오는 대신에 count 컬럼을 아예 만듦.<br>
&nbsp;&nbsp; 모델.save() 호출 시 `update_fields`로 업뎃컬럼 지정 가능.<br>
**2. enumeration**<br>
&nbsp;&nbsp; `django-model-utils`<br>
**3. 모델 추상화**<br>
&nbsp;&nbsp; created_at, updated_at 이 모든 모델마다 들어가므로 Timestamp 모델로 추상화를 한번 거친다.<br>
**4. Manager 활용**<br>
&nbsp;&nbsp; `django.db.models.manager.Manager`<br>
**5. Group by**<br>
&nbsp;&nbsp; `Model.annotate.value()`로 group by의 기준이 되는 조건 명시<br>
**6. Transaction**<br>
&nbsp;&nbsp; `QuerySet.select_for_update`<br>
&nbsp;&nbsp; `@transaction.atomic` 어노테이션으로 트랜잭션과 lock을 걸 수 있다.<br>
&nbsp;&nbsp; 주의할 점 : 트랜잭션 단위 밖에 있는 쿼리를 실행하고 트랜잭션을 실행하려면 안되는 경우가 있음.<br>
**7. 퍼포먼스**<br>
&nbsp;&nbsp; 조인할 때 성능이슈 있음.  `Model.objects.select_related` 메소드 활용(1:1 조인 가능. ) <br>
&nbsp;&nbsp;`prefetch_related`는 1:N, N:N 조인할 때 활용.<br>
**8. 디버깅**<br>
&nbsp;&nbsp; 디버깅은 오류가 없지만 느릴 때 한다. 예) 똑같은 쿼리가 여러번 던져질 때. => DB튜닝 또는 리팩토링 필요..<br>
&nbsp;&nbsp; 트랜잭션에서 데이터 흐름을 보는 방법은? <br> 
&nbsp;&nbsp; => isolation level에서 'READ-UNCOMMITED'로 설정하면 커밋되지 않은 트랜잭션들도 볼 수 있다.<br>
**9. Raw SQL**<br>
&nbsp;&nbsp; ORM으로 표현하기 어려운 쿼리가 있겠지만 왠만하면 쓰지 않는게 좋다. <br>
&nbsp;&nbsp; ORM은 injection 공격을 강력하게 방어하기 때문에. 유저데이터 입력받는 영역에서는 쓰지말자.<br>

그 외에도 19일에 진행되었던  ['작은 오픈소스 3년 운영기'](https://www.pycon.kr/2018/program/23) 강연에서는 오픈소스 컨트리뷰션을 하기 위해서는 테스트코드에 먼저 접근하여 오픈소스가 무슨 목적으로 개발되었는지 파악하라, 오픈소스 메인테이너들이 소스를 통해 추구하는 바가 무엇인지 알고 PR을 준비해야 한다는 조언을 얻을 수 있었습니다.  ['Pythonic code가 과연 효율적일까?'
](https://www.pycon.kr/2018/program/46) 세션에서는 cProfile로 파이썬의 문법과 자료구조 성능을 비교하여 더 나은 성능의 문법을 체득하는 과정을 배울 수 있었습니다. 언어에서 동일한 기능을 하는 문법 간의 성능을 비교하는 것을 '프로파일링'이라고 하는 것도 처음 알았습니다. 'JS와 Python 프로파일링 해보기'를 TIL wiki에 추가해야겠습니다. 
