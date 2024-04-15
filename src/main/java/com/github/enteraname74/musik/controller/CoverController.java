package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.domain.service.CoverService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cover")
public class CoverController {

    private final CoverService coverService;

    @Autowired
    public CoverController(CoverService coverService) {
        this.coverService = coverService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {

        ServiceResult<?> result = coverService.getById(id);

        if (result.getHttpStatus().is2xxSuccessful()) {
            byte[] array = (byte[]) result.getResult();

            ByteArrayResource resource = new ByteArrayResource(array);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(resource.contentLength())
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            ContentDisposition.attachment()
                                    .filename(id+".png")
                                    .build().toString())
                    .body(resource);
        }

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }

}
