package likelion.festival.service;

import likelion.festival.dto.ImageDto;
import likelion.festival.entitiy.Image;
import likelion.festival.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public Long saveImage(ImageDto imageDto) {
        return imageRepository.save(imageDto.toEntity()).getId();
    }

    @Transactional
    public ImageDto getImage(Long id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (!imageOptional.isPresent()) {
            throw new EntityNotFoundException("해당 공지사항이 없습니다");
        }
        Image image = imageOptional.get();

        ImageDto imageDto = ImageDto.builder()
                .id(id)
                .origin_file_name(image.getOrigin_file_name())
                .server_file_name(image.getServer_file_name())
                .stored_file_path(image.getStored_file_path())
                .build();
        return imageDto;
    }


}