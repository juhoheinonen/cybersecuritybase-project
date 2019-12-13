package sec.project.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.GuestBookItem;
import sec.project.repository.GuestBookItemRepository;

@Controller()
@RequestMapping("guestbook")
public class GuestBookController {

    @Autowired
    private GuestBookItemRepository guestBookItemRepository;    
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String loadGuestBook(Model model) {
        List<GuestBookItem> guestBookItems = guestBookItemRepository.findAll();
        
        model.addAttribute("items", guestBookItems);
        
        return "guestbook";
    }
    
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String submitForm(Authentication authentication, @RequestParam String title, @RequestParam String content) {
        String username = authentication.getName();
        LocalDateTime now = LocalDateTime.now();

        GuestBookItem item = new GuestBookItem();
        item.setTitle(title);
        item.setCreatedOn(now);
        item.setContent(content);
        item.setUsername(username);
        
        guestBookItemRepository.save(item);
        return "redirect:/guestbook";
    }

}