package ac.tz.suza.marinelink_portal.repositories;

import ac.tz.suza.marinelink_portal.models.Transactions;
import ac.tz.suza.marinelink_portal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    // Fisher transactions
    List<Transactions> findByFisher(User fisher);

    // Buyer transactions
    List<Transactions> findByBuyer(User buyer);

    // ============================
    // MONTHLY ANALYTICS
    // ============================

    @Query("SELECT SUM(t.totalPrice) FROM Transactions t WHERE t.fisher.id = :fisherId AND MONTH(t.date) = :month")
    Double getMonthlyRevenue(Long fisherId, int month);

    @Query("SELECT COUNT(t) FROM Transactions t WHERE t.buyer.id = :buyerId AND MONTH(t.date) = :month")
    Long getMonthlyOrders(Long buyerId, int month);

    @Query("SELECT COUNT(t) FROM Transactions t WHERE MONTH(t.date) = :month")
    Long countMonthlyTransactions(int month);

    @Query("SELECT SUM(t.totalPrice) FROM Transactions t WHERE MONTH(t.date) = :month")
    Double sumRevenueByMonth(int month);

    @Query("SELECT SUM(t.totalPrice) FROM Transactions t")
    Double sumAllRevenue();

    // ============================
    // STATUS BREAKDOWN
    // ============================

    @Query("SELECT COUNT(t) FROM Transactions t WHERE t.status = 'PENDING'")
    Long countPending();

    @Query("SELECT COUNT(t) FROM Transactions t WHERE t.status = 'COMPLETED'")
    Long countCompleted();

    @Query("SELECT COUNT(t) FROM Transactions t WHERE t.fisher.id = :fisherId AND t.status = 'PENDING'")
    Long countPendingByFisher(Long fisherId);

    @Query("SELECT COUNT(t) FROM Transactions t WHERE t.fisher.id = :fisherId AND t.status = 'COMPLETED'")
    Long countCompletedByFisher(Long fisherId);

    @Query("SELECT COUNT(t) FROM Transactions t WHERE t.buyer.id = :buyerId AND t.status = 'PENDING'")
    Long countPendingByBuyer(Long buyerId);

    @Query("SELECT COUNT(t) FROM Transactions t WHERE t.buyer.id = :buyerId AND t.status = 'COMPLETED'")
    Long countCompletedByBuyer(Long buyerId);

    // ============================
    // BUYER SPENDING
    // ============================

    @Query("SELECT SUM(t.totalPrice) FROM Transactions t WHERE t.buyer.id = :buyerId AND MONTH(t.date) = :month")
    Double getMonthlySpending(Long buyerId, int month);

    @Query("SELECT SUM(t.totalPrice) FROM Transactions t WHERE t.buyer.id = :buyerId")
    Double sumSpendingByBuyer(Long buyerId);

    // ============================
    // FISHER TOTALS
    // ============================

    @Query("SELECT COUNT(t) FROM Transactions t WHERE t.fisher.id = :fisherId")
    Long countByFisher(Long fisherId);

    @Query("SELECT SUM(t.totalPrice) FROM Transactions t WHERE t.fisher.id = :fisherId")
    Double sumRevenueByFisher(Long fisherId);

    // ============================
    // BUYER TOTALS
    // ============================

    @Query("SELECT COUNT(t) FROM Transactions t WHERE t.buyer.id = :buyerId")
    Long countByBuyer(Long buyerId);
}
