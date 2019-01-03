package com.pms.entity;
/**
 * 
 * @author ashutosh.tct@gmail.com
 *
 */
public class ChannelDetails {

	private Integer channelId;
	private String channelName;
	private Integer channelPrice;

	public Integer getChannelPrice() {
		return channelPrice;
	}

	public void setChannelPrice(Integer channelPrice) {
		this.channelPrice = channelPrice;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	

}
