## 실행방법

### docker - mysql 설치 및 실행

- m1 mac 기준

``` shell
> docker pull mysql

> docker run --platform linux/amd64 -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=triple -e MYSQL_USER=user -e MYSQL_PASSWORD=password -d mysql --lower_case_table_names=1

> docker exec -it mysql bash

> mysql -u root -p

> Enter password : root

mysql> use triple;
```

### Run Server

- IntelliJ 실행 후 Terminal 창에 아래 명령어 입력

```shell
$ ./gradlew clean bootRun
```
### Event 요청 API
```
POST http://localhost:8080/event
```
- Request
```json
{
  "type": "REVIEW",
  "action": "ADD",
  "reviewId": "d54f1057-945c-43d0-af7a-85fb565a0539",
  "content": "타이완",
  "userId": "46026146-9cc9-44f0-b62c-e3e833fdc7d4",
  "placeId": "3fec5466-630f-4313-bbea-0fff58819836",
  "attachedPhotoIds": [
    "11ecf28c-462c-159d-bdc8-03fd26019cff",
    "f9943a8f-fe37-4d7d-9511-e754b0f9f335"
  ]
}
```

- Response
```json
{
  "reviewId": "87b227ea-e5b3-4b94-bf54-f6859c6b1bf2",
  "content": "타이완",
  "firstReview": true,
  "user": {
    "userId": "46026146-9cc9-44f0-b62c-e3e833fdc7d4",
    "point": 24
  }
}
```
### 사용자 포인트 조회 API
``` 
GET http://localhost:8080/event/{UUID}

//ex) UUID : 46026146-9cc9-44f0-b62c-e3e833fdc7d4
```
- Response
```json
{
	"userId": "46026146-9cc9-44f0-b62c-e3e833fdc7d4",
	"point": 0
}
```


## 요구 사항 및 문제해결 사항

### 공통

- [X] DTO 적용
- [X] : reviewId 는 프론트에서 직접 만들어 보내는걸로 생각하면 되겠다.
- [X] : 장소, 유저 등 미리 데이터 생성
- [X] : GlobalErrorHandler 적용
- [X] : place entity 에도 firstReview status 있으면, 리뷰가 있었다가 삭제된 후에도 확인할 수 있다.
    - 해당 place 에 reviewId 가 존재하지 않기에, firstReview 컬럼 추가되면 좋다.
    - 리뷰 자체에, 이 리뷰가 해당 플레이스의 first_review 라고 적는건 어떰? 이미 돼있나?
- [X] : first_review 였던 review 가 삭제되면 보너스 점수를 회수해야하고, 다른 누군가에게 보너스 점수를 줄 수 있는 로직이 있어야한다.
    - review 에 first_review 불린 값 넣고, 리뷰 삭제 로직시, 해당 값이 true 이면 보너스 값 회수한다.
    - 다시 해당 place 에 리뷰를 남길 땐, Review 테이블에서 해당 place 를 검색하여(+ review.status.eq(NORMAL)) 첫 리뷰인지 확인할 수 있다.

### 리뷰 작성 (ADD)

- [X] : 한 장소에 대해 하나의 리뷰만 작성할 수 있다.
- [X] 단순 저장 (content, entity)
- [X] 사진 저장

### 리뷰 수정 (MOD)

- [X] : 사진이 존재하는 리뷰 수정시 사진을 삭제 -> 후에 한번 더 수정하여 사진을 다시 추가.

### 리뷰 삭제 (DELETE)

- [X] : 리뷰 삭제시 사진도 같이 삭제되도록 status 처리

### 예외

- [X] : 이미 작성한 리뷰
- [X] : (수정 시) 이미 삭제된 리뷰
- [X] : Content Empty 면 예외 발생 


### 요구사항 정리 메모
![image](https://user-images.githubusercontent.com/92839864/176185805-c8a29ea3-3f2a-4924-83ff-635ffbf170a1.png)
![image](https://user-images.githubusercontent.com/92839864/176185897-ba0f676f-7f1f-4f45-8138-dfb4fe31adb7.png)


