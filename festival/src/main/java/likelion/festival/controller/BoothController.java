package likelion.festival.controller;

import likelion.festival.dto.BoothDto;
import likelion.festival.dto.BoothFilterDto;
import likelion.festival.dto.ImageDto;
import likelion.festival.entitiy.Booth;
import likelion.festival.entitiy.BoothLocation;
import likelion.festival.service.BoothService;
import likelion.festival.service.ImageService;
import likelion.festival.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/booths")
@RestController
public class BoothController {

    private final BoothService boothService;
    private final ImageService imageService;

    @GetMapping(params = {"filter"})
    public List<BoothFilterDto> boothFilter(@RequestParam BoothLocation filter) {
        return boothService.boothFilter(filter);
    }

    @GetMapping("/top3")
    public List<BoothFilterDto> boothTopThree() {
        return boothService.boothTopThree();
    }

    @GetMapping(params = {"search"})
    public List<BoothFilterDto> boothSearch(@RequestParam String search) {
        return boothService.search(search);
    }

    @PostMapping()
    public Integer boothCreate(@RequestPart(value = "images",required = false) MultipartFile images, @RequestParam(value = "boothDto") BoothDto boothDto) {
        if (images==null){
            boothService.create(boothDto);
            return HttpStatus.OK.value();
        }
        try {
            String origFilename = images.getOriginalFilename();
            String servFilename = new MD5Generator(origFilename).toString();

            String savePath =System.getProperty("user.dir")+"/files";


            if (!new File(savePath).exists()){
                try {
                    new File(savePath).mkdir();
                }
                catch (Exception e){
                    e.getStackTrace();
                }
            }
            String imagePath = savePath + "/" + servFilename+".jpg";
            images.transferTo(new File(imagePath));

            ImageDto imageDto = new ImageDto();
            imageDto.setOrigin_file_name(origFilename);
            imageDto.setServer_file_name(servFilename);
            imageDto.setStored_file_path(imagePath);

            Long imageId = imageService.saveImage(imageDto);
            boothDto.setImageId(imageId);
            boothService.create(boothDto);
        }catch (Exception e){
            e.printStackTrace();
        }
        return HttpStatus.OK.value();
    }

    @GetMapping("{id}")
    public BoothDto boothRead(@PathVariable Long id) {
        return boothService.read(id);
    }

    @PutMapping("{id}")
    public Booth boothUpdate(@PathVariable Long id, @RequestBody BoothDto boothDto) {
        return boothService.update(id, boothDto);
    }

    @DeleteMapping("{id}")
    public Integer boothDelete(@PathVariable Long id) {
        return boothService.delete(id);
    }

    // TODO : like, menu, comment controller 추가하기

}
