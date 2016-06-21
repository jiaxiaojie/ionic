package com.thinkgem.jeesite.modules.cms.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.thinkgem.jeesite.common.utils.StringUtils;

public class ArrayTypeHandler extends BaseTypeHandler<Object[]>  {
	private String separator = ",";
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object[] parameter,
            JdbcType jdbcType) throws SQLException {
        
        ps.setString(i, new StringBuffer().append(separator).append(StringUtils.toString(parameter, separator)).append(separator).toString());
    }

    @Override
    public Object[] getNullableResult(ResultSet rs, String columnName)
            throws SQLException {

        return StringUtils.toArray(rs.getString(columnName));
    }

    @Override
    public Object[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        return StringUtils.toArray(rs.getString(columnIndex));
    }

    @Override
    public Object[] getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {

        return StringUtils.toArray(cs.getString(columnIndex));
    }
    
  
}
