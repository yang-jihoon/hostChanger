package src.domain;

import java.util.ArrayList;
import java.util.List;

public class ReadHostResult {
	private List<HostInfo> hostInfo = new ArrayList<HostInfo>();
	private List<String> hostGroup = new ArrayList<String>();
	public List<HostInfo> getHostInfo() {
		return hostInfo;
	}
	public void setHostInfo(List<HostInfo> hostInfo) {
		this.hostInfo = hostInfo;
	}
	public List<String> getHostGroup() {
		return hostGroup;
	}
	public void setHostGroup(List<String> hostGroup) {
		this.hostGroup = hostGroup;
	}	
}
