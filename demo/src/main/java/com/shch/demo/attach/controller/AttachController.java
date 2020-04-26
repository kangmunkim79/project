package com.shch.demo.attach.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.shch.demo.attach.dto.Attach;
import com.shch.demo.attach.service.AttachService;
import com.shch.demo.utils.StringUtils;
import com.shch.demo.utils.keyGeneratorUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "attach")
public class AttachController {

	@Autowired
	AttachService attachService;
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.GET)
	public String fileUpload(Locale locale, Model model) {
		String filegrpcd = keyGeneratorUtils.timeKey("FILEGRP");
		model.addAttribute("filegrpcd", filegrpcd);
		return "attach/fileUpload";
	}
	
	@RequestMapping(value = "/getFileList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> getFileList(@RequestBody Map<String, Object> param) throws Exception { 
		Map<String, Object> result = new HashMap<String, Object>(); 
		Attach fileVO = new Attach();
		fileVO.setFilegrpcd(StringUtils.nvl(param.get("filegrpcd"),""));
		try { 
			List<Attach> list = attachService.getFileList(fileVO);
			result.put("fList", list);
		} catch (Exception e) { 
			result.put("status", "False"); 
		} 
		return result; 
	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadFile(MultipartHttpServletRequest mtfRequest) {
		List<MultipartFile> fileList = mtfRequest.getFiles("uploadfile");
		String path = "D:\\uploads\\";
		String filegrpcd = StringUtils.nvl(mtfRequest.getParameter("filegrpcd"),"");
		if("".equals(filegrpcd)) {
			filegrpcd = keyGeneratorUtils.timeKey("FILEGRP");
		}
		for (MultipartFile mf : fileList) {
			String filecd = keyGeneratorUtils.timeKey("FILE");
			String orgfilename = mf.getOriginalFilename().substring(0,mf.getOriginalFilename().lastIndexOf(".")); // 원본 파일 명
			String uploadfilename = filecd+"_"+orgfilename;
			String ext = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".")+1);
			long fileSize = mf.getSize(); // 파일 사이즈
			String safeFile = path + filecd+"_"+orgfilename;
			try {
				mf.transferTo(new File(safeFile));
				Attach fileVO = new Attach();
				fileVO.setFilecd(filecd);
				fileVO.setFilegrpcd(filegrpcd);
				fileVO.setOrgfilename(orgfilename);
				fileVO.setUploadfilename(uploadfilename);
				fileVO.setFileurl(path);
				fileVO.setExt(ext);
				fileVO.setFilesize(fileSize);
				attachService.insertFileInfo(fileVO);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);
	} // method uploadFile
	
	@RequestMapping(value = "/deleteFileList", method = RequestMethod.POST) 
	public @ResponseBody Map<String, Object> deleteFileList(@RequestBody List<Attach> deleteList) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try { 
			attachService.deleteFileList(deleteList);
			result.put("status", "Ok");
		} catch (Exception e) { 
			result.put("status", "False"); 
		}
		return result;		
	}
}
