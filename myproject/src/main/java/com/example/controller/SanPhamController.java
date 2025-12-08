package com.example.controller;


import com.example.model.Sanpham;
import com.example.repository.SanPhamReponsitory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sanpham")
public class SanPhamController {
    private final SanPhamReponsitory sanPhamReponsitory;
    public SanPhamController (SanPhamReponsitory sanPhamReponsitory) {
        this.sanPhamReponsitory = sanPhamReponsitory;
    }
    @GetMapping
    public List<Sanpham> getAll(){
        return sanPhamReponsitory.findAll();
    }

    @PostMapping
    public Sanpham create(@RequestBody Sanpham sanpham){
        return sanPhamReponsitory.save(sanpham);
    }
}
