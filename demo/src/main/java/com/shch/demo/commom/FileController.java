package com.shch.demo.commom;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shch.demo.utils.keyGeneratorUtils;

@Controller
@RequestMapping(value = "common")
public class FileController {

	@RequestMapping(value = "/license/download")
	public ResponseEntity<Resource> download(@RequestParam String fileUrlName) throws IOException {
		Path path = Paths.get(fileUrlName);
		String contentType = Files.probeContentType(path);
		String ext = fileUrlName.substring(fileUrlName.lastIndexOf("."));
		String key = keyGeneratorUtils.timeKey("_");
		String fname = fileUrlName.substring(fileUrlName.lastIndexOf("/")+1, fileUrlName.lastIndexOf("."));
		String dname = fname+key+ext;
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);		
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + dname);
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
}
