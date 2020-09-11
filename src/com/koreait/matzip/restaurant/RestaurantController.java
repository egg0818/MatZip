package com.koreait.matzip.restaurant;

import javax.servlet.http.HttpServletRequest;

import com.koreait.matzip.CommonDAO;
import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.vo.RestaurantVO;
import com.koreait.matzip.vo.UserVO;

public class RestaurantController {
	
	private RestaurantService service;
	
	public RestaurantController() {
		service = new RestaurantService();
	}
	
	public String restMap(HttpServletRequest request) {
	      request.setAttribute(Const.TITLE, "지도보기");
	      request.setAttribute(Const.VIEW, "restaurant/restMap");
	      return ViewRef.TEMP_MENU_TEMP;
	   }
	
	public String restReg(HttpServletRequest request) {
		  final int I_M = 1; // 카테고리 코드
		  request.setAttribute("categoryList", CommonDAO.selCodeList(I_M));
		
	      request.setAttribute(Const.TITLE, "가게등록");
	      request.setAttribute(Const.VIEW, "restaurant/restReg");
	      return ViewRef.TEMP_MENU_TEMP;
	}
	
	public String restRegProc(HttpServletRequest request) {
		
		  UserVO vo = SecurityUtils.getLoginUser(request);
		 
		  String addr = request.getParameter("addr");
		  String nm = request.getParameter("nm");
		  String strLat = request.getParameter("lat");
		  double lat = Double.parseDouble(strLat);
		  String strLng = request.getParameter("lng");
		  double lng = Double.parseDouble(strLng);
		  String strCd_category = request.getParameter("cd_category");
		  int cd_category = Integer.parseInt(strCd_category);
		  int i_user = vo.getI_user();
		  
		  RestaurantVO param = new RestaurantVO();
		  param.setAddr(addr);
		  param.setCd_category(cd_category);
		  param.setI_user(i_user);
		  param.setLat(lat);
		  param.setLng(lng);
		  param.setNm(nm);
		  
		  int result = service.insRestaurant(param);
		  
		  return "redirect:/restaurant/restMap";
	}
}
