package com.shch.demo.attach.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.shch.demo.GlobalPropertySource;
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
    GlobalPropertySource globalPropertySource;
	
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
		String path = globalPropertySource.getFilepath();//config.properties
		LocalDateTime today = LocalDateTime.now();		
        String yForder = StringUtils.nvl(today.getYear(),"");
        String mForder = StringUtils.LPad(today.getMonthValue(),2,"0");
        String dForder = StringUtils.LPad(today.getDayOfMonth(),2,"0");		
		path = path + yForder + "\\" + mForder + "\\" + dForder + "\\";   
		
		File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
		
		String filegrpcd = StringUtils.nvl(mtfRequest.getParameter("filegrpcd"),"");
		if("".equals(filegrpcd)) {
			filegrpcd = keyGeneratorUtils.timeKey("FILEGRP");
		}
		for (MultipartFile mf : fileList) {
			String filecd = keyGeneratorUtils.timeKey("FILE");
			String fullorgfilename = mf.getOriginalFilename();
			String orgfilename = fullorgfilename.substring(fullorgfilename.lastIndexOf("\\")+1,fullorgfilename.lastIndexOf(".")); // 원본 파일 명
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
	
	@RequestMapping(value = "/download")
	public ResponseEntity<Resource> download(@RequestParam String filecd) throws IOException {
		Attach afile = attachService.getFile(filecd);
		String filename = afile.getFilename();
		String uploadfilename = afile.getUploadfilename();
		String fileurl = afile.getFileurl();
		String fileUrlName = fileurl + uploadfilename;
		Path path = Paths.get(fileUrlName);
		String contentType = Files.probeContentType(path);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);		
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", " "));
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
}
