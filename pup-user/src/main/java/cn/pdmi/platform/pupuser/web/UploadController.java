package cn.pdmi.platform.pupuser.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/avatar")
public class UploadController {
	
	@Autowired
	private MultipartConfigElement multipartConfig;

	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,Object> avatarUpload(@RequestParam("file") MultipartFile file) throws IOException {
		
		System.out.println(multipartConfig.getLocation());
		File newFile=new File(multipartConfig.getLocation() + file.getOriginalFilename());
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(newFile);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("filePath",newFile.getAbsolutePath());
        map.put("name", file.getOriginalFilename());
        HashMap<String,Object> result = new HashMap<String,Object>();
        result.put("success", true);
        result.put("data", map);
		return result;
	}
}
