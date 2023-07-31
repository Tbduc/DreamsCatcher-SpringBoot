package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.dto.dream.DreamDto;
import com.codecool.elproyectegrande1.entity.Dream;
import com.codecool.elproyectegrande1.mapper.DreamMapper;
import com.codecool.elproyectegrande1.repository.DreamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DreamServiceTest {

    @Mock
    private DreamRepository dreamRepository;

    @Mock
    private DreamMapper dreamMapper;

    @InjectMocks
    private DreamService dreamService;

    @Test
    void shouldReturnAllDreams() {

        Dream dream = new Dream();

        Dream dream2 = new Dream();

        List<Dream> dreams = List.of(dream, dream2);

        DreamDto dreamDto = new DreamDto();

        DreamDto dreamDto2 = new DreamDto();

        List<DreamDto> expected = List.of(dreamDto, dreamDto2);

        Mockito.when(dreamRepository.findAll()).thenReturn(dreams);
        when(dreamMapper.mapEntityToDreamDto(dream)).thenReturn(dreamDto);
        when(dreamMapper.mapEntityToDreamDto(dream2)).thenReturn(dreamDto2);

        List<DreamDto> actual = dreamService.getAllDreams();

        Assertions.assertEquals(expected, actual);

    }
}