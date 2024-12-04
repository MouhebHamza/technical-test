package com.infor.technicaltest.controller;


import com.infor.technicaltest.dto.ReservationDTO;
import com.infor.technicaltest.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    public void testCreateReservation() throws Exception {
        ReservationDTO reservationDTO = new ReservationDTO();
        when(reservationService.createReservation(any(ReservationDTO.class))).thenReturn(reservationDTO);
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"propertyId\":1, \"fromDate\":\"2024-12-01T10:00:00\", \"toDate\":\"2024-12-02T10:00:00\"}"))
                .andExpect(status().isCreated()) // Verify status code is 201
                .andExpect(jsonPath("$.propertyId").exists());
    }

    @Test
    public void testGetReservationById() throws Exception {
        ReservationDTO reservationDTO = new ReservationDTO();
        when(reservationService.findReservationById(1L)).thenReturn(reservationDTO);

        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk()) // Verify status code is 200
                .andExpect(jsonPath("$.propertyId").exists());
    }

    @Test
    public void testGetReservationsByProperty() throws Exception {
        when(reservationService.findReservationsByProperty(1L)).thenReturn(List.of(new ReservationDTO()));
        mockMvc.perform(get("/api/reservations/property/1"))
                .andExpect(status().isOk()) // Verify status code is 200
                .andExpect(jsonPath("$[0].propertyId").exists());
    }

    @Test
    public void testGetReservationsByDateRange() throws Exception {
        when(reservationService.findReservationsByDateRange(any(), any())).thenReturn(List.of(new ReservationDTO()));

        mockMvc.perform(get("/api/reservations/dates?start=2024-12-01T00:00:00&end=2024-12-10T00:00:00"))
                .andExpect(status().isOk()) // Verify status code is 200
                .andExpect(jsonPath("$[0].propertyId").exists());
    }
}
