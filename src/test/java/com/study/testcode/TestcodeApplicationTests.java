package com.study.testcode;

import com.study.testcode.entity.Study;
import com.study.testcode.entity.StudyStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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


}
