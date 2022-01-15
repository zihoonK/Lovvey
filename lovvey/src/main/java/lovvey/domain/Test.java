package lovvey.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;



public class Test {

	private Integer key_id;
	private String id;
	private String num;
	private String name;
	
	
	public Test() {
		super();
	}

	public Test(String id, String num, String name) {
		super();
		this.id = id;
		this.num = num;
		this.name = name;
	}


	

	public Integer getKey_id() {
		return key_id;
	}

	public void setKey_id(Integer key_id) {
		this.key_id = key_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Test [key_id=" + key_id + ", id=" + id + ", num=" + num + ", name=" + name + "]";
	}
	
	
	
	
}
