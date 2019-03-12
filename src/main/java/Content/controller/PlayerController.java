package Content.controller;

import Content.entity.Player;
import Content.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class PlayerController {

    @Value("${upload.path}")
    private String uploadPath;

    final private PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping("player/playerList")
    public String tvList(@RequestParam(required = false, defaultValue = "") String filter, Model model,
                         @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable){
        Page<Player> playerList;
        if(filter != null && !filter.isEmpty()){
            playerList = playerRepository.findByModel(filter, pageable);

        }else {
            playerList = playerRepository.findAll(pageable);
        }
        model.addAttribute("url", "/player/playerList");
        model.addAttribute("playerList", playerList);
        model.addAttribute("filter", filter);
        return "player/playerList";
    }

    @PostMapping("player/playerList")
    public String addTv(@Valid Player player,
                        BindingResult bindingResult,
                        @RequestParam("file") MultipartFile file,
                        Model model) throws IOException {
        if (bindingResult.hasErrors() && file == null || file.getOriginalFilename().isEmpty()){
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
            String error = "Файл не выбран";
            model.addAttribute("error", error);
            model.mergeAttributes(errorsMap);
            return "/player/addPlayer";
        }else {
            if (file != null && !file.getOriginalFilename().isEmpty()){
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                player.setFilename(resultFilename);
            }
            playerRepository.save(player);}
        List<Player> playerList = playerRepository.findAll();
        model.addAttribute("playerList", playerList);
        return "redirect:/player/playerList";
    }

    @GetMapping("player/addPlayer")
    public String addTv(Model model){
        return "player/addPlayer";
    }

    @GetMapping("player/{player}")
    public String editTv(@PathVariable Player player, Model model){
        model.addAttribute("player", player);
        return "player/editPlayer";
    }

    @PostMapping("player/save")
    public String saveTv(@RequestParam String name,
                         @RequestParam Double price,
                         @RequestParam Integer quantity,
                         @RequestParam String description,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("id") Player player) throws IOException {
        player.setModel(name);
        player.setPrice(price);
        player.setQuantity(quantity);
        player.setDescription(description);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            player.setFilename(resultFilename);
        }
        playerRepository.save(player);
        return "redirect:/player/playerList";
    }

    @GetMapping("/player/delete/{id}")
    public String deletePc(@PathVariable("id") Player player){
        playerRepository.delete(player);
        return "redirect:/player/playerList";
    }

}
