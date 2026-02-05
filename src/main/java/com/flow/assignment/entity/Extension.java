package com.flow.assignment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Extension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 20) //최대 20자리
    private String name;

    private boolean isFixed; //고정 확장자 여부
    private boolean isChecked; //체크 상태 (고정 확장자용)
}