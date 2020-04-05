package com.shch.demo.board.dto;

import com.shch.demo.security.Session;

public class Board extends Session{

	public String bid;
	public String catecd;
	public String title;
	public String content;
	public String tag;
	public int viewcnt;
	public String regcd;
	public String regdt;
	public String modcd;
	public String moddt;
	
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getCatecd() {
		return catecd;
	}
	public void setCatecd(String catecd) {
		this.catecd = catecd;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getViewcnt() {
		return viewcnt;
	}
	public void setViewcnt(int viewcnt) {
		this.viewcnt = viewcnt;
	}
	public String getRegcd() {
		return regcd;
	}
	public void setRegcd(String regcd) {
		this.regcd = regcd;
	}
	public String getRegdt() {
		return regdt;
	}
	public void setRegdt(String regdt) {
		this.regdt = regdt;
	}
	public String getModcd() {
		return modcd;
	}
	public void setModcd(String modcd) {
		this.modcd = modcd;
	}
	public String getModdt() {
		return moddt;
	}
	public void setModdt(String moddt) {
		this.moddt = moddt;
	}

}
