package kumarworld.rahul.kumarbioseeds.model;

public class Varites {
    private String id,variety_name,image,details;

    public Varites(String id, String variety_name, String image, String details) {
        this.id = id;
        this.variety_name = variety_name;
        this.image = image;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVariety_name() {
        return variety_name;
    }

    public void setVariety_name(String variety_name) {
        this.variety_name = variety_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
