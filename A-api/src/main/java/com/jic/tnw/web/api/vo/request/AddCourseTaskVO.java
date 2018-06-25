package com.jic.tnw.web.api.vo.request;


/**
 * @author lee5hx
 */
public class AddCourseTaskVO {
    private String mediaType;
    private String finishDetail;
    private String title;
    private String content;
    private Boolean optional;

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getFinishDetail() {
        return finishDetail;
    }

    public void setFinishDetail(String finishDetail) {
        this.finishDetail = finishDetail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    @Override
    public String toString() {
        return "AddCourseTaskVO{" +
                "mediaType='" + mediaType + '\'' +
                ", finishDetail='" + finishDetail + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
