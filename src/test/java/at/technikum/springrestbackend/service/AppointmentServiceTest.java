package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.appointment.AvailabilityTimetable;
import at.technikum.springrestbackend.dto.availability.CreateGeneralAvailabilityRequest;
import at.technikum.springrestbackend.dto.lawyer.CreateLawyerRequest;
import at.technikum.springrestbackend.model.*;
import at.technikum.springrestbackend.repository.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private GeneralAvailabilityRepository generalAvailabilityRepository;

    @Mock
    private SpecificAvailabilityRepository specificAvailabilityRepository;

    @Mock
    private LawyerRepository lawyerRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void getAvailabilityTimeslotsForDates_ValidInput_ReturnsOkResponse() {
        // Arrange
        UUID lawyerId = UUID.randomUUID();
        LocalDate from = LocalDate.now();
        int amountOfDays = 3;

        Lawyer lawyer = new Lawyer();
        when(lawyerRepository.findById(lawyerId)).thenReturn(Optional.of(lawyer));

        // Mock
        when(generalAvailabilityRepository.findAllByDayAndLawyer(any(), any())).thenReturn(new ArrayList<>());
        when(specificAvailabilityRepository.findAllByDateAndLawyer(any(), any())).thenReturn(new ArrayList<>());
        when(appointmentRepository.findAllByLawyerAndDate(any(), any())).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<AvailabilityTimetable> response = appointmentService.getAvailabilityTimeslotsForDates(lawyerId, from, amountOfDays);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createAppointment_ValidInput_ReturnsOkResponse() {
        // Arrange
        UUID lawyerId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        String time = "12:00";

        Lawyer mockedLawyer = getLawyer();

        when(lawyerRepository.findById(lawyerId)).thenReturn(Optional.of(mockedLawyer));
        User mockedUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockedUser));

        // Mock
        GeneralAvailability generalAvailability = createGeneralAvailability();
        when(generalAvailabilityRepository.findAllByDayAndLawyer(any(), any())).thenReturn(Collections.singletonList(generalAvailability));

        // Mock
        when(appointmentRepository.findAllByLawyerAndDate(any(), any())).thenReturn(new ArrayList<>());

        Appointment appointment = new Appointment();
        appointment.setId(UUID.randomUUID());
        appointment.setDate(date);
        appointment.setStartTime(LocalTime.of(10, 0));
        appointment.setEndTime(LocalTime.of(11, 0));
        appointment.setLawyer(mockedLawyer);
        appointment.setUser(mockedUser);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        // Act
        ResponseEntity<Appointment> response = appointmentService.createAppointment(lawyerId, userId, String.valueOf(date), time);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Appointment createdAppointment = response.getBody();
        assert createdAppointment != null;
        assertEquals(mockedLawyer, createdAppointment.getLawyer());
        assertEquals(mockedUser, createdAppointment.getUser());
        assertEquals(generalAvailability.getDuration(), createdAppointment.getEndTime().toSecondOfDay() / 60 - createdAppointment.getStartTime().toSecondOfDay() / 60);
    }

    @NotNull
    private static Lawyer getLawyer() {
        CreateLawyerRequest createLawyerRequest = new CreateLawyerRequest(
                "John",
                "Doe",
                Specialization.CRIMINAL_LAW,
                100,
                "123 Main St",
                "12345",
                "City"
        );


        Lawyer mockedLawyer = createLawyerRequest.toLawyer();


        List<GeneralAvailability> exampleGeneralAvailabilities = new ArrayList<>();
        CreateGeneralAvailabilityRequest firstTime = new CreateGeneralAvailabilityRequest(
            mockedLawyer.getId(), DayOfWeek.MONDAY, 60, "09:00", "17:00"
        );
        CreateGeneralAvailabilityRequest secondTime = new CreateGeneralAvailabilityRequest(
                mockedLawyer.getId(), DayOfWeek.WEDNESDAY, 60, "10:00", "18:00"
        );
        GeneralAvailability firstAvailability = firstTime.toGeneralAvailability();
        GeneralAvailability secondAvailability = secondTime.toGeneralAvailability();

        exampleGeneralAvailabilities.add(firstAvailability);
        exampleGeneralAvailabilities.add(secondAvailability);

        mockedLawyer.setAvailabilities(exampleGeneralAvailabilities);
        return mockedLawyer;
    }

    private GeneralAvailability createGeneralAvailability() {
        GeneralAvailability availability = new GeneralAvailability();
        availability.setId(UUID.randomUUID());
        availability.setDay(DayOfWeek.MONDAY);
        availability.setDuration(60);
        availability.setStartTime(LocalTime.of(9, 0));
        availability.setEndTime(LocalTime.of(17, 0));
        availability.setLawyer(new Lawyer());
        return availability;
    }



}
