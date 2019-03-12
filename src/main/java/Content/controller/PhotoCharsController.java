package Content.controller;

import Content.entity.ElectronicCharactsName;
import Content.entity.ElectronicCharactsValue;
import Content.entity.Photo;
import Content.entity.PhotoChars;
import Content.repository.ElectronicCharsNameRepository;
import Content.repository.ElectronicCharsValueRepository;
import Content.repository.PhotoCharsRepository;
import Content.repository.PhotoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PhotoCharsController {

    final private PhotoRepository photoRepository;
    final private PhotoCharsRepository photoCharsRepository;
    final private ElectronicCharsNameRepository electronicCharsNameRepository;
    final private ElectronicCharsValueRepository electronicCharsValueRepository;

    public PhotoCharsController(PhotoRepository photoRepository,
                              PhotoCharsRepository photoCharsRepository,
                              ElectronicCharsNameRepository electronicCharsNameRepository,
                              ElectronicCharsValueRepository electronicCharsValueRepository) {
        this.photoRepository = photoRepository;
        this.photoCharsRepository = photoCharsRepository;
        this.electronicCharsNameRepository = electronicCharsNameRepository;
        this.electronicCharsValueRepository = electronicCharsValueRepository;
    }

    @GetMapping("photo/{id}/characts")
    public String tabCharList(@PathVariable Long id, Model model, Photo photo_id) throws Exception{

        Photo photo = photoRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        List<ElectronicCharactsName> elCharList = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elCharValueList = electronicCharsValueRepository.findAll();
        List<PhotoChars> photoChars = photo.getCharacts();
        model.addAttribute("model", photo.getModel());
        model.addAttribute("photoChars", photoChars);
        model.addAttribute("elCharList", elCharList);
        model.addAttribute("elCharValueList", elCharValueList);
        model.addAttribute("tab_id", photo_id.getId());
        return "photo/photoCharList";
    }

    @PostMapping("photo/{id}/characts")
    public String addChar(@RequestParam Long id,
                          @RequestParam String name,
                          @RequestParam String value,
                          Model model) throws Exception {
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        PhotoChars photoCharacts = new PhotoChars(name, value, photo);
        photoCharsRepository.save(photoCharacts);
        List<PhotoChars> photoChars = photo.getCharacts();
        model.addAttribute("photoChars", photoChars);
        return "redirect:/photo/{id}/characts";
    }

    @GetMapping("/photoCharact/{photoChars}")
    public String editCharact(@PathVariable PhotoChars photoChars, Model model){
        List<ElectronicCharactsName> elNameList = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elValueList = electronicCharsValueRepository.findAll();
        model.addAttribute("elNameList", elNameList);
        model.addAttribute("elValueList", elValueList);
        model.addAttribute("photoChars", photoChars);
        return "photo/editPhotoChar";
    }

    //HERE IS A PROBLEM
    @PostMapping("photo/characts/{id}/save") //OR HERE
    public String charSave(@RequestParam String name,
                           @RequestParam String value,
                           @RequestParam("id") PhotoChars photoChars,
                           Model model){
        photoChars.setName(name);
        photoChars.setValue(value);
        photoCharsRepository.save(photoChars);
        return "redirect:/photo/{id}/characts"; //HERE
    }

    @GetMapping("/photo/characts/{id}/delete")
    public String deleteTab(@PathVariable("id") PhotoChars photoChars){
        photoCharsRepository.delete(photoChars);

        return "redirect:/photo/"+ photoChars.getPhoto().getId() +"/characts";
    }

}
