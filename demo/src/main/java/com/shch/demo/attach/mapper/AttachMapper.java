package com.shch.demo.attach.mapper;

import java.util.List;

import com.shch.demo.attach.dto.Attach;

public interface AttachMapper {

	public void insertFileInfo(Attach fileVO);
	
	public List<Attach> getFileList(Attach fileVO);
	
	public Attach getFile(String filecd);
	
	public void deleteFile(Attach fileVO);
	
}
