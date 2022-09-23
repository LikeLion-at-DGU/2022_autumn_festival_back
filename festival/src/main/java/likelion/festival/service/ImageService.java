package likelion.festival.service;

import likelion.festival.dto.ImageDto;
import likelion.festival.entitiy.Image;
import likelion.festival.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public Long saveImage(ImageDto imageDto) {
        return imageRepository.save(imageDto.toEntity()).getId();
    }


    public ImageDto getImage(Long id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (!imageOptional.isPresent()) {
            throw new EntityNotFoundException("해당 공지사항이 없습니다");
        }
        Image image = imageOptional.get();

        ImageDto imageDto = ImageDto.builder()
                .id(id)
                .originFileName(image.getOriginFileName())
                .serverFileName(image.getServerFileName())
                .storedFilePath(image.getStoredFilePath())
                .build();
        return imageDto;
    }


}