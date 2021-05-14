package com.csslect.app.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.csslect.app.dto.ANDto;

public class ANDao {

	DataSource dataSource;

	public ANDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/team01");
			/*dataSource = (DataSource) context.lookup("java:/comp/env/CSS");*/
		} catch (NamingException e) {
			e.getMessage();
		}

	}

	public ArrayList<ANDto> anSelectMulti() {		
		
		ArrayList<ANDto> adtos = new ArrayList<ANDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select id, name, hire_date, image1, uploadtype, videoImagePath "					
							+ " from test2" 
							+ " order by id desc";
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				Date date = resultSet.getDate("hire_date"); 
				String imagePath = resultSet.getString("image1"); 
				String uploadtype = resultSet.getString("uploadtype");
				String videoImagePath = resultSet.getString("videoImagePath");

				ANDto adto = new ANDto(id, name, date, imagePath, uploadtype, videoImagePath);
				adtos.add(adto);			
			}	
			
			System.out.println("adtos크기" + adtos.size());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adtos;

	}
	
	
	public int anInsertMulti(int id, String name, String date, String dbImgPath, String uploadType, String videoImagePath) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
				
		int state = -1;

		try {			
			// 
			connection = dataSource.getConnection();
			String query = "insert into test2(id, name, hire_date, image1, uploadtype, videoImagePath) " + "values(" + id + ",'" + name + "',"
					+ "to_date('" + date + "','rr/mm/dd') , '" + dbImgPath + "', '" + uploadType + "', '" + videoImagePath + "' )";

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "삽입성공");				
			} else {
				System.out.println(state + "삽입실패");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} 

		}

		return state;

	}
	

	public int anUpdateMulti(int id, String name, String date, String dbImgPath, String uploadtype, String videoImagePath) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
	
		try {			
			// 아이디는 수정할수 없음			
			connection = dataSource.getConnection();
			String query = "update test2 set " 			             
		             + " name = '" + name + "' "
		             + ", hire_date = '" + date + "' "
		             + ", image1 = '" + dbImgPath + "' "
		             + ", uploadtype = '" + uploadtype + "' "
		             + ", videoImagePath = '" + videoImagePath + "' "
					 + " where id = " + id ;
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
	
			if (state > 0) {
				System.out.println("수정1성공");
				
			} else {
				System.out.println("수정1실패");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	
			}
		}
	
		return state;
	
	}
	
	public int anUpdateMultiNo(int id, String name, String date) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
	
		try {			
			// 아이디는 수정할수 없음			
			connection = dataSource.getConnection();
			String query = "update test2 set " 			             
		             + " name = '" + name + "' "
		             + ", hire_date = '" + date + "' "		             
					 + " where id = " + id ;
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
	
			if (state > 0) {
				System.out.println("수정2성공");
				
			} else {
				System.out.println("수정2실패");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	
			}
		}
	
		return state;
	}
	
	public int anDeleteMulti(int id) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;

		try {
			connection = dataSource.getConnection();
			String query = "delete from test2 where id=" + id;
			
			System.out.println(id);

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("삭제성공");				
			} else {
				System.out.println("삭제실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return state;

	}

		
	
	
}
