package ac.tz.suza.marinelink_portal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ac.tz.suza.marinelink_portal.models.AnalyticsRecord;
import ac.tz.suza.marinelink_portal.models.Report;

interface ReportRepository extends JpaRepository<Report, Long> {}

public interface AnalyticsRepository extends JpaRepository<AnalyticsRecord, Long> {}
