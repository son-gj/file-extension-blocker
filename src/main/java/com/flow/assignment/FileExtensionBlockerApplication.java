package com.flow.assignment;

import com.flow.assignment.entity.Extension;
import com.flow.assignment.repository.ExtensionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FileExtensionBlockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileExtensionBlockerApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner initData(ExtensionRepository repository) {
        return args -> {
            //차단을 자주하는 고정 확장자 리스트
            String[] fixedExtensions = {"bat", "cmd", "com", "cpl", "exe", "scr", "js"};

            for (String name : fixedExtensions) {
                //DB에 해당 이름의 확장자가 이미 있는지 확인
                if (!repository.existsByName(name)) {
                    repository.save(Extension.builder()
                            .name(name)
                            .isFixed(true)     //고정 확장자 여부 설정 
                            .isChecked(false)  //default는 unCheck 
                            .build());
                }
            }
            System.out.println("기본 고정 확장자 7개 설정 완료");
        };
    }

}
