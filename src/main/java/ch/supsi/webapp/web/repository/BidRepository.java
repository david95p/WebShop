package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid,Integer> {
}
