package ch.supsi.webapp.web.service;

import ch.supsi.webapp.web.model.Category;
import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.Role;
import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.repository.ItemRepository;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RoleService roleService;

    public List<Item> getAll(){
        return itemRepository.findAll();
    }

    public Item findById (int id){
        return itemRepository.findById(id).orElse(null);
    }

    public Item save(Item item){
        return itemRepository.save(item);
    }
    public void delete (Item item){
        itemRepository.delete(item);
    }

    @PostConstruct
    public void init() throws IOException {
        BCryptPasswordEncoder crypto = new BCryptPasswordEncoder();
        if(userService.getAll().size() == 0) {
            roleService.save(new Role("ROLE_ADMIN"));
            roleService.save(new Role("ROLE_USER"));
            User admin = new User("admin", "adminFName", "adminLName", roleService.findById("ROLE_ADMIN"), crypto.encode("admin"),"");
            User def = new User("user", "userFName", "userLName", roleService.findById("ROLE_USER"),  crypto.encode("123456"), "");
            userService.save(admin);
            userService.save(def);
        }

        if(categoryService.getAll().size() == 0) {
            categoryService.save(new Category("Hobby"));
            categoryService.save(new Category("Cars"));
            categoryService.save(new Category("Sport"));
        }

        if(itemRepository.findAll().size() == 0) {

            Item item = new Item();
            item.setTitle("SLC 43 Amg");
            item.setDescription("Bellissima auto, divertente nel misto. Perfette condizioni. Airscarf,Parktronic,pelle...");
            item.setAuthor(userService.findById("admin"));
            item.setCategory(categoryService.findById("Sport"));
            item.setDate(new Date());
            item.setAdType(Item.AdType.OFFER);
            item.setImage(FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/templateImages/slc.jpg")));

            save(item);

            item = new Item();
            item.setTitle("Ferrari");
            item.setDescription("Un sacco di coppia");
            item.setAuthor(userService.findById("user"));
            item.setCategory(categoryService.findById("Cars"));
            item.setDate(new Date());
            item.setAdType(Item.AdType.REQUEST);
            item.setImage(FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/templateImages/ferrari.jpg")));

            save(item);

            item = new Item();
            item.setTitle("Dacia");
            item.setDescription("Meglio della ferrari");
            item.setAuthor(userService.findById("admin"));
            item.setCategory(categoryService.findById("Cars"));
            item.setDate(new Date());
            item.setAdType(Item.AdType.OFFER);
            item.setImage(FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/templateImages/dacia.jpg")));
            save(item);
        }
    }

    public byte[] setEmptyImage() throws IOException {
        return FileUtil.readAsByteArray(this.getClass().getResourceAsStream("/static/templateImages/noImage.png"));
    }

}
