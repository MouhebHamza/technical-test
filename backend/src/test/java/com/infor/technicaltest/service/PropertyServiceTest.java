package com.infor.technicaltest.service;

import com.infor.technicaltest.dto.PropertyDTO;
import com.infor.technicaltest.entity.Property;
import com.infor.technicaltest.repository.PropertyRepository;
import com.infor.technicaltest.transformer.PropertyTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
public class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private PropertyTransformer propertyTransformer;

    @InjectMocks
    private PropertyService propertyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllProperties() {
        // Arrange
        Property property = new Property();
        property.setId(1L);
        property.setBuildingName("Test Building");

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(1L);
        propertyDTO.setBuildingName("Test Building");

        when(propertyRepository.findAll()).thenReturn(List.of(property));
        when(propertyTransformer.toDTO(property)).thenReturn(propertyDTO);

        List<PropertyDTO> result = propertyService.findAllProperties();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Building", result.get(0).getBuildingName());
    }
}