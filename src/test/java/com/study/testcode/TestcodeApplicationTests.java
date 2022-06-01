package com.study.testcode;

import com.study.testcode.entity.Study;
import com.study.testcode.entity.StudyStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;


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

	@Test
	@DisplayName("테스트 1")
	void create_test1() throws Exception  {
		Study study = new Study(-10);
		assertAll(
				()->assertNotNull(study),
				()->assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
				()->assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 DRAFT 상태다."),
				()->assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야한다.")
		);
	}

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
		assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
			new Study(10);
			Thread.sleep(300);
		});
		//assertTimeout : 모든 로직이 끝나면 테스트가 끝남
		//assertTimeoutPreemptively : 정해진 시간이 지나면 테스트가 끝남 - 어차피 실패할 테스트이기 떄문에
		//assertTimeoutPreemptively 주의사항 : 테스트코드에서 람다 코드블럭은 별도의 쓰레드가 실행하기때문에 스프링 트랜잭션처리를 하는 쓰레드와 다른 쓰레드로 동작한다.
		//때문에 트랜잭션의 롤백 처리를 받지 못하는 경우가 생길 수 있다. - 롤백과 상관없는 로직을 테스트할때만 사용하자.
	}

}
