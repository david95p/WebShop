package ch.supsi.webapp.web.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

@Entity
public class Item {
    public enum AdType { OFFER, REQUEST}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    @NotNull
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne (optional=false)
    @NotNull
    private User author;

    @ManyToOne
    @NotNull
    private Category category;

    private Date date;

    @Enumerated (EnumType.STRING)
    @NotNull
    private AdType adType;

    @Lob
    private byte [] image;

    @OneToOne
    private Bid bid;

    public Item() {}

    public Item(String title, String description, User author, Category category, Date date, AdType adType, byte[] image) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.category = category;
        this.date = date;
        this.adType = adType;
        this.image = image;
        this.bid= null;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String get4WDescription(){
        String shortDescr ="";
        String[] str = description.split(" ");
        int limit=4;
        if (str.length<4)
            limit=str.length;
        for (int i = 0; i < limit; i++) {
            shortDescr += " " + str[i];
        }
        if (str.length>4)
            shortDescr+=" ...";
        return shortDescr;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getParsedDate() {
        String pattern = "HH:mm d-MM-yyyy ";
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern, new Locale("en", "US"));
        String parsedDate = simpleDateFormat.format(date);
        return parsedDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AdType getAdType() {
        return adType;
    }

    public void setAdType(AdType adType) {
        this.adType = adType;
    }

    public String getImageAsString() {
        return Base64.getEncoder().encodeToString(image);
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}