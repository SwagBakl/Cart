package Content.controller;

import Content.entity.ElectronicCharactsName;
import Content.entity.ElectronicCharactsValue;
import Content.entity.Tab;
import Content.entity.TabChars;
import Content.repository.ElectronicCharsNameRepository;
import Content.repository.ElectronicCharsValueRepository;
import Content.repository.TabCharsRepository;
import Content.repository.TabRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TabCharsController {

    final private TabRepository tabRepository;
    final private TabCharsRepository tabCharsRepository;
    final private ElectronicCharsNameRepository electronicCharsNameRepository;
    final private ElectronicCharsValueRepository electronicCharsValueRepository;

    public TabCharsController(TabRepository tabRepository,
                              TabCharsRepository tabCharsRepository,
                              ElectronicCharsNameRepository electronicCharsNameRepository,
                              ElectronicCharsValueRepository electronicCharsValueRepository) {
        this.tabRepository = tabRepository;
        this.tabCharsRepository = tabCharsRepository;
        this.electronicCharsNameRepository = electronicCharsNameRepository;
        this.electronicCharsValueRepository = electronicCharsValueRepository;
    }

    @GetMapping("tab/{id}/characts")
    public String tabCharList(@PathVariable Long id, Model model, Tab tab_id) throws Exception{

        Tab tab = tabRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        List<ElectronicCharactsName> elCharList = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elCharValueList = electronicCharsValueRepository.findAll();
        List<TabChars> tabChars = tab.getCharacts();
        model.addAttribute("model", tab.getModel());
        model.addAttribute("tabChars", tabChars);
        model.addAttribute("elCharList", elCharList);
        model.addAttribute("elCharValueList", elCharValueList);
        model.addAttribute("tab", tab);
        return "tab/tabCharList";
    }

    @PostMapping("tab/{id}/characts")
    public String addChar(@RequestParam Long id,
                          @RequestParam String name,
                          @RequestParam String value,
                          Model model) throws Exception {
        Tab tab = tabRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        TabChars tabCharacts = new TabChars(name, value, tab);
        tabCharsRepository.save(tabCharacts);
        List<TabChars> tabChars = tab.getCharacts();
        model.addAttribute("tabChars", tabChars);
        return "redirect:/tab/{id}/characts";
    }

    @GetMapping("/tabCharact/{tabChars}")
    public String editCharact(@PathVariable TabChars tabChars, Model model){
        List<ElectronicCharactsName> elNameList = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elValueList = electronicCharsValueRepository.findAll();
        model.addAttribute("elNameList", elNameList);
        model.addAttribute("elValueList", elValueList);
        model.addAttribute("tabChars", tabChars);
        return "tab/editTabChar";
    }

    //HERE IS A PROBLEM
    @PostMapping("tab/characts/{id}/save") //OR HERE
    public String charSave(@RequestParam String name,
                           @RequestParam String value,
                           @RequestParam("id") TabChars tabChars,
                           Model model){
        tabChars.setName(name);
        tabChars.setValue(value);
        tabCharsRepository.save(tabChars);
        return "redirect:/tab/{id}/characts"; //HERE
    }

    @GetMapping("/tab/characts/{id}/delete")
    public String deleteTab(@PathVariable("id") TabChars tabChars){
        tabCharsRepository.delete(tabChars);

        return "redirect:/tab/"+ tabChars.getTab().getId() +"/characts";
    }

}
