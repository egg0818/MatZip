package com.koreait.matzip.restaurant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.vo.RestaurantDomain;
import com.koreait.matzip.vo.RestaurantRecommendMenuVO;
import com.koreait.matzip.vo.RestaurantVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class RestaurantService {
	private RestaurantDAO dao;
	
	public RestaurantService() {
		dao = new RestaurantDAO();
	}
	
	public RestaurantDomain selRestDetail(RestaurantDomain param) {
		return dao.selRestDetail(param);
	}
	
	public int insRestaurant(RestaurantVO param) {
		return dao.insRestaurant(param);
	}
	
	public String getRestList() {
		List<RestaurantDomain> list = dao.selRestList();
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
	
	public List<RestaurantRecommendMenuVO> getRecommendMenuList(int i_rest) {
		List<RestaurantRecommendMenuVO> list = dao.selRecommendMenuList(i_rest);
		return list;
	}
	
	public List<RestaurantRecommendMenuVO> getMenuList(int i_rest) {
		List<RestaurantRecommendMenuVO> list = dao.selMenuList(i_rest);
		return list;
	}
	
	public int delRecMenu(RestaurantRecommendMenuVO param) {
		return dao.delRecMenu(param);
	}
	
	// addRecMenus 보다 이게나음, 파일 다중 업로드!!
	public int addMenus(HttpServletRequest request) { //메뉴 
		RestaurantRecommendMenuVO param = new RestaurantRecommendMenuVO();
		int i_rest = CommonUtils.getIntParameter(request, "i_rest");
		String savePath = request.getServletContext().getRealPath("/res/img/restaurant");
		String tempPath = savePath + "/" + i_rest + "/menu/"; //임시	
		System.out.println("i_rest : " + i_rest);
		
		FileUtils.makeFolder(tempPath);
		
        try {
        	for (Part part : request.getParts()) {
                String fileName = part.getSubmittedFileName();
                System.out.println("fileName : " + fileName);
                if(fileName != null) {	 
                	String ext = FileUtils.getExt(fileName);
                	String saveFileNm = UUID.randomUUID() + ext;
                	System.out.println("saveFileNm : " + saveFileNm);
                	
                	part.write(tempPath + "/" + saveFileNm);
              
                	param.setMenu_pic(saveFileNm);
                	param.setI_rest(i_rest);
                	dao.insMenu(param);
            }   
                
        	}
        } catch(Exception e) {
        	e.printStackTrace();
        }
		return i_rest;
	}
	
	public int addRecMenus(HttpServletRequest request) {
		
		//getServletContext : 내장객체, getRealPath 메소드 : 서버의 절대 주솟값(c드라이버에서 시작되는것) 절대주솟값 +주소덧붙여줌 
		String savePath = request.getServletContext().getRealPath("/res/img/restaurant"); // 상대주솟값 : 어떤 기준에서부터 시작하는것
		// tempPath : 임시
		String tempPath = savePath + "/temp";		
		FileUtils.makeFolder(tempPath);
		
		int maxFileSize = 10_485_760; //1024 * 1024 * 10 (10mb) //최대 파일 사이즈 크기
		MultipartRequest multi = null;
		int i_rest = 0;
		String[] menu_nmArr = null;
		String[] menu_priceArr = null;
		List<RestaurantRecommendMenuVO> list = null;
		
		try {
			// 객체화 하는 순간 tempPath에 파일생성
			multi = new MultipartRequest(request, tempPath, maxFileSize, "UTF-8", new DefaultFileRenamePolicy());
			
			i_rest = CommonUtils.getIntParameter(multi, "i_rest");
			
			System.out.println("i_rest : " + i_rest);
			menu_nmArr = multi.getParameterValues("menu_nm");
			menu_priceArr = multi.getParameterValues("menu_price");
			// html 에서 넘어가는 값들은 모두다 String
			
			if(menu_nmArr == null || menu_priceArr == null) {
				return i_rest;
			}
			
			if(menu_nmArr != null && menu_priceArr != null) {
				list = new ArrayList();
				for(int i=0; i<menu_nmArr.length; i++) {
					RestaurantRecommendMenuVO vo = new RestaurantRecommendMenuVO();
					vo.setI_rest(i_rest);
					vo.setMenu_nm(menu_nmArr[i]);
					vo.setMenu_price(CommonUtils.parseStrToInt(menu_priceArr[i]));
					list.add(vo);
				}	
			}
			
			String targetPath = savePath + "/" + i_rest;
			FileUtils.makeFolder(targetPath);
			
			String originFileNm = "";
			
			Enumeration files = multi.getFileNames();
			//  hasMoreElements : 값이 있냐 없냐 확인
			while(files.hasMoreElements()) {
				// nextElement : 다음값을 가르키면서 키 값(form에서 name값)을 줌.
				String key = (String)files.nextElement();
				System.out.println("key : " + key);
				// getFilesystemName : 본래 파일의 이름을 받아옴
				// DefaultFileRenamePolicy : 파일 이름이 같더라도 올라가게끔 만듬 뒤에 + 1 씩붙음
				originFileNm = multi.getFilesystemName(key);
				System.out.println("fileNm : " + originFileNm);
				
				// 파일이 선택이 안되면 null 들어감
				if(originFileNm != null) {
					// getExt 파일의 확장자를 가져옴
					String ext = FileUtils.getExt(originFileNm);
					// saveFileNm 저장용 파일네임
					// 파일명 + 확장자
					// UUID.randomUUID() : 중복방지, 한글이나 특수문자 변경
					String saveFileNm = UUID.randomUUID() + ext;
					
					System.out.println("saveFileNm : " + saveFileNm);				
					File oldFile = new File(tempPath + "/" + originFileNm);
				    File newFile = new File(targetPath + "/" + saveFileNm);
				   // 파일이 있으면 newFile 경로로 파일을 이동시킨다
				    oldFile.renameTo(newFile);	
				    
				    int idx = CommonUtils.parseStrToInt(key.substring(key.lastIndexOf("_") + 1));				    
				    RestaurantRecommendMenuVO vo = list.get(idx);
				    vo.setMenu_pic(saveFileNm);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(list != null) {
			for(RestaurantRecommendMenuVO vo : list) {
				dao.insRecommendMenu(vo);
			}	
		}
		
		return i_rest;
	}
	
}
