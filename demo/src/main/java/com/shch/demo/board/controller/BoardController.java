package com.shch.demo.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shch.demo.board.dto.Board;
import com.shch.demo.board.service.BoardService;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@RequestMapping(value = "/getBoardList", method = RequestMethod.GET)
	public String getBoardList(Model model) throws Exception {
		model.addAttribute("boardList", boardService.getBoardList());
		return "board/boardList";
	}

	@RequestMapping("/boardUpdate")
	public String boardUpdate(Model model) {
		model.addAttribute("mode", "");
		model.addAttribute("board", new Board());
		return "board/boardUpdate";
	}

	@RequestMapping(value = "/saveBoard", method = RequestMethod.POST)
	public String saveBoard(@ModelAttribute("Board") Board board, @RequestParam("mode") String mode, RedirectAttributes rttr) throws Exception {
		boardService.saveBoard(board);
		return "redirect:/board/getBoardList";
	}
	
	@RequestMapping(value = "/getBoardView", method = RequestMethod.GET)
	public String getBoardView(Model model, @RequestParam("bid") String bid) throws Exception {
		model.addAttribute("boardView", boardService.getBoardView(bid));
		return "board/boardView";
	}
	
	@RequestMapping(value = "/boardEdit", method = RequestMethod.GET)
	public String editForm(@RequestParam("bid") String bid, @RequestParam("mode") String mode, Model model) throws Exception {
		model.addAttribute("boardView", boardService.getBoardView(bid));
		model.addAttribute("mode", mode);
		model.addAttribute("board", new Board());
		return "board/boardUpdate";
	}
	
	@RequestMapping(value = "/deleteBoard", method = RequestMethod.GET)
	public String deleteBoard(RedirectAttributes rttr, @RequestParam("bid") String bid) throws Exception {
		boardService.deleteBoard(bid);
		return "redirect:/board/getBoardList";
	}

}
