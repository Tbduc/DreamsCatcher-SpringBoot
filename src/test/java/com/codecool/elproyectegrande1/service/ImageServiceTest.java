package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.entity.Dream;
import com.codecool.elproyectegrande1.entity.Image;
import com.codecool.elproyectegrande1.entity.Offer;
import com.codecool.elproyectegrande1.repository.DreamRepository;
import com.codecool.elproyectegrande1.repository.ImageRepository;
import com.codecool.elproyectegrande1.repository.OfferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ImageServiceTest {

    @Mock
    private OfferRepository offerRepository;
    @Mock
    private DreamRepository dreamRepository;
    @Mock
    private ImageRepository imageRepository;

    @Test
    void shouldUploadFile() throws IOException {
        MockMultipartFile file
                = new MockMultipartFile (
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        Dream dream = new Dream();
        Offer offer = new Offer();
        Image image = new Image();
        ImageService imageService = mock(ImageService.class);
        dream.setId(1L);
        offer.setId(1L);
        lenient().when(imageService.uploadImage(file, dream.getId().toString(), offer.getId().toString())).thenReturn(image);
        Assertions.assertNotNull(image);
    }
}