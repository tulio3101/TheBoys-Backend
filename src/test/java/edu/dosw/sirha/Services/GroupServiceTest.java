package edu.dosw.sirha.Services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.dosw.sirha.dto.request.GroupRequestDTO;
import edu.dosw.sirha.dto.request.UserRequestDTO;
import edu.dosw.sirha.dto.response.GroupResponseDTO;
import edu.dosw.sirha.dto.response.UserResponseDTO;
import edu.dosw.sirha.mapper.GroupMapper;
import edu.dosw.sirha.model.Group;
import edu.dosw.sirha.model.User;
import edu.dosw.sirha.model.enums.Career;
import edu.dosw.sirha.model.enums.Role;
import edu.dosw.sirha.model.observers.GroupObserver;
import edu.dosw.sirha.repository.GroupRepository;
import edu.dosw.sirha.service.GroupService;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {
        @Mock
        private GroupRepository groupRepository;

        @Mock
        private GroupMapper groupMapper;

        @InjectMocks
        private GroupService groupService;
        /* 
        private List<GroupObserver> observers;

        @BeforeEach
        void setUp(){
                observers = new ArrayList<>();
        }
        */
        @Test
        void shouldCreateGroup() {
                GroupRequestDTO request = GroupRequestDTO.builder()
                                .numberGroup("1")
                                .capacity(25)
                                .availableQuotas(10)
                                .subjectCode("CALI")
                                .build();

                Group fakeSaved = Group.builder()
                                .numberGroup("1")
                                .capacity(25)
                                .availableQuotas(10)
                                .subjectCode("CALI")
                                .build();

                GroupResponseDTO fakeResponse = GroupResponseDTO.builder()
                                .numberGroup("1")
                                .capacity(25)
                                .availableQuotas(10)
                                .subjectCode("CALI")
                                .build();

                when(groupMapper.toEntity(request)).thenReturn(fakeSaved);
                when(groupRepository.save(fakeSaved)).thenReturn(fakeSaved);
                when(groupMapper.toDto(fakeSaved)).thenReturn(fakeResponse);

                GroupResponseDTO response = groupService.createGroup(request);

                assertEquals("CALI", response.getSubjectCode());
                assertEquals(25, response.getCapacity());
        }
        /* 

        @Test
        void shouldUpdateGroup(){
                GroupRequestDTO request = GroupRequestDTO.builder()
                                .capacity(29)
                                .availableQuotas(10)
                                .subjectCode("CVDS")
                                .build();
                Group actualGroup = Group.builder()
                                .numberGroup("1")
                                .capacity(20) 
                                .availableQuotas(15)
                                .subjectCode("CVDS")
                                .build();
                Group updated = Group.builder()
                                .numberGroup("1")
                                .capacity(29) 
                                .availableQuotas(10)
                                .subjectCode("CVDS")
                                .build();

                GroupResponseDTO fakeResponse = GroupResponseDTO.builder()
                                .numberGroup("1")
                                .capacity(29)
                                .availableQuotas(10)
                                .subjectCode("CVDS")
                                .build();
                when(groupRepository.findById("1")).thenReturn(Optional.of(actualGroup));
                when(groupRepository.save(actualGroup)).thenReturn(updated);
                when(groupMapper.toDto(updated)).thenReturn(fakeResponse);

                GroupResponseDTO response = groupService.updateGroup("1", request);

                assertEquals(29, response.getCapacity());
                assertEquals(10, response.getAvailableQuotas());
        }
        */
        
        @Test
        void shouldDeleteGroup() {
        when(groupRepository.existsById("999")).thenReturn(true);

        groupService.deleteGroup("999");

        verify(groupRepository).deleteById("999");
        }
}
