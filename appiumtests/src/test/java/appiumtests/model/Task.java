package appiumtests.model;

public class Task {
    private String id;
    private String project_id;
    private String section_id;
    private String content;
    private boolean completed;
    private String[] label_ids;
    private String order;
    private String priority;
    private String url;
    private String comment_count;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getProjectId() {
        return project_id;
    }
    
    public void setProjectId(String project_id) {
        this.project_id = project_id;
    }
    
    public String getSectionId() {
        return section_id;
    }
    
    public void setSectionId(String section_id) {
        this.section_id = section_id;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public String[] getLabelIds() {
        return label_ids;
    }
    
    public void setLabelIds(String[] label_ids) {
        this.label_ids = label_ids;
    }
    
    public String getOrder() {
        return order;
    }
    
    public void setOrder(String order) {
        this.order = order;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getCommentCount() {
        return comment_count;
    }
    
    public void setCommentCount(String comment_count) {
        this.comment_count = comment_count;
    }    
}
