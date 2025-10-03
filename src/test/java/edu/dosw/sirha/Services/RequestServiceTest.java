package edu.dosw.sirha.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.dosw.sirha.dto.request.GroupRequestDTO;
import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.response.GroupResponseDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
import edu.dosw.sirha.mapper.RequestMapper;
import edu.dosw.sirha.model.Group;
import edu.dosw.sirha.model.Request;
import edu.dosw.sirha.model.enums.State;
import edu.dosw.sirha.repository.RequestRepository;
import edu.dosw.sirha.service.RequestService;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {
    @Mock
    private RequestRepository requestRepository;

    @Mock
    private RequestMapper requestMapper;

    @InjectMocks
    private RequestService requestService;

    @Test
    void shouldCreateRequest() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);
        RequestDTO request = RequestDTO.builder()
                .userId("123")
                .creationDate(now)
                .responseDate(tomorrow)
                .state(State.PENDIENT)
                .build();

        Request fakeSaved = Request.builder()
                .userId("123")
                .creationDate(now)
                .responseDate(tomorrow)
                .state(State.PENDIENT)
                .build();

        RequestResponseDTO fakeResponse = RequestResponseDTO.builder()
                .userId("123")
                .creationDate(now)
                .responseDate(tomorrow)
                .state(State.PENDIENT)
                .build();

        when(requestMapper.toEntity(request)).thenReturn(fakeSaved);
        when(requestRepository.save(fakeSaved)).thenReturn(fakeSaved);
        when(requestMapper.toDto(fakeSaved)).thenReturn(fakeResponse);

        RequestResponseDTO response = requestService.createRequest(request);

        assertEquals("123", response.getUserId());
    }
    @Test
    void shouldUpdateRequest() {
        
        ObjectId id = new ObjectId();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);

        Request existing = Request.builder()
                .userId("777")
                .creationDate(now)
                .state(State.PENDIENT)
                .build();

        RequestDTO dto = RequestDTO.builder()
                .responseDate(tomorrow)
                .state(State.APPROVED)
                .build();

        Request updated = Request.builder()
                .userId("777")
                .creationDate(now)
                .responseDate(tomorrow)
                .state(State.APPROVED)
                .build();

        RequestResponseDTO responseDto = RequestResponseDTO.builder()
                .userId("777")
                .responseDate(tomorrow)
                .state(State.APPROVED)
                .build();

        when(requestRepository.findById(id)).thenReturn(Optional.of(existing));
        when(requestRepository.save(existing)).thenReturn(updated);
        when(requestMapper.toDto(updated)).thenReturn(responseDto);

        RequestResponseDTO result = requestService.updateRequest(id, dto);

        assertEquals(State.APPROVED, result.getState());
        assertEquals(tomorrow, result.getResponseDate());
    }

    @Test
    void shouldDeleteRequest() {
        ObjectId id = new ObjectId();
        when(requestRepository.existsById(id)).thenReturn(true);

        requestService.deleteRequest(id);

        verify(requestRepository).deleteById(id);
    }


    @Test
    void shouldReturnAllRequestsByStudent() {
        Request request = Request.builder().userId("7").build();
        RequestResponseDTO response = RequestResponseDTO.builder().userId("7").build();

        when(requestRepository.findByUserId("7")).thenReturn(List.of(request));
        when(requestMapper.toDtoList(List.of(request))).thenReturn(List.of(response));

        List<RequestResponseDTO> result = requestService.allRequestByStudentId("7");

        assertEquals(1, result.size());
        assertEquals("7", result.get(0).getUserId());
    }


}
