package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.dto.dream.DreamDto;
import com.codecool.elproyectegrande1.dto.dream.NewDreamDto;
import com.codecool.elproyectegrande1.entity.Dream;
import com.codecool.elproyectegrande1.entity.DreamStatus;
import com.codecool.elproyectegrande1.entity.*;
import com.codecool.elproyectegrande1.service.exceptions.DreamNotFoundException;
import com.codecool.elproyectegrande1.mapper.DreamMapper;
import com.codecool.elproyectegrande1.repository.DreamRepository;
import com.codecool.elproyectegrande1.repository.DreamerRepository;
import com.codecool.elproyectegrande1.repository.ImageRepository;
import com.codecool.elproyectegrande1.util.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DreamService {

    private final DreamRepository dreamRepository;
    private final DreamMapper dreamMapper;
    private final DreamerRepository dreamerRepository;
    private final List<Dream> dreams = new ArrayList<>();
    private final ImageRepository imageRepository;


    @Autowired
    public DreamService(DreamRepository dreamRepository, DreamMapper dreamMapper, DreamerRepository dreamerRepository,
                        ImageRepository imageRepository) {
        this.dreamRepository = dreamRepository;
        this.dreamMapper = dreamMapper;
        this.dreamerRepository = dreamerRepository;
        this.imageRepository = imageRepository;
    }

    public DreamDto addDream(String name, NewDreamDto newDream) {
        EmailValidator emailValidator = new EmailValidator();
        Dreamer dreamer;
        if (!emailValidator.isValid(name, null))
            dreamer = dreamerRepository.findByUsername(name)
                .orElseThrow(() -> new IllegalArgumentException("Dreamer with id " + name + " not found"));
        else
            dreamer = dreamerRepository.findByEmail(name);

        Dream toBeSaved = dreamMapper.mapNewDreamDtoToEntity(newDream);
        toBeSaved.setDreamer(dreamer);
        Dream savedDream = dreamRepository.save(toBeSaved);

        List<Dream> updatedDreams = dreamer.getDreams();
        updatedDreams.add(savedDream);
        dreams.add(savedDream);
        dreamer.getDreams().add(savedDream);
        return dreamMapper.mapEntityToDreamDto(savedDream);
    }


    public void likeDream(Long dreamId) {
        Dream dream = dreamRepository.findById(dreamId).orElseThrow();
        dream.setLikes(dream.getLikes() + 1);
        dreamRepository.save(dream);
    }

    public void dislikeDream(Long dreamId) {
        Dream dream = dreamRepository.findById(dreamId).orElseThrow();
        dream.setLikes(dream.getLikes() - 1);
        dreamRepository.save(dream);
    }


    public List<DreamDto> getAllDreams() {
        List<Dream> dreams = dreamRepository.findAll();
        return dreams.stream()
                .map(dreamMapper::mapEntityToDreamDto)
                .collect(Collectors.toList());
    }


    public List<DreamDto> getLastEightDreams() {
        List<Dream> dreams = dreamRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<DreamDto> dreamDtos = new ArrayList<>();

        for (int i = 0; i < 8 && i < dreams.size(); i++) {
            DreamDto dto = dreamMapper.mapEntityToDreamDto(dreams.get(i));
            dreamDtos.add(dto);
        }

        return dreamDtos;
    }

    public Dream getDreamWithMostLikes() {
        return dreamRepository.findTopByOrderByLikesDesc();
    }

    public Dream getDreamWithMostViews() {
        return dreamRepository.findTopByOrderByViewsDesc();
    }

    public DreamDto getDreamById(Long id) {
        Dream dream = dreamRepository.findById(id)
                .orElseThrow(() -> new DreamNotFoundException(id));
        return dreamMapper.mapEntityToDreamDto(dream);
    }

    public void updateDreamStatus(Long id, String status) {
        Dream dream = dreamRepository.findById(id).orElseThrow();
        dream.setDreamStatus(DreamStatus.valueOf(status.toUpperCase()));
        dreamRepository.save(dream);
    }

    public void viewsDream(Long dreamId) {
        Dream dream = dreamRepository.findById(dreamId).orElseThrow();
        dream.setViews(dream.getViews() + 1);
        dreamRepository.save(dream);
    }

    public void updateDream(Long id, String title, String description, String status) {
        Dream dream = dreamRepository.findById(id).orElseThrow();
        dream.setDreamTitle(title);
        dream.setDreamDescription(description);
        dream.setDreamStatus(DreamStatus.valueOf(status.toUpperCase()));
        dreamRepository.save(dream);
    }

    public List<DreamDto> searchDreamsByHashtag(String hashtag) {
        List<Dream> dreams = dreamRepository.findByHashtagsContaining(hashtag);
        List<DreamDto> dreamDtos = new ArrayList<>();
        for (Dream dream : dreams) {
            DreamDto dreamDto = dreamMapper.mapEntityToDreamDto(dream);
            dreamDtos.add(dreamDto);
        }
        return dreamDtos;
    }

    public void deleteDreamById(Long id) {
        dreamRepository.deleteById(id);
    }

    public List<DreamDto> getTop3DreamsByLikes() {
        List<Dream> top3DreamsByLikes = dreamRepository.findTop3ByOrderByLikesDesc();
        return top3DreamsByLikes.stream()
                .map(dreamMapper::mapEntityToDreamDto)
                .collect(Collectors.toList());
    }
}
