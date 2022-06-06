package com.study.testcode;

import com.study.testcode.domain.Member;
import com.study.testcode.domain.Study;
import com.study.testcode.domain.StudyStatus;
import com.study.testcode.member.MemberService;
import com.study.testcode.study.StudyRepository;
import com.study.testcode.study.StudyService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
//@ContextConfiguration(initializers = StudyServiceTest.ContainerPropertyInitializer.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Autowired
    StudyRepository studyRepository;
//    @Autowired
//    Environment environment;
    @Value("${container.port}") int port;

//    @Container
//    static MySQLContainer mySQLContainer = new MySQLContainer<>().withDatabaseName("studytest");

//    @Container
//    static GenericContainer mySQLContainer = new GenericContainer("mysql").withEnv("MYSQL_DB", "studytest")
//            .withExposedPorts(3006);

    @Container
    static DockerComposeContainer composeContainer = new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

    @BeforeEach
    void beforeEach(){
//        System.out.println(mySQLContainer.getMappedPort(3006)   );
//        System.out.println(environment.getProperty("container.port"));
        System.out.println(port);
        studyRepository.deleteAll();
    }

//    @BeforeAll
//    static void beforeAll(){
//        mySQLContainer.start();
//        System.out.println(mySQLContainer.getJdbcUrl());
//    }
//    @AfterAll
//    static void afterAll(){
//        mySQLContainer.stop();
//    }

    @Test
    void createNewStudy() {
        System.out.println("========");

        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("keesun@email.com");

        Study study = new Study(10, "테스트");

        given(memberService.findById(1L)).willReturn(Optional.of(member));

        // When
        studyService.createNewStudy(1L, study);

        // Then
        assertEquals(1L, study.getOwnerId());
        then(memberService).should(times(1)).notify(study);
        then(memberService).shouldHaveNoMoreInteractions();
    }

    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    @Test
    void openStudy() {
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "더 자바, 테스트");
        assertNull(study.getOpenedDateTime());

        // When
        studyService.openStudy(study);

        // Then
        assertEquals(StudyStatus.OPENED, study.getStatus());
        assertNotNull(study.getOpenedDateTime());
        then(memberService).should().notify(study);
    }

//    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{
//        @Override
//        public void initialize(ConfigurableApplicationContext context) {
//            TestPropertyValues.of("container.port=" + mySQLContainer.getMappedPort(3006))
//                    .applyTo(context.getEnvironment());
//        }
//    }

}