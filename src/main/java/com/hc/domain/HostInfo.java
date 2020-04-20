package com.hc.domain;

import com.hc.HostChanger;

import javax.swing.JButton;

public class HostInfo {
	private String address = "";
	private String hostName = "";
	private String comment = "";
	private String groupName = "";
	private boolean isSelected = false;
	private static JButton jDelBtn = HostChanger.jDelBtn;
	private static JButton jUpBtn = HostChanger.jUpBtn;
	private static JButton jDownBtn = HostChanger.jDownBtn;
	
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}	
			
	public JButton getJDelBtn() {
		jDelBtn.setToolTipText(HostChanger.DEL+" "+getAddress()+"("+getHostName()+")");
		return jDelBtn;
	}
			
	public JButton getJUpBtn() {	
		jUpBtn.setToolTipText(HostChanger.UP);		
		return jUpBtn;
	}
			
	public JButton getJDownBtn() {
		jDownBtn.setToolTipText(HostChanger.DOWN);
		return jDownBtn;
	}
	
	public Object getObject(int idx) {
		Object object = new Object();
		switch(idx){
			case 0 : object = isSelected();	break;					 
			case 1 : object = getAddress();	break;
			case 2 : object = getHostName(); break;
			case 3 : object = getGroupName(); break;
			case 4 : object = getComment();	break;
			case 5 : object = getJUpBtn(); break;
			case 6 : object = getJDownBtn(); break;
			case 7 : object = getJDelBtn();	break;
		}			
		return object;
	}
	
	public void setObject(int idx, Object object) {
		switch(idx){
			case 0 : setSelected((Boolean) object);	break;
			case 1 : setAddress((String) object); break;
			case 2 : setHostName((String) object); break;
			case 3 : setGroupName((String) object);	break;
			case 4 : setComment((String) object); break;
		}			
	}
	
	@Override
	public String toString(){
		return getAddress()+"   "+ getHostName()+"     "+ getGroupName()+"     "+ getComment();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((hostName == null) ? 0 : hostName.hashCode());
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
		HostInfo other = (HostInfo) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (hostName == null) {
            return other.hostName == null;
		} else return hostName.equals(other.hostName);
    }
}
