package cn.lifengyong.java.little.things.utils.district;

public class District {
	private String id;
	private String level;
	private String cnName;
	private String enName;
	private String parentId;
	private int status = 0;
  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
    public static void main(String[] args) {
		String t = "500100";
		System.out.println(t.indexOf("00"));
		System.out.println(t.lastIndexOf("00"));
	}
	
	public String toString() {
		return "insert into `exp_district`(`id`,`level`,`cn_name`,`en_name`,`parent_id`,`status`) values(" +
	            "'" + id + "'," + "'" + level + "'," + "'" + cnName + "'," + "'" + enName + "'," + "'" + parentId  + "'," + "'" + status + "'" +
				");";
	}
  
}
