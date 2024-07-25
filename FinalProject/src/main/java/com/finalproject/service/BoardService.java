package com.finalproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.finalproject.dto.BoardDTO;
import com.finalproject.dto.CommentDTO;
import com.finalproject.mapper.BoardMapper;

@Service
public class BoardService {
	private BoardMapper mapper;

	public BoardService(BoardMapper mapper) {
		this.mapper = mapper;
	}
	
	// 게시판 목록 조회
	public List<BoardDTO> selectBoardList(int pageNo, int pageContentEa) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNo", pageNo);
		map.put("pageContentCount", pageContentEa);
		return mapper.selectBoardList(map);
	}

	// 게시글 수(vo)
	public int selectBoardTotalCount() {
		return mapper.selectBoardTotalCount();
	}

	// 게시글 상세조회
	public BoardDTO selectBoard(int bno) {
		return mapper.selectBoard(bno);
	}

	// 게시글 댓글 조회
	public List<CommentDTO> selectCommentList(int bno) {
		return mapper.selectCommentList(bno);
	}

	// 게시글 조회수 증가
	public int updateBoardCount(int bno) {
		return mapper.updateBoardCount(bno);
	}


	// ========================== 게시글 좋아요/ 싫어요
	
	public int insertBoardLike(int bno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("id", userId);
	    map.put("bno", bno);
	    return mapper.insertBoardLike(map);
	}

	public int deleteBoardLike(int bno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("id", userId);
	    map.put("bno", bno);
	    return mapper.deleteBoardLike(map);
	}

	public int selectBoardLikeCount(int bno) {
	    return mapper.selectBoardLikeCount(bno);
	}

	public int insertBoardHate(int bno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("id", userId);
	    map.put("bno", bno);
	    return mapper.insertBoardHate(map);
	}

	public int deleteBoardHate(int bno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("id", userId);
	    map.put("bno", bno);
	    return mapper.deleteBoardHate(map);
	}

	public int selectBoardHateCount(int bno) {
	    return mapper.selectBoardHateCount(bno);
	}
	
	
	
	// ========================== 댓글 좋아요/ 싫어요
	public int insertCommentLike(int cno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("userId", userId);
	    map.put("cno", cno);
	    return mapper.insertCommentLike(map);
	}

	public int deleteCommentLike(int cno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("userId", userId);
	    map.put("cno", cno);
	    return mapper.deleteCommentLike(map);
	}

	public int selectCommentLikeCount(int cno) {
	    return mapper.selectCommentLikeCount(cno);
	}

	public int insertCommentHate(int cno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("userId", userId);
	    map.put("cno", cno);
	    return mapper.insertCommentHate(map);
	}

	public int deleteCommentHate(int cno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("userId", userId);
	    map.put("cno", cno);
	    return mapper.deleteCommentHate(map);
	}

	public int selectCommentHateCount(int cno) {
	    return mapper.selectCommentHateCount(cno);
	}
	
	// ========================== 댓글 추가
	public int insertComment(CommentDTO dto) {
		return mapper.insertComment(dto);
	}
	
	
	
	
	

}
