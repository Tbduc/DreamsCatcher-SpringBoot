package com.codecool.elproyectegrande1.controller;

import com.codecool.elproyectegrande1.dto.letter.LetterDto;
import com.codecool.elproyectegrande1.dto.letter.NewLetterDto;
import com.codecool.elproyectegrande1.entity.Letter;
import com.codecool.elproyectegrande1.service.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inbox")
public class LetterController {

    @Autowired
    private LetterService letterService;

    @GetMapping("")
    public List<Letter> getAllLetters() {
        return letterService.getAllLetters();
    }

    @GetMapping("/{id}")
    public Letter getLetterById(@PathVariable Long id) {
        return letterService.getLetterById(id);
    }

    @PostMapping("/new_letter")
    public LetterDto sendLetter(@RequestBody NewLetterDto newLetterDto, Principal principal) {
        String sender = principal.getName();
        return letterService.addLetter(sender, newLetterDto);
    }

    @DeleteMapping("/{id}")
    public void deleteLetterById(@PathVariable Long id) {
        letterService.deleteLetterById(id);
    }
}
