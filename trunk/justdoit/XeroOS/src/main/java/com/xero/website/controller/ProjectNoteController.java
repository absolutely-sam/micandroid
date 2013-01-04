package com.xero.website.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xero.admin.util.DateUtil;
import com.xero.core.Response.ResponseCollection;
import com.xero.core.Response.ResponseEntity;
import com.xero.core.util.encode.EncodeUtil;
import com.xero.website.bean.Project;
import com.xero.website.bean.ProjectNote;
import com.xero.website.service.ProjectNoteService;
import com.xero.website.service.ProjectService;

@Controller
public class ProjectNoteController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private ProjectNoteService projectNoteService;

	@Resource
	private ProjectService projectService;
	
	
	@RequestMapping(value = "/update-customer", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ProjectNote> updateShowCustomer(
			HttpServletRequest request,
			@RequestParam("noteId") Integer noteId,
			@RequestParam("showCustomer") Integer showCustomer) {
		ResponseEntity<ProjectNote> res = new ResponseEntity<ProjectNote>(false);
		try {
			ProjectNote pNote = projectNoteService.get(noteId) ;
			if(null != pNote){
				boolean isShowCustomer = false;
				if (null != showCustomer && showCustomer == 1) {
					isShowCustomer = true;
				}
				pNote.setShowCustomer(isShowCustomer) ;
				pNote = projectNoteService.saveOrUpdate(pNote) ;
				
				res.setData(pNote) ;
				res.setResult(true) ;
			}else{
				res.setResult(false);
				res.setData(null);
			
			}
		} catch (Exception ex) {
			logger.error("Update Project Note Error On Controller", ex);
			res.setResult(false);
			res.setData(null);
		}
		return res;
	}
	
	
	@RequestMapping(value = "/note-add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ProjectNote> doAdd(
			HttpServletRequest request,
			@RequestParam("projectId") Integer projectId,
			@RequestParam("userId") Integer userId,
			@RequestParam("content") String noteContent,
			@RequestParam("landmarkDate") String landmarkDate,
			@RequestParam("showCustomer") Integer showCustomer,
			@RequestParam(required = false, value = "supplierId") Integer supplierId) {
		ResponseEntity<ProjectNote> res = new ResponseEntity<ProjectNote>(false);
		try {
			ProjectNote pNote = new ProjectNote();

			Date tempDate = (null == landmarkDate) ? new Date() : DateUtil
					.strToEnDate(landmarkDate);
			boolean isShowCustomer = false;
			if (null != showCustomer && showCustomer == 1) {
				isShowCustomer = true;
			}
			pNote.setContent(noteContent);
			pNote.setProjectId(projectId);
			pNote.setUserId(userId);
			pNote.setLandmarkDate(tempDate);
			pNote.setShowCustomer(isShowCustomer);
			pNote.setCreateDateTime(new Date());
			pNote.setSupplierId(supplierId);

			pNote = projectNoteService.saveOrUpdate(pNote);

			res.setData(pNote);
			res.setResult(true);

		} catch (Exception ex) {
			logger.error("Save Project Note Error On Controller", ex);
			res.setResult(false);
			res.setData(null);
		}
		return res;
	}

	@RequestMapping(value = "/note-list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCollection<ProjectNote> toList(HttpServletRequest request,
			@RequestParam("projectId") Integer projectId) {
		ResponseCollection<ProjectNote> res = new ResponseCollection<ProjectNote>();
		try {
			res = projectNoteService.getNotesByProjectId(projectId);
		} catch (Exception ex) {
			logger.error("Save Project Note Error On Controller", ex);
			res.setResult(false);
			res.setData(null);
		}
		return res;
	}

	@RequestMapping(value = "/supplier/{dataEncode}", method = RequestMethod.GET)
	public ModelAndView toSupplier(HttpServletRequest request,
			@PathVariable("dataEncode") String dataEncode) {
		ModelAndView model = new ModelAndView();
		try {
			if (null != dataEncode && dataEncode.length() > 0) {
				byte[] data = EncodeUtil.base64Decode(dataEncode);

				String newData = new String(data);
				String[] dataArry = newData.split(":");
				if (null != dataArry && dataArry.length > 0) {
					model.addObject("supplierId", dataArry[0]);

					String tempProId = dataArry[1];
					Integer projectId = (tempProId == null || !StringUtils
							.isNumeric(tempProId)) ? 0 : Integer
							.valueOf(tempProId);
					Project p = projectService.get(projectId);
					if (null != p) {
						Date tempSDate = p.getStartDate();
						Date tempEDate = p.getEndDate();

						tempSDate = (tempSDate == null) ? new Date()
								: tempSDate;
						tempEDate = (tempEDate == null) ? new Date()
								: tempEDate;

						String sDate = DateUtil.dateToString(tempSDate);
						String eDate = DateUtil.dateToString(tempEDate);

						model.addObject("sDate", sDate);
						model.addObject("eDate", eDate);
						model.addObject("project", p);
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Format Error", ex);
		}
		model.setViewName("/project_detail_supply");
		return model;
	}
}