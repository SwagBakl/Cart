package Content.controller;

import Content.entity.ElectronicCharactsName;
import Content.entity.ElectronicCharactsValue;
import Content.entity.HomeTheater;
import Content.entity.HomeTheaterChars;
import Content.repository.ElectronicCharsNameRepository;
import Content.repository.ElectronicCharsValueRepository;
import Content.repository.HomeTheaterCharsRepository;
import Content.repository.HomeTheaterRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeTheaterCharsController {

    final private HomeTheaterRepository htRepository;
    final private HomeTheaterCharsRepository htCharsRepository;
    final private ElectronicCharsNameRepository electronicCharsNameRepository;
    final private ElectronicCharsValueRepository electronicCharsValueRepository;

    public HomeTheaterCharsController(HomeTheaterRepository htRepository,
                              HomeTheaterCharsRepository htCharsRepository,
                              ElectronicCharsNameRepository electronicCharsNameRepository,
                              ElectronicCharsValueRepository electronicCharsValueRepository) {
        this.htRepository = htRepository;
        this.htCharsRepository = htCharsRepository;
        this.electronicCharsNameRepository = electronicCharsNameRepository;
        this.electronicCharsValueRepository = electronicCharsValueRepository;
    }

    @GetMapping("ht/{id}/characts")
    public String tabCharList(@PathVariable Long id, Model model) throws Exception{

        HomeTheater ht = htRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        List<ElectronicCharactsName> elCharList = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elCharValueList = electronicCharsValueRepository.findAll();
        List<HomeTheaterChars> htChars = ht.getCharacts();
        model.addAttribute("model", ht.getModel());
        model.addAttribute("htChars", htChars);
        model.addAttribute("elCharList", elCharList);
        model.addAttribute("elCharValueList", elCharValueList);
        return "hometheater/HTCharList";
    }

    @PostMapping("ht/{id}/characts")
    public String addChar(@RequestParam Long id,
                          @RequestParam String name,
                          @RequestParam String value,
                          Model model) throws Exception {
        HomeTheater ht = htRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        HomeTheaterChars htCharacts = new HomeTheaterChars(name, value, ht);
        htCharsRepository.save(htCharacts);
        List<HomeTheaterChars> htChars = ht.getCharacts();
        model.addAttribute("htChars", htChars);
        return "redirect:/ht/{id}/characts";
    }

    @GetMapping("/ht/{htChars}")
    public String editCharact(@PathVariable HomeTheaterChars htChars, Model model){
        List<ElectronicCharactsName> elNameList = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elValueList = electronicCharsValueRepository.findAll();
        model.addAttribute("elNameList", elNameList);
        model.addAttribute("elValueList", elValueList);
        model.addAttribute("htChars", htChars);
        return "hometheater/editHTChar";
    }

    //HERE IS A PROBLEM
    @PostMapping("ht/characts/{id}/save")
    public String charSave(@RequestParam String name,
                           @RequestParam String value,
                           @RequestParam("id") HomeTheaterChars htChars,
                           Model model){
        htChars.setName(name);
        htChars.setValue(value);
        htCharsRepository.save(htChars);
        return "redirect:/ht/" + htChars.getHomeTheater().getId() + "/characts";
    }

    @GetMapping("/ht/characts/{id}/delete")
    public String deleteTab(@PathVariable("id") HomeTheaterChars htChars){
        htCharsRepository.delete(htChars);

        return "redirect:/ht/"+ htChars.getHomeTheater().getId() +"/characts";
    }

}
