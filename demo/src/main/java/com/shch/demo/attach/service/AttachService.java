package com.shch.demo.attach.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shch.demo.attach.dto.Attach;
import com.shch.demo.attach.mapper.AttachMapper;

@Service
public class AttachService {

	@Autowired
	AttachMapper attachMapper;

	public void insertFileInfo(Attach fileVO) {
		attachMapper.insertFileInfo(fileVO);
	}
	
	public List<Attach> getFileList(Attach fileVO) {
		return attachMapper.getFileList(fileVO);
	}

	public Attach getFile(String filecd) {
		return attachMapper.getFile(filecd);
	}
	
	public void deleteFileList(List<Attach> delList) {
		for(Attach fParam:delList) {
			attachMapper.deleteFile(fParam);
		}
	}
	
}
