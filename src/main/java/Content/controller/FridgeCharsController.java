package Content.controller;

import Content.entity.ElectronicCharactsName;
import Content.entity.ElectronicCharactsValue;
import Content.entity.Fridge;
import Content.entity.FridgeChars;
import Content.repository.ElectronicCharsNameRepository;
import Content.repository.ElectronicCharsValueRepository;
import Content.repository.FridgeCharsRepository;
import Content.repository.FridgeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FridgeCharsController {

    final private FridgeRepository fridgeRepository;
    final private FridgeCharsRepository fridgeCharsRepository;
    final private ElectronicCharsNameRepository electronicCharsNameRepository;
    final private ElectronicCharsValueRepository electronicCharsValueRepository;

    public FridgeCharsController(FridgeRepository fridgeRepository, FridgeCharsRepository fridgeCharsRepository,
                             ElectronicCharsNameRepository electronicCharsNameRepository,
                             ElectronicCharsValueRepository electronicCharsValueRepository) {
        this.fridgeRepository = fridgeRepository;
        this.fridgeCharsRepository = fridgeCharsRepository;
        this.electronicCharsNameRepository = electronicCharsNameRepository;
        this.electronicCharsValueRepository = electronicCharsValueRepository;
    }

    @GetMapping("fridge/{id}/characts")
    public String tvCharList(@PathVariable Long id, Model model) throws Exception{

        Fridge fridge = fridgeRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        List<ElectronicCharactsName> elCharList = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elCharValueList = electronicCharsValueRepository.findAll();
        List<FridgeChars> fridgeChars = fridge.getCharacts();
        model.addAttribute("model", fridge.getModel());
        model.addAttribute("fridgeChars", fridgeChars);
        model.addAttribute("elCharList", elCharList);
        model.addAttribute("elCharValueList", elCharValueList);
        return "fridge/fridgeCharsList";
    }

    @PostMapping("fridge/{id}/characts")
    public String addChar(@RequestParam Long id,
                          @RequestParam String name,
                          @RequestParam String value,
                          Model model) throws Exception {
        Fridge fridge = fridgeRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        FridgeChars fridgeCharas = new FridgeChars(name, value, fridge);
        fridgeCharsRepository.save(fridgeCharas);
        List<FridgeChars> fridgeChars = fridge.getCharacts();
        model.addAttribute("fridgeChars", fridgeChars);
        return "redirect:/fridge/{id}/characts";
    }

    @GetMapping("/fridge/charact/{fridgeChars}")
    public String editCharact(@PathVariable FridgeChars fridgeChars, Model model){
        List<ElectronicCharactsName> elcharsName = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elcharsValue = electronicCharsValueRepository.findAll();
        model.addAttribute("elcharsName", elcharsName);
        model.addAttribute("elcharsValue", elcharsValue);
        model.addAttribute("fridgeChars", fridgeChars);
        return "fridge/editFridgeChar";
    }

    //HERE IS A PROBLEM
    @PostMapping("fridge/characts/{id}/save") //OR HERE
    public String charSave(@RequestParam String name,
                           @RequestParam String value,
                           @RequestParam("id") FridgeChars fridgeChars,
                           Model model){
        fridgeChars.setName(name);
        fridgeChars.setValue(value);
        fridgeCharsRepository.save(fridgeChars);
        return "redirect:/fridge/{id}/characts";
    }

    @GetMapping("fridge/charact/delete/{id}")
    public String deletePc(@PathVariable("id") FridgeChars fridgeChars){
        fridgeCharsRepository.delete(fridgeChars);
        return "redirect:/fridge/"+ fridgeChars.getFridge().getId() +"/characts";
    }

}
