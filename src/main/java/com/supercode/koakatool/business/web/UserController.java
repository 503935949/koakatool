package com.supercode.koakatool.business.web;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supercode.koakatool.business.domain.UserDomain;
import com.supercode.koakatool.business.service.IUserService;
import com.supercode.koakatool.business.service.query.UserQuery;
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
@RequestMapping("/user")
@Api(tags = "user",description = "用户管理相关接口",produces="application/json")
public class UserController  {

	@Autowired
	private IUserService service;
	


	@GetMapping(value = "/allUsers" )
	@ResponseBody
	public List<UserDomain> findByWhereForList(UserQuery query){
		return service.findByWhereForList(query);
	}

	/**
	 * @Description:
	 * @Author: zhao.lin
	 * @Date: 2018/10/10 9:54
	 * @Param: [query]
	 * @Return: com.github.pagehelper.PageInfo<com.supercode.koakatool.business.domain.UserDomain>
	 */
	@ApiOperation(value="获取用户列表接口", notes="分页查询",produces="application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "job_number", value = "工号", required = false, dataType = "String"),
			@ApiImplicitParam(name = "name", value = "姓名", required = false, dataType = "String")

	})
	@GetMapping(value = "/users")
	@ResponseBody
	public PageInfo<UserDomain> findByWhereForPage(UserQuery query){

		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		List<UserDomain> list = service.findByWhereForList(query);
		PageInfo<UserDomain> pi = new PageInfo<UserDomain>(list);
    	return pi;
	}

	/**
	 * @return DEMO_LIST 界面
	 */
	@RequestMapping(value = "/")
	public String userPage(){
		return "pages/auth/user/userIndex";
	}

    @PutMapping(value = "/add")
    @ResponseBody
    public Response add(UserDomain query){

	    if(StringUtils.isEmpty(query.getJob_number())){
            return Response.ErrorResponse();
        }
        List<UserDomain> listj = service.findByWhereForList(new UserQuery(query.getJob_number()));
        if(!CollectionUtils.isEmpty(listj)){
            return Response.ErrorResponse(query.getJob_number()+"已存在");
        }

		List<UserDomain> listn = service.findByWhereForList(new UserQuery().setName(query.getName()));
		if(!CollectionUtils.isEmpty(listn)){
			return Response.ErrorResponse(query.getName()+"已存在");
		}

		service.insert(query);
        return Response.SuccessResponse();
    }

	@PostMapping(value = "/edit")
    @ResponseBody
	public Response edit(UserDomain query){

		List<UserDomain> listj = service.findByWhereForList(new UserQuery(query.getJob_number()));
		if(CollectionUtils.isEmpty(listj)){
			return Response.ErrorResponse(query.getJob_number()+"不存在");
		}

		List<UserDomain> listn = service.findByWhereForList(new UserQuery().setName(query.getName()));
		if(!CollectionUtils.isEmpty(listn)){
			return Response.ErrorResponse(query.getName()+"已存在");
		}

		service.updateByKey(query);
        return Response.SuccessResponse();
	}

    @DeleteMapping(value = "/remove")
    @ResponseBody
    public Response remove(UserDomain query){

        List<UserDomain> listj = service.findByWhereForList(new UserQuery(query.getJob_number()));
		if(CollectionUtils.isEmpty(listj)){
			return Response.ErrorResponse(query.getJob_number()+"不存在");
		}

		service.deleteByKey(query);
        return Response.SuccessResponse();
    }
}
