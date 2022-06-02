package com.study.testcode;

import com.study.testcode.entity.Study;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;


    @Test
    void createStudyService() throws Exception  {
        //given
//        MemberService memberService = mock(MemberService.class);
//        StudyRepository studyRepository = mock(StudyRepository.class);
        Member member = new Member();
        member.setId(1L);
        member.setEmail("sadf@asdf.com");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));  //Stubbing - memberService.findById(1L)이라는 값이 들어오면 내가 만든 member가 리턴된다.
        when(memberService.findById(any())).thenReturn(Optional.of(member)); //모든 리턴은 내가만든 member 리턴.

        doThrow(new IllegalArgumentException()).when(memberService).validate(22L);  //22L 파라미터로 validate를 실행하면 해당 예외를 던진다.
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            memberService.validate(22L);
        });
        StudyService studyService = new StudyService(memberService, studyRepository);
        //when

        //then
    }
}
