package ch.supsi.webapp.web.service;

import ch.supsi.webapp.web.model.Bid;
import ch.supsi.webapp.web.model.Category;
import ch.supsi.webapp.web.repository.BidRepository;
import ch.supsi.webapp.web.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BidService {
    @Autowired
    private BidRepository bidRepository;

    public List<Bid> getAll(){
        return bidRepository.findAll();
    }

    public Bid findById (int id){
        return bidRepository.findById(id).orElse(null);
    }

    public Bid save(Bid myBid){
        return bidRepository.save(myBid);

    }
}
