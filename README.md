### 서비스기획 -> 테이블(엔티티) -> repository
1\. Author 
  - id : Long, pk, auto_increment 
  - name : String, length 20자, not null
  - email : String, length 20자, unique 옵션(unique =true), not null
  - password : String, not null
  - role : enum타입(클래스명 Role)
  - createdTime, updatedTime -> LocalDateTime, current_timestamp 유사옵션
  - 다되면, create 옵션으로 서버시작하여 테이블 describe

  1) 저장 후 조회
     - save
       - AuthorSavveReqDto : name, email, password
       - AuthorController
         - authorSave("/author/save")
         - 데이터바인딩(form 태그로 전송)
         - 받은 데이터를 그대로 service의 save로 전달
         - 리턴은 문자열 "ok"가 나오도록

  2) 회원 목록 조회
     - AuthorListResDto : ID, 이름, 이메일
     - End Point: /author/list
     - 메서드명: authorList
     - 리턴타입: List<AuthorListResDto>

  3) 회원 상세 조회
     - AuthorDetailResDto: id, name, email, password, createdTime
     - End-Point: author/detail/1
     - 메서드명: authorDetail

화면 설계
  - 공통 Header
  - 게시판 서비스를 누르면 "/home"
    - 회원가입
    - 회원관리: 리스트 출력
      - 각 목록별로 "상세보기" 버튼 
    - 게시글

1. 회원가입완료 => 회원목록조회화면으로 redirect
2. 회원수정기능 개발 + 회원수정완료시 상세 조회 화면 redirect
3. 회원삭제기능 개발 => 회원목록조회로 redirect


- POST Entity
  - id
  - title(length 50)
  - contents(length 3000)
  - 생성시간
  - 수정시간
- 글 목록 조회 dto
  - id
  - title
- 글 상세보기 dto
  - id
  - title
  - contents
  - 생성시간
- 글 작성 dto
  - title
  - contents
- 글 삭제