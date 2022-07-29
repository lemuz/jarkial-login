package com.jarkial.login.model.dto;

import lombok.Getter;
import lombok.Setter;

public class OutPageResponse extends OutResponse{
    
    @Getter @Setter
    private int size;

    @Getter @Setter
    private Long totalCount;

    @Getter @Setter
    private int totalPages;

    @Getter @Setter
    private int pageNumber;

}
