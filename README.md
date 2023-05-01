# be-java-cafe
마스터즈 2023 스프링 카페

## DB ERD
***
![image](https://user-images.githubusercontent.com/56629324/235486079-e1da0aac-8afe-467a-92e5-f36b955376e7.png)


## URL
***
[codesquad_cafe](http://54.180.8.60:8080/posts)

## 기능 목록
***
|__HTTP Method__| __URL__                 | __기능__                 |
|---------------|-------------------------|------------------------|
|GET|	/	|홈 화면 출력|
|GET|	/members/registration	|회원 가입 화면 출력|
|POST|	/members	|회원 가입|
|GET|	/members	|회원 목록 조회 화면 출력|
|GET|	/members/{email}	|유저 프로필 화면 조회|
|POST	|/posts/write	|게시글 작성|
|GET|	/posts/write	|게시글 작성 화면 조회|
|GET|	/posts/{id}	|게시글 상세 조회(+ 댓글도 같이 조회)|
|GET|	/members/{email}/profile	|회원 정보 수정 화면 출력|
|PUT|	/members/{email}/profile	|회원 정보 수정|
|GET|	/members/login	|로그인 화면 출력|
|POST|	/members/login	|로그인|
|POST|	/members/logout|	로그아웃|
|GET|	/posts/{id}/edit-form	|게시글 수정 화면 출력|
|PUT|	/posts/{id}	|게시글 수정|
|DELETE|	/posts/{id}|	게시글 삭제|
|POST	|/posts/{postId}/comments	|댓글 작성|
|PUT|	/posts/{postId}/comments	|댓글 수정|
|DELETE|	/posts/{postId}/comments/{commentId}|	댓글 삭제|
