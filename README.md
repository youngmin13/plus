# voyage
#### 1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
* PathVariable로 title을 받아왔다.
  - 처음에는 id를 받아와서 비교하려 했으나, 유저 입장에서 본인 게시글의 id를 기억하지 않을 것이라 보았고, title은 메인 페이지에서 바로 보이기 때문 
* RequestBody로 필요한 RequestDto들을 받아왔다.
  - 우선 처음으로 PathVariable로 받아온 title이 존재하는지
  - 존재한다면 RequestBody로 보내온 password가 해당 title을 가진 database의 Entity와 일치하는지
  - 전부 일치한다면 return 해주고 아니면 throw exception
#### 2. 어떤 상황에 어떤 방식의 request를 써야하나요?
* 수정이나 삭제를 할 경우 사실 지금과 같은 방식처럼 RequestBody로 받아오는 것보다는 form을 통해서 password와 title을 받는 것이 더 좋은 방식으로 보인다.

#### 3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
#### 4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
* Controller, Repository, Service는 전부 적절하게 분리해서 적용하였다고 생각한다.
#### 5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!
* 발제한 과제 자료와 비교해보았을 경우 
-> 큰 차이는 없는 것으로 보임
* 가이드라인과 비교해보았을 경우 
-> 기능 별로 어떤 기능인지 설명이 필요할 듯

##### 작성한 api와 use case도 올려두었음 (user case는 src/main/resources/images)