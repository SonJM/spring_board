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

--------------------------------------------------------------
POST의 DB구조를 어떻게 설계할 것인가.
=> String appointMent = 'Y' vs 'N'
=> LocalDateTime appointMentTime = 예약시간 vs null
=> 목록조회 => !'Y'조회

스케쥴러 : 때되면 프로그램이 실행되서, DB update
        (때되면) => 예약시간이 현재시간보다 이전시간이 되어버리면

---------------------------------------------------------------
로그관리 + AOP, 공통예외처리(controlleradvice) => 미들웨어
1. logback(@slf4j) 라이브러리 사용
2. 로그관리(로그레벨-debug, info, error)
3. 로그파일관리(날짜별, 용도별) - logback설정파일

----------------------------------------------------------------
* 컨트롤러 앞단에서 선 처리 해주는 놈들(주요 미들웨어성 기술)
1) ControllerAdvice : 컨트롤러 특화 기능(주로 예외처리)
2) AOP : 모든 레이어에서 공통기능수행 (로그, 보안처리)
3) Filter : 주로 로그인 시에 공통기능수행

이론: AOP가 무엇인가 -> 관점지향프로그래밍
=> 공통관심사항, 핵심관심사항 분리 아키텍쳐
=> 공통관심사항(로깅, 보안)

정리
1) 로그설정(로그레벨, 파일관리)
2) AOP(공통로그처리 -> before, after)
3) 예외처리(ControllerAdvice, ExceptionHandler)