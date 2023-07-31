package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.entity.*;
import com.codecool.elproyectegrande1.repository.*;
import com.codecool.elproyectegrande1.util.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepo;
    private final AvatarRepository avatarRepo;
    private final OfferRepository offerRepository;
    private final DreamRepository dreamRepository;

    private final UserRepository userRepository;

    public ImageService(ImageRepository imageRepo,
                        AvatarRepository avatarRepo, OfferRepository offerRepository,
                        DreamRepository dreamRepository, UserRepository userRepository) {
        this.imageRepo = imageRepo;
        this.avatarRepo = avatarRepo;
        this.offerRepository = offerRepository;
        this.dreamRepository = dreamRepository;
        this.userRepository = userRepository;
    }

    public Image uploadImage(MultipartFile file, String dreamId, String offerId) throws IOException {
        Long drId = null;
        Long ofId = null;
        if(dreamId != null)
            drId = Long.valueOf(dreamId);
        if(offerId != null)
            ofId = Long.valueOf(offerId);

        Image pImage = new Image();
        pImage.setName(file.getOriginalFilename());
        pImage.setType(file.getContentType());
        pImage.setImageData(ImageUtil.compressImage(file.getBytes()));
        Offer offer;
        Dream dream;

        if(offerId != null) {
            offer = offerRepository.findById(ofId).orElse(null);
            pImage.setOffer(offer);
        }
        if(dreamId != null) {
            dream = dreamRepository.findById(drId).orElse(null);
            pImage.setDream(dream);
        }
        Image savedImage = imageRepo.save(pImage);
        updateDreamAndOffer(drId, ofId, savedImage);
        return savedImage;
    }

    public byte[] downloadImage(String fileName){
        Optional<Image> imageData = imageRepo.findByName(fileName);
        return ImageUtil.decompressImage(imageData.get().getImageData());
    }

    public byte[] getImageById(Long id) {
        Image image = imageRepo.findImageByID(id);
        byte [] imageData = ImageUtil.decompressImage(image.getImageData());
        return imageData;
    }

    public void updateDreamAndOffer(Long dreamId, Long offerId, Image image) {
        Dream dream;
        Offer offer;
        if (dreamId != null) {
            dream = dreamRepository.findById(dreamId).orElse(null);
            if (!dream.equals(null)) {
                dream.setMainImage(image);
                dreamRepository.update(dream, dreamId);
            }
        }
        if (offerId != null) {
            offer = offerRepository.findById(offerId).orElse(null);
            if (!offer.equals(null)) {
                offer.setMainImage(image);
                offerRepository.update(offer, offerId);
            }
        }
    }

    public void updateProfileImage(MultipartFile file, Long userId) throws IOException {
        Avatar pAvatar = new Avatar();
        pAvatar.setName(file.getOriginalFilename());
        pAvatar.setType(file.getContentType());
        pAvatar.setImageData(ImageUtil.compressImage(file.getBytes()));
        Avatar savedImage = avatarRepo.save(pAvatar);
        User user = userRepository.findById(userId).orElse(null);
        user.setProfilePicture(savedImage);
        userRepository.update(user, userId);
    }
}
