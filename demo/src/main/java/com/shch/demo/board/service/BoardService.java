package com.shch.demo.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shch.demo.board.dto.Board;
import com.shch.demo.board.mapper.BoardMapper;
import com.shch.demo.utils.StringUtils;
import com.shch.demo.utils.keyGeneratorUtils;

@Service
public class BoardService {

	@Autowired
	BoardMapper boardMapper;
	
	public List<Board> getBoardList() throws Exception {
		return boardMapper.getBoardList();
	}
	
	public Board getBoardView(String bid) throws Exception{
		boardMapper.updateViewCnt(bid);
		return boardMapper.getBoardView(bid);
	}

	public void saveBoard(Board board) throws Exception {
		String bid = StringUtils.nvl(board.getBid(),"");
		if("".equals(bid)) {
			board.setBid(keyGeneratorUtils.timeKey("BBS"));
			boardMapper.insertBoard(board);
		}else {
			boardMapper.updateBoard(board);
		}
	}
	
	public void insertBoard(Board board) throws Exception {
		boardMapper.insertBoard(board);
	}

	public void updateBoard(Board board) throws Exception {
		boardMapper.updateBoard(board);
	}
	
}
