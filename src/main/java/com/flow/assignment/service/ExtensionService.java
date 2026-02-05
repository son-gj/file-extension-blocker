package com.flow.assignment.service;

import com.flow.assignment.entity.Extension;
import com.flow.assignment.repository.ExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtensionService {
    private final ExtensionRepository repository;

    public List<Extension> getFixedExtensions() { return repository.findByIsFixed(true); }
    public List<Extension> getCustomExtensions() { return repository.findByIsFixed(false); }

    @Transactional
    public void toggleFixed(String name, boolean checked) {
        Extension ext = repository.findByName(name);
        if (ext != null) ext.setChecked(checked);
    }

    @Transactional
    public void addCustom(String name) {
        String lowerName = name.toLowerCase();
        
        // 영문인지 검증 (정규식 사용) [cite: 6, 37]
        if (!lowerName.matches("^[a-z]+$")) throw new IllegalArgumentException("확장자는 영문만 허용됩니다.");
        //최대 200개 제한
        if (repository.countByIsFixedFalse() >= 200) throw new IllegalStateException("최대 200개까지만 가능합니다.");
        //중복 체크
        if (repository.existsByName(lowerName)) throw new IllegalArgumentException("이미 존재하는 확장자입니다.");
        
        repository.save(Extension.builder().name(lowerName).isFixed(false).build());
    }

    @Transactional
    public void deleteCustom(Long id) { repository.deleteById(id); } // 요건 3-2 [cite: 35]
}