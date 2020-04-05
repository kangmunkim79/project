package com.shch.demo.board.mapper;

import java.util.List;

import com.shch.demo.board.dto.Board;

public interface BoardMapper {

	public List<Board> getBoardList();

	public Board getBoardView(String bid);

	public int insertBoard(Board board);

	public int updateBoard(Board board);

	public int deleteBoard(String bid);

	public int updateViewCnt(String bid);

}
