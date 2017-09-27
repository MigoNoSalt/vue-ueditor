package com.example.action;

import com.baidu.ueditor.ActionEnter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UeditorController {

	@RequestMapping("/")
	public String index(){
		return "ueditor";
	}

	@Autowired
	private ActionEnter actionEnter;

	@ResponseBody
	@RequestMapping("/ueditor/exec")
	public String exe(HttpServletRequest request){
		return actionEnter.exec(request);
	}

}
