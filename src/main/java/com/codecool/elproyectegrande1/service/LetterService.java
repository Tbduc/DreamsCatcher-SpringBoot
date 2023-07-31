package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.dto.letter.LetterDto;
import com.codecool.elproyectegrande1.dto.letter.NewLetterDto;
import com.codecool.elproyectegrande1.entity.Dreamer;
import com.codecool.elproyectegrande1.entity.Letter;
import com.codecool.elproyectegrande1.mapper.LetterMapper;
import com.codecool.elproyectegrande1.repository.DreamerRepository;
import com.codecool.elproyectegrande1.repository.LetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
public class LetterService {

    private final LetterRepository letterRepository;
    private final LetterMapper letterMapper;
    private final DreamerRepository dreamerRepository;

    @Autowired
    public LetterService(LetterRepository letterRepository, LetterMapper letterMapper, DreamerRepository dreamerRepository) {
        this.letterRepository = letterRepository;
        this.letterMapper = letterMapper;
        this.dreamerRepository = dreamerRepository;
    }

    public List<Letter> getAllLetters() {
        return letterRepository.findAll();
    }

    public Letter getLetterById(Long id) {
        return letterRepository.findById(id).orElse(null);
    }

    public LetterDto addLetter(String sender, NewLetterDto newLetterDto) {
        Dreamer dreamer = dreamerRepository.findByEmail(sender);
        Letter toBeSaved = letterMapper.mapNewLetterDtoToEntity(newLetterDto, sender);
        toBeSaved.setDreamer(dreamer);
        Letter savedLetter = letterRepository.save(toBeSaved);
        return letterMapper.mapEntityToLetterDto(savedLetter);
    }

    public void deleteLetterById(Long id) {
        letterRepository.deleteById(id);
    }
}
