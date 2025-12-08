package com.example.repository;

import com.example.model.Sanpham;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SanPhamReponsitory extends JpaRepository<Sanpham, Long> {
}
