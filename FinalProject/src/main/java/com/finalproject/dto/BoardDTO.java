package com.finalproject.dto;

import org.apache.ibatis.type.Alias;

@Alias("board")
public class BoardDTO {
	private String boardNo;
	private String boardTitle;
	private String boardContent;
	private int boardCount;
	private String boardWriteDate;
	private String boardUpdateDate;
	private String userId;
	private int categorieNo;
	private int boardLike;
	private int boardHate;
	
	public BoardDTO() {	}

	public String getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(String boardNo) {
		this.boardNo = boardNo;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public int getBoardCount() {
		return boardCount;
	}

	public void setBoardCount(int boardCount) {
		this.boardCount = boardCount;
	}

	public String getBoardWriteDate() {
		return boardWriteDate;
	}

	public void setBoardWriteDate(String boardWriteDate) {
		this.boardWriteDate = boardWriteDate;
	}

	public String getBoardUpdateDate() {
		return boardUpdateDate;
	}

	public void setBoardUpdateDate(String boardUpdateDate) {
		this.boardUpdateDate = boardUpdateDate;
	}

	public String getUserID() {
		return userId;
	}

	public void setUserID(String userId) {
		this.userId = userId;
	}

	public int getCategorieNo() {
		return categorieNo;
	}

	public void setCategorieNo(int categorieID) {
		this.categorieNo = categorieID;
	}

	@Override
	public String toString() {
		return "BoardDTO [boardNo=" + boardNo + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
				+ ", boardCount=" + boardCount + ", boardWriteDate=" + boardWriteDate + ", boardUpdateDate="
				+ boardUpdateDate + ", userId=" + userId + ", categorieNo=" + categorieNo + ", boardLike=" + boardLike
				+ ", boardHate=" + boardHate + "]";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getBoardLike() {
		return boardLike;
	}

	public void setBoardLike(int boardLike) {
		this.boardLike = boardLike;
	}

	public int getBoardHate() {
		return boardHate;
	}

	public void setBoardHate(int boardHate) {
		this.boardHate = boardHate;
	}


	
	
	
}
