package sp.dit.jad.cleaning_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class BookingStatisticsDTO {
    private long totalBookings;
    private long activeBookings;
    private long completedBookings;
    private long cancelledBookings;
    private BigDecimal totalRevenue;
    private Map<LocalDate, BigDecimal> dailyRevenue;
    private Map<String, Integer> bookingsByPostalCode;
    private Map<String, BookingServiceStats> serviceStatistics;

    // Inner class for service-specific statistics
    public static class BookingServiceStats {
        private long bookingCount;
        private BigDecimal totalRevenue;
        private double averageRating;

        public BookingServiceStats(long bookingCount, BigDecimal totalRevenue, double averageRating) {
            this.bookingCount = bookingCount;
            this.totalRevenue = totalRevenue;
            this.averageRating = averageRating;
        }

        // Getters
        public long getBookingCount() { return bookingCount; }
        public BigDecimal getTotalRevenue() { return totalRevenue; }
        public double getAverageRating() { return averageRating; }
    }

    // Getters and Setters
    public long getTotalBookings() { return totalBookings; }
    public void setTotalBookings(long totalBookings) { this.totalBookings = totalBookings; }

    public long getActiveBookings() { return activeBookings; }
    public void setActiveBookings(long activeBookings) { this.activeBookings = activeBookings; }

    public long getCompletedBookings() { return completedBookings; }
    public void setCompletedBookings(long completedBookings) { this.completedBookings = completedBookings; }

    public long getCancelledBookings() { return cancelledBookings; }
    public void setCancelledBookings(long cancelledBookings) { this.cancelledBookings = cancelledBookings; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public Map<LocalDate, BigDecimal> getDailyRevenue() { return dailyRevenue; }
    public void setDailyRevenue(Map<LocalDate, BigDecimal> dailyRevenue) { this.dailyRevenue = dailyRevenue; }

    public Map<String, Integer> getBookingsByPostalCode() { return bookingsByPostalCode; }
    public void setBookingsByPostalCode(Map<String, Integer> bookingsByPostalCode) { 
        this.bookingsByPostalCode = bookingsByPostalCode; 
    }

    public Map<String, BookingServiceStats> getServiceStatistics() { return serviceStatistics; }
    public void setServiceStatistics(Map<String, BookingServiceStats> serviceStatistics) { 
        this.serviceStatistics = serviceStatistics; 
    }

    // Convenience methods for calculating percentages
    public double getCompletionRate() {
        return totalBookings == 0 ? 0 : (double) completedBookings / totalBookings * 100;
    }

    public double getCancellationRate() {
        return totalBookings == 0 ? 0 : (double) cancelledBookings / totalBookings * 100;
    }
}