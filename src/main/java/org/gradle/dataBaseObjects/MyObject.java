package org.gradle.dataBaseObjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="MYOBJECT")
@XmlRootElement
public class MyObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlAttribute
	@Column(name="Id")
	private Long id;

	@Lob
	@Column(name="Content",length = 100000)
	private String content;

	protected MyObject() {
	}

	public MyObject(String content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
