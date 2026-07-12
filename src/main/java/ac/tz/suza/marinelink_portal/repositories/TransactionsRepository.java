package ac.tz.suza.marinelink_portal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ac.tz.suza.marinelink_portal.models.Transactions;
import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    List<Transactions> findByBuyerId(Long buyerId);

    List<Transactions> findByFisherId(Long fisherId);
}
