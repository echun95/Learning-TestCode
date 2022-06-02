package com.study.testcode;

import com.study.testcode.entity.Study;
import com.study.testcode.entity.StudyStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestcodeApplicationTests {



	@BeforeAll
	static void beforeAll() throws Exception  {
	    //given
		System.out.println("beforeAll");
	    //when

	    //then
	}

	@Test
	void contextLoads() {
	}

	@Order(2)
	@Test
	@DisplayName("테스트 1")
	void create_test1() throws Exception  {
		Study study = new Study(10);
		assertAll(
				()->assertNotNull(study),
				()->assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
				()->assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 DRAFT 상태다."),
				()->assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야한다.")
		);
	}

	@Order(1)
	@Test
	@DisplayName("예외 테스트")
	void throwExceptionTest() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
		String message = exception.getMessage();
		assertEquals("limit은 0보다 커야한다.", message);
	}

	@Test
	@DisplayName("정해진 시간안에 성공 테스트")
	void assertTimeoutTest() {
//		assertTimeout(Duration.ofMillis(100), () -> {
//			new Study(10);
//			Thread.sleep(300);
//		});
		assertTimeoutPreemptively(Duration.ofMillis(400), () -> {
			new Study(10);
			Thread.sleep(300);
		});
		//assertTimeout : 모든 로직이 끝나면 테스트가 끝남
		//assertTimeoutPreemptively : 정해진 시간이 지나면 테스트가 끝남 - 어차피 실패할 테스트이기 떄문에
		//assertTimeoutPreemptively 주의사항 : 테스트코드에서 람다 코드블럭은 별도의 쓰레드가 실행하기때문에 스프링 트랜잭션처리를 하는 쓰레드와 다른 쓰레드로 동작한다.
		//때문에 트랜잭션의 롤백 처리를 받지 못하는 경우가 생길 수 있다. - 롤백과 상관없는 로직을 테스트할때만 사용하자.
	}

	@Test
	void assumeTrueTest() {
		String test_env = System.getenv("TEST_ENV");
		//환경변수 값에 따라 테스트 실행 유무 결정하기
		assumeTrue("LOCAL".equalsIgnoreCase(test_env));

		assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
			System.out.println("test_env = " + test_env);
			new Study(-10);
		});
		assumingThat("DEV".equalsIgnoreCase(test_env), () -> {
			System.out.println("test_env = " + test_env);
			new Study(-10);
		});

		assertThrows(IllegalArgumentException.class, () -> new Study(-10));
	}
	@Test
	@EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
	void ENV_테스트1() throws Exception  {
		System.out.println("TEST_ENV 환경 변수의 값이 LOCAL일때만 실행되는 테스트");
	}
	@Test
	@EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "DEV")
	void ENV_테스트2() throws Exception  {
		System.out.println("TEST_ENV 환경 변수의 값이 DEV일때만 실행되는 테스트");
	}

	@Test
	@EnabledOnOs({OS.MAC, OS.LINUX})
	void OS_mac_linux_test() throws Exception  {
		System.out.println("리눅스, 맥 os만 실행되는 테스트");
	}
	@Test
	@EnabledOnOs({OS.WINDOWS})
	void OS_window_test() throws Exception  {
		System.out.println("window os만 실행되는 테스트");
	}
	@Test
	@EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11})
	void JRE_ver_test1() throws Exception  {
		System.out.println("JRE 8,9,10,11 버전만 실행되는 테스트");
	}
	@Test
	@EnabledOnJre(JRE.OTHER)
	void JRE_ver_test2() throws Exception  {
		System.out.println("JRE 옛날 버전만 실행되는 테스트");
	}
}
