package cn.lifengyong.java.little.things.gson;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gson.Gson;

public class GsonUtilTest {

	@Test
	public void testJsonToPojo() {
		String json = "{\"teacherNo\":\"1001\",\"name\":\"杨\"}";
		Teacher teacher = GsonUtil.jsonToPojo(json, Teacher.class);
		
		assertEquals("1001", teacher.getTeacherNo());
		assertEquals("杨", teacher.getName());
	}

	@Test
	public void testPojoToJson() {
		Teacher teacher = new Teacher();
		teacher.setTeacherNo("1002");
		teacher.setName("lili");
		
		String json = GsonUtil.pojoToJson(teacher);
		assertEquals("{\"teacherNo\":\"1002\",\"name\":\"lili\",\"students\":\"\"}", json);
	}
	
	/**
	 * 对象互相引用转json
	 */
	@Test(expected = StackOverflowError.class)
	public void testGsonClassIncludeEachOther() {
		Teacher teacher = new Teacher();
		teacher.setTeacherNo("1002");
		teacher.setName("lili");
		teacher.setStudents(new ArrayList<Student>());
		
		Student stu01 = new Student();
		stu01.setStuNo("2001");
		stu01.setName("xiaowang");
		stu01.setTeacher(teacher);
		teacher.getStudents().add(stu01);
		
		Student stu02 = new Student();
		stu02.setStuNo("2002");
		stu02.setName("xiaocao");
		stu02.setTeacher(teacher);
		teacher.getStudents().add(stu02);
		
		GsonUtil.pojoToJson(teacher);
	}
	
	/**
	 * 对象互相引用，排除相互引用的字段
	 */
	@Test
	public void testGsonExclude() {
		Teacher teacher = new Teacher();
		teacher.setTeacherNo("1002");
		teacher.setName("lili");
		teacher.setStudents(new ArrayList<Student>());
		
		Student stu01 = new Student();
		stu01.setStuNo("2001");
		stu01.setName("xiaowang");
		stu01.setTeacher(teacher);
		teacher.getStudents().add(stu01);
		
		Student stu02 = new Student();
		stu02.setStuNo("2002");
		stu02.setName("xiaocao");
		stu02.setTeacher(teacher);
		teacher.getStudents().add(stu02);
		
		Gson gson = GsonUtil.createGsonExclusionStrategy(new GsonExclusionStrategy(null));
		String json = gson.toJson(teacher);
		
		String result = "{\"teacherNo\":\"1002\",\"name\":\"lili\",\"students\":"
				+ "[{\"stuNo\":\"2001\",\"name\":\"xiaowang\"},{\"stuNo\":\"2002\",\"name\":\"xiaocao\"}]}";
		
		assertEquals(result, json);
	}
	
	

}
