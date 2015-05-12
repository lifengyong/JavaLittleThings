package cn.lifengyong.java.little.things.gson;

public class Student {
	private String stuNo;
	private String name;
	//该对象和Teacher对象间互相引用，转json时陷入循环
	@GsonExclude
	private Teacher teacher;
	
	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
}
