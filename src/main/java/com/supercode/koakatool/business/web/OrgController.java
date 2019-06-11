package com.supercode.koakatool.business.web;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supercode.koakatool.business.domain.OrgDomain;
import com.supercode.koakatool.business.service.IOrgService;
import com.supercode.koakatool.business.service.query.OrgQuery;
import com.supercode.koakatool.system.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
*    
* 项目名称：
* 类名称：
* 类描述：   
* 创建人：林曌   
* 创建时间：
* 修改人：   
* 修改时间：
* 修改备注：   
* @version
 * swagger通过注解表明该接口会生成文档，包括接口名、请求方法、参数、返回信息的等等。
 *
 * @Api：修饰整个类，描述Controller的作用
 * @ApiOperation：描述一个类的一个方法，或者说一个接口
 * @ApiParam：单个参数描述
 * @ApiModel：用对象来接收参数
 * @ApiProperty：用对象接收参数时，描述对象的一个字段
 * @ApiResponse：HTTP响应其中1个描述
 * @ApiResponses：HTTP响应整体描述
 * @ApiIgnore：使用该注解忽略这个API
 * @ApiError ：发生错误返回的信息
 * @ApiImplicitParam：一个请求参数
 * @ApiImplicitParams：多个请求参数
*    
*/
@Controller
@RequestMapping("/org")
@Api(tags = "org",description = "用户管理相关接口",produces="application/json")
public class OrgController  {

	@Autowired
	private IOrgService service;
	


	@GetMapping(value = "/allOrgs" )
	@ResponseBody
	public List<OrgDomain> findByWhereForList(OrgQuery query){
		return service.findByWhereForList(query);
	}

	/**
	 * @Description:
	 * @Author: zhao.lin
	 * @Date: 2018/10/10 9:54
	 * @Param: [query]
	 * @Return: com.github.pagehelper.PageInfo<com.supercode.koakatool.business.domain.OrgDomain>
	 */
	@ApiOperation(value="获取用户列表接口", notes="分页查询",produces="application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "org_id", value = "工号", required = false, dataType = "String"),
			@ApiImplicitParam(name = "name", value = "姓名", required = false, dataType = "String")

	})
	@GetMapping(value = "/orgs")
	@ResponseBody
	public PageInfo<OrgDomain> findByWhereForPage(OrgQuery query){

		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		List<OrgDomain> list = service.findByWhereForList(query);
		PageInfo<OrgDomain> pi = new PageInfo<OrgDomain>(list);
    	return pi;
	}

	/**
	 * @return DEMO_LIST 界面
	 */
	@RequestMapping(value = "/")
	public String orgPage(){
		return "pages/auth/org/orgIndex";
	}

    @PutMapping(value = "/add")
    @ResponseBody
    public Response add(OrgDomain query){

	    if(StringUtils.isEmpty(query.getOrg_id())){
            return Response.ErrorResponse();
        }
        List<OrgDomain> listj = service.findByWhereForList(new OrgQuery(query.getOrg_id()));
        if(!CollectionUtils.isEmpty(listj)){
            return Response.ErrorResponse(query.getOrg_id()+"已存在");
        }

		List<OrgDomain> listn = service.findByWhereForList(new OrgQuery().setName(query.getName()));
		if(!CollectionUtils.isEmpty(listn)){
			return Response.ErrorResponse(query.getName()+"已存在");
		}

		service.insert(query);
        return Response.SuccessResponse();
    }

	@PostMapping(value = "/edit")
    @ResponseBody
	public Response edit(OrgDomain query){

		List<OrgDomain> listj = service.findByWhereForList(new OrgQuery(query.getOrg_id()));
		if(CollectionUtils.isEmpty(listj)){
			return Response.ErrorResponse(query.getOrg_id()+"不存在");
		}

		List<OrgDomain> listn = service.findByWhereForList(new OrgQuery().setName(query.getName()));
		if(!CollectionUtils.isEmpty(listn)){
			return Response.ErrorResponse(query.getName()+"已存在");
		}

		service.updateByKey(query);
        return Response.SuccessResponse();
	}

    @DeleteMapping(value = "/remove")
    @ResponseBody
    public Response remove(OrgDomain query){

        List<OrgDomain> listj = service.findByWhereForList(new OrgQuery(query.getOrg_id()));
		if(CollectionUtils.isEmpty(listj)){
			return Response.ErrorResponse(query.getOrg_id()+"不存在");
		}

		service.deleteByKey(query);
        return Response.SuccessResponse();
    }
}
