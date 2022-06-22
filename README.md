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

- IntelliJ 실행 후 Terminal 창에 아래 명령어 입력
```shell
$ ./gradlew clean bootRun
```




