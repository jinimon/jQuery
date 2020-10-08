package comments;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentsDAO extends common.DAO {

	static CommentsDAO instance;

	public static CommentsDAO getInstance() {
		if (instance != null)
			return instance;
		else
			return new CommentsDAO();
	}

	// 댓글목록
	public List<HashMap<String, Object>> selectAll(String no) throws Exception {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from COMMENTS order by ID ");
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", rs.getInt("id"));
				map.put("name", rs.getString("name"));
				map.put("content", rs.getString("content"));
				// map.put("board_id", rs.getString("board_id"));
				list.add(map);
			}
		} catch (Throwable e) {
			System.out.println("comment selectAll error" + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return list;
	}// end of method

	public HashMap<String, Object> update(Comments bean) throws Exception {
		PreparedStatement pstmtCommentInsert = null;
		try {
			connect();
			conn.setAutoCommit(false); // 트랜잭션 처리
			String sql = "update COMMENTS set name=?, content=? where id=? ";
			pstmtCommentInsert = conn.prepareStatement(sql);
			pstmtCommentInsert.setString(1, bean.getName());
			pstmtCommentInsert.setString(2, bean.getContent());
			pstmtCommentInsert.setString(3, bean.getId());
			pstmtCommentInsert.executeUpdate();
			conn.commit(); // 커밋
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", bean.getId());
			map.put("name", bean.getName());
			map.put(("content"), bean.getContent());
			return map;
		} catch (Throwable e) {
			try {
				conn.rollback(); // 롤백
			} catch (SQLException ex) {
			}
			throw new Exception(e.getMessage());
		} finally {
			conn.close();
		}
	}

	public HashMap<String, Object> delete(Comments bean) throws Exception {
		PreparedStatement pstmtCommentInsert = null;
		try {
			connect();
			conn.setAutoCommit(false); // 트랜잭션 처리
			String sql = "delete COMMENTS where id=? ";
			pstmtCommentInsert = conn.prepareStatement(sql);
			pstmtCommentInsert.setString(1, bean.getId());
			pstmtCommentInsert.executeUpdate();
			conn.commit(); // 커밋
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", bean.getId());
			return map;
		} catch (Throwable e) {
			try {
				conn.rollback(); // 롤백
			} catch (SQLException ex) {
			}
			throw new Exception(e.getMessage());
		} finally {
			conn.close();
		}
	}

	public HashMap<String, Object> insert(Comments bean) throws Exception {
		Statement stmtIdSelect = null;
		ResultSet rsIdSelect = null;
		PreparedStatement pstmtIdUpdate = null;
		PreparedStatement pstmtCommentInsert = null;
		int nextId = 0;
		System.out.println(bean);
		try {
			connect();
			conn.setAutoCommit(false); // 트랜잭션 처리
			stmtIdSelect = conn.createStatement();
			rsIdSelect = stmtIdSelect.executeQuery("select VALUE from ID_REPOSITORY where NAME='COMMENT'");
			if (rsIdSelect.next()) {
				nextId = rsIdSelect.getInt("VALUE");
			}
			nextId++; // 시퀀스 용도
			pstmtIdUpdate = conn.prepareStatement("update ID_REPOSITORY set VALUE = ? where NAME='COMMENT'");
			pstmtIdUpdate.setInt(1, nextId);
			pstmtIdUpdate.executeUpdate();

			pstmtCommentInsert = conn.prepareStatement("insert into COMMENTS(id, name, content) values (?, ?, ?)");
			pstmtCommentInsert.setInt(1, nextId);
			pstmtCommentInsert.setString(2, bean.getName());
			pstmtCommentInsert.setString(3, bean.getContent());
			// pstmtCommentInsert.setString(4, "1");
			pstmtCommentInsert.executeUpdate();
			conn.commit(); // 커밋
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", nextId);
			map.put("name", bean.getName());
			map.put("content", bean.getContent());
			return map;
		} catch (Throwable e) {
			e.printStackTrace();
			try {
				conn.rollback(); // 롤백
			} catch (SQLException ex) {
			}
			throw new Exception(e.getMessage());
		}
	}

	// 등록(프로시져)
	public HashMap<String, Object> insertProc(Comments bean) throws Exception {
		CallableStatement cstmt = null;
		int nextId = 0;
		try {
			connect();
			conn.setAutoCommit(false); // 트랜잭션 처리
			cstmt = conn.prepareCall("{call COMMENTS_INS(?,?,?,?,?)}");
			cstmt.setString(1, bean.getName());
			cstmt.setString(2, bean.getContent());
			cstmt.setString(3, "1");
			cstmt.registerOutParameter(4, java.sql.Types.NUMERIC);
			cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);

			cstmt.executeUpdate();
			conn.commit(); // 커밋
			nextId = cstmt.getInt(4);
			String out_msg = cstmt.getString(5);
			System.out.println(out_msg);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", nextId);
			map.put("name", bean.getName());
			map.put(("content"), bean.getContent());
			return map;
		} catch (Throwable e) {
			try {
				conn.rollback(); // 롤백
			} catch (SQLException ex) {
			}
			throw new Exception(e.getMessage());
		}
	}

}// end of class
