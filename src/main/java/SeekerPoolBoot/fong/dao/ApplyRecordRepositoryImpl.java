package SeekerPoolBoot.fong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import SeekerPoolBoot.fong.vo.ApplyRecordShow;

public class ApplyRecordRepositoryImpl implements ApplyRecordOperation{
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private JdbcTemplate template;
	
	PreparedStatement pstmt = null;

	@Override
	public int findTotalCount(int memId, String keyWord, int filterNum) {

		String sql = "select count(*) from JOB J, company_member C , apply_record A where J.JOB_NO = A.JOB_NO AND C.COM_MEM_ID = A.COM_MEM_ID AND MEM_ID = ? and( J.JOB_NAME like ? or C.COM_NAME like ? )";

		if (filterNum != 100) { 
			//100就是全部顯示
			sql += " and hire_status = ? ";
			return template.queryForObject(sql, Integer.class, memId, "%" + keyWord + "%", "%" + keyWord + "%",
					filterNum);
		} else {
			return template.queryForObject(sql, Integer.class, memId, "%" + keyWord + "%", "%" + keyWord + "%");
		}

	}

	@Override
	public List<ApplyRecordShow> findByPage(int memId, int start, int pageSize, String keyWord, int filterNum) {
		String sql = " select C.COM_NAME, J.JOB_NAME, A.APPLY_DATE, A.INTER_DATE, A.HIRE_STATUS from JOB J, company_member C , apply_record A where J.JOB_NO = A.JOB_NO AND C.COM_MEM_ID = A.COM_MEM_ID AND MEM_ID = ? and( J.JOB_NAME like ? or C.COM_NAME like ? ) ";

		try (Connection con = dataSource.getConnection()) {
			// 動態拼接
			StringBuilder sb = new StringBuilder(sql);

			if (filterNum != 100) {
				sb.append(" and hire_status = ?");
			}
			sb.append(" ORDER BY A.INTER_DATE limit ? , ? ;");

			sql = sb.toString();
			pstmt = con.prepareStatement(sql);

			// 給?賦值
			pstmt.setInt(1, memId);
			pstmt.setString(2, "%" + keyWord + "%");
			pstmt.setString(3, "%" + keyWord + "%");
			if (filterNum != 100) {
				pstmt.setInt(4, filterNum);
				pstmt.setInt(5, start);
				pstmt.setInt(6, pageSize);
			} else {
				pstmt.setInt(4, start);
				pstmt.setInt(5, pageSize);
			}

			// 執行SQL
			ResultSet rs = pstmt.executeQuery();
			List<ApplyRecordShow> applyRecords = new ArrayList<ApplyRecordShow>();

			//存入applyRecords中
			while (rs.next()) {
				ApplyRecordShow applyRecord = new ApplyRecordShow();
				applyRecord.setComName(rs.getString("COM_NAME"));
				applyRecord.setJobName(rs.getString("JOB_NAME"));
				applyRecord.setApplyDate(rs.getDate("APPLY_DATE"));
				applyRecord.setInterDate(rs.getString("INTER_DATE"));
				applyRecord.setHireStatus(rs.getInt("HIRE_STATUS"));
				applyRecords.add(applyRecord);
			}
			return applyRecords;

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
			}
		}
		return null;
	}
	
}
