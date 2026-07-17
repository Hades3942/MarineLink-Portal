package ac.tz.suza.marinelink_portal.Dto;


import lombok.Data;

@Data
public class CreateListing {
    private String species;
    private String location;
    private double weight;
    private double pricePerKg;
    private Long fisherId;
}

