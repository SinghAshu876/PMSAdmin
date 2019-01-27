package com.pms.entity;

/**
 * 
 * @author ashutosh.tct@gmail.com
 *
 */
public class ChannelDetails implements Comparable<ChannelDetails> {

	private Integer channelId;
	private String channelName;
	private Double channelPrice;
	private String channelType;

	public Double getChannelPrice() {
		return channelPrice;
	}

	public void setChannelPrice(Double channelPrice) {
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

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	@Override
	public int compareTo(ChannelDetails channelDetails) {
		return this.channelId - channelDetails.getChannelId();
	}

	@Override
	public String toString() {
		return channelName + "(" + channelPrice + ")" +"--"+channelType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channelId == null) ? 0 : channelId.hashCode());
		result = prime * result + ((channelName == null) ? 0 : channelName.hashCode());
		result = prime * result + ((channelPrice == null) ? 0 : channelPrice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChannelDetails other = (ChannelDetails) obj;
		if (channelId == null) {
			if (other.channelId != null)
				return false;
		} else if (!channelId.equals(other.channelId))
			return false;
		if (channelName == null) {
			if (other.channelName != null)
				return false;
		} else if (!channelName.equals(other.channelName))
			return false;
		if (channelPrice == null) {
			if (other.channelPrice != null)
				return false;
		} else if (!channelPrice.equals(other.channelPrice))
			return false;
		return true;
	}

}
