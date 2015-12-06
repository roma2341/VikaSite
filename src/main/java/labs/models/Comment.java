package labs.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;

import labs.View;


@Entity
public class Comment {
	@JsonView(View.Summary.class)
	@Id
	@GeneratedValue
    private Long id;
	
	@NotNull
	@ManyToOne
    private User author;
	
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@JsonView(View.Summary.class)
	@NotNull
	@ManyToOne
    private Post post;
	
	@JsonView(View.Summary.class)
	@NotBlank
    @Size(min = 1, max = 1024)
    private String text;
	
	@NotNull
    private Date createdAt;
	
	public Comment(User author,Post post,String text){
		this.author=author;
		this.text=text;
		this.post=post;
		createdAt=new Date();
	}

	public Comment(){
		
	}
}
