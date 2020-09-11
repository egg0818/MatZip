package com.koreait.matzip.restaurant;

import javax.servlet.http.HttpServletRequest;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;

public class RestaurantController {
	
	public String restMap(HttpServletRequest request) {
	      request.setAttribute(Const.TITLE, "지도보기");
	      request.setAttribute(Const.VIEW, "restaurant/restMap");
	      return ViewRef.TEMP_MENU_TEMP;
	   }
	
	public String restReg(HttpServletRequest request) {
	      request.setAttribute(Const.TITLE, "가게등록");
	      request.setAttribute(Const.VIEW, "restaurant/restReg");
	      return ViewRef.TEMP_MENU_TEMP;
	}
}
