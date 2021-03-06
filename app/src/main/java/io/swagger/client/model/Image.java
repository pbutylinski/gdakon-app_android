package io.swagger.client.model;

import io.swagger.client.model.EntityBase;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;


@ApiModel(description = "")
public class Image extends EntityBase {
  
  @SerializedName("Url")
  private String url = null;
  @SerializedName("Title")
  private String title = null;
  @SerializedName("Width")
  private Integer width = null;
  @SerializedName("Height")
  private Integer height = null;
  @SerializedName("FileSizeInBytes")
  private Integer fileSizeInBytes = null;
  @SerializedName("MimeType")
  private String mimeType = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getWidth() {
    return width;
  }
  public void setWidth(Integer width) {
    this.width = width;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getHeight() {
    return height;
  }
  public void setHeight(Integer height) {
    this.height = height;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getFileSizeInBytes() {
    return fileSizeInBytes;
  }
  public void setFileSizeInBytes(Integer fileSizeInBytes) {
    this.fileSizeInBytes = fileSizeInBytes;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getMimeType() {
    return mimeType;
  }
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Image image = (Image) o;
    return (url == null ? image.url == null : url.equals(image.url)) &&
        (title == null ? image.title == null : title.equals(image.title)) &&
        (width == null ? image.width == null : width.equals(image.width)) &&
        (height == null ? image.height == null : height.equals(image.height)) &&
        (fileSizeInBytes == null ? image.fileSizeInBytes == null : fileSizeInBytes.equals(image.fileSizeInBytes)) &&
        (mimeType == null ? image.mimeType == null : mimeType.equals(image.mimeType));
  }

  @Override 
  public int hashCode() {
    int result = 17;
    result = 31 * result + (url == null ? 0: url.hashCode());
    result = 31 * result + (title == null ? 0: title.hashCode());
    result = 31 * result + (width == null ? 0: width.hashCode());
    result = 31 * result + (height == null ? 0: height.hashCode());
    result = 31 * result + (fileSizeInBytes == null ? 0: fileSizeInBytes.hashCode());
    result = 31 * result + (mimeType == null ? 0: mimeType.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Image {\n");
    sb.append("  " + super.toString()).append("\n");
    sb.append("  url: ").append(url).append("\n");
    sb.append("  title: ").append(title).append("\n");
    sb.append("  width: ").append(width).append("\n");
    sb.append("  height: ").append(height).append("\n");
    sb.append("  fileSizeInBytes: ").append(fileSizeInBytes).append("\n");
    sb.append("  mimeType: ").append(mimeType).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
