package com.finalproject.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.dto.BoardDTO;
import com.finalproject.dto.CommentDTO;
import com.finalproject.dto.UserDTO;
import com.finalproject.service.BoardService;
import com.finalproject.vo.BoardPaggingVo;

import jakarta.servlet.http.HttpSession;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BoardController {
    private final BoardService service;

    public BoardController(BoardService service) {
        this.service = service;
    }

    @GetMapping("/board/list")
    public Map<String, Object> selectBoardList(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageContentEa) {
        
        List<BoardDTO> boardList = service.selectBoardList(pageNo, pageContentEa);
        int totalCount = service.selectBoardTotalCount();

        BoardPaggingVo vo = new BoardPaggingVo(totalCount, pageNo, pageContentEa);

        Map<String, Object> response = new HashMap<>();
        response.put("list", boardList);
        response.put("pagging", vo);

        return response;
    }
    
    // 게시글 상세조회
    @GetMapping("/board/{bno}")
    public Map<String, Object> boardView(@PathVariable int bno, HttpSession session) {
        
        //글번호에 해당하는 게시글 조회
        BoardDTO dto = service.selectBoard(bno);
        
        //해당 게시글의 댓글 조회 
        List<CommentDTO> commentList = service.selectCommentList(bno);
        
        //게시글 조회수 업데이트
        HashSet<Integer> set = (HashSet<Integer>) session.getAttribute("history");
        
        //세션에 방문한 게시글 목록이 없을때
        if(set == null) {
            set = new HashSet<Integer>();
            session.setAttribute("history", set);
        }
        if(set.add(bno)) {
            service.updateBoardCount(bno);
        }
        
        // Map을 사용하여 데이터를 반환
        Map<String, Object> response = new HashMap<>();
        response.put("board", dto);
        response.put("commentList", commentList);

        return response;
    }
    
    // 게시글 좋아요
    @GetMapping("/boardLike/{bno}")
    public ResponseEntity<Map<String, Object>> boardLike(@PathVariable int bno, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            map.put("code", 2);
            map.put("msg", "로그인 하셔야 이용하실수 있습니다.");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED); // 401 Unauthorized 반환
        } else {
            try {
                service.insertBoardLike(bno, user.getUserId());
                map.put("msg", "해당 게시글에 좋아요 하셨습니다.");
            } catch (Exception e) {
                service.deleteBoardLike(bno, user.getUserId());
                map.put("msg", "해당 게시글에 좋아요를 취소하셨습니다.");
            }
            map.put("code", 1);
        }
        int count = service.selectBoardLikeCount(bno);
        map.put("count", count);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // 게시글 싫어요
    @GetMapping("/boardHate/{bno}")
    public ResponseEntity<Map<String, Object>> boardHate(@PathVariable int bno, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            map.put("code", 2);
            map.put("msg", "로그인 하셔야 이용하실수 있습니다.");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED); // 401 Unauthorized 반환
        } else {
            try {
                service.insertBoardHate(bno, user.getUserId());
                map.put("msg", "해당 게시글에 싫어요 하셨습니다.");
            } catch (Exception e) {
                service.deleteBoardHate(bno, user.getUserId());
                map.put("msg", "해당 게시글에 싫어요를 취소하셨습니다.");
            }
            map.put("code", 1);
        }
        int count = service.selectBoardHateCount(bno);
        map.put("count", count);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    
    // 댓글 좋아요
    @GetMapping("/commentLike/{cno}")
    public ResponseEntity<Map<String, Object>> commentLike(@PathVariable int cno, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            map.put("code", 2);
            map.put("msg", "로그인 하셔야 이용하실 수 있습니다.");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        } else {
            try {
                service.insertCommentLike(cno, user.getUserId());
                map.put("msg", "해당 댓글에 좋아요 하셨습니다.");
            } catch (Exception e) {
                service.deleteCommentLike(cno, user.getUserId());
                map.put("msg", "해당 댓글에 좋아요를 취소하셨습니다.");
            }
            map.put("code", 1);
        }
        int count = service.selectCommentLikeCount(cno);
        map.put("count", count);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // 댓글 싫어요
    @GetMapping("/commentHate/{cno}")
    public ResponseEntity<Map<String, Object>> commentHate(@PathVariable int cno, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            map.put("code", 2);
            map.put("msg", "로그인 하셔야 이용하실 수 있습니다.");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        } else {
            try {
                service.insertCommentHate(cno, user.getUserId());
                map.put("msg", "해당 댓글에 싫어요 하셨습니다.");
            } catch (Exception e) {
                service.deleteCommentHate(cno, user.getUserId());
                map.put("msg", "해당 댓글에 싫어요를 취소하셨습니다.");
            }
            map.put("code", 1);
        }
        int count = service.selectCommentHateCount(cno);
        map.put("count", count);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    
    
//	@PostMapping("/comment/add")
//	public String commentAdd(BoardCommentDTO dto,HttpSession session) {
//		System.out.println(dto);
//		BoardMemberDTO user = (BoardMemberDTO) session.getAttribute("user");
//		dto.setWriter(user.getBoardMemberId());
//		
//		boardService.insertBoardComment(dto);
//		
//		return "redirect:/board/"+dto.getBno();
//	}
    
    
    
    
}