package appiumtests.model;

public class Project {
    private String id;
    private String order;
    private String sync_id;
    private String name;
    private String color;
    private boolean shared;
    private boolean favorite;
    private String comment_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSyncId() {
        return sync_id;
    }

    public void setSyncId(String sync_id) {
        this.sync_id = sync_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getCommentCount() {
        return comment_count;
    }

    public void setCommentCount(String comment_count) {
        this.comment_count = comment_count;
    }
}
