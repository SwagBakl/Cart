package Content.controller;

import Content.entity.ElectronicCharactsName;
import Content.entity.ElectronicCharactsValue;
import Content.entity.Player;
import Content.entity.PlayerChars;
import Content.repository.ElectronicCharsNameRepository;
import Content.repository.ElectronicCharsValueRepository;
import Content.repository.PlayerCharsRepository;
import Content.repository.PlayerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PlayerCharsController {

    final private PlayerRepository playerRepository;
    final private PlayerCharsRepository playerCharsRepository;
    final private ElectronicCharsNameRepository electronicCharsNameRepository;
    final private ElectronicCharsValueRepository electronicCharsValueRepository;

    public PlayerCharsController(PlayerRepository playerRepository, PlayerCharsRepository playerCharsRepository,
                             ElectronicCharsNameRepository electronicCharsNameRepository,
                             ElectronicCharsValueRepository electronicCharsValueRepository) {
        this.playerRepository = playerRepository;
        this.playerCharsRepository = playerCharsRepository;
        this.electronicCharsNameRepository = electronicCharsNameRepository;
        this.electronicCharsValueRepository = electronicCharsValueRepository;
    }

    @GetMapping("player/{id}/characts")
    public String playerCharList(@PathVariable Long id, Model model) throws Exception{

        Player player = playerRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        List<ElectronicCharactsName> elCharList = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elCharValueList = electronicCharsValueRepository.findAll();
        List<PlayerChars> playerChars = player.getPlayerChars();
        model.addAttribute("model", player.getModel());
        model.addAttribute("playerChars", playerChars);
        model.addAttribute("elCharList", elCharList);
        model.addAttribute("elCharValueList", elCharValueList);
        return "player/playerCharsList";
    }

    @PostMapping("player/{id}/characts")
    public String addChar(@RequestParam Long id,
                          @RequestParam String name,
                          @RequestParam String value,
                          Model model) throws Exception {
        Player player = playerRepository.findById(id).orElseThrow(() -> new Exception("PostId " + id + " not found"));
        PlayerChars playerCharas = new PlayerChars(name, value, player);
        playerCharsRepository.save(playerCharas);
        List<PlayerChars> playerChars = player.getPlayerChars();
        model.addAttribute("playerChars", playerChars);
        return "redirect:/player/{id}/characts";
    }

    @GetMapping("/player/charact/{playerChars}")
    public String editCharact(@PathVariable PlayerChars playerChars, Model model){
        List<ElectronicCharactsName> elcharsName = electronicCharsNameRepository.findAll();
        List<ElectronicCharactsValue> elcharsValue = electronicCharsValueRepository.findAll();
        model.addAttribute("elcharsName", elcharsName);
        model.addAttribute("elcharsValue", elcharsValue);
        model.addAttribute("playerChars", playerChars);
        return "player/editPlayerChar";
    }

    //HERE IS A PROBLEM
    @PostMapping("player/characts/{id}/save") //OR HERE
    public String charSave(@RequestParam String name,
                           @RequestParam String value,
                           @RequestParam("id") PlayerChars playerChars,
                           Model model){
        playerChars.setName(name);
        playerChars.setValue(value);
        playerCharsRepository.save(playerChars);
        return "redirect:/player/{id}/characts"; //HERE
    }

    @GetMapping("player/charact/delete/{id}")
    public String deletePc(@PathVariable("id") PlayerChars playerChars){
        playerCharsRepository.delete(playerChars);
        return "redirect:/player/"+ playerChars.getPlayer().getId() +"/characts";
    }

}
