package com.example.demo.Repository;

import com.example.demo.Model.HotLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface HotLineRepository extends JpaRepository<HotLine, Integer> {
    HotLine findHotLinesByHotlineId(int hotlineId);
}
