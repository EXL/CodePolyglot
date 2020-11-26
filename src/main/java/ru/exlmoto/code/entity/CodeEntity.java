package ru.exlmoto.code.entity;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "snippets")
public class CodeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long timestamp;

	@Column
	private String title;

	@Column
	private String options;

	@Column(nullable = false)
	private String highlight;

	@Column(nullable = false, length = 262144)
	private String codeRaw;

	@Column(nullable = false, length = 262144 * 2)
	private String codeHtml;

	public CodeEntity() {

	}

	public CodeEntity(Long timestamp, String title, String options, String highlight, String codeRaw, String codeHtml) {
		this.timestamp = timestamp;
		this.title = title;
		this.options = options;
		this.highlight = highlight;
		this.codeRaw = codeRaw;
		this.codeHtml = codeHtml;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getHighlight() {
		return highlight;
	}

	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

	public String getCodeRaw() {
		return codeRaw;
	}

	public void setCodeRaw(String codeRaw) {
		this.codeRaw = codeRaw;
	}

	public String getCodeHtml() {
		return codeHtml;
	}

	public void setCodeHtml(String codeHtml) {
		this.codeHtml = codeHtml;
	}

	@Override
	public String toString() {
		return "CodeEntity{" +
			"id=" + id +
			", timestamp=" + timestamp +
			", title='" + title + '\'' +
			", options='" + options + '\'' +
			", highlight='" + highlight + '\'' +
			", codeRaw='" + "<skipped>" + '\'' +
			", codeHtml='" + "<skipped>" + '\'' +
			'}';
	}
}
