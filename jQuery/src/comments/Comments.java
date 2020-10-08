package comments;

public class Comments {

	String id;
	String name;
	String content;
	String board_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBoard_id() {
		return board_id;
	}

	public void setBoard_id(String board_id) {
		this.board_id = board_id;
	}

	@Override
	public String toString() {
		return "Comments [id=" + id + ", name=" + name + ", content=" + content + ", board_id=" + board_id + "]";
	}

}
