package com.flow.assignment.controller;

import com.flow.assignment.service.ExtensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ExtensionController {

    private final ExtensionService extensionService;

    //1. 메인 화면 호출
    @GetMapping("/")
    public String index(Model model) {
        //고정 확장자 리스트 전달
        model.addAttribute("fixedExtensions", extensionService.getFixedExtensions());
        //커스텀 확장자 리스트 전달
        model.addAttribute("customExtensions", extensionService.getCustomExtensions());
        //현재 커스텀 확장자 개수 전달
        model.addAttribute("customCount", extensionService.getCustomExtensions().size());
        return "index";
    }

    //2. 고정 확장자 체크/언체크
    @PostMapping("/api/extensions/fixed")
    @ResponseBody
    public ResponseEntity<Void> toggleFixed(@RequestParam("name") String name, @RequestParam("checked") boolean checked) {
        extensionService.toggleFixed(name, checked);
        return ResponseEntity.ok().build();
    }

    //3. 커스텀 확장자 추가 (최대 200개, 중복 체크)
    @PostMapping("/api/extensions/custom")
    @ResponseBody
    public ResponseEntity<String> addCustom(@RequestParam("name") String name) {
        try {
            extensionService.addCustom(name);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            //중복이거나 200개 초과 시 에러 메시지 반환 
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //4. 커스텀 확장자 삭제 (X 클릭시 db에서 삭제)
    @DeleteMapping("/api/extensions/custom/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCustom(@PathVariable("id") Long id) {
        extensionService.deleteCustom(id);
        return ResponseEntity.ok().build();
    }
}