package com.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.spring.beans.Login;
import com.spring.beans.User;


public class UserDAOImpl implements UserDAO
{
	@Autowired
	DataSource datasource;
	
	@Autowired
	JdbcTemplate template;
	
	//@Autowired
	//NamedParameterJdbcTemplate nTemplate;
	
	
	public void register(User user) 
	{
		
		 String sql = "insert into users values(?,?,?,?,?,?,?,?,?)";
		 
		 template.update(sql, new Object[] {user.getId(),user.getUsername(), user.getPassword(), user.getFirstname(),
				    user.getLastname(), user.getEmail(), user.getAddress(), user.getPhone(),LocalDateTime.now()});
		
	}

	
	public User validateUser(Login login) {
		
		String sql="select * from users where username='" + login.getUsername() + "' and password='" + login.getPassword() + "'";
		
		 List<User> users = template.query(sql, new UserMapper());
		
		 return users.size() > 0 ? users.get(0) : null;
	}


	public List<User> getUsers()
	{
		
		String sql="select id,username,password,firstname,lastname,email,address,phone,date_format(createddate, '%d-%m-%Y') as createddate from users";
		List<User> list=template.query(sql,new UserMapper());
		return list;
	}


	@Override
	public int delete(int id)
	{
		
		String sql="delete from users where id="+id+"";    
		
	    return template.update(sql);
		
	}


	@Override
	public int update(User user) 
	{
		    String sql="update users set username='"+user.getUsername()+"', password='"+user.getPassword()+"',firstname='"+user.getFirstname()+"' ,lastname='"+user.getLastname()+"' ,email='"+user.getEmail()+"' ,address='"+user.getAddress()+"' ,phone='"+user.getPhone()+"' where id="+user.getId()+"";    
		    return template.update(sql);    
	}


	@Override
	public User getUserById(int id) 
	{
		String sql="select * from users where id=?";    
	    return template.queryForObject(sql, new Object[]{id},new BeanPropertyRowMapper<User>(User.class)); 
	}


	@Override
	public User unamecheck(User user) 
	{
		String sql="select * from users where username='"+ user.getUsername() + "'";
		
		 List<User> users = template.query(sql, new UserMapper());
		
		 return users.size() > 0 ? users.get(0) : null;
	}


	
}
	

class UserMapper implements RowMapper<User> 
{
	  public User mapRow(ResultSet rs, int arg1) throws SQLException 
	  {
	    User user = new User();
	    user.setId(rs.getInt("id"));
	    user.setUsername(rs.getString("username"));
	    user.setPassword(rs.getString("password"));
	    user.setFirstname(rs.getString("firstname"));
	    user.setLastname(rs.getString("lastname"));
	    user.setEmail(rs.getString("email"));
	    user.setAddress(rs.getString("address"));
	    user.setPhone(rs.getString("phone"));
	    user.setCreateddate(rs.getString("createddate"));
	    return user;
	  }
}


