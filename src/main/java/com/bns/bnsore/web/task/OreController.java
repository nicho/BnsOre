package com.bns.bnsore.web.task;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.bns.bnsore.entity.Ore;
import com.bns.bnsore.entity.User;
import com.bns.bnsore.service.OreService;
import com.bns.bnsore.service.account.ShiroDbRealm.ShiroUser;
import com.google.common.collect.Maps;

/**
 * 矿石管理的Controller.
 * 
 * @author ly
 */
@Controller
@RequestMapping(value = "/ore")
public class OreController {

	private static final int PAGE_SIZE = 3;

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private OreService oreService;

	@RequestMapping(value = "")
	public String list(@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();

		Page<Ore> ores = oreService.getUserOre(userId, searchParams, pageNumber, PAGE_SIZE, sortType);
		
		model.addAttribute("ores", ores);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "task/oreList";
	}
	
	@RequestMapping(value = "{map}")
	public String list(@PathVariable("map") int map,@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();

		String map_name="";
		if(map==1)
		{
			map_name="土门阵";
		}
		else if(map==2)
		{
			map_name="烈沙地带";
		}
		else if(map==3)
		{
			map_name="陶土林";
		}
		else if(map==4)
		{
			map_name="五彩岩岛";
		}

		searchParams.put("EQ_map",map_name);
		request.setAttribute("map_name",map_name);
		
		Page<Ore> ores = oreService.getUserOre(userId, searchParams, pageNumber, PAGE_SIZE, sortType);
		
		model.addAttribute("ores", ores);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "task/oreList";
	}
 
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("ore", new Ore());
		model.addAttribute("action", "create");
		return "task/oreForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Ore newOre, RedirectAttributes redirectAttributes) {
		User user = new User(getCurrentUserId());
		newOre.setUser(user);

		oreService.saveOre(newOre);
		redirectAttributes.addFlashAttribute("message", "创建任务成功");

		String index="";
		if("土门阵".equals(newOre.getMap()))
			index="1";
		if("烈沙地带".equals(newOre.getMap()))
			index="2";
		if("陶土林".equals(newOre.getMap()))
			index="3";
		if("五彩岩岛".equals(newOre.getMap()))
			index="4";
		return "redirect:/ore/"+index;
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ore", oreService.getOre(id));
		model.addAttribute("action", "update");
		return "task/oreForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("preloadOre") Ore ore, RedirectAttributes redirectAttributes) {
		oreService.saveOre(ore);
		redirectAttributes.addFlashAttribute("message", "更新任务成功");
		String index="";
		if("土门阵".equals(ore.getMap()))
			index="1";
		if("烈沙地带".equals(ore.getMap()))
			index="2";
		if("陶土林".equals(ore.getMap()))
			index="3";
		if("五彩岩岛".equals(ore.getMap()))
			index="4";
		return "redirect:/ore/"+index;
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		Ore ore=oreService.getOre(id);
		oreService.deleteOre(id);
		redirectAttributes.addFlashAttribute("message", "删除任务成功");
		String index="";
		if("土门阵".equals(ore.getMap()))
			index="1";
		if("烈沙地带".equals(ore.getMap()))
			index="2";
		if("陶土林".equals(ore.getMap()))
			index="3";
		if("五彩岩岛".equals(ore.getMap()))
			index="4";
		return "redirect:/ore/"+index;
	}

	/**
	 * 使用@ModelAttribute, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Ore对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此本方法在该方法中执行.
	 */
	@ModelAttribute("preloadOre")
	public Ore getOre(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			return oreService.getOre(id);
		}
		return null;
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}

}
