package com.jt.common.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 为了实现跨系统调用调用对象的一致, 采用第三方的方式
 * 定义pojo对象
 * 采用第三方的方式定义pojo对象
 * @author Administrator
 *
 */

@Table(name="tb_item") //对应数据库的表名
@JsonIgnoreProperties(ignoreUnknown=true)//忽略位置属性 json转化时使用
public class Item extends BasePojo{
    @Id //定义主键 
    @GeneratedValue(strategy=GenerationType.IDENTITY) //定义主键自增
	private Long id; // 商品ID
	private String title; // 商品标题
	private String sellPoint; //商品卖点
	private Long price; //商品价格
	private Integer num;//商品数量
	private String barcode;//条形码
	private String image;//商品图片
	private Long cid; //商品分类
	private Integer status; //商品状态码
	
	//为了实现图片获取第一张 添加
	public String[] getImages() {
		return image.split(",");
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Item [id=" + id + ", title=" + title + ", sellPoint=" + sellPoint + ", price=" + price + ", num=" + num
				+ ", barcode=" + barcode + ", image=" + image + ", cid=" + cid + ", status=" + status + "]";
	}
	
	
}
