package com.microcore.modules.test.service;

import com.microcore.jcf.pojo.dto.base.QueryParams;
import com.microcore.jcf.service.SimpleTemplateService;
import com.microcore.modules.test.controller.params.TestPageParams;
import com.microcore.modules.test.entity.Test;
import com.microcore.modules.test.mapper.TestMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试Service服务
 *
 * @author leizhenyang
 * @date 2018/5/23
 */
@Service
public class TestService extends SimpleTemplateService<Test> {

    @Autowired
    private TestMapper mapper;

    @Override
    protected Mapper<Test> getMapper() {
        return mapper;
    }

    @Override
    protected String getModuleName() {
        return "测试Service服务";
    }

    @Override
    protected Example getPageCondition(QueryParams params) {
        TestPageParams pageParams = (TestPageParams) params;
        Example example = newInstanceExample();
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(pageParams.getName())) {
            criteria.andLike("name", "%" + pageParams.getName() + "%");
        }
        return example;
    }

    public Date getStringToDate(String str) {
        SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
     /*public static void main(String[] args) {
         List<Map<String, Object>> list = new ArrayList<>();
         RequestStatisticsService statisticsService = new RequestStatisticsService();
         if (list.size() == 0) {
             int j = 0;
             String datetType = "周";
             Date dateType1 = statisticsService.getDateCondition(datetType);
             List<Map<String, Object>> result = new ArrayList<>();
             SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/dd");
             while (j < 7) {
                 Map<String, Object> map = new HashedMap();
                 map.put(format.format(dateType1), 0);
                 result.add(map);
                 j++;
                 Calendar cal = Calendar.getInstance();
                 cal.setTime(dateType1);
                 cal.add(Calendar.DAY_OF_WEEK, 1);
                 dateType1 = cal.getTime();
             }
             System.out.println(new TestService().getStringToDate("2018-06-14 11:47:59"));

         }}
*/

          public static void main(String[] args) {
              File file = new File("D:\\uplodFile\\require\\180731171114024需求采集平台20180613154010.xlsx");
              file.delete();


           }
}
