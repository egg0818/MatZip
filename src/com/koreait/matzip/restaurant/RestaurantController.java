package com.koreait.matzip.restaurant;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.koreait.matzip.CommonDAO;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.vo.RestaurantDomain;
import com.koreait.matzip.vo.RestaurantRecommendMenuVO;
import com.koreait.matzip.vo.RestaurantVO;
import com.koreait.matzip.vo.UserVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

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

	public String ajaxGetList(HttpServletRequest request) {
		return "ajax:" + service.getRestList();
	}

	public String restDetail(HttpServletRequest request) {
		int i_rest = CommonUtils.getIntParameter(request, "i_rest");

		RestaurantDomain param = new RestaurantDomain();
		param.setI_rest(i_rest);
		
		request.setAttribute("css", new String[]{"restaurant"});
		request.setAttribute("recommendMenuList", service.getRecommendMenuList(i_rest));
		request.setAttribute("menuList", service.getMenuList(i_rest));
		request.setAttribute("data", service.selRestDetail(param));
		request.setAttribute(Const.TITLE, "디테일");
		request.setAttribute(Const.VIEW, "restaurant/restDetail");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	public String addMenusProc(HttpServletRequest request) { //메뉴
		int i_rest = service.addMenus(request);
		return "redirect:/restaurant/restDetail?i_rest=" + i_rest;
	}
	
	public String addRecMenusProc(HttpServletRequest request) {
		int i_rest = service.addRecMenus(request);
		return "redirect:/restaurant/restDetail?i_rest=" + i_rest;
	}
	
	
	public String ajaxDelRecMenu(HttpServletRequest request) {
		int i_rest = CommonUtils.getIntParameter(request, "i_rest");
		int seq = CommonUtils.getIntParameter(request, "seq");
		int i_user = SecurityUtils.getLoginUserPk(request);
		
		RestaurantRecommendMenuVO param = new RestaurantRecommendMenuVO();
		param.setI_rest(i_rest);
		param.setSeq(seq);
		param.setI_user(i_user);
		
		int result = service.delRecMenu(param);
		return "ajax:" + result;
	}
}
