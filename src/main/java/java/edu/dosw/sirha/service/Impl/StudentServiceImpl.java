package java.edu.dosw.sirha.service.Impl;

import java.edu.dosw.sirha.dto.AcademicPeriodRequestDTO;
import java.edu.dosw.sirha.dto.AcademicTrafficLightResponseDTO;
import java.edu.dosw.sirha.dto.RequestDTO;
import java.edu.dosw.sirha.dto.ResponseRequestDTO;
import java.edu.dosw.sirha.dto.StudentRequestDTO;
import java.edu.dosw.sirha.dto.StudentScheduleResponseDTO;
import java.edu.dosw.sirha.mapper.StudentMapper;
import java.edu.dosw.sirha.model.AcademicTrafficLight;
import java.edu.dosw.sirha.model.Request;
import java.edu.dosw.sirha.model.Student;
import java.edu.dosw.sirha.model.enums.StatusOfRequest;
import java.edu.dosw.sirha.repository.AcademicTrafficLightRepository;
import java.edu.dosw.sirha.repository.RequestRepository;
import java.edu.dosw.sirha.repository.StudentRepository;
import java.edu.dosw.sirha.service.StudentService;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.ws.rs.core.Response;
/**
 * 
 
@Service
public class StudentServiceImpl implements StudentService {

    private final RequestRepository requestRepository;
    private final StudentRepository studentRepository;
    private final AcademicTrafficLightRepository academicTrafficLightRepository;

    private StudentMapper studentMapper;

    public StudentServiceImpl(RequestRepository requestRepository, StudentMapper studentMapper,
            StudentRepository studentRepository, AcademicTrafficLightRepository academicTrafficLightRepository) {
        this.requestRepository = requestRepository;
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.academicTrafficLightRepository = academicTrafficLightRepository;
    }

    public ResponseRequestDTO createRequest(RequestDTO dto) {
        return ResponseRequestDTO.builder()
                .statusOfRequest(StatusOfRequest.PENDING)
                .build();
    }

    public List<ResponseRequestDTO> getRequestByStudent(StudentRequestDTO dto) {

        List<Request> requests = requestRepository.findByStudentId(dto.getId());

        return requests.stream().map(request -> ResponseRequestDTO.builder()
                .statusOfRequest(request.getStatus())
                .build())
                .toList();

    }

    public AcademicTrafficLightResponseDTO consultAcademicTrafficLight(Long id) {
        Student student = studentRepository.findByStudentId(id);
        AcademicTrafficLight academicTrafficLight = academicTrafficLightRepository.findByAcademicId(student.getCode());

        return AcademicTrafficLightResponseDTO.builder()
                .studentCode(academicTrafficLight.getStudentCode())
                .average(academicTrafficLight.getAverage())
                .semester(academicTrafficLight.getSemester())
                .academicPlan(academicTrafficLight.getAcademicPlan())
                // Falta subjects
                .build();
    }

    public StudentScheduleResponseDTO consultSchedule(AcademicPeriodRequestDTO dto) {
        return 
    }

}
    */
