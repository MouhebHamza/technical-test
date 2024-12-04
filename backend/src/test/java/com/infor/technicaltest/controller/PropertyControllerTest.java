package com.infor.technicaltest.controller;

import com.infor.technicaltest.dto.PropertyDTO;
import com.infor.technicaltest.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PropertyControllerTest {

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private PropertyController propertyController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(propertyController).build();
    }

    @Test
    public void testGetAllProperties() throws Exception {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress("test address");
        propertyDTO.setBuildingName("Test Property");
        propertyDTO.setCountry("FRA");


        when(propertyService.findAllProperties()).thenReturn(List.of(propertyDTO));
        mockMvc.perform(get("/api/properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].buildingName").value("Test Property"));
    }
}
