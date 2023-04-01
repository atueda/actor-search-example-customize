package com.example.actor.web;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.actor.repository.Actor;
import com.example.actor.repository.ActorRepository;
import com.example.actor.repository.Prefecture;
import com.example.actor.repository.PrefectureRepository;

@Controller
public class ActorController {
  final static Logger logger = LoggerFactory.getLogger(ActorController.class);

  @Autowired
  ActorRepository actorRepository;

  @Autowired
  PrefectureRepository prefectureRepository;

  @Autowired
  MessageSource msg;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    sdf.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
  }

  @GetMapping("/login")
  public String login(Model model) {
    logger.debug("Actor + login");
    return "/login";
  }
  
  @GetMapping("/actor")
  public String index(Model model) {
    logger.debug("Actor + index");
    List<Actor> list = actorRepository.findAll();
    if (CollectionUtils.isEmpty(list)) {
      String message = msg.getMessage("actor.list.empty", null, Locale.JAPAN);
      model.addAttribute("emptyMessage", message);
    }
    model.addAttribute("list", list);
    modelDump(model, "index");
    return "Actor/index";
  }

  @GetMapping("/actor/{id}")
  public ModelAndView detail(@PathVariable Integer id) {
	logger.debug("Actor + detail");
	ModelAndView mv = new ModelAndView();
	try {
	    mv.setViewName("Actor/detail");
	    Optional<Actor> actor = actorRepository.findById(id);
	    mv.addObject("actor", actor.get());
	} catch (Exception e) {
		logger.debug("Exception:" + e.getStackTrace());
	}
    return mv;

  }

  @GetMapping("/actor/search")
  public ModelAndView search(@RequestParam String keyword) {
    logger.debug("Actor + search");
    ModelAndView mv = new ModelAndView();
    mv.setViewName("Actor/index");
    if (StringUtils.isNotEmpty(keyword)) {
      List<Actor> list = actorRepository.findActors(keyword);
      if (CollectionUtils.isEmpty(list)) {
        String message = msg.getMessage("actor.list.empty", null, Locale.JAPAN);
        mv.addObject("emptyMessage", message);
      }
      mv.addObject("list", list);
    }
    return mv;
  }

  @GetMapping("/actor/create")
  public String create(ActorForm form, Model model) {
    logger.debug("Actor + create");
    List<Prefecture> pref = prefectureRepository.findAll();
    model.addAttribute("pref", pref);
    modelDump(model, "create");
    return "Actor/create";
  }

  @PostMapping("/actor/save")
  public String save(@Validated @ModelAttribute ActorForm form, BindingResult result, Model model) {
    logger.debug("Actor + save");
    if (result.hasErrors()) {
      String message = msg.getMessage("actor.validation.error", null, Locale.JAPAN);
      model.addAttribute("errorMessage", message);
      return create(form, model);
    }
    Actor actor = convert(form);
    logger.debug("actor:{}", actor.toString());
    actor = actorRepository.saveAndFlush(actor);
    modelDump(model, "save");
    return "redirect:/actor/" + actor.getId().toString();
  }

  @GetMapping("/actor/delete/{id}")
  public String delete(@PathVariable Integer id, RedirectAttributes attributes, Model model) {
    logger.debug("Actor + delete");
    actorRepository.deleteById(id);
    attributes.addFlashAttribute("deleteMessage", "delete ID:" + id);
    return "redirect:/actor";
  }

  /**
   * convert form to model.
   */
  private Actor convert(ActorForm form) {
    Actor actor = new Actor();
    actor.setName(form.getName());
    if (StringUtils.isNotEmpty(form.getHeight())) {
      actor.setHeight(Integer.valueOf(form.getHeight()));
    }
    if (StringUtils.isNotEmpty(form.getBlood())) {
      actor.setBlood(form.getBlood());
    }
    if (StringUtils.isNotEmpty(form.getBirthday())) {
      DateTimeFormatter withoutZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      LocalDateTime parsed = LocalDateTime.parse(form.getBirthday() + " 00:00:00", withoutZone);
      Instant instant = parsed.toInstant(ZoneOffset.ofHours(9));
      actor.setBirthday(Date.from(instant));
    }
    if (StringUtils.isNotEmpty(form.getBirthplaceId())) {
      actor.setBirthplaceId(Integer.valueOf(form.getBirthplaceId()));
    }
    actor.setUpdateAt(new Date());
    return actor;
  }

  /**
   * for debug.
   */
  private void modelDump(Model model, String m) {
    logger.debug(" ");
    logger.debug("Model:{}", m);
    Map<String, Object> mm = model.asMap();
    for (Entry<String, Object> entry : mm.entrySet()) {
      logger.debug("key:{}, value:{}", entry.getKey(), entry.getValue().toString());
    }
  }

}
