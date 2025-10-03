package edu.dosw.sirha.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.request.SubjectRequestDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
import edu.dosw.sirha.dto.response.SubjectResponseDTO;
import edu.dosw.sirha.mapper.SubjectMapper;
import edu.dosw.sirha.model.Request;
import edu.dosw.sirha.model.Subject;
import edu.dosw.sirha.model.enums.State;
import edu.dosw.sirha.model.enums.Status;
import edu.dosw.sirha.repository.SubjectRepository;
import edu.dosw.sirha.service.SubjectService;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {

        @Mock
        private SubjectRepository subjectRepository;

        @Mock
        private SubjectMapper subjectMapper;

        @InjectMocks
        private SubjectService subjectService;

        @Test
        void shouldCreateSubject() {

                SubjectRequestDTO request = SubjectRequestDTO.builder()
                                .code("CALI")
                                .name("Calculo Integral")
                                .credits(23)
                                .status(Status.APPROVED)
                                .build();

                Subject fakeSaved = Subject.builder()
                                .code("CALI")
                                .name("Calculo Integral")
                                .credits(23)
                                .status(Status.APPROVED)
                                .build();

                SubjectResponseDTO fakeResponse = SubjectResponseDTO.builder()
                                .code("CALI")
                                .name("Calculo Integral")
                                .credits(23)
                                .status(Status.APPROVED)
                                .build();

                when(subjectMapper.toEntity(request)).thenReturn(fakeSaved);
                when(subjectRepository.save(fakeSaved)).thenReturn(fakeSaved);
                when(subjectMapper.toDto(fakeSaved)).thenReturn(fakeResponse);

                SubjectResponseDTO response = subjectService.createSubject(request);

                assertEquals("Calculo Integral", response.getName());
                assertEquals("CALI", response.getCode());
        }

        @Test
        void shouldUpdateSubject(){

                SubjectRequestDTO toUpdateSubject = SubjectRequestDTO.builder()
                                .code("MABA")
                                .name("Matematicas Basicas")
                                .credits(11)
                                .status(Status.APPROVED)
                                .build();

                Subject actual = Subject.builder()
                                .code("MABA")
                                .name("Precalculo")
                                .credits(15)
                                .status(Status.APPROVED)
                                .build();
                
                Subject updated = Subject.builder()
                                .code("MABA")
                                .name("Matematicas Basicas")
                                .credits(11)
                                .status(Status.APPROVED)
                                .build();   
                
                SubjectResponseDTO fakeResponse = SubjectResponseDTO.builder()
                                .code("MABA")
                                .name("Matematicas Basicas")
                                .credits(11)
                                .status(Status.APPROVED)
                                .build();
                when(subjectRepository.findById("MABA")).thenReturn(Optional.of(actual));
                when(subjectRepository.save(actual)).thenReturn(updated);
                when(subjectMapper.toDto(updated)).thenReturn(fakeResponse);
                SubjectResponseDTO response = subjectService.updateSubject("MABA", toUpdateSubject);
                assertEquals("MABA", response.getCode());
                assertEquals(11, response.getCredits());

        }
        @Test
        void shouldDeleteSubject(){
                Subject subject = Subject.builder()
                                .code("CALV")
                                .name("Calculo Vectorial")
                                .credits(10)
                                .status(Status.REPROVED)
                                .build();
                when(subjectRepository.findById("CALV")).thenReturn(Optional.of(subject));
                subjectService.deleteSubject("CALV");
                verify(subjectRepository).delete(subject);

        }



}
