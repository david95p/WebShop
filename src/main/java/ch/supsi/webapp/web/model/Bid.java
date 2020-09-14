package ch.supsi.webapp.web.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Bid {
    @Id
    private int id;

    @NotNull
    private boolean open;

    @NotNull
    private  int basePrice;

    @NotNull
    private int bidAmount;

    @NotNull
    private int finalPrice;

    private Date date;

    @OneToOne
    private User lastBidder;

    public Bid() {
    }

    public Bid (Boolean open, int basePrice, int bidAmount){
        this.open=open;
        this.basePrice=basePrice;
        this.bidAmount=bidAmount;
        this.date = new Date();
        this.finalPrice=basePrice;
        this.lastBidder=  null;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getBasePrice() {
        return basePrice;
    }


    public int getBidAmount() {
        return bidAmount;
    }


    public int getFinalPrice() {
        return finalPrice;
    }


    public Date getDate() {
        return date;
    }

    public void setDate() {
        this.date = new Date();
    }

    public User getLastBidder() {
        return lastBidder;
    }

    public void setLastBidder(User lastBidder) {
        this.lastBidder = lastBidder;
    }

    public void yep (){
        finalPrice+=bidAmount;
    }
}
