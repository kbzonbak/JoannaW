package bbr.b2b.logistic.buyorders.data.dto;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class OrderDetailReportResultDTO extends BaseResultDTO {

	
	private HeaderOrderDetailDTO headerdetail;
	
	private OrderProductDetailDTO[] productdetail;
	private OrderPreDeliveryDetailDTO[] predeliverydetail;
	
	private PageInfoDTO pageInfoproduct;
	private PageInfoDTO pageInfopredelivery;
	
	private TotalProductsDTO totalproducts;
	private TotalPredeliveryDTO totalpredelivery;
	
	public HeaderOrderDetailDTO getHeaderdetail() {
		return headerdetail;
	}
	public void setHeaderdetail(HeaderOrderDetailDTO headerdetail) {
		this.headerdetail = headerdetail;
	}
	public OrderProductDetailDTO[] getProductdetail() {
		return productdetail;
	}
	public void setProductdetail(OrderProductDetailDTO[] productdetail) {
		this.productdetail = productdetail;
	}
	public OrderPreDeliveryDetailDTO[] getPredeliverydetail() {
		return predeliverydetail;
	}
	public void setPredeliverydetail(OrderPreDeliveryDetailDTO[] predeliverydetail) {
		this.predeliverydetail = predeliverydetail;
	}
	public PageInfoDTO getPageInfoproduct() {
		return pageInfoproduct;
	}
	public void setPageInfoproduct(PageInfoDTO pageInfoproduct) {
		this.pageInfoproduct = pageInfoproduct;
	}
	public PageInfoDTO getPageInfopredelivery() {
		return pageInfopredelivery;
	}
	public void setPageInfopredelivery(PageInfoDTO pageInfopredelivery) {
		this.pageInfopredelivery = pageInfopredelivery;
	}
	public TotalProductsDTO getTotalproducts() {
		return totalproducts;
	}
	public void setTotalproducts(TotalProductsDTO totalproducts) {
		this.totalproducts = totalproducts;
	}
	public TotalPredeliveryDTO getTotalpredelivery() {
		return totalpredelivery;
	}
	public void setTotalpredelivery(TotalPredeliveryDTO totalpredelivery) {
		this.totalpredelivery = totalpredelivery;
	}
	
}
