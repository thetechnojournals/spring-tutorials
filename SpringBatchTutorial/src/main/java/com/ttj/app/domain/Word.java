package com.ttj.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="WORDS")
public class Word {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String text;
	
	@Enumerated(EnumType.STRING)
	private Language language;

	public Word(){}
	public Word(String text, Language lang){
		this.text = text;
		this.language = lang;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	@Override
	public int hashCode() {
		return ((language == null) ? 0 : language.hashCode())+((text == null) ? 0 : text.hashCode());
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (language != other.language)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
}
