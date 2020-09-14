package com.koreait.matzip.restaurant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.matzip.db.JdbcSelectInterface;
import com.koreait.matzip.db.JdbcTemplate;
import com.koreait.matzip.db.JdbcUpdateInterface;
import com.koreait.matzip.vo.RestaurantDomain;
import com.koreait.matzip.vo.RestaurantVO;

public class RestaurantDAO {

	public static int insRestaurant(RestaurantVO param) {
		
		String sql = " INSERT INTO t_restaurant (nm, addr, lat, lng, cd_category, i_user) " 
				+ " VALUES (?,?,?,?,?,?) ";
		
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface(){

			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getNm());
				ps.setNString(2, param.getAddr());
				ps.setDouble(3, param.getLat());
				ps.setDouble(4, param.getLng());
				ps.setInt(5, param.getCd_category());
				ps.setInt(6, param.getI_user());
			}});
	}
	
	
	public List<RestaurantDomain> selRestList() {
		List<RestaurantDomain> list = new ArrayList();
		
		String sql = " SELECT i_rest, nm, lat, lng FROM t_restaurant ";
		
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {}

			@Override
			public void executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					RestaurantDomain vo = new RestaurantDomain();
					vo.setI_rest(rs.getInt("i_rest"));
					vo.setNm(rs.getNString("nm"));
					vo.setLat(rs.getDouble("lat"));
					vo.setLng(rs.getDouble("lng"));
					
					list.add(vo);
				}
				
			}});
		
		return list;
	}
	
	
	public static int selRestDetail(RestaurantDomain param) {
		
		String sql = " SELECT A.i_rest, A.i_user, A.nm, A.addr, C.val as cd_category_nm"
				+ " FROM t_restaurant A "
				+ " LEFT JOIN t_user B "
				+ " ON A.i_user = B.i_user "
				+ " LEFT JOIN c_code_d C "
				+ " ON A.cd_category = C.cd "
				+ " WHERE A.i_rest = ? ";
		
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface(){

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_rest());
				
			}

			@Override
			public void executeQuery(ResultSet rs) throws SQLException {
				if(rs.next()) {
					param.setI_rest(rs.getInt("i_rest"));
					param.setI_user(rs.getInt("i_user"));
					param.setNm(rs.getString("nm"));
					param.setAddr(rs.getString("addr"));
					param.setCd_category_nm(rs.getString("cd_category_nm"));
				}
			}});
		
		return 0;
	}
	
}
