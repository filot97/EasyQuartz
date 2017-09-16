# spring-boot-eqsyquartz
## 개요
이 프로젝트 샘플코드는 DBMS와 POJO 기반의 배치를 스케쥴링하기 위한 기본 템플릿을 제공한다. Spring Boot 기반으로 스케쥴링 프레임워크인 [Quartz](http://www.quartz-scheduler.org/)를 사용한다.
일반적으로 운영업무에서 스케쥴링 방식은 OS 기반의 쉘 스크립트, DBMS에서 제공하는 Job, 스케쥴러  또는 프로그래밍 언어를 통한 standalone 데몬성 프로그램 기반으로 많이 운영이 된다. 이러한 방식의 단점은 다음과 같다.
* IDE와 연계된 streamlined 형상관리의 어려움(특정 프로그래밍 언어 기반은 제외)
* HA(High Availability) 미지원
## 프로젝트 구조
## 사용법
### log4jdbc
SQL을 로깅처리하기 위해서 log4jdbc 라이브러를 사용한다. 설정정보는 /resource/log4jdbc.log4j2.properties를 수정하면 된다. 관련 속성정보는 
### 로깅처리
로깅관련 부분은 /resource/logback.xml에서 설정을 변경할 수 있다. 특이사항은 log4jdbc를 통한 SQL 로깅이 아래와 같이 추가되어 있다.
```xml
<logger name="jdbc" level="OFF" />     
<logger name="jdbc.sqlonly" level="OFF" />  
<logger name="jdbc.sqltiming" level="DEBUG" />  
<logger name="jdbc.audit" level="OFF" />  
<logger name="jdbc.resultset" level="OFF" />  
<logger name="jdbc.resultsettable" level="OFF" />  
<logger name="jdbc.connection" level="OFF" />
```
특이사항으로는 Quartz 프레임워크 관련 SQL은 아래 설정으로 제외처리 한다.
```xml
<filter class="ch.qos.logback.core.filter.EvaluatorFilter">                  
 <evaluator>                       
  <expression>return message.contains("QRTZ_");</expression>                   
 </evaluator>                  
 <OnMismatch>NEUTRAL</OnMismatch>                  
 <OnMatch>DENY</OnMatch>              
</filter>
```
## 제약사항
* CronTrigger 방식만 지원
* SQL Mapper 프레임워크는 MyBatis와 Anyframe의 QueryService만 지원
## 주의사항
* WebLogic에 배포시에는 war로 할 경우는 war의 /WEB-INF 폴더 하위에  /webapp/WEB-INF/weblogic.xml과  dispatcherServlet-servlet.xml파일을 다시 패키징하여 배포