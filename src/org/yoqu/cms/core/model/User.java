package org.yoqu.cms.core.model;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import org.yoqu.cms.core.Constant.Constant;
import org.yoqu.cms.core.Constant.SystemVariable;
import org.yoqu.cms.core.model.base.BaseUser;
import org.yoqu.cms.core.util.SqlHandle;

import java.sql.SQLException;
import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class User extends BaseUser<User> {
	public static final User dao = new User();


	public List<User> finduserByNamePasswordOrName(String... parameters){
		if (parameters.length==2){
			return find("select * from user where name=? and password=? and is_delete=0",parameters);
		}
		else if(parameters.length==1){
			return find("select * from user where name=? and is_delete=0",parameters);
		}else if(parameters.length==0){
			return find("select * from user where is_delete=0");
		}
		else return null;
	}
	public Role getRole(){
		return Role.dao.findById(getRid());
	}
	public Page<User> findUserByPage(int pageNumber){
		return paginate(pageNumber, Integer.parseInt(SystemVariable.get(Constant.PAGE_SIZE).trim()),"select *","from user where is_delete=0 order by createDate desc");
	}

	public void softDelete(int uid) throws SQLException {
		SqlHandle sqlHandle=new SqlHandle(SqlHandle.OPERATES[3],"user");
		sqlHandle.OPERATEFILED("id",uid);
		try {
		Db.queryNumber(sqlHandle.toString());
		}catch (Exception ex){
			throw new SQLException(ex.getMessage());
		}
	}
	@Override
	public String toString() {
		return "ID:"+getId()+"Name: "+getName()+" Password: "+getPassword()+" createDate: "+getCreateDate()+" lastDate:"+getLastDate()+"role: "+getRole();
	}
}