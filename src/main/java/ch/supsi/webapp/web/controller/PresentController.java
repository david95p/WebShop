package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.model.Bid;
import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.service.*;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PresentController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private CategoryService catService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BidService bidService;

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("items", itemService.getAll());
        model.addAttribute("home", true);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping(value="/item/")
    public String searchItem(Model model) {
        model.addAttribute("items", itemService.getAll());
        return "itemDetails";
    }

    @GetMapping(value="/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping(value="/register")
    public String createUser(Model model, @ModelAttribute User user){
        BCryptPasswordEncoder crypto = new BCryptPasswordEncoder();
        user.setPassword(crypto.encode(user.getPassword()));
        user.setRole(roleService.findById("ROLE_USER"));
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping(value="/item/{id}")
    public String seeItem(Model model, @PathVariable int id) {
        User myUser = null;
        if ( !SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(String.class) ){
            org.springframework.security.core.userdetails.User  user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             myUser= userService.findById(user.getUsername());

        }
        model.addAttribute("myUser", myUser);
        model.addAttribute("items", itemService.getAll());
        model.addAttribute("item", itemService.findById(id));
        return "itemDetails";
    }

    @GetMapping(value="/item/new")
    public String addNew(Model model) {
        model.addAttribute("label", "Add");
        model.addAttribute("title", "Add item");
        model.addAttribute("item", new Item());
        model.addAttribute("cats", catService.getAll());
        model.addAttribute("types", Item.AdType.class.getEnumConstants());
        model.addAttribute("users", userService.getAll());
        model.addAttribute("newItem", true);
        return "createItemForm";
    }

    @PostMapping(value="/item/new")
    public String createItem(Model model, @ModelAttribute Item item, @RequestParam("imageFile")MultipartFile imageFile) throws IOException {
        org.springframework.security.core.userdetails.User  user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        item.setDate(new Date());
        item.setAuthor(userService.findById(user.getUsername()));
        item.setCategory(catService.findById(item.getCategory().getCatName()));
        try {
            if ( !imageFile.isEmpty() && imageFile.getSize()<1048576)
                item.setImage(imageFile.getBytes());
            else
                item.setImage(itemService.setEmptyImage());

        } catch (IOException e) {
            e.printStackTrace();
        }
        itemService.save(item);
        return "redirect:/";
    }

    @GetMapping(value = "/item/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] image(@PathVariable int id) {
        Item item = itemService.findById(id);
        return item.getImage();
    }

    @GetMapping(value = "/icons/{iconName}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] icons(@PathVariable String iconName) throws IOException {
        if (iconName.equals("search.png"))
            return FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/icons/search.png"));
        else if (iconName.equals("edit.png"))
            return FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/icons/edit.png"));
        else if (iconName.equals("delete.png"))
            return FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/icons/delete.png"));
        else if (iconName.equals("sell.png"))
            return FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/icons/sell.png"));
        else if (iconName.equals("NotFavorite.png"))
            return FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/icons/NotFavorite.png"));
        else if (iconName.equals("Favorite.png"))
            return FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/icons/Favorite.png"));
        else if (iconName.equals("Bid.png"))
            return FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/icons/Bid.png"));
        else if (iconName.equals("OpenAuction.png"))
            return FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/icons/OpenAuction.png"));
        else if (iconName.equals("Sold.png"))
            return FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/icons/Sold.png"));
        return null;
    }


    @GetMapping(value="/item/{id}/edit")
    public String editViewItem(Model model, @PathVariable int id) {
        model.addAttribute("label", "Save");
        model.addAttribute("title", "Edit item");
        model.addAttribute("item", itemService.findById(id));
        model.addAttribute("cats", catService.getAll());
        model.addAttribute("types", Item.AdType.class.getEnumConstants());
        model.addAttribute("users", userService.getAll());
        return "createItemForm";
    }



    @PostMapping(value="/item/{id}/edit")
    public String editItem(@PathVariable int id, @ModelAttribute Item item, @RequestParam("imageFile")MultipartFile imageFile){
        org.springframework.security.core.userdetails.User  user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Item oldItem = itemService.findById(id);
        try {
            if ( !imageFile.isEmpty() && imageFile.getSize() < 10485760)
                item.setImage(imageFile.getBytes());
            else
                item.setImage(oldItem.getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        item.setAuthor(userService.findById(user.getUsername()));
        item.setItemId(id);
        item.setDate(new Date());
        itemService.save(item);
        return "redirect:/";
    }

    @GetMapping(value="/item/{id}/openauction")
    public String openAuction(Model model, @PathVariable int id) {
        model.addAttribute("bid", new Bid());
        model.addAttribute("item", itemService.findById(id));
        return "auction";
    }

    @PostMapping(value="/item/{id}/openauction")
    public String createBid(@PathVariable int id, @ModelAttribute Bid bid){
        org.springframework.security.core.userdetails.User  user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bid.setLastBidder(userService.findById(user.getUsername()));
        bid.setDate();
        bid.setOpen(true);
        Item item = itemService.findById(id);
        bidService.save(bid);
        item.setBid(bid);
        itemService.save(item);
        return "redirect:/item/"+id;
    }

    @GetMapping(value="/item/{id}/closeauction")
    public String closeAuction(Model model, @PathVariable int id) {
        itemService.findById(id).getBid().setOpen(false);
        return "redirect:/item/"+id;
    }

    @GetMapping(value="/item/{id}/bid")
    public String bidAuction(Model model, @PathVariable int id) {
        itemService.findById(id).getBid().yep();
        return "redirect:/item/"+id;
    }

    @GetMapping(value="/item/{id}/delete")
    public String deleteItem(@PathVariable int id) {
        itemService.delete(itemService.findById(id));
        return "redirect:/";
    }

    @GetMapping(value="/item/search")
    @ResponseBody
    public List<Item> searchInItem(@RequestParam String q) {
        List<Item > filteredIt = new ArrayList<>();
        if (q.equals(""))
            filteredIt.addAll(itemService.getAll());
        else {
            itemService.getAll().forEach(i-> {
                if(i.getTitle().toUpperCase().contains(q.toUpperCase()) ||
                        i.getCategory().getCatName().toUpperCase().contains(q.toUpperCase()) ||
                        i.getDescription().toUpperCase().contains(q.toUpperCase())){
                    filteredIt.add(i);
                }
            });
        }
        return filteredIt;  
    }

    @GetMapping(value="/favorites")
    public String getFavorites(Model model) {
        org.springframework.security.core.userdetails.User  user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User myUser = userService.findById(user.getUsername());
        String favs = myUser.getFavorites();
        ArrayList<Item> favItems= new ArrayList<>();
        if (!favs.equals("")){
            String [] favsarr= favs.split(",");

            for (int i = 0; i < favsarr.length; i++) {
                if(!favsarr[i].equals(""))
                    favItems.add(itemService.findById(Integer.parseInt(favsarr[i])));
            }
        }


        model.addAttribute("items", favItems);
        model.addAttribute("myUser", myUser);
        return "favoriteItems";
    }

    @GetMapping(value="/favorites/{id}/add")
    public String addToFavs(Model model, @PathVariable int id) {
        org.springframework.security.core.userdetails.User  user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User myUser = userService.findById(user.getUsername());
        if (!myUser.isFavorite(id)){
            myUser.addFavorite(id);
            userService.save(myUser);
        }
        model.addAttribute("item", itemService.findById(id));
        return "redirect:/item/"+id;
    }

    @GetMapping(value="/favorites/{id}/remove")
    public String removeFromFavs(Model model, @PathVariable int id) {
        org.springframework.security.core.userdetails.User  user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User myUser = userService.findById(user.getUsername());
        if (myUser.isFavorite(id)){
            myUser.removeFavorite(id);
            userService.save(myUser);
        }
        model.addAttribute("item", itemService.findById(id));
        return "redirect:/item/"+id;
    }

}
