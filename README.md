#1. 기능 정의
- 전체 게시글 목록 조회
제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
작성 날짜 기준 내림차순으로 정렬하기
- 게시글 작성
제목, 작성자명, 비밀번호, 작성 내용을 저장하고
저장된 게시글을 Client 로 반환하기
- 선택한 게시글 조회
선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기
- 선택한 게시글 수정
수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
- 선택한 게시글 삭제 API
삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
선택한 게시글을 삭제

#2.UseCaseDiagram
![image](https://github.com/onda0-0/SpringTodoAssignment/assets/102335813/b93eb2d1-b435-415f-b5fb-4e6244042a0a)

#3 Erd
![image](https://github.com/onda0-0/SpringTodoAssignment/assets/102335813/6b8a51af-abd8-4318-8b79-373bd4e1d23c)

#4 API명세서
1. 일정 등록
URL: /todos
Method: POST
Request Body
```
json
{
    "username": "string",
    "title": "string",
    "contents": "string",
    "password": "string"
}
```
Response Body
```
json
{
    "id": "long",
    "username": "string",
    "title": "string",
    "contents": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
}
```

2. 전체 일정 조회
URL: /todos
Method: GET
Response

[
{
    "id": "long",
    "username": "string",
    "title": "string",
    "contents": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
}...
]

4. 선택 일정 조회
URL: /todos/{id}
Method: GET
Path Variable:
id: long (일정의 고유 식별자)
Response:
```
json
{
    "id": "long",
    "username": "string",
    "title": "string",
    "contents": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
}
```

6. 선택 일정 수정
URL: /todos/{id}
Method: PUT
Path Variable:
id: long (일정의 고유 식별자)

```
json
{
    "title": "string",
    "contents": "string",
    "password": "string"
}
```
response
```
json
{
    "id": "long",
    "username": "string",
    "title": "string",
    "contents": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
}
```
5. 선택 일정 삭제
URL: /todos/{id}/delete
Method: POST
Path Variable:
id: long (일정의 고유 식별자)
request
```
json

{
    "password": "string"
}
```
